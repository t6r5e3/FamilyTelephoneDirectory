package com.example.sysucjl.familytelephonedirectory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sysucjl.familytelephonedirectory.adapter.PhoneListAdapter;
import com.example.sysucjl.familytelephonedirectory.tools.ColorUtils;

public class PersonInfoActivity extends AppCompatActivity {

    private ImageView ivBackDrop;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        final Intent intent = getIntent();
        String personName = intent.getStringExtra("name");
        int color = Color.parseColor(ColorUtils.getColor(personName.hashCode()));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ivBackDrop = (ImageView) findViewById(R.id.iv_backdrop);
        ivBackDrop.setBackgroundColor(color);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbarLayout.setBackgroundColor(color);
        mToolbarLayout.setContentScrimColor(color);
        mToolbar.setBackgroundColor(color);
        mToolbarLayout.setTitle(personName);

        if(intent.getStringExtra("tab_name").equals("contact")){
            final PhoneListAdapter adapter = new PhoneListAdapter(this,R.layout.list_phone_item,intent.getStringArrayListExtra("phonelist"), color);
            final ListView phoneList = (ListView)findViewById(R.id.phone_list);

            phoneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.getView(position,view,phoneList);
                }
            });

            phoneList.setAdapter(adapter);
            setListViewHeightBasedOnChildren(phoneList);
        }
    }

    //解决ListView在ScrollView中无法显示多列的情况
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
