package android.mdy.com.notificationexam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NapAlarmActivity extends AppCompatActivity implements View.OnClickListener{

    static final int NAP_NOTI = 1;
    NotificationManager mNotiManager;
    Button btnNapStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_alarm);
        setViews();
        setListeners();


        mNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private void setViews(){
        btnNapStart = (Button) findViewById(R.id.btnNapStart);
    }

    private void setListeners(){
        btnNapStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNapStart:
                napTask(view);
                break;
        }
    }

    private void napTask(View view){
        Toast.makeText(NapAlarmActivity.this, "안녕히 주무세요", Toast.LENGTH_SHORT).show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(NapAlarmActivity.this, NapEndActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent content = PendingIntent.getActivity(NapAlarmActivity.this, 0, intent, 0);

                Notification noti = new Notification.Builder(NapAlarmActivity.this)
                        .setTicker("일어나세요")
                        .setContentTitle("기상 시간")
                        .setContentText("일어나! 일할 시간이야.")
                        .setSubText("일을 해야 돈을 벌고 돈을 벌어야 밥먹고 살지!!")
                        .setSmallIcon(R.drawable.bell)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.boy))
                        .setContentIntent(content)
                        .build();

                        mNotiManager.notify(NapAlarmActivity.NAP_NOTI, noti);
            }
        }, 5 * 1000);
    }
}
