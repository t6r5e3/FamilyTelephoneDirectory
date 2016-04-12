package com.example.sysucjl.familytelephonedirectory.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.sysucjl.familytelephonedirectory.R;
import com.example.sysucjl.familytelephonedirectory.data.ContactItem;
import com.example.sysucjl.familytelephonedirectory.tools.CharacterParser;
import com.example.sysucjl.familytelephonedirectory.tools.ContactOptionManager;
import com.example.sysucjl.familytelephonedirectory.adapter.PersonViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class PersonFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private android.support.v7.widget.RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<ContactItem> mContactItems;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mFirstVisible = 0;
    private String mFirstSection;
    private TextView tvSection;

    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        tvSection = (TextView) view.findViewById(R.id.tv_section);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ContactOptionManager tool = new ContactOptionManager();
        mContactItems = new ArrayList<>();
        mContactItems = tool.getBriefContactInfor(getContext());
        for(int i = 0; i < mContactItems.size(); i++){
            String pinyin = CharacterParser.getSelling(mContactItems.get(i).getName());
            mContactItems.get(i).setmPinYin(pinyin);
            //System.out.println(pinyin);
            String sortLetter = pinyin.substring(0,1).toUpperCase();
            //System.out.println(sortLetter);
            if(sortLetter.matches("[A-Z]")){
                mContactItems.get(i).setmSection(sortLetter);
            }else{
                mContactItems.get(i).setmSection("#");
            }
        }
        mAdapter = new PersonViewAdapter(mContactItems, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mFirstSection = mContactItems.get(mFirstVisible).getmSection();
        tvSection.setText(mFirstSection);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mFirstVisible = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                mFirstSection = mContactItems.get(mFirstVisible).getmSection();
                String tmp = mContactItems.get(mFirstVisible+1).getmSection();
                if(!tmp.equals(mFirstSection)){
                    tvSection.setVisibility(View.GONE);
                    ((TextView)mLayoutManager.findViewByPosition(mFirstVisible).findViewById(R.id.tv_section)).setText(mFirstSection);
                    ((TextView)mLayoutManager.findViewByPosition(mFirstVisible+1).findViewById(R.id.tv_section)).setText(tmp);
                    //mFirstSection = tmp;
                }else{
                    tvSection.setVisibility(View.VISIBLE);
                    tvSection.setText(mFirstSection);
                    ((TextView)mLayoutManager.findViewByPosition(mFirstVisible).findViewById(R.id.tv_section)).setText(" ");
                    ((TextView)mLayoutManager.findViewByPosition(mFirstVisible+1).findViewById(R.id.tv_section)).setText(" ");
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
