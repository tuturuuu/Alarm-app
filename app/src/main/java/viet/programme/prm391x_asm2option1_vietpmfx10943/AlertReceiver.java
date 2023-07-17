package viet.programme.prm391x_asm2option1_vietpmfx10943;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AlertReceiver extends BroadcastReceiver {

    public static final String TYPE_ALARM ="TYPE_ALARM";
    public static final String TYPE_SMS = "TYPE_SMS";
    public static final String TYPE_PHONE  ="TYPE_PHONE";
    public static final String KEY_TYPE ="KEY_TYPE";
    public static final String KEY_MSG = "KEY_MSG";
    public static final String KEY_ID ="KEY_ID";
    private Context mContext;


    /**
     * Chay method tuy theo fragment
     */
    private final Handler handler = new Handler(msg->{
        if(msg.what ==1 ){
            Object[] data = (Object[]) msg.obj;
            if(data[0].equals(TYPE_PHONE)){
                makeCall((Object[]) msg.obj);
            }
            else if(data[0].equals(TYPE_SMS)){
                sendSMS((Object[]) msg.obj);
            }
            else {
                makeAnAlarm((Object[]) msg.obj);
            }
        }

        return false;
    });
    int index;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Lay data tu intent
        Bundle bundle = intent.getExtras();
        String type =bundle.getString("TYPE");
        String sender =bundle.getString("SENDER");
        String msg = bundle.getString("MSG");
        index = bundle.getInt("INDEX");
        long time = bundle.getLong("TIME");
        Object[] data = {type,sender,msg,time};
        handler.sendMessage(Message.obtain(handler,1,data));
        mContext = context;

    }

    /**
     * Tao mot cuoc goi tu data duoc input
     * @param objects
     */
    private void makeCall(Object[] objects) {
        Log.d("TAG","Call");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+objects[1]));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.getApplicationContext().startActivity(intent);
    }

    /**
     * Gui tin nhan giua tren data duoc input
     * @param objects
     */
    private void sendSMS(Object[] objects){
        Log.d("TAG","SMS");
        sendMessage((String) objects[1],(String) objects[2]);

    }

    /**
     * Tao 1 cuoc goi
     * @param phoneNumber
     * @param msg
     */
    private void sendMessage(String phoneNumber, String msg){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,msg,null,null);
    }

    /**
     * Tao alarm giua tren data duoc input
     * @param objects
     */
    private void makeAnAlarm(Object[] objects){
        Log.d("TAG","Alarm");
        Intent intent = new Intent(mContext,AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_TYPE,TYPE_ALARM);
        intent.putExtra(KEY_MSG,(String) objects[2]);
        intent.putExtra(KEY_ID,KEY_ID);
        mContext.startActivity(intent);
        List<AlarmEntity> listData = new ArrayList<>();
        String txtData = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE).getString(M005ListAlarmFrg.KEY_DATA,null);
        AlarmEntity[] arrAlarm = new Gson().fromJson(txtData,AlarmEntity[].class);
        listData.addAll(Arrays.asList(arrAlarm));
        arrAlarm[index].setDone(true);
        SharedPreferences pref = mContext.getSharedPreferences("pref",Context.MODE_PRIVATE);
        pref.edit().putString(M005ListAlarmFrg.KEY_DATA, new Gson().toJson(listData)).apply();

    }


}
