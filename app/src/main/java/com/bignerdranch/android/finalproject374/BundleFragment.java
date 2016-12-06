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


/**
 * Created by meghanhogan on 11/28/16.
 */

public class BundleFragment extends Fragment {

    private static final String ARG_BUNDLE_LIST = "bundle_list";

    public ArrayList<Item> mBundleList;
    public RecyclerView mBundleRecyclerView;
    public TextView mPriceTextView;
    public ItemAdapter mAdapter;
    public Button mRequestPaymentButton;
    public List<Member> mMembers;


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
        System.out.println("price is " + priceAdder());
        mPriceTextView.setText("Bundle total is: $" + priceAdder().toString());

        mRequestPaymentButton = (Button) v.findViewById(R.id.request_payment_button);
        mRequestPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start intent to send request payment messages
                String numList = makeNumbersList();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER, numList);
                intent.putExtra(Intent.EXTRA_TEXT, getMessageText());
                startActivity(intent);
            }
        });

        updateUI();
        ItemTouchHelper.Callback callback = new BundleFragment.ListItemTouchHelper(mAdapter);
        ItemTouchHelper mIth = new ItemTouchHelper(callback);
        mIth.attachToRecyclerView(mBundleRecyclerView);


        return v;


    }

    public String makeNumbersList(){
        String numList = "";
        MemberGen memberGen = MemberGen.get(getActivity());
        mMembers = memberGen.getMembers();

        for (Member member : mMembers){
            String number = member.getNumber();
            numList += number + ";";
        }
        numList = numList.substring(0, numList.length()-1);
        return numList;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        List<Item> items = mBundleList;
        mPriceTextView.setText("Bundle total is: $" + priceAdder().toString());

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

    public String getMessageText(){
        String messageText = null;
        Double dividedPrice = priceAdder()/mMembers.size();
        String priceString = dividedPrice.toString();
        messageText = getString(R.string.message_text, priceString);
        return messageText;
    }


    public class ListItemTouchHelper extends ItemTouchHelper.SimpleCallback {
        private ItemAdapter mAdapter;

        public ListItemTouchHelper(ItemAdapter itemAdapter){
            super(0| 0, ItemTouchHelper.LEFT|0);
            this.mAdapter = itemAdapter;
        }
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //move not implemented
            return false;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // if left, remove from adapter
            if (direction == ItemTouchHelper.LEFT){
                mAdapter.remove(viewHolder.getAdapterPosition());
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

