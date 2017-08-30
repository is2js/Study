package android.mdy.com.swiperefreshlayoutexam;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    private void setViews(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        textView = (TextView) findViewById(R.id.textView);
    }

    private void setListeners(){
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        count++;
        textView.setText(count + "");

        swipeRefreshLayout.setRefreshing(false);
    }
}
