package android.mdy.com.samplereceiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsActivity extends AppCompatActivity {

    private EditText editNumber, editContent, editReceptionTime;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        setViews();
        setListeners();

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent != null){
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String receivedDate = intent.getStringExtra("receivedDate");

            editNumber.setText(sender);
            editContent.setText(contents);
            editReceptionTime.setText(receivedDate);
        }
    }

    private void setListeners() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setViews() {
        editNumber = (EditText) findViewById(R.id.editNumber);
        editContent = (EditText) findViewById(R.id.editContent);
        editReceptionTime = (EditText) findViewById(R.id.editReceptionTime);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
    }
}
