package com.mdy.android.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    WebView webView;
    Button btnPre, btnBack, btnGo;
    EditText editTextUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        btnPre = (Button) findViewById(R.id.btnBack);
        btnBack = (Button) findViewById(R.id.btnPre);
        btnGo = (Button) findViewById(R.id.btnGo);
        editTextUrl = (EditText) findViewById(R.id.editTextUrl);


        btnBack.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnGo.setOnClickListener(this);



        // script 사용 설정 (필수) - 페이지내의 javascript가 동작하도록 한다.
        webView.getSettings().setJavaScriptEnabled(true);

        // 1. 웹뷰 클라이언트를 지정... (안하면 내장 웹브라우저가 팝업된다.) . 스마트폰에 있는 웹브라
        // http로 시작하는 주소를 실행시킬 수 있게 해준다.
        webView.setWebViewClient(new WebViewClient());

        // 2. 둘다 세팅할것 : 프로토콜에 따라 클라이언트가 선택되는 것으로 파악됨...
        // https로 시작하는 주소를 실행시킬 수 있게 해준다.
        webView.setWebChromeClient(new WebChromeClient());

        // URL 호출하기
//        loadUrl(editTextUrl.getText().toString());

    }

    private void loadUrl(String url){
        // 문자열의 앞에 프로토콜인 http 문자열이 없다면 붙여준다.
        if(!url.startsWith("http")){  //
            url = "http://" + url;
        }
        // url 호출하기
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPre:  // 뒤로 가기
                if(webView.canGoBack()) {// 내가 뒤로 갈 수 있는지 확인(마지막페이지인지 확인)
                    webView.goBack();
                }
                break;
            case R.id.btnBack:   // 앞으로 가기
                if(webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.btnGo:
                // url 이동
                String temp = editTextUrl.getText().toString();
                if (!"".equals(temp)) {  // 공백이 아닐경우 처리
                    // or 정규식으로 url 패턴일때만 처리

                    loadUrl(temp);
                }

                break;
            default:
                break;
        }

    }
}
