package com.bignerdranch.android.finalproject374;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.finalproject374.database.ItemBaseHelper;
import com.bignerdranch.android.finalproject374.database.ItemCursorWrapper;
import com.bignerdranch.android.finalproject374.database.ItemDbSchema;
import com.bignerdranch.android.finalproject374.database.MemberCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by meghanhogan on 12/6/16.
 */

public class MemberGen {

    private static  MemberGen sMemberGen;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MemberGen get(Context context) {
        if (sMemberGen == null) {
            sMemberGen = new MemberGen(context);
        }
        return sMemberGen;
    }

    private MemberGen(Context context) {

        mContext = context.getApplicationContext();
        mDatabase=new ItemBaseHelper(mContext).getWritableDatabase();
    }

    public void addMember(Member m){
        ContentValues values = getContentValues(m);
        mDatabase.insert(ItemDbSchema.MemberTable.NAME, null, values);
    }

    public List<Member> getMembers(){
        //return members list from database

        List<Member> members = new ArrayList<>();

        MemberCursorWrapper cursor = queryItems(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                members.add(cursor.getMember());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return members;
    }

    public Member getMember(UUID id){
        //return member from database

        MemberCursorWrapper cursor = queryItems(ItemDbSchema.MemberTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );
        try{
            if (cursor.getCount()==0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMember();
        } finally {
            cursor.close();
        }
    }

    private MemberCursorWrapper queryItems(String whereClause, String[] whereArgs){
        //create member cursor wrapper
        Cursor cursor = mDatabase.query(
                ItemDbSchema.MemberTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new MemberCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Member member){
        //get content values for Member
        //get content values for Member
        ContentValues values = new ContentValues();

        values.put(ItemDbSchema.MemberTable.Cols.UUID, member.getId().toString());
        values.put(ItemDbSchema.MemberTable.Cols.Name, member.getName());
        values.put(ItemDbSchema.MemberTable.Cols.Number, member.getNumber());
        return values;
    }
}
