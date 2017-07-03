package firebase.mdy.com.firebaseuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import firebase.mdy.com.firebaseuser.domain.User;

public class MainActivity extends AppCompatActivity {

    TextView txtUser;
    Button btnSignUp;
    ListView listView;

    UserAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        adapter = new UserAdapter(this);
        listView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot users) {
                List<User> data = new ArrayList<>();
                for(DataSnapshot item : users.getChildren()){
                    String key = item.getKey();
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


    public void refreshData(List<User> data){
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }


    public void setViews(){
        txtUser = (TextView) findViewById(R.id.txtUser);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        listView = (ListView) findViewById(R.id.listView);
    }
}
