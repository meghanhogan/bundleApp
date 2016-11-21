package com.bignerdranch.android.finalproject374;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.graphics.Color.GREEN;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemListFragment extends Fragment{

    private RecyclerView mItemRecyclerView;
    private ItemAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mItemRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_1);;
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        ItemGen itemGen = ItemGen.get(getActivity());
        List<Item> items = itemGen.getItems();

        mAdapter = new ItemAdapter(items);
        mItemRecyclerView.setAdapter(mAdapter);

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mItemRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private Item mItem;

        public ItemHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name_text_view);
        }

        @Override
        public void onClick(View v) {
            Intent intent = ItemActivity.newIntent(getActivity(), mItem.getId());
            startActivity(intent);
        }

        public void bindItem(Item item){
            mItem = item;
            mNameTextView.setText(item.getName());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<Item> mItems;

        public ItemAdapter(List<Item> items){
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position){
            Item item = mItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount(){
            return mItems.size();
        }
    }
}
