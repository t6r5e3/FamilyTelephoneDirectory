package com.example.sysucjl.familytelephonedirectory.tools;

/**
 * Created by Administrator on 2016/4/12.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/4/7.
 */
public class GetAddress {
    String phoneNum;
    public String query(String num,int typeOfNumber) throws Exception {
        phoneNum=num;
        String theUrl = String.format("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobilecode=%s&userId=", num);
        String theUrl2=String.format("http://tel.51240.com/%s__tel/", num);
        // String theUrl3=String.format("http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theCityCode=%s&theUserID=",num);
        //设置URL
        URL url = new URL(theUrl);
        URL url2 = new URL(theUrl2);
        // URL url3 = new URL(theUrl3);
        HttpURLConnection conn = null;
        if(typeOfNumber==1) {
            conn = (HttpURLConnection) url.openConnection();
        }
        else if(typeOfNumber==2){
            conn = (HttpURLConnection) url2.openConnection();
        }

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        //Log.i("FangXiaowei", "查询网络");
        if (conn.getResponseCode() == 200)
            return parse(conn.getInputStream(),typeOfNumber);
        return null;
    }
    //解析流拿到getMobileCodeInfoResult中的数据
    private String parse(InputStream inputStream,int typeOfNumber) throws Exception {
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        StringBuilder response2 = new StringBuilder();
        String line;


        if (typeOfNumber == 1) {
            //修改手机格式排版
            line = reader.readLine();//懒得理第一句
            line = reader.readLine();
            response.append(line);

            String temp = response.toString();
            Log.i("FangXiaowei",temp);

            int countBlank = 0;
            int i;
            int beginIndex = 0;
            int middleIndex = 0;
            int endIndex = 0;
            String convince;
            String city;
            String carrieroperator = "";
            for (i = 0; i < temp.length() - 2; i++) {
                if (temp.charAt(i) == '：')
                    beginIndex = i + 1;
                if (temp.charAt(i) == ' ') {
                    countBlank++;
                    if (countBlank == 2)
                        middleIndex = i;
                    if (countBlank == 3)
                        endIndex = i;
                }
                if (temp.charAt(i) == '移') {
                    carrieroperator = "移动";
                    break;
                } else if (temp.charAt(i) == '联') {
                    carrieroperator = "联通";
                    break;
                } else if (temp.charAt(i) == '电') {
                    carrieroperator = "电信";
                    break;
                }
                //如果判断没有该手机或者号码有错，则去查询固话
                if(temp.charAt(i)=='没'&&temp.charAt(i+1)=='有'||temp.charAt(i)=='错'&&temp.charAt(i+1)=='误'){
                    typeOfNumber=2;
                    return query(phoneNum,typeOfNumber);
                }
            }

            convince = temp.substring(beginIndex, middleIndex);
            city = temp.substring(middleIndex + 1, endIndex);
            String res = convince;
            if (!convince.equals(city))
                res += city;
            res+=" ";
            res += carrieroperator;
            return res;
        }

        if (typeOfNumber == 2) {
            //固话板式
            int countLine=0;
            while(countLine<=13){
                line=reader.readLine();
                countLine++;
            }
            //在第15行
            line=reader.readLine();
            response2.append(line);
            String temp = response2.toString();
            //Log.i("FangXiaowei",temp);

            boolean beginRead = false;
            int beginIndex2 = 0;
            int middleIndex2 = 0;
            int endIndex2 = 0;
            String convince2;
            String city2;

            for (int j = 0; j < temp.length()-4; j++) {
                if(temp.charAt(j)=='无'&&temp.charAt(j+1)=='法'&&temp.charAt(j+2)=='识'&&temp.charAt(j+3)=='别')
                    return " 无法识别";
                if (temp.charAt(j) == '于') {
                    beginRead = true;
                    beginIndex2 = j + 1;
                }
                if (beginRead) {
                    if (temp.charAt(j) == ' ')
                        middleIndex2 = j;
                    if (temp.charAt(j) == '的') {
                        endIndex2 = j;
                        break;
                    }
                }
            }
            convince2 = temp.substring(beginIndex2, middleIndex2);
            city2 = temp.substring(middleIndex2 + 1, endIndex2);
            String res2 = convince2;
            if (!convince2.equals(city2))
                res2 += city2;
            res2 += "固话";

            return res2;
        }
        return " 无法识别";
    }
}