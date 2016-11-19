package com.bignerdranch.android.finalproject374;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemFragment extends Fragment {
    private Item mItem;
    private EditText mItemName;
    private EditText mItemPrice;
    private Spinner mStatusSelector;
    private int mStartOf1 = -1; //keeps track of where 'running low' items start in the list
    private int mStartOf2 = -1; //keeps track of where 'all out' items start
                                //initialized to -1 so we know if its the first time an item of said status is added

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = new Item();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        mItemName = (EditText)v.findViewById(R.id.item_name);
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
        mItemPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                Double price = Double.parseDouble(s.toString());
                mItem.setPrice(price);

            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mStatusSelector = (Spinner)v.findViewById(R.id.status_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mStatusSelector.setAdapter(adapter);

        mStatusSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                String selectedItem = parent.getSelectedItem().toString();
    /*
                find a way to read selections~!
                when selected swap item's position in the list
                ItemGen.getItems --> update order

                items = ItemGen.getItems()
                if(selectedItem == "In Stock"){
                    check if new status is different than old status
                    mItem.setStatus(0);
                    if new status is different than old:
                        item.getPos()
                        check to see if Pos > mStartOf1
                            if not, move to correct pos

                }
                else if(selectedItem == "Running Low"){
                    check to see if new is dif than old
                    mItem.setStatus(1);
                    if new is diff:
                        item.getPos()
                        check to see if mStartOf1 <= pos < StartOf2
                            if not, move to correct pos
                }
                else if(selectedItem == "All Out"){
                    check to see if new is diff than old
                    mItem.setStatus(2);
                    if new is diff:
                        item.getPos()
                        check to see if pos >= startOf2
                            if not, move to correct pos
                }
                System.out.println("item status is " + mItem.getStatus());
    */


            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        return v;
    }


}
