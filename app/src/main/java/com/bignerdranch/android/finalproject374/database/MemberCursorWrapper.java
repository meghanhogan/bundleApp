package com.bignerdranch.android.finalproject374.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.finalproject374.Item;
import com.bignerdranch.android.finalproject374.Member;

import java.util.UUID;

/**
 * Created by meghanhogan on 12/6/16.
 */

public class MemberCursorWrapper extends CursorWrapper {
    //creates a cursor wrapper for getting members out of the database
    public MemberCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Member getMember() {
        String uuidString = getString(getColumnIndex(ItemDbSchema.MemberTable.Cols.UUID));
        String name = getString(getColumnIndex(ItemDbSchema.MemberTable.Cols.Name));
        String number = getString(getColumnIndex(ItemDbSchema.MemberTable.Cols.Number));


        Member member = new Member(UUID.fromString(uuidString));
        member.setName(name);
        member.setNumber(number);

        return member;
    }
}
