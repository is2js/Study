package com.mdy.android.intentbasic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtCall, txtUrl;
    Button btnCall, btnBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCall = (EditText) findViewById(R.id.txtCall);
        btnCall = (Button) findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {   // inputType 속성을 숫자로만 해주면 숫자만 입력하게 된다.
            @Override
            public void onClick(View v) {
                String phoneNumber = txtCall.getText().toString();   // getText()가 EditText를 반환하기 때문에 toString()으로 해줘야한다.
                Uri uri = Uri.parse("tel:" + phoneNumber); // parse라는 것은 스트링으로 되어 있는 것을 객체화 시켜주는 것.
                                                            // parse와 반대되는 것이 toString();
                                                            // tel:  이 글씨는 바뀌면 안된다.
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                // Uri는 안드로이드에 최적화되어있는 자원을 가리키는 주소체계
                // 안드로이드의 자원은 리소드 폴더에 있는 모든 것이 자원이고, 사진을 캡쳐한 다음 갤리러로 넘어가는데 이것도 자원이다.
                    // ACTION_DIAL  묵시적으로 정해진 값
                startActivity(intent);
            }
        });

        txtUrl = (EditText) findViewById(R.id.txtUrl);
        btnBrowser = (Button) findViewById(R.id.btnBrowser);
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = txtUrl.getText().toString();
                Uri uri = Uri.parse("http://" + phoneNumber);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
