package com.bignerdranch.android.finalproject374;

import java.util.UUID;

/**
 * Created by meghanhogan on 12/5/16.
 */


public class Member {
    //class for group members
    public String mName;
    public String mNumber;
    public UUID mId;

    public Member(){
        mId = UUID.randomUUID();
    }

    public Member(UUID id){
        mId = id;
    }

    public void setName(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public void setNumber(String number){
        mNumber = number;
    }

    public String getNumber(){
        return mNumber;
    }

    public UUID getId(){
        return mId;
    }
}
