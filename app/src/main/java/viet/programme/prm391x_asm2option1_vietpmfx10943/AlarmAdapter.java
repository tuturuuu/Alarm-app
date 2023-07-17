package viet.programme.prm391x_asm2option1_vietpmfx10943;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private final Context mContext;
    private final List<AlarmEntity> alarmEntityList;

    public AlarmAdapter(Context mContext, List<AlarmEntity> alarmEntityList) {
        this.mContext = mContext;
        this.alarmEntityList = alarmEntityList;
    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item =LayoutInflater.from(mContext).inflate(R.layout.item_alarm,parent,false);
        AlarmHolder alarmHolder =  new AlarmHolder(item);
        //Tao dialog
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Alarm");
                builder.setMessage(alarmHolder.tvMessageTitle.getText().toString());
                //Add button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Lay position qua method getAdapterPosition va xoa du lieu trong getSharedReference + AlarmEntityList
                        alarmEntityList.remove(alarmHolder.getAdapterPosition());
                        notifyItemRemoved(alarmHolder.getAdapterPosition());
                        notifyItemRangeChanged(alarmHolder.getAdapterPosition(),alarmEntityList.size());
                        SharedPreferences pref = mContext.getSharedPreferences("pref",Context.MODE_PRIVATE);
                        pref.edit().putString(M005ListAlarmFrg.KEY_DATA, new Gson().toJson(alarmEntityList)).apply();
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return alarmHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        AlarmEntity alarmEntity = this.alarmEntityList.get(position);
        holder.tvDate.setText(alarmEntity.getTime());
        holder.tvMessageTitle.setText(alarmEntity.getMessage());
        if (alarmEntity.isDone())
        {
            holder.ivIsDone.setColorFilter(mContext.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return alarmEntityList.size();
    }

    public class AlarmHolder extends RecyclerView.ViewHolder{
        TextView tvDate, tvMessageTitle;
        ImageView ivIsDone;
        int id;
        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvMessageTitle = itemView.findViewById(R.id.tvMessageTitle);
            this.ivIsDone = itemView.findViewById(R.id.ivIsDone);
        }
    }
}
