package firebase.mdy.com.firebaseuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firebase.mdy.com.firebaseuser.domain.User;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignUp;
    EditText editTextEmail, editTextName, editTextPassword;
    Button btnSignUp;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setViews();

    }

    public void setViews(){
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    public void postData(View view){
        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        User user = new User(name, email, password);

        String childKey = userRef.push().getKey();
        userRef.child(childKey).setValue(user);

        finish();
    }
}



























