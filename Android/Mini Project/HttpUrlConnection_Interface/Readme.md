# HttpUrlConnection_Interface
- #### 공공API 로 제공되는 json 데이터를 활용해서 맵에 좌표를 출력합니다
<br>

#### 1. JSON / GSON
#### 2. String 연산
#### 3. GoogleMap / Marker


- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/HttpUrlConnection_Interface/app/src/main/java/com/mdy/android/httpurlconnection_interface/MainActivity.java)**
<br>


## 1. JSON / GSON
### [1] JSON
- JSON은 JavaScript Object Notation으로 데이터 교환포맷의 일종이다.
- XML에 비해 경량화된 데이터 교환포맷으로 기존 XML이 상당히 무거운데 그 점을 보완할 수 있는 것이 JSON이다.
- Ajax로 서버와 통신하며 데이터를 주고 받을 때 데이터 교환을 쉽게 하기 위해 JSON을 사용한다.

- 주고 받을 수 있는 자료형은 숫자, 문자열, boolean, 배열, 객체이며 기본 데이터 배열은 KEY와 VALUE로 구성되어 있으며 중괄호로 감싼다.
- KEY값은 문자열이기 때문에 반드시 "KEYNAME" 이렇게 쌍따옴표를 붙여줘야 하고 VALUE에는 기본 자료형이나 배열, 객체를 넣으면 된다.

<br>
- JSON의 기본표현 형태
  ```
  {
      "age": 25,
      "name": "HongGilDong",
      "family": {"father": "HongFather", "mother": "HongMother"}
  }
  ```

### [2] GSON
- GSON는 json 형태를 자바 객체로 변환하는데 사용할 수 있도록 구글에서 제공한 자바 라이브러리 이다.
- 기본적으로 주로 Gson 클래스를 사용하고, new Gson() 으로 객체를 생성할 수 있다.
- ##### 설정
  ```xml
  dependencies {
      ...

      compile 'com.google.code.gson:gson:2.8.+'

      ...
  }
  ```

- 사용방법
  - (1) json string -> java object
    - gson 객체의 fromJson() 메서드를 사용하여 쉽게 매핑할 수 있다.
    ```java
    @Override
    public void postExecute(String jsonString){
        Gson gson = new Gson();

        // 1. json String -> class 로 변환
        Data data = gson.fromJson(jsonString, Data.class);
        ...
    }
    ```

  - (2) java object -> json string
    - gson 객체를 생성 후 toJson() 메서드를 사용하여 Map객체를 json 형태로 변환할 수 있다.

- ##### GSON의 특징
  - JSON 파일을 쉽게 읽고 만들 수있는 toJson(), fromJson() 메소드를 API로 제공한다.
  - JAVA Generics를 지원한다.(타입을 제한한다.)

- ##### 직렬화 / 역직렬화
  - 직렬화: 객체 → string
  - 역직렬화: string → 객체

- ##### 데이터통신은 string으로만 한다.

## 2. String 연산
- (1) String
- (2) StringBuffer    - <동기화 지원>    - StringBuilder보다 속도가 느림.
- (3) StringBuilder   - <동기화 미지원>  - 문자열 연산시 가장 빠름 (StringBuffer보다 몇십배 빠르다.)

> <String 연산>
> String result = "문자열1" + "문자열2";
> result = "문자열1문자열2";
> String result = "문자열1" + "문자열2" + "문자열3";
> '문자열1' 과 '문자열2' 를 위한 메모리 공간이 할당되고,
> 이 합쳐진 값과 '문자열3'을 위한 메모리 공간이 할당된다.
>
>> StringBuffer sb = new StringBuffer();   // 동기화 지원
>> sb.append("문자열");
>> sb.append("문자열");
>>
>>
>> StringBuilder sbl = new StringBuilder();    // 동기화 미지원(속도가 빠름)
>> sbl.append("문자열");
>> slb.append("문자열");

---
String, StringBuffer, StringBuilder ... 모두 문자열을 저장하고, 관리하는 클래스인 것 같기는 한데, 왜 이렇게 굳이 여러가지를 만들어 놓았을까요?

