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

/* File Provider 사용했음. (manifest 설정 및 res - xml - file_path.xml 생성) */
    // <File Provider 사용하는 이유>
    // 내가 이미지 파일을 저장할 특정 저장 공간에 대한 권한을 획득하기 위해 사용한다.
    // 그 저장 공간은 외부 공간이어서 모든 앱들이 같이 액세스를 할 수 있다.
    // 그래서 거기서 실제 write를 하겠다고 권한을 얻는 것이다.

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
        // Intent를 통해 (MediaStore.ACTION_IMAGE_CAPTURE) 라는 플래그값을 넘겨준다.
        // 미리 정의되어 있는 액션 플래그이다.
        // 사진을 찍은 다음에 돌려받아야 하니까 startActivityForResult()에 담아서 보낸다.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 롤리팝 이상 버전에서는 사진을 찍을때 카메라를 그냥 띄우면 안되고,
            // 내가 찍은 사진 파일을 저장할 파일을 미리 생성해놔야 한다. -> photoFile
                File photoFile = null;  // File I/O가 발생해야 되서 null 처리를 해놨다.
                try {
                        photoFile = createFile();   // 파일을 만들어서 담는다.

                        if(photoFile != null){
                                // 마시멜로 이상 버전은 파일 프로바이더를 통해 권한을 획득
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                    fileUri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID+".provider", photoFile);
                                        // .getUriForFile()의 두번째 속성은 autoritiy인데 manifest에 설정해놓은 android:authorities에서 확인하면 된다.
                                        // photoFile을 넘겨주면 이 파일에 대해서 권한이 획득이 되는 것이다.
                                        // 이제 권한이 획득이 되었으니 intent로 넘겨주면 된다. 5째줄 아래 코드로 이동.
                                // 롤리팝 버전은 권한 없이 획득이 가능
                                } else {
                                    fileUri = Uri.fromFile(photoFile);
                                }
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                // 이렇게 보내주면 시스템이 받아서 처리한다.
                                // 시스템에서는 사진을 찍은 다음에 이 fileUri에다가 파일을 담아서 나한테 준다.
                                // 인텐트로 주는게 아니라 담아만 준다.
                                startActivityForResult(intent, Const.Camera.REQ_CAMERA);
                        }
                }catch(Exception e){
                    //  e.printStackTrace();  -> 예외처리에서 에러가 나면 이렇게 해주면 에러를 확인할 수 있다.
                    Toast.makeText(getBaseContext(), "사진파일 저장을 위한 임시파일을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;  // 사진파일이 생성안되면 더이상 진행되지 않게 하려고 return; 한다.
                }

        } else { // 롤리팝 미만 버젼에서만 바로 실행
            startActivityForResult(intent, Const.Camera.REQ_CAMERA);
        }
    }


    // 이걸 만들때 exception이 발생할 수 있으니 예외처리를 해주라는 메세지가 떠서 처리해줬다.
    // 이렇게 해주면 호출한 쪽에서 try ~ catch 처리를 해주면 된다.
    private File createFile() throws IOException{
        // 임시파일이름 생성
        String tempFileName = "TEMP_"+System.currentTimeMillis();
        // 임시파일 저장용 디렉토리 생성
        File tempDir = new File(Environment.getExternalStorageDirectory() + "/CameraN/");
        // Environment.getExternalStorageDirectory() : External Storage의 루트경로를 가져옴. (파일탐색기를 열었을때 제일 먼저 뜨는)
        // 그런데 이게 시스템의 루트 경로는 아니다.

        // tempDir가 없으면 생성한다.
        if(!tempDir.exists()){
            tempDir.mkdir();   // tempDir.mkdirs()로 하면 중간에 없는 경로까지 다 생성해준다.
        }
        // 실제 임시파일을 생성
        File tempFile = File.createTempFile(
                tempFileName,   // 파일의 이름
                ".jpg",         // 확장자
                tempDir         // 디렉토리 경로
        );
        return tempFile;
    }


    // startActivityForResult()가 끝나면 호출되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 요청코드 구분
        if(requestCode == Const.Camera.REQ_CAMERA){
            // 결과처리 상태 구분
            if(resultCode == RESULT_OK) {
                Uri imageUri = null;
                // 롤리팝 미만 버전에서는 data 인텐트에 찍은 사진의 uri 가 담겨온다.(intent를 통해 넘겨받는다.)
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                    imageUri = data.getData();
                } else {
                // 롤리팝 이상 에서는 내가 생성한 곳의 fileUri를 함수에서 직접 끌어다 쓰는 것이다.
                    imageUri = fileUri; // 위에 있는 takePhoto();를 통해 만들어진 fileUri를 담는다.
                }
                Log.i("Camera","file=============================="+fileUri);
                Log.i("Camera","file=============================="+imageUri);
                imageView.setImageURI(imageUri);
            }
        }
    }
}
