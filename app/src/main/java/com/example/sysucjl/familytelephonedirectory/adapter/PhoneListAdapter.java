package com.example.sysucjl.familytelephonedirectory.adapter;

/**
 * Created by sysucjl on 16-3-31.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sysucjl.familytelephonedirectory.R;

import java.util.List;

public class PhoneListAdapter extends ArrayAdapter<String> {

    private int mResourceId;
    private int mColor;

    public PhoneListAdapter(Context context, int textViewResourceId, List<String> objects, int color){
        super(context,textViewResourceId,objects);
        mResourceId = textViewResourceId;
        this.mColor = color;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        final String item =getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
        TextView phone_number = (TextView)view.findViewById(R.id.tv_phone_number);
        ImageView message = (ImageView)view.findViewById(R.id.sent_message);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            message.setImageTintList(ColorStateList.valueOf(mColor));
        }

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String data = "smsto:" + item;
                intent.setData(Uri.parse(data));
                getContext().startActivity(intent);
            }
        });

        phone_number.setText(item);
        phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String data = "tel:" + item;
                intent.setData(Uri.parse(data));
                getContext().startActivity(intent);
            }
        });
        return view;
    }

    class PhoneListHolder{
        public ImageView ivPhone;
        public ImageButton ivbtnSetMessage;
        public TextView tvPhoneNum, tvLocation;
    }
}