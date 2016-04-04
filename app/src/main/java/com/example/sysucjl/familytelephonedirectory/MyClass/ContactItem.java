package com.example.sysucjl.familytelephonedirectory.MyClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysucjl on 16-3-23.
 */

public class ContactItem {
    String Dispalyname;
    String contactId;
    int phoneCount;
    ArrayList<String> phoneList;

    public ContactItem(String name){
        Dispalyname = name;
    }

    public void setContactId(String id){
        contactId = id;
    }

    public void setPhoneCount(int count){
        phoneCount = count;
    }

    public void setPhoneNumber(List<String> list){
        phoneList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            phoneList.add(list.get(i));
        }
    }

    public String getName(){
        return Dispalyname;
    }

    public String getContactId(){
        return contactId;
    }

    public int getPhoneCount(){
        return phoneCount;
    }

    public ArrayList<String> getPhoneList(){
        return phoneList;
    }

}
