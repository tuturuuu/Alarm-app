package viet.programme.prm391x_asm2option1_vietpmfx10943;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class M003PhoneFrg extends Fragment {
    private final Calendar CAL = Calendar.getInstance();
    private  Context mContext;
    Button btCallSetup;
    EditText etNumberCall;
    TextView tvCallTime;

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
        View v =inflater.inflate(R.layout.fragment_m003_phone_frg, container, false);
        initView(v);
        return v;
    }

    /**
     * Khoi tao cac bien
     * @param view
     */
    private void initView(View view) {
        btCallSetup=view.findViewById(R.id.btCallSetup);
        tvCallTime =view.findViewById(R.id.tvCallTime);
        etNumberCall = view.findViewById(R.id.etNumberCall);
        tvCallTime.setOnClickListener(v -> initDate());
        btCallSetup.setOnClickListener(v -> setupCall());

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
        tvCallTime.setText(simpleDateFormat.format(CAL.getTime()));
        tvCallTime.setTag(CAL.getTimeInMillis());
    }

    /**
     * Su dung AlarmManager de chay task trong 1 thoi gian nhat dinh
     */
    private void setupCall(){
        Toast.makeText(getActivity(),"A message will be sent soon",Toast.LENGTH_LONG).show();
        //Khoi tao AlarmManager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        //Truyen data vao trong intent
        Intent intent = new Intent(getActivity(),AlertReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString("TYPE",AlertReceiver.TYPE_PHONE);
        bundle.putString("SENDER", etNumberCall.getText().toString());
        bundle.putLong("TIME",(long) tvCallTime.getTag());
        intent.putExtras(bundle);
        //Tao pendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),3,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //Chay task bao 1 thoi gian nhat dinh
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,(long)tvCallTime.getTag(),pendingIntent);
    }


}