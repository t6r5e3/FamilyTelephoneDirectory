package com.example.sysucjl.familytelephonedirectory.tools;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/6.
 */
public class Tools {

    public static Map<Integer, String> mDayMap;

    static {
        mDayMap = new HashMap<Integer, String>();
        mDayMap.put(1, "星期一");
        mDayMap.put(2, "星期二");
        mDayMap.put(3, "星期三");
        mDayMap.put(4, "星期四");
        mDayMap.put(5, "星期五");
        mDayMap.put(6, "星期六");
        mDayMap.put(0, "星期日");
    }


    public static String getNumberFormat(String source){
        StringBuilder sb = new StringBuilder();
        if(source.length() == 11){
            sb.append(source.substring(0, 3));
            sb.append(" ");
            sb.append(source.substring(3, 7));
            sb.append(" ");
            sb.append(source.substring(7, 11));
            return sb.toString();
        }
        else if(source.length() == 8){
            sb.append(source.substring(0, 3));
            sb.append(" ");
            sb.append(source.substring(3, 8));
            return sb.toString();
        }
        else
            return source;
    }

    public static String getDuration(long source){
        if(source < 60){
            return String.valueOf(source)+"秒";
        }
        else if(source < 3600){
            return String.valueOf(source / 60)+"分"+String.valueOf(source%60)+"秒";
        }
        else {
            int second = (int) (source % 3600);
            return String.valueOf(source/3600)+"时"+String.valueOf(second/60)+"分"+String.valueOf(second%60)+"秒";
        }
    }

    public static String getRecordSegmentDate(long source){
        SimpleDateFormat sfd1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sfd2 = new SimpleDateFormat("HH:mm");
        Date date = new Date(source);// 打电话的日期
        if(mDayMap.get(date.getDay()) == null){
            System.out.println("date.getDay():"+date.getDay());
        }
        return sfd1.format(date)+" "+mDayMap.get(date.getDay())+" "+sfd2.format(date);
    }

    public static String getRecordItemData(long source){
        Calendar cal = Calendar.getInstance();
        int year =cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        Date recordDate = new Date(source);

        //System.out.println(month +" "+recordDate.getMonth());
        if((recordDate.getYear()-100+2000) == year){
            if(recordDate.getMonth() == month){
                if(recordDate.getDate() == date){
                    if(recordDate.getHours() == hour){
                        if(recordDate.getMinutes() == minute){
                            return "刚刚";
                        }else{
                            return String.valueOf(minute - recordDate.getMinutes())+"分前";
                        }
                    }else{
                        return String.valueOf(hour-recordDate.getHours())+"小时前";
                    }
                }
                else if((date - recordDate.getDate()) < 2){
                    SimpleDateFormat sfd = new SimpleDateFormat("HH:mm");
                    return "昨天";
                }
                else if((date - recordDate.getDate()) < 8){
                    return String.valueOf(date - recordDate.getDate())+"天前";
                }
                else{
                    SimpleDateFormat sfd = new SimpleDateFormat("MM-dd HH:mm");
                    return sfd.format(recordDate);
                }
            }
            else{
                SimpleDateFormat sfd = new SimpleDateFormat("MM-dd HH:mm");
                return sfd.format(recordDate);
            }

        }else{
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sfd.format(recordDate);
        }
    }
}
