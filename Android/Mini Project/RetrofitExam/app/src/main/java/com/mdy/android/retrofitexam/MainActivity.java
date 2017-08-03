package com.mdy.android.retrofitexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    public void setViews(){
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void setListeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
                Call<List<Contributor>> call = gitHubService.repoContributors("square", "retrofit");
                call.enqueue(new Callback<List<Contributor>>() {
                    @Override
                    public void onResponse(Response<List<Contributor>> response) {
                        TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
    }
}
