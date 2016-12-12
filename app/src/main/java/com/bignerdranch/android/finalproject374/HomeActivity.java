package com.bignerdranch.android.finalproject374;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by meghanhogan on 12/4/16.
 */

public class HomeActivity  extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, HomeActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }
}
