package com.bignerdranch.android.finalproject374;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * Created by meghanhogan on 11/20/16.
 */

public class StatusSelectorFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{

    //class for setting up spinner dialog

    public static final String EXTRA_STATUS =
            "com.bignerdranch.android.criminalintent.date";

    private static final String ARG_STATUS = "status";

    public String mStatus;

    public Spinner mStatusSelector;
    public static StatusSelectorFragment newInstance(String status) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STATUS, status);
        StatusSelectorFragment fragment = new StatusSelectorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String status = (String) getArguments().getSerializable(ARG_STATUS);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_selector, null);

        mStatusSelector = (Spinner)v.findViewById(R.id.status_selector_dialog);
        mStatusSelector.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStatusSelector.setAdapter(adapter);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.select_status)
                .setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, mStatus);
                    }
                    })
                .create();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        mStatus = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void sendResult(int resultCode, String status) {
        if (getTargetFragment() == null) {
            return; }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STATUS, status);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
