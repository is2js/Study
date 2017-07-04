package com.mdy.android.firebasebbs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mdy.android.firebasebbs.domain.Bbs;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    TextView txtTitle, txtAuthor, txtContent;
    TextView txtImage;
    EditText editTextTitle, editTextAuthor, editTextContent;
    Button btnWrite;

    // 데이터베이스
    FirebaseDatabase database;
    DatabaseReference bbsRef;

    // 스토리지
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setViews();

        // 데이터베이스 레퍼런스
        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        // 스토리지 레퍼런스
        mStorageRef = FirebaseStorage.getInstance().getReference("images");


    }

    public void uploadFile(String filePath){
        // 스마트폰에 있는 파일의 경로
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        // 파이어베이스에 있는 파일 경로
        String fileName = file.getName(); // + 시간값 or UUID 추가해서 만들어준다.
        // 데이터베이스의 키가 값과 동일한 구조 ( 키 = 값 )
        StorageReference fileRef = mStorageRef.child(fileName);

        fileRef.putFile(uri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.i("FBStorage", "Upload Success!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.i("FBStorage", "Upload Fail : " + exception.getMessage());
                }
            });
    }



    public void postData(View view){
        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        String content = editTextContent.getText().toString();

        // 파이어베이스 데이터베이스에 데이터 넣기
        // 1. 데이터 객체 생성
        Bbs bbs = new Bbs(title, author, content);

        // 2. 입력할 데이터의 키 생성
        String bbsKey = bbsRef.push().getKey(); // 자동생성된 키를 가져온다.

        // 3. 생성된 키를 레퍼런스로 데이터를 입력
        //   insert와 update, delete 는 동일하게 동작
        bbsRef.child(bbsKey).setValue(bbs);

        /* 수정 */
        // bbsRef.child(bbsKey).setValue(bbs)

        /* 삭제 */
        // bbsRef.child(bbsKey).setValue(null);

        // 데이터 입력 후 창 닫기
        finish();


    }

    public void setViews(){
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtContent = (TextView) findViewById(R.id.txtContent);

        txtImage = (TextView) findViewById(R.id.txtImage);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextContent = (EditText) findViewById(R.id.editTextContent);

        btnWrite = (Button) findViewById(R.id.btnWrite);


    }

    // 화면의 Gallery 버튼에서 자동 링크
    public void openGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // EXTERNAL_CONTENT_URI 에 여러가지가 있는데 그 중에서 이미지들을 가져올 수 있게 해준다.
        // 가. 이미지 선택창 호출
        startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱이 여러개일 경우 선택하게끔 해준다.
    }

    // startActivityForResult()가 끝나면 호출되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                // 나. 이미지 선택창에서 선택된 이미지의 경로 추출
                case 100:
                    Uri imageUri = data.getData();
                    Log.i("Gallery","imageUri========================="+imageUri);
                    txtImage.setText(imageUri.getPath());
//                    image.setImageURI(imageUri);
                    break;
            }
        }
    }

}
