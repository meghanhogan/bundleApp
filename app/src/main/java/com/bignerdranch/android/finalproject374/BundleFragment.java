package com.bignerdranch.android.finalproject374;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.telephony.PhoneNumberUtils.normalizeNumber;


/**
 * Created by meghanhogan on 11/28/16.
 */

public class BundleFragment extends Fragment{

    private static final String ARG_BUNDLE_LIST = "bundle_list";

    public ArrayList<Item> mBundleList;
    public RecyclerView mBundleRecyclerView;
    public TextView mPriceTextView;
    public ItemAdapter mAdapter;
    public Button mRequestPaymentButton;
    public List<Member> mMembers;
    public DecimalFormat df = new DecimalFormat("###.##"); //used to truncate doubles into normal dollar form


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
        //init mBundleList to intent extra
        mBundleList = (ArrayList<Item>) getArguments().getSerializable(ARG_BUNDLE_LIST);
        MemberGen memberGen = MemberGen.get(getActivity());
        mMembers = memberGen.getMembers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bundle, container, false);


        mBundleRecyclerView = (RecyclerView)v.findViewById(R.id.bundle_recycler_view);
        mBundleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set price view
        mPriceTextView = (TextView)v.findViewById(R.id.bundle_price_text_view);
        String truncatedPrice = df.format(priceAdder());
        System.out.println("trunc price" + truncatedPrice);
        mPriceTextView.setText("Bundle total is: $" + truncatedPrice);
        mRequestPaymentButton = (Button) v.findViewById(R.id.request_payment_button);
        mRequestPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start intent to send request payment messages
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(makeNumbersList()));
                intent.putExtra("sms_body", getMessageData());
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
        //generates a list of numbers from the members table to send message to
        String numList = "";

        for (Member member : mMembers){
            String number = member.getNumber();
            number = normalizeNumber(number);
            numList += number + ";";
        }
        numList = "smsto:" + numList.substring(0, numList.length()-1);
        System.out.println(numList);
        return numList;
    }
    public String getMessageData(){
        //generate text message
        String messageText = null;
        Double dividedPrice = priceAdder()/mMembers.size();
        df.setRoundingMode(RoundingMode.DOWN);
        String priceString = "$"+ df.format(dividedPrice);
        messageText = getString(R.string.message_text, priceString);
        return messageText;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        //reset the adapter
        List<Item> items = mBundleList;
        //reset the price
        String truncatedPrice = df.format(priceAdder());
        mPriceTextView.setText("Bundle total is: $" + truncatedPrice);

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
        //calculate price
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
        //class for detecting swipes on recyclerView
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
            //item view in recyclerview
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

    }

}

