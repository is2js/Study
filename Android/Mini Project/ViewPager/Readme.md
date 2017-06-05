# ViewPager
ViewPager에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ViewPager/app/src/main/java/com/mdy/android/viewpager/MainActivity.java)**

## ViewPager, TabLayout 추가
- ViewPager
```xml
  <android.support.v4.view.ViewPager
      android:id="@+id/pager"
      android:layout_width="match_parent"
      android:layout_height="0dp"
      android:layout_marginEnd="0dp"
      android:layout_marginLeft="0dp"
      android:layout_marginRight="0dp"
      android:layout_marginStart="0dp"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintHorizontal_bias="0.0"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      android:layout_marginTop="0dp"
      app:layout_constraintTop_toBottomOf="@+id/tab"
      app:layout_constraintVertical_bias="0.0"></android.support.v4.view.ViewPager>
```
- TabLayout
```xml
  <android.support.design.widget.TabLayout
      android:id="@+id/tab"
      android:layout_width="match_parent"
      android:layout_height="48dp"
      app:layout_constraintHorizontal_bias="0.0"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      app:layout_constraintTop_toTopOf="parent"
      android:layout_marginTop="0dp">
```

## PagerAdapter 클래스
- PagerAdapter 클래스 만들기
```java
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
```


## ViewPager와 TabLayout의 연결
- ViewPager와 TabLayout을 연결해준다.
```java
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
```

## Google Map
- Google Map을 사용해본다.   [소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/ViewPager/app/src/main/java/com/mdy/android/viewpager/FourFragment.java)

```java
public class FourFragment extends Fragment implements OnMapReadyCallback {

    LocationManager manager = null;
    GoogleMap map = null;

    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        // 프래그먼트에서 map(아이디) 호출하기
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map); // 프래그먼트 안에서 프래그먼트를 가져오기
        // 맵이 사용할 준비가 되면 onMapReady 함수가 자동으로 호출된다.
        mapFragment.getMapAsync(this);

        // 상위 액티비티의 자원을 사용하기 위해서 Activity를 가져온다.
        // 캐스팅을 해줘야 내가 만든 함수를 사용할 수 있다.
        MainActivity activity = (MainActivity) getActivity();   // getActivity();는 Fragment()가 가지고 있는 고유 함수이다. getActivity()는 내가 포함된 액티비티를 반환한다.

        manager = activity.getLocationManager();

        return view;
    }

    // 이 함수에서부터 맵을 그리기 시작
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;    // map은 전역변수
        // 좌표만 생성
        LatLng sinsa = new LatLng(37.516066, 127.019461);
        // 좌표를 적용
        // 1. 마커를 생성하고
        MarkerOptions marker = new MarkerOptions();
        marker.position(sinsa);
        marker.title("신사역");
        // 1.2 마커를 화면에 그린다.
        map.addMarker(marker); // googleMap은 시스템이 던져준 화면의 변수

        // 2. 맵의 중심을 해당 좌표로 이동시킨다.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sinsa, 17)); // 인자값(좌표, 줌레벨)
    }


    // 리스너를 등록하려면 locationManager를 통해 리스너를 등록할 수 있다.

    // 현재 프래그먼트가 러닝직전
    @Override
    public void onResume() {
        super.onResume();

        // GPS 리스너 등록
        // =>  위치제공자 사용을 위한 권한처리를 해줘야 한다. (MainAcitivity에 했음.)
        // =>  예외처리를 해줘야 한다.

        // 마시멜로 이상버전에서는 런타임 권한 체크여부를 확인해야 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // GPS 사용을 위한 권한 획득이 되어 있지 않으면 리스너 등록을 하지 않는다.
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,    //위치 제공자
                3000,   // 변경사항 체크 주기 milisecond 단위 (3초마다)
                1,      // 변경사항 체크 거리 meter 단위 (내가 1m 단위를 벗어나면 변경)
                locationListener
        );
    }


    // 현재 프래그먼트가 정지(Pause)
    @Override
    public void onPause() {
        super.onPause();

        // 마시멜로 이상버전에서는 런타임 권한 체크여부를 확인해야 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // GPS 사용을 위한 권한 획득이 되어 있지 않으면 리스너를 해제하지 않는다.
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }

        // 리스너 해제 (프래그먼트가 더이상 동작하지 않으면 리스너를 해제한다.)
        manager.removeUpdates(locationListener);
    }


    // GPS 사용을 위해서 좌표 리스너를 생성
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {  // 내 좌표가 변경되면 내 위치를 넘겨준다.
            // 경도
            double lng = location.getLongitude();
            // 위도
            double lat = location.getLatitude();
            // 고도
            double alt = location.getAltitude();
            // 정확도
            float acc = location.getAccuracy();
            // 위치제공자
            String provider = location.getProvider();

            // 바뀐 현재좌표
            LatLng current = new LatLng(lat, lng);

            // 현재좌표로 카메라를 이동
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 17));
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
```
