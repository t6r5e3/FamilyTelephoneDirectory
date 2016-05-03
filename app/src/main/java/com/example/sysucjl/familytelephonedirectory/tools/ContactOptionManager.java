package com.example.sysucjl.familytelephonedirectory.tools;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

import com.example.sysucjl.familytelephonedirectory.data.ContactItem;
import com.example.sysucjl.familytelephonedirectory.data.RecordItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysucjl on 16-4-4.
 */
public class ContactOptionManager {

    //读取通讯记录
    public List<RecordItem> getCallLog(Context context) {
        /* Query the CallLog Content Provider */
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);
        int id_index = cursor.getColumnIndex(CallLog.Calls._ID);
        int number_index = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type_index = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date_index = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration_index = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int name_index = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);

        List<RecordItem> recordItems = new ArrayList<>();
        String lastphNum = "";
        while (cursor.moveToNext()) {
            int callid = cursor.getInt(id_index);
            String phNum = cursor.getString(number_index);
            int callcode = Integer.parseInt(cursor.getString(type_index));
            long callDate = Long.valueOf(cursor.getString(date_index));
            long callDuration = Long.valueOf(cursor.getString(duration_index));
            String name = cursor.getString(name_index);
            if (phNum.equals(lastphNum) && recordItems.size() > 0) {
                recordItems.get(recordItems.size() - 1).addRecordSegement(callid,callcode, callDate, callDuration);
            } else {
                RecordItem item = new RecordItem(callid, callcode, callDate, callDuration, phNum, name);
                recordItems.add(item);
            }
            lastphNum = phNum;
        }
        cursor.close();
        return recordItems;
    }

    //根据记录ID来删除通讯记录
    public void deleteRecordById(Context context, int id) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, CallLog.Calls._ID + "=?", new String[]{id + ""});
    }

    //根据电话号码来删除通讯记录
    public void deleteRecordByNumber(Context context, String phnumber) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number=?", new String[]{phnumber});
    }

    //读取联系人列表
    public List<ContactItem> getBriefContactInfor(Context context) {
        //定义常量，节省重复引用的时间
        String ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        //临时变量
        String contactId;
        String displayName;
        //生成ContentResolver对象
        ContentResolver contentResolver = context.getContentResolver();
        // 获取手机联系人
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, "sort_key");
        List<ContactItem> friendList = new ArrayList<>();
        // 无联系人直接返回
        if (!cursor.moveToFirst()) {//moveToFirst定位到第一行
            return null;
        }
        do {
            // 获得联系人的ID：String类型  列名-->列数-->列内容
            contactId = cursor.getString(cursor.getColumnIndex(ID));
            // 获得联系人姓名：String类型
            displayName = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
            // 查看联系人有多少个号码，如果没有号码，返回0
            int phoneCount = cursor.getInt(cursor.getColumnIndex(HAS_PHONE_NUMBER));
            ContactItem item;
            if(phoneCount!=0){
                item = new ContactItem(displayName);
                item.setmContactId(contactId);
                item.setmPhoneCount(phoneCount);
                friendList.add(item);
            }

        } while (cursor.moveToNext());
        return friendList;
    }

    //读取单个联系人详细信息
    public ContactItem getDetailFromContactID(Context context, ContactItem item) {
        Uri CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Cursor phoneCursor;
        ContentResolver contentResolver = context.getContentResolver();
        List<String> phoneList = new ArrayList<>();
        if (item.getmContactId() == null) {
            return item;
        }
        phoneCursor = contentResolver.query(CONTENT_URI, null, CONTACT_ID + "=" + item.getmContactId(), null, null);
        if (!phoneCursor.moveToFirst()) {
            return item;
        }
        do {
            String temp = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
            if (temp != null) {
                phoneList.add(temp);
            }
        } while (phoneCursor.moveToNext());

        item.setPhoneNumber(phoneList);
        return item;
    }
}
