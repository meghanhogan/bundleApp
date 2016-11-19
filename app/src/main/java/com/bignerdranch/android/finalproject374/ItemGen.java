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
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setName("Test item #" + i);
            item.setPos(i);
            item.setStatus(i%3);
            mItems.add(item);
        }
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

