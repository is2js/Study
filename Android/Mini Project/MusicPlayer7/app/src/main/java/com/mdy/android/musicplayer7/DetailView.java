package com.mdy.android.musicplayer7;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mdy.android.musicplayer7.domain.Music;
import com.mdy.android.musicplayer7.util.TimeUtil;

import java.util.List;

import static com.mdy.android.musicplayer7.DetailFragment.CHANGE_SEEKBAR;
import static com.mdy.android.musicplayer7.DetailFragment.STOP_THREAD;

/**
 * Created by MDY on 2017-06-20.
 */

// ViewPager 의 View
public class DetailView implements View.OnClickListener{
    Context context;
    View view;
    ViewPager viewPager;
    RelativeLayout layoutController;
    ImageButton btnPlay,btnNext,btnPrev;
    SeekBar seekBar;
    TextView current,duration;

    // 플레이어를 컨트롤하는 메인에 있는 함수 사용
    DetailFragment.PlayerInterface playerInterface;

    // 음악 플레이에 따라 seekbar를 변경해주는 thread
    SeekBarThread seekBarThread = null;

    // 프레젠터 역할 - 인터페이스 설계 필요
    DetailFragment presenter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case CHANGE_SEEKBAR:
                    setSeekBarPosition(msg.arg1);
                    break;
                case STOP_THREAD:
                    seekBarThread.setRunFlag(false);
                    break;
            }
        }
    };

    public View getView(){
        return view;
    }

    public DetailView(View view, DetailFragment presenter, DetailFragment.PlayerInterface playerInterface){
        this.playerInterface = playerInterface;
        this.context = view.getContext();
        this.view = view;
        this.presenter = presenter;
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        layoutController = (RelativeLayout) view.findViewById(R.id.layoutController);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        btnPrev = (ImageButton) view.findViewById(R.id.btnPrevious);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        current = (TextView) view.findViewById(R.id.txtCurrent);
        duration = (TextView) view.findViewById(R.id.txtDuration);
    }

    public void init(int position){
        setOnClickListener();
        setViewPager(position);
        setSeekBar();
        setSeekBarThread();
    }

    private void setOnClickListener(){
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
    }

    private List<Music.Item> getDatas(){
        Music music = Music.getInstance();
        music.loader(context);

        return music.getItems();
    }

    private void setViewPager(int position){

        DetailAdapter adapter = new DetailAdapter(getDatas());
        // 아답터를 생성
        viewPager.setAdapter(adapter);
        // 리스너를 달았다...
        viewPager.addOnPageChangeListener(viewPagerListener);
        // 페이지를 이동하고
        viewPager.setCurrentItem(position);
    }

    private void setSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 사용자가 seekbar 를 터치했을 때만 동작하도록 설정
                if(fromUser)
                    Player.setCurrent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setSeekBarThread(){
        // seekBar를 변경해주는 thread
        seekBarThread = new SeekBarThread(handler);
        seekBarThread.start();
    }

    public void setDuration(int time){
        String formatted = miliToString(time);
        duration.setText( formatted );
    }

    // 시간 포맷 변경 Integer -> 00:00
    private String miliToString(int mSecond){
        long min = mSecond / 1000 / 60;
        long sec = mSecond / 1000 % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPlay:
                play();
                break;
            case R.id.btnNext:
                next();
                break;
            case R.id.btnPrevious:
                prev();
                break;
        }
    }

    public void play(){
        switch(Player.status){
            case Const.Player.PLAY:
//                Player.pause();
                playerInterface.pausePlayer();
                // pause 가 클릭되면 이미지 모양이 play 로 바뀐다.
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                break;
            case Const.Player.PAUSE:
//                Player.play();
                playerInterface.playPlayer();
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                break;
            case Const.Player.STOP:
                playerInterface.playPlayer();
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                break;
        }
        // Player.play();
    }

    public void next(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    public void prev(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }

    public void setSeekBarPosition(int time){
        seekBar.setProgress(time);
        current.setText(TimeUtil.miliToMinSec(time));
    }

    // 최초에 호출될 경우는 페이지의 이동이 없으므로 호출되지 않는다.
    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        // 페이지의 변경사항을 체크해서 현재 페이지 값을 알려준다
        @Override
        public void onPageSelected(int position) {
            // 현재 페이지가 변경된 후 호출된다.
            // 플레이어에 음악을 세팅해준다.
            musicInit(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // 음악을 초기화해준다
    public void musicInit(int position){
        Uri musicUri = getDatas().get(position).musicUri;
        Player.init(musicUri, context, handler);

        int musicDuration = Player.getDuration();
        setDuration(musicDuration);
        seekBar.setMax(Player.getDuration());

        // 뷰페이저 이동시 음원을 초기화하는데
        // 이전 음원이 실행상태였으면 자동으로 실행시켜준다.
        if(Player.status == Const.Player.PLAY){
            playerInterface.playPlayer();
        }
    }

    public void setDestroy() {
        seekBarThread.setRunFlag(false);
        seekBarThread.interrupt();
    }
}