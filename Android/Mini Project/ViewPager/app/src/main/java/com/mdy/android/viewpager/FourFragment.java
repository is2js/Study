package com.mdy.android.viewpager;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
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
        MainActivity activity = (MainActivity) getActivity();   // getActivity();는 Fragment()가 가지고 있는 고유 함수이다. 나를 상속하고 있는 엑티비티???

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


    // 리스널르 등록하려면 locationManager를 통해 리스너를 등록할 수 있다.

    // 현재 프래그먼트가 러닝직전
    @Override
    public void onResume() {
        super.onResume();

        // GPS 리스너 등록           =>  위치제공자 사용을 위한 권한처리를 해줘야 한다. (MainAcitivity에 했음.)
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
