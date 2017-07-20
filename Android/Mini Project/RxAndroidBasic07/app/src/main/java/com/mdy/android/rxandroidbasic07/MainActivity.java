package com.mdy.android.rxandroidbasic07;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(editEmail);
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(editPassword);

        Observable.combineLatest(idEmitter, pwEmitter,
                (idEvent, pwEvent) -> {
                    boolean checkId = idEvent.text().length() >= 5;
                    boolean checkPw = pwEvent.text().length() >= 8;
                    return checkId && checkPw && isValidEmail(idEvent.text().toString());
                }
        ).subscribe(
            flag -> btnSign.setEnabled(flag)
        );
    }

    // 이메일 유효성 검사
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    private void setViews() {
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setEnabled(false);
    }
}
