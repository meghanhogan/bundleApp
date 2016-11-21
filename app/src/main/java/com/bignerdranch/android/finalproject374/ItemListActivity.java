package com.bignerdranch.android.finalproject374;

import android.support.v4.app.Fragment;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new ItemListFragment();
    }
}
