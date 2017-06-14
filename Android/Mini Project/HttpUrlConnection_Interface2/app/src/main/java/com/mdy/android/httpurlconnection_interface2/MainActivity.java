package com.mdy.android.httpurlconnection_interface2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
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
import com.mdy.android.httpurlconnection_interface2.domain.Data;
import com.mdy.android.httpurlconnection_interface2.domain.Row;

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
    static final String URL_CERT    = "5043654c666d647935386866617047";         // 인증키
    static final String URL_MID     = "/json/SearchPublicToiletPOIService/";


    // 한 페이지에 불러오는 데이터 수
    static final int PAGE_OFFSET = 10;

    // 현재 페이지
    int page = 0;

    ListView listView;
    TextView textView;
    String url = "";

    ArrayAdapter<String> adapter;


    // 아답터에서 사용할 데이터 공간을 정의
    // 이렇게 final을 붙여주면 datas는 new로는 들어올 방법이 없다.
    // datas의 메모리 공간은 바뀔 수 없다.
    // 아답터에 딱 들어가는 순간 이 datas의 데이터가 빠지고 나갈때 아답터를 notify해주면
    // 변경사항이 무조건 반영이 된다.  final을 해줘서.
    // 다른 곳에서 datas를 new 해주면 메모리 주소가 바뀌어서 아답터가 notify를 해줄 수가 없게 된다.
    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        setListener();  // 리스너는 뷰가 세팅된 다음에 호출되도 상관없다.
        setMap();


    }

    private void setViews(){
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        // 데이터는 위에서 공간 할당 됨

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas); // 아답터
        listView.setAdapter(adapter);
    }


    private void setListener(){
        // 스크롤의 상태값을 체크해주는 리스너
        listView.setOnScrollListener(scrollListener);
    }


    private void setMap(){
        // 맵을 세팅
        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) manager.findFragmentById(R.id.mapView);
        // 로드되면 onReady 호출하도록
        mapFragment.getMapAsync(this);
    }



    boolean lastItemVisible = false;    // 리스트의 마지막 아이템이 보이는지 여부를 확인해주는 변수
    // 스크롤 리스너
    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // onScrollStateChanged => 현재 스크롤이 끝까지 가서 더이상 움직이지 않는다. 그럴때 상태를 체크해준다.

            /* if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        Log.i("Scroll", "현재 스크롤이 idle 상태입니다.");   // => 스크롤을 움직이다 멈추면 멈춘 순간에 Log 메세지가 찍힌다.
                } */
            if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                    && lastItemVisible) {
                loadPage();
            }
        }




        // firstVisibleItem = 현재 보여지는 첫번째 아이템의 번호(index) / 제일 앞에 있는 아이템의 index를 넘겨준다.
        // visibleItemCount = 현재 화면에 보여지는 아이템의 개수
        // totalItemCount   = 리스트에 담겨있는 전체 아이템의 개수 / 전체 아이템의 개수
        // AbsListView 는 ListView가 상속받고 있는 부모 객체이다.
        // GridView도 AbsListView를 상속받고 있는데 GridView도 사용할 수 있게 AbsListView로 되어 있는 것이다.
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            /* Log 로 넘어오는 인자를 확인해보자. */
            Log.d("ListView", "firstVisibleItem = " + firstVisibleItem);        // 0 이 찍힌다.
            Log.d("ListView", "visibleItemCount = " + visibleItemCount);        // 5 가 찍힌다.
            Log.d("ListView", "totalItemCount = " + totalItemCount);            // 10이 찍힌다.

            // lastItemVisible은 우리가 직접 선언한 변수이다. (리스트의 마지막 아이템이 보이는지 여부를 확인해주는 변수)
            if(totalItemCount <= firstVisibleItem + visibleItemCount){
                lastItemVisible = true;
            }else{
                lastItemVisible = false;
            }
        }
    };

    // 페이지를 로드
    private void loadPage() {
        nextPage();
        setUrl();
        Remote.newTask(MainActivity.this);
    }

    private void nextPage(){
        page = page + 1;
    }


    private void setUrl(){
        int end = page * PAGE_OFFSET;
        int begin = end - PAGE_OFFSET + 1;

        url = URL_PREFIX + URL_CERT + URL_MID +begin+"/"+end;
    }

    @Override
    public String getUrl(){
        return url;
    }


    // 이 함수 안에 계산식이 하나도 없게 함수로 다 분리했다.
    @Override
    public void postExecute(String jsonString){

        Data data = convertJson(jsonString);

        int totalCount = data.getSearchPublicToiletPOIService().getList_total_count();
        Row items[] = data.getSearchPublicToiletPOIService().getRow();


        setItemCount(totalCount);

        addDatas(items);

        addMarkers(items);

        LatLng sinsa = new LatLng(37.516066, 127.019461);
        moveMapPosition(sinsa);

        // 그리고 adapter를 갱신해준다.
        adapter.notifyDataSetChanged();
    }

    // 지도 이동
    private void moveMapPosition(LatLng position){
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
    }

    // datas에 데이터 더하기
    private void addDatas(Row[] items){
        for(Row item : items){
            datas.add(item.getFNAME());
        }
    }

    // 지도에 마커 생성
    private void addMarkers(Row[] items){
        for(Row row : items){
            // row를 돌면서 화장실 하나하나의 좌표를 생성한다.
            MarkerOptions marker = new MarkerOptions();
            LatLng tempCoord = new LatLng(row.getY_WGS84(), row.getX_WGS84());
            marker.position(tempCoord);
            marker.title(row.getFNAME());

            // 지도에 마커를 더한다.
            myMap.addMarker(marker);
        }
    }

    // 총 개수를 화면에 출력
    private void setItemCount(int totalCount){
        textView.setText("총 개수:" + totalCount);
    }

    // json 스트링을 Data 오브젝트(객체)로 변환
    // 스트링을 오브젝트로 변환하면 점 뭐 점 뭐로 접근해서 데이터를 꺼내기가 쉬워진다.
    public Data convertJson(String jsonString){
        Gson gson = new Gson();

        return gson.fromJson(jsonString, Data.class);  // 스트링을 클래스로 변환해준다.
    }

    GoogleMap myMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // 최초 호출시 첫번째 집합을 불러온다.
        loadPage();
    }
}