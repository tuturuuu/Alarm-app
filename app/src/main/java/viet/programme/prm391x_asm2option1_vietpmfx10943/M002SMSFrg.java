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



public class M002SMSFrg extends Fragment {
    private Context mContext;
    private final Calendar CAL = Calendar.getInstance();
    Button btMessageButton;
    EditText etNumber, etMessage;
    TextView tvMessageTime;



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m002_s_m_s_frg, container, false);
        initView(view);
        return view;
    }

    /**
     * Khoi tao cac bien
     * @param view
     */
    private void initView(View view) {
        btMessageButton=view.findViewById(R.id.btMessageSetup);
        etMessage =view.findViewById(R.id.etMessage);
        tvMessageTime =view.findViewById(R.id.tvMessageTime);
        etNumber = view.findViewById(R.id.etNumber);
        tvMessageTime.setOnClickListener(v -> initDate());
        btMessageButton.setOnClickListener(v->setupSMS());
    }

    /**
     * Khoi tao ngay thang nam Calendar
     */
    private void initDate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            new DatePickerDialog(mContext,(view,year,month,dayOfMonth) ->{
               CAL.set(Calendar.YEAR,year);
               CAL.set(Calendar.MONTH, month);
               CAL.set(Calendar.DAY_OF_MONTH,dayOfMonth);

               initTime();

            }, CAL.get(Calendar.YEAR),CAL.get(Calendar.MONTH),CAL.get(Calendar.DAY_OF_MONTH)).show();
        }
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
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tvMessageTime.setText(simpleDateFormat.format(CAL.getTime()));
        tvMessageTime.setTag(CAL.getTimeInMillis());

    }

    /**
     * Su dung AlarmManager de chay task trong 1 thoi gian nhat dinh
     */
    private void setupSMS(){
        Toast.makeText(getActivity(),"A message will be made soon",Toast.LENGTH_LONG).show();
        //Khoi tao AlarmManager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        //Truyen data vao trong intent
        Intent intent = new Intent(getActivity(),AlertReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString("TYPE",AlertReceiver.TYPE_SMS);
        bundle.putString("SENDER", etNumber.getText().toString());
        bundle.putString("MSG",etMessage.getText().toString());
        bundle.putLong("TIME",(long) tvMessageTime.getTag());
        intent.putExtras(bundle);
        //Tao pendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //Chay task bao 1 thoi gian nhat dinh
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,(long)tvMessageTime.getTag(),pendingIntent);
    }


}