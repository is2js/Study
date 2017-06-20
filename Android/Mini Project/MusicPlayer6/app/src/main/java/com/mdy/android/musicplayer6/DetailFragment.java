package com.mdy.android.musicplayer6;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mdy.android.musicplayer6.domain.Music;

import java.util.List;


public class DetailFragment extends Fragment {

    public static final int CHANGE_SEEKBAR = 99;
    public static final int STOP_THREAD = 98;
    static final String ARGS1 = "position";
    private int position = -1;
    // Detail페이지의 현재 포지션이 무엇인지 체크하는 변수 position 선언
    // int에는 null값이 들어가지 않아서 -1(의미없는값)로 해놨다. (인덱스는 0부터 시작하니까)


    // 음악 플레이에 따라 seekbar를 변경해주는 thread
    SeekBarThread seekBarThread = null;

    private ViewHolder viewHolder = null;


    // CHANGE_SEEKBAR를 사용하는 Handler 생성
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_SEEKBAR :
                    viewHolder.setSeekBarPosition(msg.arg1);
                    break;
            }
        }
    };



    public DetailFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /*public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS1, position);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        Bundle bundle = getArguments();
        position = bundle.getInt(ARGS1);

        viewHolder = new ViewHolder(view, position);
        return view;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        Log.d("DetailFragment","viewHolder====================================="+viewHolder);
        if(viewHolder == null) {
            view = inflater.inflate(R.layout.fragment_pager, container, false);
            viewHolder = new ViewHolder(view, this);
        }else{
            view = viewHolder.getView();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewHolder.init(position);
    }



    public List<Music.Item> getDatas(){
        Music music = Music.getInstance();
        music.loader(getContext());

        return music.getItems();
    }


    // 최초에 호출될 경우는 페이지의 이동이 없으므로 호출되지 않는다.
    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        // 페이지의 변경사항을 체크해서 현재 페이지 값을 알려준다.
        @Override
        public void onPageSelected(int position) {
            musicInit(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    // 음악을 초기화해준다
    public void musicInit(int position){
        Uri musicUri = getDatas().get(position).musicUri;
        Player.init(musicUri, getContext());

        Log.d("DetailFragment","musicInit viewHolder====================================="+viewHolder);
        int musicDuration = Player.getDuration();
        viewHolder.setDuration(musicDuration);
        viewHolder.seekBar.setMax(Player.getDuration());
    }



    // ViewPager의 View 객체
    public class ViewHolder implements View.OnClickListener {
        View view;
        ViewPager viewPager;
        RelativeLayout layoutController;
        ImageButton btnPlay, btnPrevious, btnNext;
        SeekBar seekBar;
        TextView txtCurrent, txtDuration;


        // 프레젠터 역할 - 인터페이스 설계 필요
        DetailFragment presenter;

        public View getView(){
            return view;
        }


        public ViewHolder(View view, DetailFragment presenter) {
            this.view = view;
            this.presenter = presenter;
            viewPager = (ViewPager) view.findViewById(R.id.viewPager);
            layoutController = (RelativeLayout) view.findViewById(R.id.layoutController);
            btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
            btnPrevious = (ImageButton) view.findViewById(R.id.btnPrevious);
            btnNext = (ImageButton) view.findViewById(R.id.btnNext);
            seekBar = (SeekBar) view.findViewById(R.id.seekBar);
            txtCurrent = (TextView) view.findViewById(R.id.txtCurrent);
            txtDuration = (TextView) view.findViewById(R.id.txtDuration);
            setOnClickListener();
            setViewPager(position);


        }

        public void init(int position){
            setOnClickListener();
            setViewPager(position);
        }

        private void setOnClickListener(){
            btnPrevious.setOnClickListener(this);
            btnPlay.setOnClickListener(this);
            btnNext.setOnClickListener(this);
        }


        private void setViewPager(int position){
            DetailAdapter adapter = new DetailAdapter(getDatas());
            // 아답터를 생성
            viewPager.setAdapter(adapter);
            // 리스너를 달았다...
            viewPager.addOnPageChangeListener(viewPagerListener);
            // 페이지를 이동하고
            viewPager.setCurrentItem(position);     // 뷰페이저가 최초에 세팅될 때 해당 페이지로 이동을 하게 된다.
            // 처음 한번 Presenter 에 해당되는 Fragment 의 musicInit 을 호출해서 음악을 초기화 해준다.
            presenter.musicInit(position);
        }

        public void setDuration(int time) {
            String formatted = miliToString( time );
            txtDuration.setText( formatted );
        }

        // 시간 포맷 변경 Integer -> 00:00
        private String miliToString(int mSecond){
            long min = mSecond / 1000 / 60;
            long sec = mSecond / 1000 % 60;

            return String.format("%02d", min) + ":" + String .format("%02d", sec);
        }


        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnPrevious :
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                    break;
                case R.id.btnPlay :
                    Player.play();
                    // Player에 음악이 세팅되면 음악의 길이를 받아올 수 있다.

                    /* getDuration()으로 어떤 값이 넘어오는지.. */
                    // Player.getDuration()의 Log값이 253100 이런식으로 찍힌다.
                    // 단위가 초가 아닌 값이 넘어오는데 굳이 초로 변경하지 않아도 된다.
                    // 중간중간에 음의 길이에 따라서 seekBar를 변경해줄텐데 duration이 이런 값을 넘겨줬다는 것은
                    // player에서 중간에 현재 실행된 구간값을 체크를 하면 비슷한 형태로 값이 넘어올 것이기 때문에..


                    // seekBar의 최대 길이를 지정
                    // 3분짜리면 3분만 돌고, 4분짜리면 4분만 돌게..
                    seekBar.setMax(Player.getDuration());


                    // seekBar를 변경해주는 thread => start 되면서 자기가 돌면서 알아서 변경해줄 것이다.
                    new SeekBarThread(handler).start();


                    break;
                case R.id.btnNext :
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    break;
            }
        }

        public void setSeekBarPosition(int current) {
            seekBar.setProgress(current);
        }
    }
}


class SeekBarThread extends Thread {
    Handler handler;
    boolean runFlag = true;

    public SeekBarThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run(){


        while(runFlag) {

            // 매초마다 음원의 실행영역을 가져와서
            int current = Player.getCurrent();
            // seekBar의 위치를 변경해준다. => 핸들러에다 메세지를 보내줄 것이다.
            Message msg = new Message();
            msg.what = DetailFragment.CHANGE_SEEKBAR;
            msg.arg1 = current;
            handler.sendMessage(msg);

            // 플레이 시간이 끝나면 thread를 종료한다.
            if(current >= Player.getDuration()){
                runFlag = false;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }



}