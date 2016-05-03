package com.example.sysucjl.familytelephonedirectory.tools;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * Created by sysucjl on 16-4-19.
 */
public class PhoneListener extends BroadcastReceiver {

    private BlackListOptionManager blackListOptionManager;
    private Context mcontext;

    public void onReceive(Context context, Intent intent) {
        mcontext = context;
        blackListOptionManager = new BlackListOptionManager(context);
        String action = intent.getAction();
        Log.i("PhoneListener", "ord:" + isOrderedBroadcast() + " act:" + action);
        if (action.equals("android.intent.action.NEW_OUTGOING_CALL")) {
            Log.i("PhoneListener", "Outgoing");
        } else {
            Log.i("PhoneListener", "InComing");
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            boolean incomingFlag = false;
            String incoming_number = "";
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incomingFlag = true;// 标识当前是来电
                    incoming_number = intent.getStringExtra("incoming_number");
                    Log.i("PhoneListener", "RINGING :" + incoming_number);
                    //Intent tmpI = new Intent(context, ShowAct.class);
                    //tmpI.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //context.startActivity(tmpI);
                    if (incoming_number!=null && blackListOptionManager.isBlackNumber(incoming_number)){
                        endCall(context, incoming_number);
                        context.getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, new MyObserver(new Handler(), incoming_number));
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingFlag) {
                        Log.i("PhoneListener", "incoming ACCEPT :" + incoming_number);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (incomingFlag) {
                        Log.i("PhoneListener", "incoming IDLE");
                    }
                    break;
            }
        }
    }

    private void endCall(Context context,String phnum){
        TelephonyManager telMag = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Method mthEndCall = null;
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        try {
            mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
            mthEndCall.setAccessible(true);
            ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag, (Object[]) null);
            iTel.endCall();
            /*
            if(iTel!=null){
                iTel.cancelMissedCallsNotification();
            }
            else {
                Log.i("PhoneListener", "Telephony service is null, can't call " + "cancelMissedCallsNotification");
            }
            */
            Log.i("PhoneListener", iTel.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Log.i("PhoneListener", "endCall test");
    }

    class MyObserver extends ContentObserver {
        private String num;//黑名单号码

        public MyObserver(Handler handler,String num) {
            super(handler);
            this.num=num;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            ContactOptionManager contactOptionManager = new ContactOptionManager();
            contactOptionManager.deleteRecordByNumber(mcontext, num);
            //刪除完通话记录之后，对内容观察者进行反注册
            mcontext.getContentResolver().unregisterContentObserver(this);
        }
    }
}
