package com.mdy.android.treee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ModifyActivity extends AppCompatActivity {

    EditText editTextContent1, editTextContent2, editTextContent3;
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
        editTextContent1 = (EditText) findViewById(R.id.editTextContent1);
        editTextContent2 = (EditText) findViewById(R.id.editTextContent2);
        editTextContent3 = (EditText) findViewById(R.id.editTextContent3);

        txtDate = (TextView) findViewById(R.id.txtDate);

        imageViewTime = (ImageView) findViewById(R.id.imageViewTime);
        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);
        imageViewSave = (ImageView) findViewById(R.id.imageViewSave);

    }
}
