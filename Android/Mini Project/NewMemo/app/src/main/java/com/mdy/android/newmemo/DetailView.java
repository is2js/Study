package com.mdy.android.newmemo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pc on 5/29/2017.
**/

public class DetailView {
    // Presenter
    DetailActivity activity;

    // 위젯 연결
    EditText memo;
    FloatingActionButton button;
    Toolbar toolbar;

    public DetailView(DetailActivity activity){
        this.activity = activity;
    }

    public void init(){
        activity.setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        // setSupportActionBar() 는 AppCompatActivity 클래스에 선언되어 있는 메소드이다.
        // 여기서 activity는 DetailActivity인데 DetailActivity가 AppCompatActivity 클래스를 상속하기 때문에 사용할 수 있다.

        memo = (EditText) activity.findViewById(R.id.editText);

        button = (FloatingActionButton) activity.findViewById(R.id.fab);

        // 메모 저장 (짧게 클릭)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // memo의 타입이 EditText이기 때문에 .toString()을 해줘야 한다.
                // EditText는 타입이 Editable이기 떄문에 타입캐스팅을 해줘야 한다.
                // 그러나 TextView의 경우는 .toString()을 안해줘도 된다.
                String content = memo.getText().toString();
                if(content != null && !"".equals(content)){
                    activity.save(content);
                } else {
                    Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 취소 (길게 클릭)
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.back();
                return true;
            }
        });
    }

    public void setMemo(String content) {
        memo.setText(content);
    }
}