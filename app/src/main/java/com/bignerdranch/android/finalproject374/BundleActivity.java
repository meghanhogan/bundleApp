package com.bignerdranch.android.finalproject374;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by meghanhogan on 11/28/16.
 */

public class BundleActivity extends SingleFragmentActivity {

    private static final String EXTRA_BUNDLE_LIST =
            "com.bignerdranch.android.project374-grocery_list";
    public static Intent newIntent(Context packageContext, ArrayList<Item> bundleList) {
        Intent intent = new Intent(packageContext, BundleActivity.class);
        intent.putExtra(EXTRA_BUNDLE_LIST,bundleList);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        ArrayList<Item> bundleList = (ArrayList<Item>) getIntent()
                .getSerializableExtra(EXTRA_BUNDLE_LIST);
        return BundleFragment.newInstance(bundleList);
    }

}