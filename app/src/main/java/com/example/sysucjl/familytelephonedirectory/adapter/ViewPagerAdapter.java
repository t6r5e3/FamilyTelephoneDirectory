package com.example.sysucjl.familytelephonedirectory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sysucjl.familytelephonedirectory.fragment.PersonFragment;
import com.example.sysucjl.familytelephonedirectory.fragment.RecordFragment;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RecordFragment.newInstance(null, null);
            case 1:
                return PersonFragment.newInstance(null, null);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "通话记录";
            case 1:
                return "联系人";
        }
        return null;
    }
}