먼저 String 과 다른 클래스(StringBuffer, StringBuilder)의 차이점을 알아보겠습니다.

두 문자열 클래스의 아주 기본적인 차이는 String은 immutable(불변함)하고, StringBuffer는 mutable(변함,변하기쉬움)하다는 것입니다.

String 객체는 한 번 생성되면 할당된 메모리 공간이 변하지 않습니다. + 연산자 또는 concat 메서드를 통해 기존에 생성된 String 클래스 객체 문자열에 다른 문자열을 붙여도 기존 문자열에 새로운 문자열이 붙는 것이 아니라, 새로운 String 객체를 만든 후, 새 String 객체에 연결된 문자열을 저장하고, 그 객체를 참조하도록 합니다. (즉, String 클래스 객체는 Heap 메모리 영역(가비지 컬렉션이 동작하는 영역)에 생성되며, 한 번 생성된 객체의 내부 내용을 변화시킬 수 없습니다. 기존 객체가 제거 되면 Java의 가비지 컬렉션이 회수합니다.)

String 객체는 이러한 이유로 문자열 연산이 많은 경우, 그 성능이 좋지 않습니다. 하지만, Imuutable한 객체는 간단하게 사용가능하고, 동기화에 대해 신경쓰지 않아도 되기 때문에(Thread-Safe), 내부 데이터를 자유롭게 공유할 수 있습니다.

그럼 StringBuffer와 StringBuilder 클래스를 한 번 볼까요?
StringBuffer/StringBuffer는 String 과 다르게 동작합니다. 문자열 연산 등으로 기존 객체에 공간이 부족하게 되는 경우, 기존의 버퍼 크기를 늘리며 유연하게 동작합니다. StringBuffer와 StringBuilder 클래스가 제공하는 메서드는 서로 동일합니다.

그럼 두 클래스의 차이점은 무엇일까요? 바로 동기화 여부입니다.

StringBuffer는 각 메서드 별로 Synchronized Keyword 가 존재하여, 멀티스레드 환경에서도 동기화를 지원합니다. 하지만, StringBuilder는 동기화를 보장하지 않습니다.
그렇기 때문에 멀티스레드 환경이라면 값 동기화 보장을 위해 StringBuffer를 사용하고, 단일스레드 환경이라면 StringBuilder를 사용하는 것이 좋습니다. 단일스레드환경에서 StringBuffer를 사용한다고 문제가 되는 것은 아니지만, 동기화 관련 처리로 인해 StringBuilder에 비해 성능이 좋지 않습니다.

사실 JDK 1.5 버전 이전에서는 이전에서는 문자열 연산(+, concat)을 할 때에는 조합된 문자열을 새로운 메모리에 할당하여 참조함으로 인해서 성능상의 이슈가 있었습니다(위에서 설명한 String 클래스 동작 방식에 의한 이유). 그러나 JDK 1.5 버전 이후에는 컴파일 단계에서 String 객체를 사용하더라도 StringBuilder로 컴파일 되도록 변경되었습니다. 그리하여 JDK 1.5 이후 버전에서는 String 클래스를 활용해도 StringBuilder와 성능상으로 차이가 없어졌습니다.


단순한 성능만 놓고 본다면 연산이 많은 경우
StringBuilder > StringBuffer >>> String
정도로 보시면 되겠습니다.
하지만 각 문자열 클래스들은 성능 이슈 외에도 사용 편의성, 멀티스레드 환경 등 여러가지 고려해야할 요인이 있으므로 이에 적합한 것을 사용하면 될 것입니다.

* 참조: http://ooz.co.kr/298
---
<br>
<br>


## 3. GoogleMap / Marker

### 사용방법
(1) 인터페이스 구현 : implements OnMapReadyCallback
(2) Override Methods  -  **onMapReady()**
  ```java
    @Override
    public void onMapReady(GoogleMap googleMap) {

      ...

    }
  ```


### 적용
- onCreate
  (1) Map을 세팅
  (2) 로드되면 onReady 호출되로록 설정
- 좌표 생성 후, 지도에 마커를 더한다.
- onMapReady() 메소드

### 코드
```java
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
```
