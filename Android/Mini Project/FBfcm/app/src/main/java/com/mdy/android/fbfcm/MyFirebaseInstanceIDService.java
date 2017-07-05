package com.mdy.android.fbfcm;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    // 이 토큰이 바뀌면 안드로이드 자동으로 이 메소드를 호출해준다.
    // 내 스마트폰을 인식하는 토큰이 변경되면 시스템에서 자동으로 호출
    @Override
    public void onTokenRefresh() {
        sendTokenToServer("토큰");
    }

    private void sendTokenToServer(String token){
//        HttpURLConnection 구현
//        서버에 token 을 전송
    }
}
