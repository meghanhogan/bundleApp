package com.bignerdranch.android.finalproject374.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.finalproject374.Item;
import com.bignerdranch.android.finalproject374.database.ItemDbSchema.ItemTable;

import java.util.UUID;


/**
 * Created by meghanhogan on 11/29/16.
 */

public class ItemCursorWrapper extends CursorWrapper{
    public ItemCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Item getItem() {
        String uuidString = getString(getColumnIndex(ItemTable.Cols.UUID));
        String name = getString(getColumnIndex(ItemTable.Cols.Name));
        int status = getInt(getColumnIndex(ItemTable.Cols.Status));
        String price = getString(getColumnIndex(ItemTable.Cols.Price));

        Item item = new Item(UUID.fromString(uuidString));
        item.setName(name);
        item.setStatus(status);
        item.setPrice(price);

        return null;
    }
}
