package android.mdy.com.broadcastreceiverexam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

    }

    private void setViews(){
        txtStatus = (TextView) findViewById(R.id.txtStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(mBRBattery, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBRBattery);
    }

    BroadcastReceiver mBRBattery = new BroadcastReceiver() {
        int count = 0;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            count++;
            if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
                onBatteryChanged(intent);
            }
            if(action.equals(Intent.ACTION_BATTERY_LOW)){
                Toast.makeText(context, "배터리 위험 수준", Toast.LENGTH_SHORT).show();
            }
            if(action.equals())
        }
    }
}
