package com.mdy.android.treee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageViewTopTree, imageViewProfileShadow;
    ImageView imageProfile;
    TextView txtName, txtEmail;
    Button btnLogout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();

        mAuth = FirebaseAuth.getInstance();
        txtName.setText(mAuth.getCurrentUser().getDisplayName());
        txtEmail.setText(mAuth.getCurrentUser().getEmail());

    }

    public void setViews(){
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewProfileShadow = (ImageView) findViewById(R.id.imageViewProfileShadow);
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
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
}
