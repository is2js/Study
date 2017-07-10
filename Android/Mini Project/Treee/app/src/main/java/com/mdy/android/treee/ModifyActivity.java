package com.mdy.android.treee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ModifyActivity extends AppCompatActivity {

    EditText txtContent1, txtContent2, txtContent3;
    TextView txtDate;
    ImageView imageViewGallery, imageViewCamera, imageViewSave;
    ImageView imageViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        setViews();


    }

    public void setViews(){
        txtContent1 = (EditText) findViewById(R.id.txtContent1);
        txtContent2 = (EditText) findViewById(R.id.txtContent2);
        txtContent3 = (EditText) findViewById(R.id.txtContent3);

        txtDate = (TextView) findViewById(R.id.txtDate);

        imageViewTime = (ImageView) findViewById(R.id.imageViewTime);
        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);
        imageViewSave = (ImageView) findViewById(R.id.imageViewSave);

    }
}
