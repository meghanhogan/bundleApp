package com.bignerdranch.android.finalproject374;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.attr.id;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemGen {

    private static ItemGen sItemGen;


    private List<Item> mItems;

    public static ItemGen get(Context context) {
        if (sItemGen == null) {
            sItemGen = new ItemGen(context);
        }
        return sItemGen;
    }
    private ItemGen(Context context) {
        mItems = new ArrayList<>();
    }

    public void addItem(Item i){
        mItems.add(i);
        i.setPos(mItems.size()-1);
    }

    public List<Item> getItems(){
        return mItems;
    }

    public Item getItem(UUID id){
        for (Item item : mItems){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

}