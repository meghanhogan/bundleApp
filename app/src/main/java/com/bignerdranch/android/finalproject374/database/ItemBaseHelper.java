package com.bignerdranch.android.finalproject374.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.finalproject374.Member;
import com.bignerdranch.android.finalproject374.database.ItemDbSchema.ItemTable;
import com.bignerdranch.android.finalproject374.database.ItemDbSchema.MemberTable;

/**
 * Created by meghanhogan on 11/29/16.
 */

public class ItemBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "itemBase.db";

    public ItemBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    //creates tables if necessary
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + ItemTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ItemTable.Cols.UUID + ", " +
                ItemTable.Cols.Name + ", " +
                ItemTable.Cols.Status + ", " +
                ItemTable.Cols.Price +
                ")"
        );

        db.execSQL("create table " + MemberTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                MemberTable.Cols.UUID + ", " +
                MemberTable.Cols.Name + ", " +
                MemberTable.Cols.Number +
                ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
