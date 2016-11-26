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

         ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,
                        ItemTouchHelper.START | ItemTouchHelper.END) {
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        // move item in `fromPos` to `toPos` in adapter.
                        System.out.println("moved");
                        return true;// true if moved, false otherwise
                    }
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // if left, remove from adapter
                        if (direction == ItemTouchHelper.LEFT){
                            System.out.println("swiped left");
                            //remove from adapter
                        }
                        if (direction == ItemTouchHelper.RIGHT){
                            //start itemFragment intent
                            System.out.println("swiped right");
                        }

                    }
                });

        mIth.attachToRecyclerView(mItemRecyclerView);
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

        public void remove(int position){
            mItems.remove(position);
        }
    }

}
