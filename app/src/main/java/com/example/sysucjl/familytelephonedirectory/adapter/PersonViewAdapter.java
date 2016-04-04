package com.example.sysucjl.familytelephonedirectory.adapter;

/**
 * Created by sysucjl on 16-3-31.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sysucjl.familytelephonedirectory.MyClass.ContactItem;
import com.example.sysucjl.familytelephonedirectory.PersonInfoActivity;
import com.example.sysucjl.familytelephonedirectory.R;
import com.example.sysucjl.familytelephonedirectory.tool.MyTool;

import java.util.ArrayList;
import java.util.List;

public class PersonViewAdapter extends RecyclerView.Adapter {

    private ArrayList<ContactItem> mPersons;
    private Context mContext;

    public PersonViewAdapter(List<ContactItem> persons, Context context) {
        this.mPersons = (ArrayList<ContactItem>) persons;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_person_item, parent, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PersonHolder personHolder = (PersonHolder)holder;
        ContactItem person = mPersons.get(position);
        personHolder.person_name.setText(person.getName());
        personHolder.person_icon.setBackgroundResource(R.drawable.ic_account_box_black_48dp);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView person_icon;
        public TextView person_name;

        public PersonHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            person_icon = (ImageView) itemView.findViewById(R.id.iv_person_icon);
            person_name = (TextView) itemView.findViewById(R.id.tv_person_name);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            MyTool tool = new MyTool();
            ContactItem item = tool.getDetailFromContactID(v.getContext(), mPersons.get(getPosition()));
            Intent intent = new Intent();
            intent.setClass(mContext, PersonInfoActivity.class);
            intent.putExtra("tab_name","contact");
            intent.putExtra("name", item.getName());
            intent.putStringArrayListExtra("phonelist", item.getPhoneList());
            mContext.startActivity(intent);
        }
    }


}
