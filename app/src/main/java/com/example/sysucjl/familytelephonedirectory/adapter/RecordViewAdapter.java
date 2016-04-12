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
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sysucjl.familytelephonedirectory.PersonInfoActivity;
import com.example.sysucjl.familytelephonedirectory.R;
import com.example.sysucjl.familytelephonedirectory.data.RecordItem;
import com.example.sysucjl.familytelephonedirectory.tools.ColorUtils;
import com.example.sysucjl.familytelephonedirectory.tools.DateTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordViewAdapter extends RecyclerView.Adapter {

    private ArrayList<RecordItem> mRecordItems;
    private Context mContext;

    public RecordViewAdapter(List<RecordItem> recordItems, Context context) {
        this.mRecordItems = (ArrayList<RecordItem>) recordItems;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_record_item, parent, false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecordHolder recordHolder = (RecordHolder)holder;
        RecordItem recordItem = mRecordItems.get(position);
        if(recordItem.getName()!=null) {
            recordHolder.tvRecordName.setText(recordItem.getName());
            //System.out.println(recordItem.getName()+" "+recordItem.getName().hashCode());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                recordHolder.ivRecordIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(ColorUtils.getColor(recordItem.getName().hashCode()))));
            }

        }
        else {
            //System.out.println(recordItem.getNumber()+" "+recordItem.getNumber().hashCode());
            recordHolder.tvRecordName.setText(DateTools.getNumberFormat(recordItem.getNumber()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                recordHolder.ivRecordIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(ColorUtils.getColor(recordItem.getNumber().hashCode()))));
            }
        }
        int callcode = recordItem.getType();
        switch (callcode) {
            case CallLog.Calls.OUTGOING_TYPE:
                recordHolder.imgRecordType.setBackgroundResource(R.drawable.ic_outgoing);
                break;
            case CallLog.Calls.INCOMING_TYPE:
                recordHolder.imgRecordType.setBackgroundResource(R.drawable.ic_incoming);
                break;
            case CallLog.Calls.MISSED_TYPE:
                recordHolder.imgRecordType.setBackgroundResource(R.drawable.ic_missed);
                break;
        }

        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd hh:mm");
        Date date = new Date(recordItem.getCallTime());// 打电话的日期
        recordHolder.tvRecordDate.setText(sfd.format(date));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mRecordItems.size();
    }

    class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivRecordIcon;
        public TextView tvRecordName;
        public TextView tvRecordDate;
        public ImageView imgRecordType;

        public RecordHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivRecordIcon = (ImageView) itemView.findViewById(R.id.iv_record_icon);
            tvRecordName = (TextView) itemView.findViewById(R.id.tv_record_name);
            tvRecordDate = (TextView) itemView.findViewById(R.id.tv_record_date);
            imgRecordType = (ImageView) itemView.findViewById(R.id.img_record_type);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            RecordItem recordItem = mRecordItems.get(getPosition());
            Intent intent = new Intent();

            intent.setClass(mContext, PersonInfoActivity.class);
            intent.putExtra("tab_name","record");
            if(recordItem.getName()!=null)
                intent.putExtra("name", recordItem.getName());
            else
                intent.putExtra("name", recordItem.getNumber());
            mContext.startActivity(intent);
        }
    }
}

