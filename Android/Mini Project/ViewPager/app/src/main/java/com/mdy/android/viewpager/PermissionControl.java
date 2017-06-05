package com.mdy.android.viewpager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by MDY on 2017-06-05.
 */

public class PermissionControl {
    // 요청할 권한 목록
    public static final String PERMISSIONS[] = {
      Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION // 이건 와이파이 사용 / 권한이 추가되거나 변경되면 여기만 수정해주면 된다.
    };

    // 권한요청 플래그
    public static final int REQ_PERMISSION = 14567;     // 잘 안쓰는 숫자로 설정. 겹치지 않게

    // 권한체크 함수

    // checkSelfPermission는 액티비티에 있는 함수라 액티비티를 인자로 넘겨야 한다

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(Activity activity){
        //1 권한체크 - 특정권한이 있는지 시스템에 물어본다

        // 하나라도 권한이 없으면 빠져나오게
        for(String perm : PERMISSIONS){
            if(activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                activity.requestPermissions(PERMISSIONS , REQ_PERMISSION); // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다
                break;
            }
        }
    }

    // 권한체크 후 결과처리 함수
}
