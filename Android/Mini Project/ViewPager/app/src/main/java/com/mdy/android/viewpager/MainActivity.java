package com.mdy.android.viewpager;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tab;

    Fragment one;
    Fragment two;
    Fragment three;
    Fragment four;

    LocationManager manager;

    public LocationManager getLocationManager(){
        return manager;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 단위로 다룰때는 액티비티에 생성해준다.
        // 액티비티만 살아있으면 프래그먼트가 바뀌어도 LocationManager는 계속 살아있다.
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);   // Context 클래스에 있는 상수라는 것을 알려주기 위해서 Context.을 써준 것이다.


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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
    }

    // 위치제공자 사용을 위한 권한처리
    private final int REQ_PERMISSION = 100;
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        // 1. 권한체크 - 특정권한이 있는지 시스템에 물어본다
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }else{
            // 2. 권한이 없으면 사용자에 권한을 달라고 요청
            String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions ,REQ_PERMISSION); // -> 권한을 요구하는 팝업이 사용자 화면에 노출된다
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            // 3.1 사용자가 승인을 했음
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                cancel();
            }
        }
    }

    public void cancel(){
        Toast.makeText(this, "권한요청을 승인하셔야 GPS를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        finish();
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
