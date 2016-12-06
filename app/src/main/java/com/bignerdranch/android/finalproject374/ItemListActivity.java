package com.bignerdranch.android.finalproject374;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemListActivity extends SingleFragmentActivity{

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ItemListActivity.class);
        //intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return ItemListFragment.newInstance();
    }
}
