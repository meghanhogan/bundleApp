package com.bignerdranch.android.finalproject374;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.finalproject374.database.ItemDbSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meghanhogan on 12/4/16.
 */

public class HomeFragment extends Fragment {

    private Button mAddMemberButton;
    private TextView mMembersTextView;
    public List<Member> mMembers = new ArrayList();
    //private List<String> mNumbers = new ArrayList();
    private static final int REQUEST_CONTACT = 0;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        final Intent pickContact = new Intent(Intent.ACTION_PICK);
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        mMembersTextView = (TextView) view.findViewById(R.id.group_members);

        mAddMemberButton = (Button) view.findViewById(R.id.add_member_button);
        mAddMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start intent to choose contact and add to mMembers
                System.out.println("button clicked");
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        setMembersText();

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeLeft() {
                //Start ItemListActivity
                System.out.println("screen swiped");
                Intent intent = ItemListActivity.newIntent(getActivity());
                startActivity(intent);
            }

        });

        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, packageManager.MATCH_DEFAULT_ONLY) == null){
            mAddMemberButton.setEnabled(false);
        }

        MemberGen memberGen = MemberGen.get(getActivity());
        mMembers = memberGen.getMembers();
        setMembersText();

        return view;
    }

    private void setMembersText() {
        if(mMembers.size()>0){
            String members = "";
            for (int i=0; i<mMembers.size(); i++){
                members += mMembers.get(i).getName()+ "\n";
            }
            mMembersTextView.setText(members);
        }
    }

    public List getMembers(){
        return mMembers;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CONTACT && data != null){
            Uri contactUri = data.getData();
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            Cursor cursor = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            try{
                if(cursor.getCount() == 0){
                    return;
                }

                cursor.moveToFirst();
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);
                Member member = new Member();
                member.setName(name);
                member.setNumber(number);
                mMembers.add(member);
                MemberGen.get(getActivity()).addMember(member);

            } finally {
                setMembersText();
                cursor.close();
            }
        }
    }
}
