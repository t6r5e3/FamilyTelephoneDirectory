package com.example.sysucjl.familytelephonedirectory.adapter;

/**
 * Created by sysucjl on 16-3-31.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sysucjl.familytelephonedirectory.data.ContactItem;
import com.example.sysucjl.familytelephonedirectory.PersonInfoActivity;
import com.example.sysucjl.familytelephonedirectory.R;
import com.example.sysucjl.familytelephonedirectory.tools.ColorUtils;
import com.example.sysucjl.familytelephonedirectory.tools.MyTool;

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
        personHolder.tvPersonName.setText(person.getName());
        if(person.getName().charAt(0) < '9' && person.getName().charAt(0) > '0'){
            personHolder.ivAvatarSim.setVisibility(View.VISIBLE);
            personHolder.tvAvatarName.setVisibility(View.GONE);
        }else{
            personHolder.ivAvatarSim.setVisibility(View.GONE);
            personHolder.tvAvatarName.setVisibility(View.VISIBLE);
            personHolder.tvAvatarName.setText(""+person.getName().charAt(0));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            personHolder.imgPersonIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(ColorUtils.getColor(person.getName().hashCode()))));
        }
        int resultPositon = getPositionForSection(mPersons.get(position).getmSection());
        if(position == resultPositon){
            personHolder.tvSection.setText(mPersons.get(position).getmSection());
        }else{
            personHolder.tvSection.setText(" ");
        }
    }

    public int getPositionForSection(String section) {
        for (int i = 0; i < mPersons.size(); i++) {
            String sortStr = mPersons.get(i).getmSection();
            if (sortStr.equals(section)) {
                return i;
            }
        }
        return -1;
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
        public ImageView imgPersonIcon, ivAvatarSim;
        public TextView tvPersonName, tvAvatarName, tvSection;

        public PersonHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgPersonIcon = (ImageView) itemView.findViewById(R.id.iv_person_icon);
            tvPersonName = (TextView) itemView.findViewById(R.id.tv_person_name);
            ivAvatarSim = (ImageView) itemView.findViewById(R.id.img_avatar_sim);
            tvAvatarName = (TextView) itemView.findViewById(R.id.tv_avatar_name);
            tvSection = (TextView) itemView.findViewById(R.id.tv_section);
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
            intent.putStringArrayListExtra("phonelist", item.getmPhoneList());
            mContext.startActivity(intent);
        }
    }


}
