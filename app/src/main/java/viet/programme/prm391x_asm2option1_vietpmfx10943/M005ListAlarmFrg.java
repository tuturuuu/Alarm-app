package viet.programme.prm391x_asm2option1_vietpmfx10943;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class M005ListAlarmFrg extends Fragment {
    private Context mContext;
    private RecyclerView rvAlarm;
    public static final String KEY_DATA = "KEY_DATA";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_m005_list_alarm_frg, container, false);
        initView(v);
        return v;
    }

    /**
     * Lam tuong tu code mau
     * @param v
     */
    private void initView(View v) {

        String txtData = mContext.getSharedPreferences("pref",Context.MODE_PRIVATE).getString(KEY_DATA, null);
        if(txtData==null) return;

        AlarmEntity[] arrAlarm = new Gson().fromJson(txtData, AlarmEntity[].class);
        List<AlarmEntity> listData = new ArrayList<>(Arrays.asList(arrAlarm));
        AlarmAdapter alarmAdapter = new AlarmAdapter(mContext, listData);
        rvAlarm = v.findViewById(R.id.rv_alarm);
        rvAlarm.setLayoutManager(new LinearLayoutManager(mContext));
        rvAlarm.setAdapter(alarmAdapter);
    }


}