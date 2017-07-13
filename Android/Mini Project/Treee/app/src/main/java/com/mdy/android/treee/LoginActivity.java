package com.mdy.android.treee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.treee.domain.UserProfile;
import com.mdy.android.treee.util.PermissionControl;
import com.mdy.android.treee.util.PreferenceUtil;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, PermissionControl.CallBack{

    private static final String TAG = "===FaceBook===";
    private static final int RC_SIGN_IN = 10;

    private SignInButton btnLoginGoogle;
    private LoginButton loginButton;
    private Button btnLoginEmail;
    private EditText editTextEmail, editTextPassword;
    private TextView textViewTitle;
    private ImageView imageViewLogo;


    private GoogleApiClient mGoogleApiClient;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();

        PermissionControl.checkVersion(this);


        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        // 구글 로그인 버튼 리스너
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Google 로그인 버튼이 클릭되었습니다,", Toast.LENGTH_SHORT).show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



        // 이메일 로그인 버튼 리스너 -- 유효성 검사 추가
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                boolean emailValidate = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (emailValidate == true) {
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 리스너 - 사용자의 로그인 상태 변화에 응답하는 AuthStateListener를 설정
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    PreferenceUtil.setUid(LoginActivity.this, user.getUid());
                    Log.w("======== Uid ========", user.getUid());

                    userRef = database.getReference("user").child(user.getUid()).child("profile");
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                                // 프로필 사진이 저장되어 있지 않으면
                                if(userProfile == null){
                                    setProfileImg("");
                                } else {
                                    // 프로필 사진이 저장되어 있으면
                                    Log.w("=========userProfile.profileFileUteriString============", userProfile.profileFileUriString);
                                    setProfileImg(userProfile.profileFileUriString);
                                }
                            } catch (Exception e){
                                Log.e("Firebase", e.getMessage());
                            }
                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


//                    String userEmail = mAuth.getCurrentUser().getEmail();
//                    Log.w("===== DisplayName =====", mAuth.getCurrentUser().getDisplayName());
//                    userRef = database.getReference("user").child(user.getUid()).child("profile");
//                    userRef.child("userEmail").setValue(userEmail);

//                    PreferenceUtil.setProfileImageUri(LoginActivity.this, "");
                    // TODO

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        mCallbackManager = CallbackManager.Factory.create();

        // 페이스북 로그인 버튼
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG1", "facebook:onSuccess:" + loginResult);
                AccessToken token = loginResult.getAccessToken();
                Log.d(TAG, "token expired:" + token.isExpired());
                Log.d(TAG, "token value:" + token.toString());
                Log.d(TAG, "token permissions:" + token.getDeclinedPermissions());
                Log.d(TAG, "token userid:" + token.getUserId());
                handleFacebookAccessToken(token);
            }

            @Override
            public void onCancel() {
                Log.d("facebook:onCancel", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG2", "facebook:onError", error);
            }
        });
    }

    public void setProfileImg(String url){
        PreferenceUtil.setProfileImageUri(this, url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    // 페이스북 로그인
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Log.d(TAG, "token:" + token.getApplicationId());
        Log.d(TAG, "token:" + token.getUserId());
        Log.d(TAG, "token:" + token.describeContents());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.d(TAG, "credential:" + credential.toString());

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "signInWithCredential", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "FaceBook 아이디 연동 성공", Toast.LENGTH_SHORT).show();
//                        PreferenceUtil.saveUidPreference(LoginActivity.this, mAuth);
                        Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
    }

    // 이메일 로그인 관련
    private void createUser(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 로그인 실패했을때 실행되는 부분
                        if (!task.isSuccessful()) {
                            loginUser(email, password);
                            Toast.makeText(LoginActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // 로그인 성공했을때 실행되는 부분
                            Toast.makeText(LoginActivity.this, "회원가입이 성공되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    // 이메일 로그인 관련
    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "이메일 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "이메일 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                            PreferenceUtil.saveUidPreference(LoginActivity.this, mAuth);
                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }



    private void setViews(){
        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);   // 페이스북 로그인 버튼
        btnLoginGoogle = (SignInButton) findViewById(R.id.btnLoginGoogle);
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Google 아이디 인증이 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                            PreferenceUtil.saveUidPreference(LoginActivity.this, mAuth);
                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    // implements GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {
        Toast.makeText(this, "권한을 요청을 승인하셔야 앱을 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
