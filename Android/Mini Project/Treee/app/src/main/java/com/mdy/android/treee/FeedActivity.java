package com.mdy.android.treee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class FeedActivity extends AppCompatActivity {

    ImageView btnMinus, btnFeedIcon, btnProfile;
    ImageView imageViewTopTree, imageViewBottomTree;
    ImageView imageViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setViews();


    }

    public void setViews(){
        btnMinus = (ImageView) findViewById(R.id.btnMinus);
        btnFeedIcon = (ImageView) findViewById(R.id.btnFeedIcon);
        btnProfile = (ImageView) findViewById(R.id.btnProfile);

        imageViewTopTree = (ImageView) findViewById(R.id.imageViewTopTree);
        imageViewBottomTree = (ImageView) findViewById(R.id.imageViewBottomTree);

        imageViewText = (ImageView) findViewById(R.id.imageViewText);

    }
}
