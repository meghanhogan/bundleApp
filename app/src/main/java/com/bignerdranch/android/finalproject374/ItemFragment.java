package com.bignerdranch.android.finalproject374;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemFragment extends Fragment {

    private static final String ARG_ITEM_ID = "item_id";
    private static final String DIALOG_SELECTOR = "DialogSelector";
    private static final int REQUEST_STATUS = 0;

    private Item mItem;
    private EditText mItemName;
    private EditText mItemPrice;
    private Button mStatusSelector;
    private int mStartOf1; //keeps track of where 'running low' items start in the list
    private int mStartOf2; //keeps track of where 'all out' items start
    private Button mDoneButton;


    public static ItemFragment newInstance(UUID itemId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_ID, itemId);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID ItemId = (UUID) getArguments().getSerializable(ARG_ITEM_ID);
        mItem =  ItemGen.get(getActivity()).getItem(ItemId);
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        ItemGen.get(getActivity()).updateItem(mItem);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        mItemName = (EditText)v.findViewById(R.id.item_name);
        mItemName.setText(mItem.getName());
        mItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mItem.setName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mItemPrice = (EditText)v.findViewById(R.id.item_price);
        mItemPrice.setText(mItem.getPrice());
        mItemPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                String input = s.toString();
                //check for valid input
                mItem.setPrice(s.toString());

            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mStatusSelector = (Button) v.findViewById(R.id.status_selector);
        mStatusSelector.setText(mItem.statusString());
        mStatusSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                StatusSelectorFragment dialog = StatusSelectorFragment.newInstance(mItem.statusString());
                dialog.setTargetFragment(ItemFragment.this, REQUEST_STATUS);
                dialog.show(manager, DIALOG_SELECTOR);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return; }
        if (requestCode == REQUEST_STATUS) {
            String status = (String) data
                    .getSerializableExtra(StatusSelectorFragment.EXTRA_STATUS);
            mItem.setStatus(mItem.convertStatus(status));
            mStatusSelector.setText(status);
            moveItem();
        }
    }

    public void moveItem(){
        if (mItem.getStatus() == 0){
            if (mItem.getPos() > mStartOf1){
                //move up
            }
        }
        if (mItem.getStatus() == 1){
            if(mItem.getPos() > mStartOf1){
                //move down
            }
            else if(mItem.getPos() > mStartOf2){
                //move up
            }
        }
        if (mItem.getStatus() == 3){
            if(mItem.getPos() < mStartOf2){
                //move down
            }
        }
    }


}
