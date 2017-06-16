package com.mdy.android.musicplayer3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mdy.android.musicplayer3.domain.Music;

import java.util.Set;


public class DetailFragment extends Fragment {

    ViewHolder viewHolder = null;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        viewHolder = new ViewHolder(view);
        return view;
    }

    public Set<Music.Item> getDatas(){
        Music music = Music.getInstance();
        music.loader(getContext());

        return music.getItems();
    }


    // ViewPager의 View 객체
    public class ViewHolder implements View.OnClickListener {
        ViewPager viewPager;
        RelativeLayout layoutController;
        ImageButton btnPlay, btnPrevious, btnNext;
        SeekBar seekBar;
        TextView txtCurrent, txtDuration;


        public ViewHolder(View view){
            viewPager = (ViewPager) view.findViewById(R.id.viewPager);
            layoutController = (RelativeLayout) view.findViewById(R.id.layoutController);
            btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
            btnPrevious = (ImageButton) view.findViewById(R.id.btnPrevious);
            btnNext = (ImageButton) view.findViewById(R.id.btnNext);
            seekBar = (SeekBar) view.findViewById(R.id.seekBar);
            txtCurrent = (TextView) view.findViewById(R.id.txtCurrent);
            txtDuration = (TextView) view.findViewById(R.id.txtDuration);
            setOnClickListener();
            setViewPager();

        }

        private void setOnClickListener(){
            btnPrevious.setOnClickListener(this);
            btnPlay.setOnClickListener(this);
            btnNext.setOnClickListener(this);
        }


        private void setViewPager(){
            DetailAdapter adapter = new DetailAdapter(getDatas());
            viewPager.setAdapter(adapter);
        }


        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnPrevious :
                    break;
                case R.id.btnPlay :
                    break;
                case R.id.btnNext :
                    break;
            }
        }
    }
}
