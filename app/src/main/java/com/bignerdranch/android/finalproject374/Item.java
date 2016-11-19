package com.bignerdranch.android.finalproject374;

import java.util.UUID;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class Item {

    private UUID mId;
    private String mName;
    private Double mPrice;
    private int mStatus; //can be 0, 1, 2 == in stock, running low, out
    private int mPosition;

    public Item(){
        //generate random unique id for each item
        mId = UUID.randomUUID();
        mStatus = 0;
    }

    public UUID getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public Double getPrice(){
        return mPrice;
    }

    public void setPrice(Double price){
        mPrice = price;
    }

    public int getStatus(){
        return mStatus;
    }

    public void setStatus(int status){
        mStatus = status;
    }

    public int getPos(){
        return mPosition;
    }

    public void setPos(int pos){
        mPosition = pos;
    }

    public int color(){
        if (mStatus == 0){
            return GREEN;
        }
        else if (mStatus== 1){
            return YELLOW;
        }
        else if (mStatus == 2){
            return RED;
        }
        return BLACK;
    }

}
