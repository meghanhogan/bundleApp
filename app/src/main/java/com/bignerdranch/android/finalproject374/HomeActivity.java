package com.bignerdranch.android.finalproject374;

import android.support.v4.app.Fragment;

/**
 * Created by meghanhogan on 12/4/16.
 */

public class HomeActivity  extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }
}
