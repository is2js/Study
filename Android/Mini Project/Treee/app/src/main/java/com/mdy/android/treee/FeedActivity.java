package com.mdy.android.treee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mdy.android.treee.domain.Data;
import com.mdy.android.treee.domain.Memo;
import com.mdy.android.treee.util.PreferenceUtil;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ImageView btnMinus, btnFeedIcon, btnProfile;
    ImageView imageViewTopTree, imageViewBottomTree;
    ImageView imageViewText;

    //플로팅 버튼을 스크롤 하는 정도에 따라 나타나게 하기 위한 전역변수 준비 시작
    FloatingActionButton fabFeed;
    NestedScrollView nestedFeed;
    CardView cardviewFeed;
    int firstCardHeight;

    RecyclerView recyclerFeed;
    FeedAdapter feedAdapter;

    // 파이어베이스 데이터베이스
    FirebaseDatabase database;
    DatabaseReference userRef;

    // 파이어베이스 인증
    FirebaseAuth auth;

    // SharedPreferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        setViews();
        setFab();
        setNestedFeed();

        String userUid = PreferenceUtil.getUid(this);
        userRef = database.getReference("user").child(userUid).child("memo");

        loadFeedData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //onCreate에서 생성이 완료된 맨 위의 카드뷰(클릭하면 쓰기 액티비티로 이동하는 카드뷰)의
        //크기값을 int 값인 firstCardHeight에 받아서 넘겨줍니다.
        //(firstCardHeight도 미리 전역변수로 설정해두었습니다.)
        //또한, 카드뷰도 cardviewFeed로 전역변수로 미리 선언하였습니다.
        cardviewFeed = (CardView) findViewById(R.id.cardviewFeed);
        firstCardHeight = cardviewFeed.getHeight();
    }

    //플로팅 액션 버튼 생성과 클릭 시 발생 이벤트를 정의한 함수입니다.
    public void setFab(){
        //FloatingActionButton fabFeed로 전역변수 설정하였습니다.
        fabFeed = (FloatingActionButton) findViewById(R.id.fabFeed);
        fabFeed.setVisibility(View.GONE);
        fabFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
    }

    //맨 처음의 카드뷰 높이 만큼 스크롤 되었을 때(화면에서 첫번째 카드뷰가 사라지면) 플로팅 액션 버튼이 뜨도록 하기 위한 함수입니다.
    //NestedScrollView에 onScrollChangeListener를 달아서 y축의 스크롤 변화를 감지하였습니다.
    public void setNestedFeed(){
        nestedFeed = (NestedScrollView) findViewById(R.id.nestedFeed);
        nestedFeed.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //scrollY값이 현재 스크롤된 값입니다. 로그로 찍어보니 dp로 찍히는 듯 한데 확실하지는 않습니다.
                if(scrollY >= firstCardHeight){
                    fabFeed.setVisibility(View.VISIBLE);
                } else {
                    fabFeed.setVisibility(View.GONE);
                }
            }
        });
    }




    public void loadFeedData(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Data.list.clear();
                for( DataSnapshot item : data.getChildren() ){
                    // json 데이터를 Bbs 인스턴스로 변환오류 발생 가능성 있어서 예외처리 필요
                    try {
                        Memo memo = item.getValue(Memo.class);
                        if(memo.userUid.equals(auth.getCurrentUser().getUid()) ) {
                            Data.list.add(memo);
                        }
                    } catch (Exception e){
                        Log.e("FireBase", e.getMessage());
                    }
                }
                refreshFeed(Data.list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void refreshFeed(List<Memo> data){
        feedAdapter.setFeedData(data);
        feedAdapter.notifyDataSetChanged();
    }


    public void setViews(){
        btnMinus = (ImageView) findViewById(R.id.btnMinus);
        btnFeedIcon = (ImageView) findViewById(R.id.btnListIcon);
        btnFeedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnProfile = (ImageView) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewBottomTree = (ImageView) findViewById(R.id.imageViewBottomTree);

        imageViewText = (ImageView) findViewById(R.id.imageViewText);
        imageViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

        recyclerFeed = (RecyclerView) findViewById(R.id.recyclerFeed);

        feedAdapter = new FeedAdapter(this);
        recyclerFeed.setAdapter(feedAdapter);
        recyclerFeed.setLayoutManager(new LinearLayoutManager(this));
    }
}
