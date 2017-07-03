# FireBaseBbs
#### FireBase를 이용하여 간단한 게시판을 만들어본다.
- FireBase 데이터 C(입력), R(읽기), U(수정), D(삭제)
- NaviGation Drawer Activity
  - onNavigationItemSelected()
  - onCreateOptionsMenu()
  - onOptionsItemSelected

<br>

## 동작 화면
<!-- ![NaviActivity](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseBbs/graphics/NaviActivity.png)  ![ListActivity](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseBbs/graphics/ListActivity.png)  ![DetailActivity](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseBbs/graphics/DetailActivity.png) -->

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseBbs/graphics/NaviActivity.png' width='200' height='333' />  <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseBbs/graphics/ListActivity.png' width='200' height='333' />  <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/FireBaseBbs/graphics/DetailActivity.png' width='200' height='333' />

<br>

## 파이어베이스 - 데이터 CRUD
```java
  FirebaseDatabase database;
  DatabaseReference bbsRef;

  ...

  database = FirebaseDatabase.getInstance();
  bbsRef = database.getReference("bbs");

  ...

  // 데이터 전송
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
  }
```

<br>

## NaviGation Drawer Activity
#### 네이게이션 메뉴 클릭시 호출되는 함수
```java
  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
      // Handle navigation view item clicks here.
      int id = item.getItemId();

      if (id == R.id.nav_camera) {
          Intent intent = new Intent(this, ListActivity.class);
          startActivity(intent);
      } else if (id == R.id.nav_gallery) {

      } else if (id == R.id.nav_slideshow) {

      } else if (id == R.id.nav_manage) {

      } else if (id == R.id.nav_share) {

      } else if (id == R.id.nav_send) {

      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
  }
```
#### 설정 관련
```java
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.navi, menu);
      return true;
  }

  // 설정 관련
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
          return true;
      }

      return super.onOptionsItemSelected(item);
  }
```

<br>

## 소스코드
- #### NaviActivity.java
```java
package com.mdy.android.firebasebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi, menu);
        return true;
    }

    // 설정 관련
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 네이게이션 메뉴 클릭시 호출되는 함수
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
```

<br>

- #### CustomAdapter.java
```java
package com.mdy.android.firebasebbs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.firebasebbs.domain.Bbs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MDY on 2017-07-03.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    List<Bbs> data = new ArrayList<>();
    LayoutInflater inflater;

    public CustomAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Bbs> data){
        this.data = data;
    }

    @Override
    public CustomAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);

        holder.title.setText(bbs.title);
        holder.author.setText(bbs.author);

        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        holder.date.setText(sdf.format(currentTime));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView date;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            author = (TextView) itemView.findViewById(R.id.txtAuthor);
            date = (TextView) itemView.findViewById(R.id.txtDate);

        }
    }
}

```

<br>

- #### ListActivity.java
```java
package com.mdy.android.firebasebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.firebasebbs.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recycler;
    CustomAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        setViews();

        loadData();
    }

    public void loadData(){
        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                List<Bbs> list = new ArrayList<>();
                for( DataSnapshot item : data.getChildren() ){
                    // json 데이터를 Bbs 인스턴스로 변환오류 발생 가능성 있어서 예외처리 필요
                    try {
                        Bbs bbs = item.getValue(Bbs.class);
                        list.add(bbs);
                    } catch (Exception e){
                        Log.e("FireBase", e.getMessage());
                    }
                }
                refreshData(list);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void refreshData(List<Bbs> data){
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    // onClick
    public void postData(View view){
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    public void setViews(){
        recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new CustomAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
```

<br>

- #### DetailActivity.java
```java
package com.mdy.android.firebasebbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mdy.android.firebasebbs.domain.Bbs;

public class DetailActivity extends AppCompatActivity {

    TextView txtTitle, txtAuthor, txtContent;
    EditText editTextTitle, editTextAuthor, editTextContent;
    Button btnWrite;

    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        setViews();
    }

    // 데이터 전송
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

        btnWrite = (Button) findViewById(R.id.btnWrite);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
    }
}
```
