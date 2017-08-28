package android.mdy.com.notificationexam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class CustomNotiViewActivity extends AppCompatActivity implements View.OnClickListener{

    static final int NAP_NOTI = 1;
    NotificationManager mNotiManager;
    Button btnCustomStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_noti_view);
        setViews();
        setListeners();

        mNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    private void setViews(){
        btnCustomStart = (Button) findViewById(R.id.btnCustomStart);
    }

    private void setListeners(){
        btnCustomStart.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCustomStart:
                customTask(view);
                break;
        }
    }

    private void customTask(View view){
        Toast.makeText(CustomNotiViewActivity.this, "안녕히 주무세요", Toast.LENGTH_SHORT).show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CustomNotiViewActivity.this, NapEndActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent content = PendingIntent.getActivity(CustomNotiViewActivity.this, 0, intent, 0);

                RemoteViews napView = new RemoteViews(getPackageName(), R.layout.customnotiview);

                Notification noti = new Notification.Builder(CustomNotiViewActivity.this)
                        .setTicker("일어나세요")
                        .setSmallIcon(R.drawable.boy)
                        .setContentIntent(content)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setVibrate(new long[] {1000, 1000, 500, 500, 200, 200, 200, 200, 200, 200})
                        .setLights(0xff00ff00, 500, 500)
                        .setContent(napView)
                        .build();

                noti.flags |= (Notification.FLAG_INSISTENT | Notification.FLAG_SHOW_LIGHTS);

                mNotiManager.notify(NapAlarmActivity.NAP_NOTI, noti);
            }
        }, 5*1000);
    }
}
