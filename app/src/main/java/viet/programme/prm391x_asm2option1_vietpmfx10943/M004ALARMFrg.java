package viet.programme.prm391x_asm2option1_vietpmfx10943;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class M004ALARMFrg extends Fragment {
    private final Calendar CAL = Calendar.getInstance();
    private Context mContext;
    private static int id;

    Button btAlarmSetup;
    EditText etAlarmMessage;
    TextView tvAlarmTime;

    /**
     * Lay context cua Activity
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_m004_a_l_a_r_m_frg, container, false);
        id = initId();
        initView(v);
        return v;
    }

    private int initId(){
        SharedPreferences pref = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return pref.getInt("ID",4);
    }

    private void saveId(){
        SharedPreferences pref = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        pref.edit().putInt("ID", id).apply();
    }


    /**
     * Khoi tao ngay thang nam Calendar
     */
    private void initDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            new DatePickerDialog(mContext,(view, year, month, dayOfMonth) ->{
                CAL.set(Calendar.YEAR,year);
                CAL.set(Calendar.MONTH, month);
                CAL.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                initTime();

            }, CAL.get(Calendar.YEAR),CAL.get(Calendar.MONTH),CAL.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    /**
     * Khoi tao cac bien
     * @param view
     */
    private void initView(View view) {
        btAlarmSetup=view.findViewById(R.id.btAlarmSetup);
        etAlarmMessage =view.findViewById(R.id.etAlarmMessage);
        tvAlarmTime =view.findViewById(R.id.tvAlarmTime);
        tvAlarmTime.setOnClickListener(v -> initDate());
        btAlarmSetup.setOnClickListener(v->setupAlarm());

    }

    /**
     * Khoi tao gio phut cho Calendar
     */
    private void initTime() {
        new TimePickerDialog(mContext,(view, hourOfDay, minute)->{
            CAL.set(Calendar.HOUR_OF_DAY,hourOfDay);
            CAL.set(Calendar.MINUTE, minute);
            updateLabel();
        },CAL.get(Calendar.HOUR_OF_DAY),CAL.get(Calendar.MINUTE), true).show();
    }

    /**
     * Hien thi thoi gian theo format
     */
    String time;
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tvAlarmTime.setText(simpleDateFormat.format(CAL.getTime()));
        tvAlarmTime.setTag(CAL.getTimeInMillis());
        time = simpleDateFormat.format(CAL.getTime());
    }


    /**
     * Su dung AlarmManager de chay task trong 1 thoi gian nhat dinh
     */

    private void setupAlarm(){
        AlarmEntity alarm = new AlarmEntity(id,CAL.get(CAL.YEAR),CAL.get(CAL.MONTH),CAL.get(CAL.DAY_OF_MONTH), CAL.get(CAL.HOUR), CAL.get(CAL.MINUTE),
                etAlarmMessage.getText().toString(),time, false, (long) tvAlarmTime.getTag());
        int index = saveData(alarm);
        Toast.makeText(getActivity(),"A alarm will be made soon",Toast.LENGTH_LONG).show();
        //Khoi tao AlarmManager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        //Truyen data vao trong intent
        Intent intent = new Intent(getActivity(),AlertReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString("TYPE",AlertReceiver.TYPE_ALARM);
        bundle.putString("MSG",etAlarmMessage.getText().toString());
        bundle.putInt("INDEX",index);
        intent.putExtras(bundle);
        //Tao pendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //Sau moi lan setup tang id len 1 don vi
        id++;
        saveId();

        //Chay task bao 1 thoi gian nhat dinh
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,(long)tvAlarmTime.getTag(),pendingIntent);

    }

    /**
     * Set up data
     * @param entity
     * @return
     */
    private int saveData(AlarmEntity entity){
        List<AlarmEntity> listData = new ArrayList<>();
        String txtData = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE).getString(M005ListAlarmFrg.KEY_DATA,null);
        if(txtData==null){
            listData.add(entity);
        } else {
            AlarmEntity[] arrAlarm = new Gson().fromJson(txtData,AlarmEntity[].class);
            listData.addAll(Arrays.asList(arrAlarm));
            listData.add(entity);
        }
        SharedPreferences pref = mContext.getSharedPreferences("pref",Context.MODE_PRIVATE);
        pref.edit().putString(M005ListAlarmFrg.KEY_DATA, new Gson().toJson(listData)).apply();
        return listData.size()-1;
    }



}