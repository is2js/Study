package android.mdy.com.broadcastreceiverexam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
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

    private void setViews() {
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
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                onBatteryChanged(intent);
            }
            if (action.equals(Intent.ACTION_BATTERY_LOW)) {
                Toast.makeText(context, "배터리 위험 수준", Toast.LENGTH_SHORT).show();
            }
            if (action.equals(Intent.ACTION_BATTERY_OKAY)) {
                Toast.makeText(context, "배터리 양호", Toast.LENGTH_SHORT).show();
            }
            if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(context, "전원 연결됨", Toast.LENGTH_SHORT).show();
            }
            if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Toast.makeText(context, "전원 분리됨", Toast.LENGTH_SHORT).show();
            }
        }

        public void onBatteryChanged(Intent intent) {
            int plug, status, scale, level, ratio;
            String sPlug = "";
            String sStatus = "";

            if (intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) == false) {
                txtStatus.setText("배터리 없음");
                return;
            }

            plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            ratio = level * 100 / scale;

            switch (plug) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    sPlug = "AC";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    sPlug = "USB";
                    break;
                default:
                    sPlug = "Battery";
                    break;
            }

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    sStatus = "충전중";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    sStatus = "충전중 아님";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    sStatus = "방전중";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    sStatus = "만충전";
                    break;
                default:
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    sStatus = "알 수가 없어";
                    break;
            }

            String str = String.format("수신 회수: %d\n연결: %s\n상태: %s\n레벨: %d%%", count, sPlug, sStatus, ratio);
            txtStatus.setText(str);
        }
    };

}
