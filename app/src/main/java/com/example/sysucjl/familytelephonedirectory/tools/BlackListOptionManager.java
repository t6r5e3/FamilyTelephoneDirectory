package com.example.sysucjl.familytelephonedirectory.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysucjl on 16-4-19.
 */
public class BlackListOptionManager {

    private SQLiteOpenHelper mOpenHelper;

    public BlackListOptionManager(Context context) {
        // TODO Auto-generated constructor stub
        mOpenHelper = BlackListDBHelper.getInstance(context);
    }

    //添加黑名单
    public void add(String number){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("number", number);
            db.insert("blacklist", "_id", values);
            db.close();
        }
    }

    //判断号码是否是黑名单
    public boolean isBlackNumber(String number){
        boolean isExist = false;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.query("blacklist", null, " number = ? ", new String[]{number}, null, null, null);
            if(c.moveToFirst()){
                isExist = true;
            }
            c.close();
            db.close();
        }
        return isExist;
    }

    //根据号码查询id
    public int queryId(String number){
        int _id = 0;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.query("blacklist", new String[]{"_id"}, " number = ? ", new String[]{number}, null, null, null);
            if(c.moveToFirst()){
                _id = c.getInt(0);
            }
            c.close();
            db.close();
        }
        return _id;
    }

    //删除黑名单
    public void delete(String number){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            db.delete("blacklist", " number = ? ", new String[]{number});
            db.close();
        }
    }

    //更新黑名单
    public void update(int id,String number){
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("number", number);
            db.update("blacklist", values, " _id = ? ", new String[]{id+""});
            db.close();
        }
    }

    //得到所有的黑名单
    public List<String> findAll(){
        List<String> blacklist = new ArrayList<String>();
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.query("blacklist", new String[]{"number"}, null, null, null, null, null);
            while(c.moveToNext()){
                String number = c.getString(0);
                blacklist.add(number);
            }
            c.close();
            db.close();
        }
        return blacklist;
    }

}
