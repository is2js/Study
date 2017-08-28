package android.mdy.com.notificationexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnNapAlarm, btnCustomNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();

    }

    private void setViews(){
        btnNapAlarm = (Button) findViewById(R.id.btnNapAlarm);
        btnCustomNoti = (Button) findViewById(R.id.btnCustomNoti);
    }

    private void setListeners(){
        btnNapAlarm.setOnClickListener(this);
        btnCustomNoti.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnNapAlarm:
                intent = new Intent(MainActivity.this, NapAlarmActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCustomNoti:
                intent = new Intent(MainActivity.this, CustomNotiViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
