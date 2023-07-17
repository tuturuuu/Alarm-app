package viet.programme.prm391x_asm2option1_vietpmfx10943;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class M001ActMenu extends AppCompatActivity {
    private DrawerLayout navView;
    private static final int SEND_MESSAGE = 1;
    private static final int CALL = 2;
    private static final int SCHEDULE_EXACT_ALARM = 3;
    ImageView ivAddAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hoi permission
        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SEND_MESSAGE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m001_act_menu);
        initMenu();
    }

    /**
     *  Tao tool bar va navigation drawer
     */
    private void initMenu(){
        //Tao toolbar va icon menu cho activity
        ivAddAlarm = findViewById(R.id.ivAddAlarm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //App bat dau tu fragment M002SMSFrg
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.frgPlaceHolder,new M002SMSFrg())
                .commit();

        ivAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                M005ListAlarmFrg m005ListAlarmFrg = (M005ListAlarmFrg) getSupportFragmentManager().findFragmentByTag("m005");
                if(m005ListAlarmFrg!=null&& m005ListAlarmFrg.isVisible()) {
                    manager.beginTransaction()
                            .replace(R.id.frgPlaceHolder, new M004ALARMFrg())
                            .commit();
                }
                else {
                    manager.beginTransaction()
                            .replace(R.id.frgPlaceHolder, new M005ListAlarmFrg(), "m005")
                            .commit();
                }
            }
        });


        //Khoi tao navigation drawer
        navView = findViewById(R.id.act1);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(item -> {
            FragmentManager manager1 = getSupportFragmentManager();
            if(item.getItemId()==R.id.nav_CALL){
                    //Hoi nguoi dung permission
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL);
                    if (ActivityCompat.checkSelfPermission(M001ActMenu.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(M001ActMenu.this,"The permission is needed",Toast.LENGTH_LONG).show();
                        return true;
                }
                //Doi placeHolder -> M003PhoneFrg
                manager1.beginTransaction()
                        .replace(R.id.frgPlaceHolder, new M003PhoneFrg())
                        .commit();
                //Thay doi title
                toolbar.setTitle("Pending Call");
               ivAddAlarm.setVisibility(View.GONE);
            }
            //Doi placeHolder -> M002SMSFrg
            else if(item.getItemId()==R.id.nav_SMS){
                manager1.beginTransaction()
                        .replace(R.id.frgPlaceHolder, new M002SMSFrg())
                        .commit();
                //Thay doi title
                toolbar.setTitle("SMS Schedule");
                ivAddAlarm.setVisibility(View.GONE);
            }
            //Doi placeHolder -> M004ALARMFrg
            else {
                manager1.beginTransaction()
                        .replace(R.id.frgPlaceHolder, new M005ListAlarmFrg(), "m005")
                        .commit();
                //Thay doi title
                toolbar.setTitle("Alarm");
                ivAddAlarm.setVisibility(View.VISIBLE);
            }
            //Dong drawer khi nguoi dung chon 1 item
            item.setChecked(true);
            navView.closeDrawers();
            return true;
        });
    }

    /**
     * Khi nguoi dung nhan nut home -> openDrawer
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
                navView.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Xu li permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SEND_MESSAGE) {
            if (ActivityCompat.checkSelfPermission(M001ActMenu.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
                    Toast.makeText(M001ActMenu.this,"The permission is needed",Toast.LENGTH_LONG).show();
                    navView.openDrawer(GravityCompat.START);
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SEND_MESSAGE);
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}









