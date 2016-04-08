package com.example.sysucjl.familytelephonedirectory.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sysucjl on 16-3-26.
 */
public class RecordItem {
    int type;// 类型
    long CallTime;// 打电话时间
    long Duration;//通话时长
    String Number = null;// 电话号码
    String Name = null;// 姓名
    List<RecordSegment> recordSegments;

    public RecordItem(int type, long CallTime, long Duration, String Number, String Name){
        this.type = type;
        this.CallTime = CallTime;
        this.Duration = Duration;
        this.Number = Number;
        this.Name = Name;
        this.recordSegments = new ArrayList<>();
        recordSegments.add(new RecordSegment(type, CallTime, Duration));
    }

    public int getType(){
        return type;
    }

    public long getCallTime(){
        return CallTime;
    }

    public long getDuration(){
        return Duration;
    }

    public String getNumber(){
        return Number;
    }

    public String getName(){
        return Name;
    }

    public List<RecordSegment> getRecordSegments() {
        return recordSegments;
    }

    public void setRecordSegments(List<RecordSegment> recordSegments) {
        this.recordSegments = recordSegments;
    }

    public void addRecordSegement(int type, long callTime, long duration){
        RecordSegment recordSegment = new RecordSegment(type, callTime, duration);
        this.recordSegments.add(recordSegment);
    }

}
