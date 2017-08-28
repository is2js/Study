package android.mdy.com.notificationexam;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NapEndActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnNapEnd;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_end);
        setViews();
        setListeners();

        NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NM.cancel(NapAlarmActivity.NAP_NOTI);

    }

    private void setViews(){
        btnNapEnd = (Button) findViewById(R.id.btnNapEnd);
        textView = (TextView) findViewById(R.id.textView);
    }

    private void setListeners(){
        btnNapEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNapEnd:
                finish();
                break;
        }
    }
}
