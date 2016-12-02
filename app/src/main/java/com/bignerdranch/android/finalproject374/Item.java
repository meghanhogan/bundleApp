package com.bignerdranch.android.finalproject374;

import java.io.Serializable;
import java.util.UUID;


/**
 * Created by meghanhogan on 11/17/16.
 */

public class Item implements Serializable {

    private UUID mId;
    private String mName;
    private String mPrice;
    private int mStatus; //can be 0, 1, 2 == in stock, running low, out
    private int mPosition;

    public Item(){
        //generate random unique id for each item
        mId = UUID.randomUUID();

    }

    public Item(UUID uuid){
        mId = uuid;
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

    public String getPrice(){
        return mPrice;
    }

    public void setPrice(String price){
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

    public String statusString(){
        if (mStatus == 0){
            return "In Stock";
        }
        else if (mStatus== 1){
            return "Running Low";
        }
        else if (mStatus == 2){
            return "All Out";
        }
        return "Set Status";
    }

    public int convertStatus(String status){

        if (status.equals("In Stock")){
            return 0;
        }

        else if (status.equals("Running Low")){
            return 1;
        }

        else if (status.equals("All Out")){
            return 2;
        }
        return -1;
    }

}
