package com.bignerdranch.android.finalproject374;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.bignerdranch.android.finalproject374.ItemListActivity;
import com.bignerdranch.android.finalproject374.R;

/**
 * Created by meghanhogan on 12/9/16.
 */

public class EmptyBundleFragment extends DialogFragment {

//dialog appears when user attempts to create an empty bundle. Click ok to dismiss

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.empty_bundle);
        builder.setNegativeButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EmptyBundleFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
