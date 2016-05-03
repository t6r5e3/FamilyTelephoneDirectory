package com.example.sysucjl.familytelephonedirectory.tools;

/**
 * Created by sysucjl on 16-4-19.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackListDBHelper extends SQLiteOpenHelper {

    private static SQLiteOpenHelper mInstance;

    private final static String name = "blacklist.db";

    public static SQLiteOpenHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new BlackListDBHelper(context, name, null, 1);
        }
        return mInstance;
    }

    private BlackListDBHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table blacklist(_id integer primary key autoincrement,number text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
