package com.mdy.android.treee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class WriteActivity extends AppCompatActivity {

    ImageView imageView, imageViewCamera, imageViewSave;
    ImageView imageViewGallery, imageViewTime;
    TextView txtDate;
    TextView txtContent1, txtContent2, txtContent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setViews();



    }

    public void setViews(){
        imageView = (ImageView) findViewById(R.id.imageViewLogo);
        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);
        imageViewSave = (ImageView) findViewById(R.id.imageViewSave);
        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewTime = (ImageView) findViewById(R.id.imageViewTime);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtContent1 = (TextView) findViewById(R.id.txtContent1);
        txtContent2 = (TextView) findViewById(R.id.txtContent2);
        txtContent3 = (TextView) findViewById(R.id.txtContent3);
    }
}
