package com.mdy.android.treee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.UserProfile;
import com.mdy.android.treee.util.PreferenceUtil;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageViewTopTree, imageViewProfileShadow;
    ImageView imageProfile;
    TextView txtName, txtEmail;
    TextView txtTreeCountName, txtTotalCount;
    Button btnLogout;

    // 파이어베이스 데이터베이스
    FirebaseDatabase database;
    DatabaseReference userRef;

    // 파이어베이스 스토리지
    private StorageReference mStorageRef;

    // 파이어베이스 인증
    private FirebaseAuth mAuth;

    // SharedPreference
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        mStorageRef = FirebaseStorage.getInstance().getReference("profiles");

        mAuth = FirebaseAuth.getInstance();

        txtName.setText(mAuth.getCurrentUser().getDisplayName());
        txtEmail.setText(mAuth.getCurrentUser().getEmail());


        txtTotalCount.setText(Data.list.size()+"");

    }

    public void setViews(){
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtTreeCountName = (TextView) findViewById(R.id.txtTreeCountName);
        txtTotalCount = (TextView) findViewById(R.id.txtTotalCount);
        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewProfileShadow = (ImageView) findViewById(R.id.imageViewProfileShadow);

        imageProfile = (ImageView) findViewById(R.id.imageProfile);

//        String userUid = PreferenceUtil.getUid(this);
//        userRef = database.getReference("user").child(userUid).child("profile");
////        userRef = database.getReference("user").child(userUid).child("profile").child("profileFileUriString");
//        Log.w("============= USER ===========", userRef.getKey());
////        userRef.child("profileFileUriString").setValue(UserProfile.profileFileUriString);


        String userProfileImageUri = PreferenceUtil.getProfileImageUri(this);
        if(!userProfileImageUri.equals("")) {
            Glide.with(this).load(userProfileImageUri).into(imageProfile);
        }

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult( Intent.createChooser(intent, "앱을 선택하세요."), 100);    // 사진앱들 중 선택
            }
        });


        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();    // 페이스북 로그아웃 코드 (페이스북이 없으면 안써도 된다.)
                finish();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 100){
                Uri imageUri = data.getData();
                String filePath = getPathFromUri(this, imageUri);
                Glide.with(this).load(filePath).into(imageProfile);
//                imageProfile.setImageURI(imageUri);
                Toast.makeText(this, "사진이 등록되었습니다,", Toast.LENGTH_SHORT).show();
                uploadFile(filePath);
            }
        }
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
                    // 파이어베이스 스토리지에 방금 업로드한 파일의 경로
                    @SuppressWarnings("VisibleForTests")
                    Uri uploadedUri = taskSnapshot.getDownloadUrl();
                    afterUploadFile(uploadedUri);
                    Log.w("============uploadedUri===========", String.valueOf(uploadedUri));
                    PreferenceUtil.setProfileImageUri(ProfileActivity.this, uploadedUri.toString());
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

    public void afterUploadFile(Uri imageUri){

        UserProfile userProfile = new UserProfile();

        if(imageUri != null){
            userProfile.profileFileUriString = imageUri.toString();
        }

        String userUid = PreferenceUtil.getUid(this);
        userRef = database.getReference("user").child(userUid).child("profile");
        userRef.child("profileFileUriString").setValue(userProfile.profileFileUriString);
//        PreferenceUtil.setProfileImageUri(this, UserProfile.profileFileUriString);
    }

    // Uri에서 실제 경로 꺼내는 함수
    public String getPathFromUri(Context context, Uri uri){
        String realPath = "";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if( cursor.moveToNext() ){
            realPath = cursor.getString(cursor.getColumnIndex("_data"));
        }
        cursor.close();

        return realPath;
    }




}
