# FireBaseUser
#### FireBase를 이용하여 실시간으로 데이터를 입력해본다.

<br>




## 동작 화면
![MainActivity_UserList](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseUser/graphics/MainActivity_UserList.png)![SignUpActivity](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseUser/graphics/SignUpActivity.png)


<br>

## 소스코드
- #### User.java
```java
package firebase.mdy.com.firebaseuser.domain;

public class User {
    // 멤버필드, 속성, 멤버변수, 전역변수 -> 4개 다 같은말
    public String username;
    public String email;
    public String password;

    // 생성자
    public User(){

    }

    // 파라미터가 있는 생성자 오버로드
    public User(String username, String email, String password){
        this.username = username;
        this.email = email;

        // password를 단방향 암호화
        this.password = password;
    }
}
```
- #### UserAdapter.java
```java
package firebase.mdy.com.firebaseuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import firebase.mdy.com.firebaseuser.domain.User;

public class UserAdapter extends BaseAdapter {

    List<User> data;
    LayoutInflater inflater;

    public UserAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<>();
    }

    public void setData(List<User> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.item_activity_main, parent, false);
            holder.email = (TextView) convertView.findViewById(R.id.txtEmail);
            holder.username = (TextView) convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        User user = data.get(position);
        holder.email.setText(user.email);
        holder.username.setText(user.username);

        return convertView;
    }

    class Holder {
        TextView email;
        TextView username;
    }
}
```

- #### SignUpActivity.java
```java
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

        User user = new User(username, email, password);

        // 파이어베이스에 키를 자동으로 생성하는 방법
        String childKey = userRef.push().getKey();        // hash 코드로 된 키를 레퍼런스 아래에 삽입을 해준다.
        userRef.child(childKey).setValue(user);

        finish();
    }
}
```

- #### MainActivity.java
```java
package firebase.mdy.com.firebaseuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import firebase.mdy.com.firebaseuser.domain.User;


/*
    https://firebase.google.com/docs/database/android/save-data
 */
public class MainActivity extends AppCompatActivity {

    ListView listView;
    UserAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new UserAdapter(this);
        listView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        // root 노드인 user를 레퍼런스로 사용
        userRef = database.getReference("user");  // Root 이름을 일치시켜줘야 한다.
        // 이벤트 리스너 달기
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot users) {  // DataSnapshot은 JSON String구조이다.
                List<User> data = new ArrayList<User>();
                for( DataSnapshot item : users.getChildren() ){
                    String key = item.getKey();
                    Log.i("Firebase", "user key = " + key);
                    User user = item.getValue(User.class);
                    data.add(user);
                }
                refreshData(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    // 파이어베이스의 valueEventListener에서 호출
    public void refreshData(List<User> data){
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
}
```
