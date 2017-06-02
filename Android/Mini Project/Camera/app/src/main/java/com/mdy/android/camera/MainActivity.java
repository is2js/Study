package com.mdy.android.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private final int REQ_PERMISSION = 100;

    Button btnCam, btnGal;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCam = (Button) findViewById(R.id.btnCamera);
        btnGal = (Button) findViewById(R.id.btnGallery);
        image = (ImageView) findViewById(R.id.imageView);


        // 버튼 잠금
        btnCam.setEnabled(false);
        btnGal.setEnabled(false);


        //리스너
        btnCam.setOnClickListener(this);
        btnGal.setOnClickListener(this);


        // 마시멜로 이상 버전에서만 런타임 권한 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // level 23은 마시멜로이다.  Build.VERSION_CODES.M : 마시멜로를 가리킨다.
            // 마시멜로부터는 앞에 대문자 한자만 써도 된다.
            checkPermission();
        } else {
            // 아니면 그냥 run() 메소드 실행
            init();
        }


    }




    @TargetApi(Build.VERSION_CODES.M)       // @RequireApi 와 @TargetApi 는 동일하다고 생각해도 된다.
    private void checkPermission() {
        // 1. 권한체크 - 특정권한이 있는지 시스템에 물어본다.
        // checkSelfPermission 반환값이 true, false가 아니라 미리 정의된 상수로 반환한다.
        if( checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {

            // 2. 권한이 없으면 사용자에게 권한을 달라고 요청
            String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; // 동시에 여러개 호출할 수 있으니까 복수로
            requestPermissions(permissions , REQ_PERMISSION);  // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다.

        }
    }

    // 3. 사용자가 권한체크 팝업에서 권한을 승인 또는 거절하면 아래 메서드가 호출된다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION) {
            // 3.1 사용자가 승인을 했음.
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                init();
                // 3.2 사용자가 거절 했음.
            } else {
                cancel();
            }
        }
    }

    public void init(){
        btnCam.setEnabled(true);
        btnGal.setEnabled(true);
    }

    public void cancel(){
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnGallery:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // EXTERNAL_CONTENT_URI 에 여러가지가 있는데 그 중에서 이미지들을 가져올 수 있게 해준다.
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱이 여러개일 경우 선택하게끔 해준다.
                // startActivityForResult( intent, 100 ); 으로 해도 된다.
                break;
            case R.id.btnCamera:
                intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case 100:
                    Uri imageUri = data.getData();
                    Log.i("Gallery","imageUri========================="+imageUri);
                    image.setImageURI(imageUri);
                    break;
            }
        }
    }
}
