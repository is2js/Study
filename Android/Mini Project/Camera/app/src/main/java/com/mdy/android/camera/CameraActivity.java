package com.mdy.android.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button btnCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setView(); // 뷰를 세팅
        setListener(); // 리스터를 세팅



    }

    private void setView(){
        imageView = (ImageView) findViewById(R.id.imageView);
        btnCapture = (Button) findViewById(R.id.btnCapture);
    }

    private void setListener(){
        btnCapture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        takePhoto();
    }


    Uri fileUri = null;
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            File photoFile = null;
            try {
                photoFile = createFile();   //파일을 만들어서 담는다.

                if(photoFile != null){
                    // 마시멜로 이상 버전은 파일 프로바이더를 통해 권한을 획득
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);
                    // 롤리팝 버전은 권한 없이 획득이 가능
                    } else {
                        fileUri = Uri.fromFile(photoFile);
                    }

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, Const.Camera.REQ_CAMERA);
                }


            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "사진파일 저장을 위한 임시파일을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;  // 사진파일이 생성안되면 더이상 진행되지 않게 하려고 return; 한다.
            }


        } else { // 롤리팝 미만 버젼에서만 바로 실행
            startActivityForResult(intent, Const.Camera.REQ_CAMERA);
        }
    }

    private File createFile() throws IOException{
        // 임시파일명 생성
        String tempFileName = "TEMP_"+System.currentTimeMillis();
        // 임시파일 저장용 디렉토리 생성
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
        // Environment.getExternalStorageDirectory() : 루트경로를 가져옴.
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        // 실제 임시파일을 생성
        File tempFile = File.createTempFile(
                tempFileName,
                ".jpg",
                tempDir
        );
        return tempFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청코드 구분
        if(requestCode == Const.Camera.REQ_CAMERA){
            // 결과처리 상태 구분
            if(resultCode == RESULT_OK) {
                Uri imageUri = null;
                // 롤리팝 미만 버전에서는 data 인텐트에 찍은 사진의 uri 가 담겨온다.
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                    imageUri = data.getData();
                } else {
                    imageUri = fileUri;
                }
                Log.i("Camera","file=============================="+fileUri);
                Log.i("Camera","file=============================="+imageUri);
                imageView.setImageURI(imageUri);
            }
        }
    }
}
