package firebase.mdy.com.firebaseuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firebase.mdy.com.firebaseuser.domain.User;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;


    EditText editTextEmail, editTextName, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

    }

    // Sign Up 버튼의 onClick 속성에 직접 연결
    // android:onClick="postData"
    public void postData(View view){
        String email = editTextEmail.getText().toString();
        String username = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();
        // 정규식으로 이메일이 맞는지 체크후

        // 패스워드 자릿수 체크

        User user = new User(email, username, password);

        // 파이어베이스에 키를 자동으로 생성하는 방법
        String childKey = userRef.push().getKey();        // hash 코드로 된 키를 레퍼런스 아래에 삽입을 해준다.
        userRef.child(childKey).setValue(user);
    }
}
