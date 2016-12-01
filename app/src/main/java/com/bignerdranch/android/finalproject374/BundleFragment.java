package com.bignerdranch.android.finalproject374;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by meghanhogan on 11/28/16.
 */

public class BundleFragment extends Fragment {

    private static final String ARG_BUNDLE_LIST = "bundle_list";

    private ArrayList<Item> mBundleList;
    public RecyclerView mBundleRecyclerView;
    public TextView mPriceTextView;
    public Double mPrice;
    private ItemAdapter mAdapter;


    public static BundleFragment newInstance(ArrayList<Item> bundleList) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BUNDLE_LIST, bundleList);
        BundleFragment fragment = new BundleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundleList = (ArrayList<Item>) getArguments().getSerializable(ARG_BUNDLE_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bundle, container, false);

        mBundleRecyclerView = (RecyclerView)v.findViewById(R.id.bundle_recycler_view);
        mBundleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPriceTextView = (TextView)v.findViewById(R.id.bundle_price_text_view);
        mPrice = priceAdder();
        System.out.println("price is " + priceAdder());
        mPriceTextView.setText("Bundle total is: $" + mPrice.toString());

        updateUI();
        ItemTouchHelper.Callback callback = new BundleFragment.ListItemTouchHelper(mAdapter);
        ItemTouchHelper mIth = new ItemTouchHelper(callback);
        mIth.attachToRecyclerView(mBundleRecyclerView);


        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        List<Item> items = mBundleList;

        mAdapter = new ItemAdapter(items);
        mBundleRecyclerView.setAdapter(mAdapter);

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mBundleRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }

    public Double priceAdder(){
        Double finPrice = 0.0;
        for (int i=0; i<mBundleList.size(); i++){
            if(mBundleList.get(i).getPrice() != null) {
                Double price = Double.parseDouble(mBundleList.get(i).getPrice());
                finPrice += price;
            }
        }
        return finPrice;
    }

    public class ListItemTouchHelper extends ItemTouchHelper.SimpleCallback {
        private ItemAdapter mAdapter;

        public ListItemTouchHelper(ItemAdapter itemAdapter){
            super(ItemTouchHelper.UP| ItemTouchHelper.DOWN, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            this.mAdapter = itemAdapter;
        }
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;// drag and drop not included
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // if left, remove from adapter
            if (direction == ItemTouchHelper.LEFT){
                //System.out.println("swiped left");
                mAdapter.remove(viewHolder.getAdapterPosition());
            }
            if (direction == ItemTouchHelper.RIGHT){
                //System.out.println("swiped right");
                mAdapter.onSwipe(viewHolder.getAdapterPosition());
            }

        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private Item mItem;

        public ItemHolder(View itemView){
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name_text_view);
        }


        public void bindItem(Item item){
            mItem = item;
            mNameTextView.setText(item.getName());
        }


    }

    private class ItemAdapter extends RecyclerView.Adapter<BundleFragment.ItemHolder> {

        private List<Item> mBundleList;

        public ItemAdapter(List<Item> items){
            mBundleList = items;
        }

        @Override
        public BundleFragment.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new BundleFragment.ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(BundleFragment.ItemHolder holder, int position){
            Item item = mBundleList.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount(){
            return mBundleList.size();
        }

        public void remove(int position){
            mBundleList.remove(position);
            notifyItemRemoved(position);
            updateUI();
        }

        public void onSwipe(int position){
            Item item = mBundleList.get(position);
            Intent intent = ItemActivity.newIntent(getActivity(), item.getId());
            startActivity(intent);
        }
    }

}

