package com.mdy.android.viewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tab;

    Fragment one;
    Fragment two;
    Fragment three;
    Fragment four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. ViewPager 위젯 연결
        pager = (ViewPager) findViewById(R.id.pager);
        // 1.1 TabLayout 위젯 연결
        tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("One"));
        tab.addTab(tab.newTab().setText("Two"));
        tab.addTab(tab.newTab().setText("Three"));
        tab.addTab(tab.newTab().setText("Four"));

        // 2. 프래그먼트들 생성
        one = new OneFragment();
        two = new TwoFragment();
        three = new ThreeFragment();
        four = new FourFragment();


        // 3. 프래그먼트를 datas 저장소에 담은 후
        // 모든 Fragment는 Fragment를 상속받기 때문에 제네릭에 Fragment를 써준다.
        List<Fragment> datas = new ArrayList<>();
        datas.add(one);
        datas.add(two);
        datas.add(three);
        datas.add(four);

        // 4. 프래그먼트 매니저와 함께 아답터에 전달
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), datas);

        // 5. 아답터를 페이저 위젯에 연결
        pager.setAdapter(adapter);


        // 6. pager가 변경되었을 때 tab을 변경해주는 리스너 연결
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        // 7. tab이 변경되었을 때 pager를 변경해주는 리스너 연결
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

    }



    class PagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> datas;

        // List<Fragment> datas 넘겨줄 필요가 있어서 인자에 추가했다.
        // 그리고 아답터 만들때도 new PagerAdapter( ,  ) 두번째 인자에 datas를 넣어준다.
        public PagerAdapter(FragmentManager fm, List<Fragment> datas) {
            super(fm);
            this.datas = datas;
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }
}
