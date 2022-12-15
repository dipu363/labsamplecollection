package lab.com.sa.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lab.com.sa.R;
import lab.com.sa.fragment.DoneSampleListFragment;
import lab.com.sa.fragment.SampleListFragment;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd,btnPending,btnDone;
    TextView battery;
    BroadcastReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("All Sample");
        btnAdd = findViewById(R.id.btn_add_sample);
        btnPending = findViewById(R.id.btn_pending_sample);
        btnDone = findViewById(R.id.btn_done_sample);
        battery = findViewById(R.id.text_battery_id);
        btnAdd.setOnClickListener(this);
        btnPending.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        //battery sensor
        batteryReceiver  = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double batteryLevel= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                double levelScale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
                int batteryPercent = (int) Math.floor(batteryLevel/levelScale*100); //4
                battery.setText("Battery Charge "+batteryPercent+" %");
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,new SampleListFragment())
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_sample:
                Intent intent = new Intent(DashboardActivity.this,AddTestSampleActivity.class);
                startActivity(intent);
                break;

                case R.id.btn_pending_sample:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,new SampleListFragment())
                        .commit();
                break;  case R.id.btn_done_sample:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,new DoneSampleListFragment())
                        .commit();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null)
            unregisterReceiver(batteryReceiver);

    }
}