package viet.programme.prm391x_asm2option1_vietpmfx10943;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends AppCompatActivity {
    TextView tvTitle;
    Button btQuit;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initView();
        //Bat va lap lai am thanh
        player.start();
        player.setLooping(true);
    }

    /**
     * Khoi tao cac bien
     */
    private void initView(){
        tvTitle = findViewById(R.id.tvTitle);
        btQuit = findViewById(R.id.btQuit);
        player = MediaPlayer.create(AlarmActivity.this,R.raw.alarm_sound);
        //Lay data tu class alertReceiver
        Intent intent = getIntent();
        if(intent.getExtras().getString("KEY_ID").equals(AlertReceiver.KEY_ID)){
            tvTitle.setText(intent.getExtras().getString("KEY_MSG"));
        }
        //Quay ve man hinh 1 khi nguoi dung nhan quit
        btQuit.setOnClickListener(view -> {
            player.stop();
            Intent i = new Intent(AlarmActivity.this, M001ActMenu.class);
            startActivity(i);
        });

    }
}