package com.example.sysucjl.familytelephonedirectory.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysucjl on 16-3-23.
 */

public class ContactItem {
    private String mDisplayName;
    private String mPinYin;
    private String mSection;
    private String mContactId;
    private String mAvatar;
    private int mPhoneCount;
    private ArrayList<String> mPhoneList;

    public ContactItem(String name){
        mDisplayName = name;
    }

    public void setmContactId(String id){
        mContactId = id;
    }

    public void setmPhoneCount(int count){
        mPhoneCount = count;
    }

    public void setPhoneNumber(List<String> list){
        mPhoneList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            mPhoneList.add(list.get(i));
        }
    }

    public String getName(){
        return mDisplayName;
    }

    public String getmContactId(){
        return mContactId;
    }

    public int getmPhoneCount(){
        return mPhoneCount;
    }

    public ArrayList<String> getmPhoneList(){
        return mPhoneList;
    }

    public String getmPinYin() {
        return mPinYin;
    }

    public void setmPinYin(String mPinYin) {
        this.mPinYin = mPinYin;
    }

    public String getmSection() {
        return mSection;
    }

    public void setmSection(String mSection) {
        this.mSection = mSection;
    }
}
