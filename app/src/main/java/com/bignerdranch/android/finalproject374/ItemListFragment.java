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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.GREEN;
import static android.support.v7.widget.helper.ItemTouchHelper.Callback.makeMovementFlags;

/**
 * Created by meghanhogan on 11/17/16.
 */

public class ItemListFragment extends Fragment{

    private RecyclerView mItemRecyclerView;
    private ItemAdapter mAdapter;
    private Button mAddItemButton;
    private Button mBundleButton;
    public ArrayList<Item> mBundleList = new ArrayList<Item>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mItemRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_1);;
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddItemButton = (Button)view.findViewById(R.id.list_add_button);
        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                ItemGen.get(getActivity()).addItem(item);
                Intent intent = ItemActivity.newIntent(getActivity(), item.getId());
                startActivity(intent);
            }
        });

        mBundleButton = (Button)view.findViewById(R.id.list_bundle_button);
        mBundleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BundleActivity.newIntent(getActivity(), mBundleList);
                startActivity(intent);
            }
        });

        updateUI();
        ItemTouchHelper.Callback callback = new ListItemTouchHelper(mAdapter);
        ItemTouchHelper mIth = new ItemTouchHelper(callback);
        mIth.attachToRecyclerView(mItemRecyclerView);


        //System.out.println("does the list exist? " + mBundleList.size());
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
        mBundleList.clear();

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mItemRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
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
            boolean inList = listCheck();
            if(inList){
                mBundleList.add(mItem);
                mNameTextView.setBackgroundResource(R.color.colorSelected);
            }
            else if (!inList){
                mNameTextView.setBackgroundResource(R.color.colorPrimary);
            }

        }

        public void bindItem(Item item){
            mItem = item;
            mNameTextView.setText(item.getName());
        }

        public boolean listCheck(){
            for(int i = 0; i<mBundleList.size(); i++){
                if (mBundleList.get(i).getId() == mItem.getId()){
                    mBundleList.remove(i);
                    return false;
                }
            }
            return true;
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

        public void remove(int position){
            mItems.remove(position);
            notifyItemRemoved(position);
            updateUI();
        }

        public void onSwipe(int position){
            Item item = mItems.get(position);
            Intent intent = ItemActivity.newIntent(getActivity(), item.getId());
            startActivity(intent);
        }

        public void setItems(List<Item> items){
            mItems=items;
        }
    }

}
