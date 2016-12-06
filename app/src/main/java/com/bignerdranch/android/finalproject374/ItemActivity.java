package com.bignerdranch.android.finalproject374;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;


/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemActivity extends SingleFragmentActivity {

    private static final String EXTRA_ITEM_ID =
            "com.bignerdranch.android.project374-grocery_list.item_id";

    public static Intent newIntent(Context packageContext, UUID itemId) {
        Intent intent = new Intent(packageContext, ItemActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID ItemId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_ITEM_ID);
        return ItemFragment.newInstance(ItemId);
    }

}
