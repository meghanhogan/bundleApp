package com.bignerdranch.android.finalproject374;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private void updateUI(){
        ItemGen itemGen = ItemGen.get(getActivity());
        List<Item> items = itemGen.getItems();

        mAdapter = new ItemAdapter(items);
        mItemRecyclerView.setAdapter(mAdapter);
    }

    private class ItemHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private Item mItem;

        public ItemHolder(View itemView){
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_name_text_view);
        }

        public void bindItem(Item item){
            mItem = item;
            mNameTextView.setText(item.getName());
            mNameTextView.setTextColor(item.color());
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
