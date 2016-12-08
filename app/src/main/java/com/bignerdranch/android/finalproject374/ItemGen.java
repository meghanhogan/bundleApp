package com.bignerdranch.android.finalproject374;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.finalproject374.database.ItemBaseHelper;
import com.bignerdranch.android.finalproject374.database.ItemCursorWrapper;
import com.bignerdranch.android.finalproject374.database.ItemDbSchema;
import com.bignerdranch.android.finalproject374.database.ItemDbSchema.ItemTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.attr.id;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemGen {

    private static ItemGen sItemGen;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ItemGen get(Context context) {
        if (sItemGen == null) {
            sItemGen = new ItemGen(context);
        }
        return sItemGen;
    }
    private ItemGen(Context context) {

        mContext = context.getApplicationContext();
        mDatabase=new ItemBaseHelper(mContext).getWritableDatabase();

    }

    public void addItem(Item i){
        //add item to database
        ContentValues values = getContentValues(i);
        mDatabase.insert(ItemTable.NAME, null, values);
    }

    public List<Item> getItems(){
        //return mItems
        List<Item> items = new ArrayList<>();

        ItemCursorWrapper cursor = queryItems(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    public Item getItem(UUID id){
        //return item from ItemCursorWrapper
        ItemCursorWrapper cursor = queryItems(ItemTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );
        try{
            if (cursor.getCount()==0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getItem();
        } finally {
            cursor.close();
        }
    }

    public void removeItem(Item item){
        //remove item from the database
        String stringUUID = item.getId().toString();
        mDatabase.delete(ItemTable.NAME,
                ItemTable.Cols.UUID + " = ?",
                new String[] {stringUUID});
    }

    public void updateItem(Item item){
        //update item in the database
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);

        mDatabase.update(ItemTable.NAME, values, ItemTable.Cols.UUID + "= ?", new String[] {uuidString});
    }

    private static ContentValues getContentValues(Item item){
        //get content values for item
        ContentValues values = new ContentValues();

        values.put(ItemTable.Cols.UUID, item.getId().toString());
        values.put(ItemTable.Cols.Name, item.getName());
        values.put(ItemTable.Cols.Status, item.getStatus());
        values.put(ItemTable.Cols.Price, item.getPrice());

        return values;
    }

    private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs){
        //create an item cursor wrapepr
        Cursor cursor =mDatabase.query(
                ItemTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new ItemCursorWrapper(cursor);
    }

}