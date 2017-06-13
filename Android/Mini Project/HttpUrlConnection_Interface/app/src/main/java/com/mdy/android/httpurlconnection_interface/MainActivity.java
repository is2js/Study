package com.mdy.android.httpurlconnection_interface;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mdy.android.httpurlconnection_interface.domain.Data;
import com.mdy.android.httpurlconnection_interface.domain.Row;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements TaskInterface, OnMapReadyCallback{

    /* 기초정보
        url : http://openAPI.seoul.go.kr:8088/(인증키)/json/SearchPublicToiletPOIService/1/5/
        인증키 : 5043654c666d647935386866617047
        사용 : http://openAPI.seoul.go.kr:8088/5043654c666d647935386866617047/json/SearchPublicToiletPOIService/1/5/
     */
    static final String URL_PREFIX  = "http://openAPI.seoul.go.kr:8088/";
    static final String URL_CERT    = "5043654c666d647935386866617047";
    static final String URL_MID     = "/json/SearchPublicToiletPOIService/";

    int pageBegin = 1;
    int pageEnd = 10;

    // 한 페이지에 불러오는 데이터 수
    static final int PAGE_OFFSET = 10;

    ListView listView;
    TextView textView;
    String url = "";

    ArrayAdapter<String> adapter;


    // 아답터에서 사용할 데이터 공간을 정의
    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        // 데이터 - 위에서 공간 할당 됨
        // 아답터
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);

        listView.setAdapter(adapter);




        // 맵을 세팅
        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) manager.findFragmentById(R.id.mapView);
        // 로드되면 onReady 호출하도록
        mapFragment.getMapAsync(this);





    }

    private void setPage(int page) {
        pageEnd = page * PAGE_OFFSET;
        pageBegin = pageEnd - PAGE_OFFSET + 1;
    }

    private void setUrl(int begin, int end){
        // String
        // StringBuffer
        // StringBuilder   - 문자열 연산시 가장 빠름 (몇십배 차이가 남. StringBuffer보다)

        // String 연산..........
        // String result = "문자열" + "문자열";
        // result = "문자열문자열";
        // String result = "문자열" + "문자열" + "문자열";
        //                 -------------------
        //                   메모리 공간 할당
        //                 ------------------------------
        //                         메모리 공간 할당

        /*StringBuffer sb = new StringBuffer();   // 동기화 지원
        sb.append("문자열");
        sb.append("문자열");

        StringBuilder sbl = new StringBuilder();    // 동기화 미지원(속도가 빠름)
        sb.append("문자열");
        sb.append("문자열");*/
        // 요즘엔 자동으로 컴파일러가 스트림빌더로 바꿔준다.(단순한 경우)

        url = URL_PREFIX + URL_CERT + URL_MID + begin + "/" + end;
    }

    @Override
    public String getUrl(){
        return url;
    }

    @Override
    public void postExecute(String jsonString){
        Gson gson = new Gson();

        // 1. json String -> class 로 변환
        Data data = gson.fromJson(jsonString, Data.class);

        // 총 개수를 화면에 세팅
        textView.setText("총 개수:" + data.getSearchPublicToiletPOIService().getList_total_count());
        // 건물의 이름을 listView에 세팅

        Row rows[] = data.getSearchPublicToiletPOIService().getRow();

        // 네트웍에서 가져온 데이터를 꺼내서 datas에 담아준다.
        for(Row row : rows){
            datas.add(row.getFNAME());

            // row를 돌면서 화장실 하나하나의 좌표를 생성한다.
            MarkerOptions marker = new MarkerOptions();
            LatLng tempCoord = new LatLng(row.getY_WGS84(), row.getX_WGS84());
            marker.position(tempCoord);
            marker.title(row.getFNAME());

            // 지도에 마커를 더한다.
            myMap.addMarker(marker);
        }

        // 지도 컨트롤
        LatLng sinsa = new LatLng(37.516066, 127.019461);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sinsa, 10));

        // 그리고 adapter를 갱신해준다.
        adapter.notifyDataSetChanged();
    }

    GoogleMap myMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // 최초 호출시 첫번째 집합을 불러온다.
        setPage(1);
        setUrl(pageBegin, pageEnd);
        Remote.newTask(this);
    }
}