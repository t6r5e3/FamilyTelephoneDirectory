package com.example.sysucjl.familytelephonedirectory.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import java.net.HttpURLConnection;
/**
 * Created by Administrator on 2016/4/12.
 */
public class ChildThread {
    public static final int SHOW_RESPONSE = 0;
    public void sendRequestWithHttpURLConnection(final Context context,final String number) {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    String num=number;
                    GetAddress xmlser=new GetAddress();
                    String res=xmlser.query(num,1);
                    Log.i("Fang","查询结果为"+res);

                    SQLiteDatabase db=DBManager.getInstance(context).openDatabase();
                    String sql = "select location from mob_location where _id = ? ";
                    String[] param = new String[] { number };
                    if (db != null && db.isOpen()) {
                        Cursor cursor = db.rawQuery(sql, param);
                        if (cursor.moveToNext()) {
                            String test = cursor.getString(cursor.getColumnIndex("location"));
                            Log.i("Fang","数据库中已有数据 "+test+""+number);
                        }
                        else{
                            //如果没有数据则插入
                            Log.i("Fang","数据库中没有数据,现在插入 "+res+""+number);
                            ContentValues values = new ContentValues();
                            db.beginTransaction();//开事务插入数据
                            try {
                                values.put("_id", number);
                                values.put("location", res);
                                db.insert("mob_location", null, values);
                                db.setTransactionSuccessful();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                Log.i("Fang", "事务有错");
                            } finally {
                                db.endTransaction(); // 结束事务
                                Log.i("Fang", "结束事务");
                            }
                        }
                        cursor.close();
                    }
                    DBManager.getInstance(context).closeDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
