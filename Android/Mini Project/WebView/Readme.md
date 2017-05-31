# WebView
웹뷰(WebView) 사용법을 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/WebView/app/src/main/java/com/mdy/android/webview/MainActivity.java)**

## 퍼미션(permission)
- AndroidManifest.xml 파일에 인터넷을 사용할 수 있게 퍼미션(permission)을 준다.
```xml
    <uses-permission android:name="android.permission.INTERNET"/>
```


## WebView Client 사용
* 기본 설정
```java
    // script 사용 설정 (필수) - 페이지내의 javascript가 동작하도록 한다.
    webView.getSettings().setJavaScriptEnabled(true);

    // 확대,축소 기능을 사용할 수 있도록 설정
    webView.getSettings().setSupportZoom(true);
    // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
    webView.getSettings().setBuiltInZoomControls(true);
```

* 클라이언트 설정
```java
    // 웹뷰 클라이언트를 지정
    //  - 안하면 내장 웹브라우저(스마트폰에 있는 웹 브라우저)가 팝업된다.
    // http로 시작하는 주소를 실행시킬 수 있게 해준다.
    webView.setWebViewClient(new WebViewClient());
    // https로 시작하는 주소를 실행시킬 수 있게 해준다.
    webView.setWebChromeClient(new WebChromeClient());
    // 둘다 세팅할것 : 프로토콜에 따라 클라이언트가 선택
```

* URL 호출 기본함수
```java
    // url 이동
    webView.loadUrl(url);
    // 뒤로가기
    webView.goBack();
```

* 문자열의 앞에 프로토콜인 http 문자열이 없다면 붙여준다.
```java
    private void loadUrl(String url){
      // 문자열의 앞에 프로토콜인 http 문자열이 없다면 붙여준다.
      if(!url.startsWith("http")){  //
          url = "http://" + url;
      }
      // url 호출하기
      webView.loadUrl(url);
    }
```

* 정규표현식을 이용한 URL 이동
```java
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
            case R.id.btnGo:    // url 이동
                String url = editTextUrl.getText().toString();
                if(!"".equals(url)) { // 공백이 아닐경우 처리
                    // 문자열이 url 패턴일때만
                    if(url.matches("^(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$")) {
                        loadUrl(url);
                    }else{
                        Toast.makeText(this, "URL이 잘못되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }
```

## 특수문자 사용
- 특수문자
  - (1) \&lt;
    - \< (less than)
  - (2) \&gt;
    - \> (greater than)

```xml
// res - values - strings.xml
<resources>
    <string name="app_name">WebView</string>
    <string name="btnPre"> &lt; </string>   <!-- less than -->
    <string name="btnBack"> &gt; </string>   <!-- greater than -->
</resources>
```


```xml
<Button
    android:id="@+id/btnPre"
    android:layout_width="70dp"
    android:layout_height="50dp"
    android:layout_marginTop="8dp"
    android:text="@string/btnPre"     // 버튼의 text에서 사용
    app:layout_constraintTop_toTopOf="parent"
    android:layout_marginLeft="8dp"
    app:layout_constraintLeft_toLeftOf="parent" />
```
