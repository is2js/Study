package android.mdy.com.customtoolbarexam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    int rotation = 0;
    TextView textView;
    ImageView imageView;
    ConstraintLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("메뉴를 눌러보세요.");
        textView = (TextView) findViewById(R.id.textView1);
        imageView = (ImageView) findViewById(R.id.image1);
        layout1 = (ConstraintLayout) findViewById(R.id.constraintLayout);

        Button btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuRed:
                layout1.setBackgroundColor(Color.rgb(255,0,0));
                break;

            case R.id.mnuBlue:
                layout1.setBackgroundColor(Color.rgb(0,0,255));
                break;

            case R.id.mnuYellow:
                layout1.setBackgroundColor(Color.rgb(255,255,0));
                break;
            case R.id.mnuWhite:
                layout1.setBackgroundColor(Color.rgb(255,255,255));
                break;

            case R.id.item_rotation:
                rotation+=60;
                if(rotation >=360) {
                    rotation-=360;
                }
                imageView.setRotation(rotation);
                break;

            case R.id.item_title:
                if(item.isChecked()){
                    item.setChecked(false);
                    textView.setVisibility(View.GONE);
                }else{
                    item.setChecked(true);
                    textView.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.item_expansion:
                if(item.isChecked()){
                    item.setChecked(false);
                    imageView.setScaleX(1);
                    imageView.setScaleY(1);
                }else{
                    item.setChecked(true);
                    imageView.setScaleX(2);
                    imageView.setScaleY(2);
                }
                break;

            case R.id.chicken:
                item.setChecked(true);
                textView.setText("치킨");
                imageView.setImageResource(R.drawable.chicken);
                break;

            case R.id.spaghetti:
                item.setChecked(true);
                textView.setText("스파게티");
                imageView.setImageResource(R.drawable.spaghetti);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
