# RxAndroidBasic09
#### `RxJava`, `Retrofit` 을 이용해 서울시 Open API 정보를 안드로이드 화면에 출력해본다.


<br>
<br>

## Gradle 추가
```groovy
implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'           // Rx 안드로이드
implementation 'com.squareup.retrofit2:retrofit:2.3.0'          // Retrofit
implementation 'com.squareup.retrofit2:converter-gson:2.3.0'    // Retrofit json 컨버터
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'   // Retrofit Rx 아답터
```


<br>

## 주의사항
- JSON으로 가져올때, String으로 담는 것이 안전하다. int로 담을 경우, 넘어오는 값이 null이면 int에 저장하지 못해 Exception이 발생한다. 그래서 숫자라고 할지라도 String으로 넘겨받고, 변환해주는게 안전하다.
- 안드로이드 스튜디오 3.0 버전으로 실행헀더니 갤럭시노트2에서 에러발생했음.
- Retrofit 사용 (@GET / @Path)
  - URL 다루기
    - 요청 URL은 동적으로 부분 치환 가능하며, 이는 메소드 매개변수로 변경이 가능합니다. 부분 치환은 영문/숫자로 이루어진 문자열을 { 와 } 로 감싸 정의해줍니다. 반드시 이에 대응하는 @Path 를 메소드 매개변수에 명시해줘야합니다.

```java
@GET("/group/{id}/users")
Call<List<User>> groupList(@Path("id") int groupId);
```



<br>


## 서울시 Open API 사용
### 서울시 관측소별 실시간 기상관측 현황
- 샘플 URL

![OpenAPI_01](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/OpenAPI_01.PNG)

- xml

![OpenAPI_02](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/OpenAPI_02.PNG)

- json

![OpenAPI_03](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/OpenAPI_03.PNG)

- Convert JSON to Java Pojo Classes

![OpenAPI_04](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/OpenAPI_04.PNG)

- 변환 결과

![OpenAPI_05](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/OpenAPI_05.PNG)


<br>

## 예제 소스 코드
- ##### 전역변수 선언
```java
// http://openAPI.seoul.go.kr:8088/(인증키)/json/RealtimeWeatherStation/1/5/중구
// 인증키 : 5043654c666d647935386866617047

public static final String SERVER = "http://openAPI.seoul.go.kr:8088/";
public static final String SERVER_KEY = "5043654c666d647935386866617047";

// 데이터 정의
List<Info> data = new ArrayList<>();

```

- ##### 인터페이스
```java
interface IWeather {
    // @GET("인증키/json/RealtimeWeatherStation/시작인덱스/가져올개수/구이름")
    @GET("{key}/json/RealtimeWeatherStation/{start}/{count}/{name}")
    Observable<Data> getData(@Path("key") String server_key
            , @Path("start") int begin_index
            , @Path("count") int offset
            , @Path("name") String gu);
}
```

- ##### Retrofit 사용
```java
  // 1. Retrofit 생성
  Retrofit client = new Retrofit.Builder()
          .baseUrl(SERVER)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();

  // 2. 서비스 생성
  IWeather service = client.create(IWeather.class);

  // 3. 옵저버블 생성 ( addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 이걸 해줬기 때문에 가능 )
  Observable<Data> observable = service.getData(SERVER_KEY, 1, 10, editTextName.getText().toString()+"");

  // 4. 발행 시작
  observable.subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          // 구독 시작
          .subscribe(
                  item -> {
                      // 예외 처리
                      if(item.getRealtimeWeatherStation() == null) {
                        Toast.makeText(getApplicationContext(), "데이터 형식이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                        return; // return; 을 해줘야 null 일때 아래 코드가 실행되지 않는다.
                      }


                      Row rows[] = item.getRealtimeWeatherStation().getRow();
                      data.clear();   // 목록에 있는 것들을 다 삭제한다.
                      for(Row row : rows){
                          Info info = new Info();
                          info.SAWS_OBS_TM = row.getSAWS_OBS_TM();    // 측정일시
                          info.STN_NM = row.getSTN_NM();              // 지점명
                          info.STN_ID = row.getSTN_ID();              // 지점코드
                          info.SAWS_TA_AVG = row.getSAWS_TA_AVG();    // 기온
                          info.SAWS_HD = row.getSAWS_HD();            // 습도
                          info.CODE = row.getCODE();                  // 풍향1
                          info.NAME = row.getNAME();                  // 풍향2
                          info.SAWS_WS_AVG = row.getSAWS_WS_AVG();    // 풍속
                          info.SAWS_RN_SUM = row.getSAWS_RN_SUM();    // 강수
                          info.SAWS_SOLAR = row.getSAWS_SOLAR();      // 일사
                          info.SAWS_SHINE = row.getSAWS_SHINE();      // 일조

                          data.add(info);
                      }
                      adapter.notifyDataSetChanged();
                  }
          );
```


- ##### Domain
  - Data.java
  ```java
  public class Data {
      private RealtimeWeatherStation RealtimeWeatherStation;

      public RealtimeWeatherStation getRealtimeWeatherStation ()
      {
          return RealtimeWeatherStation;
      }

      public void setRealtimeWeatherStation (RealtimeWeatherStation RealtimeWeatherStation)
      {
          this.RealtimeWeatherStation = RealtimeWeatherStation;
      }

      @Override
      public String toString()
      {
          return "ClassPojo [RealtimeWeatherStation = "+RealtimeWeatherStation+"]";
      }
  }
  ```

  - RealtimeWeatherStation.java
  ```java
  public class RealtimeWeatherStation {
    private RESULT RESULT;

    private String list_total_count;

    private Row[] row;

    public RESULT getRESULT ()
    {
        return RESULT;
    }

    public void setRESULT (RESULT RESULT)
    {
        this.RESULT = RESULT;
    }

    public String getList_total_count ()
    {
        return list_total_count;
    }

    public void setList_total_count (String list_total_count)
    {
        this.list_total_count = list_total_count;
    }

    public Row[] getRow ()
    {
        return row;
    }

    public void setRow (Row[] row)
    {
        this.row = row;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RESULT = "+RESULT+", list_total_count = "+list_total_count+", row = "+row+"]";
    }
  }
  ```
  - RESULT.java
  ```java
  public class RESULT {
    private String MESSAGE;

    private String CODE;

    public String getMESSAGE ()
    {
        return MESSAGE;
    }

    public void setMESSAGE (String MESSAGE)
    {
        this.MESSAGE = MESSAGE;
    }

    public String getCODE ()
    {
        return CODE;
    }

    public void setCODE (String CODE)
    {
        this.CODE = CODE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [MESSAGE = "+MESSAGE+", CODE = "+CODE+"]";
    }
  }
  ```
  - Row.java
  ```java
  public class Row {
    private String SAWS_SOLAR;

    private String SAWS_OBS_TM;

    private String SAWS_SHINE;

    private String SAWS_WS_AVG;

    private String NAME;

    private String SAWS_HD;

    private String STN_NM;

    private String SAWS_RN_SUM;

    private String STN_ID;

    private String CODE;

    private String SAWS_TA_AVG;

    public String getSAWS_SOLAR ()
    {
        return SAWS_SOLAR;
    }

    public void setSAWS_SOLAR (String SAWS_SOLAR)
    {
        this.SAWS_SOLAR = SAWS_SOLAR;
    }

    public String getSAWS_OBS_TM ()
    {
        return SAWS_OBS_TM;
    }

    public void setSAWS_OBS_TM (String SAWS_OBS_TM)
    {
        this.SAWS_OBS_TM = SAWS_OBS_TM;
    }

    public String getSAWS_SHINE ()
    {
        return SAWS_SHINE;
    }

    public void setSAWS_SHINE (String SAWS_SHINE)
    {
        this.SAWS_SHINE = SAWS_SHINE;
    }

    public String getSAWS_WS_AVG ()
    {
        return SAWS_WS_AVG;
    }

    public void setSAWS_WS_AVG (String SAWS_WS_AVG)
    {
        this.SAWS_WS_AVG = SAWS_WS_AVG;
    }

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    public String getSAWS_HD ()
    {
        return SAWS_HD;
    }

    public void setSAWS_HD (String SAWS_HD)
    {
        this.SAWS_HD = SAWS_HD;
    }

    public String getSTN_NM ()
    {
        return STN_NM;
    }

    public void setSTN_NM (String STN_NM)
    {
        this.STN_NM = STN_NM;
    }

    public String getSAWS_RN_SUM ()
    {
        return SAWS_RN_SUM;
    }

    public void setSAWS_RN_SUM (String SAWS_RN_SUM)
    {
        this.SAWS_RN_SUM = SAWS_RN_SUM;
    }

    public String getSTN_ID ()
    {
        return STN_ID;
    }

    public void setSTN_ID (String STN_ID)
    {
        this.STN_ID = STN_ID;
    }

    public String getCODE ()
    {
        return CODE;
    }

    public void setCODE (String CODE)
    {
        this.CODE = CODE;
    }

    public String getSAWS_TA_AVG ()
    {
        return SAWS_TA_AVG;
    }

    public void setSAWS_TA_AVG (String SAWS_TA_AVG)
    {
        this.SAWS_TA_AVG = SAWS_TA_AVG;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SAWS_SOLAR = "+SAWS_SOLAR+", SAWS_OBS_TM = "+SAWS_OBS_TM+", SAWS_SHINE = "+SAWS_SHINE+", SAWS_WS_AVG = "+SAWS_WS_AVG+", NAME = "+NAME+", SAWS_HD = "+SAWS_HD+", STN_NM = "+STN_NM+", SAWS_RN_SUM = "+SAWS_RN_SUM+", STN_ID = "+STN_ID+", CODE = "+CODE+", SAWS_TA_AVG = "+SAWS_TA_AVG+"]";
    }
  }
  ```


- ##### Info.java (출력화면에 보여줄 자료를 담는 데이터 클래스)  

```java
public class Info {
    public String SAWS_OBS_TM;  // 측정일시
    public String STN_NM;       // 지점명
    public String STN_ID;       // 지점코드
    public String SAWS_TA_AVG;  // 기온
    public String SAWS_HD;      // 습도
    public String CODE;         // 풍향
    public String NAME;         // 풍향2
    public String SAWS_WS_AVG;  // 풍속
    public String SAWS_RN_SUM;  // 강수
    public String SAWS_SOLAR;   // 일사
    public String SAWS_SHINE;   // 일조
}
```

- ##### CustomAdapter.java (Recycler뷰)
```java
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

    List<Info> data = null;
    LayoutInflater inflater = null;

    public CustomAdapter(Context context, List<Info> data){
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Info info = data.get(position);
        holder.txtSAWS_OBS_TM.setText("측정일시 : " + info.SAWS_OBS_TM);
        holder.txtSTN_NM.setText("지점명 : " + info.STN_NM);
        holder.txtSTN_ID.setText("지점코드 : " + info.STN_ID);
        holder.txtSAWS_TA_AVG.setText("기온 : " + info.SAWS_TA_AVG + " 도");
        holder.txtSAWS_HD.setText("습도 : " + info.SAWS_HD + " %");
        holder.txtCODE.setText("풍향1 : " + info.CODE);
        holder.txtNAME.setText("풍향2 : " + info.NAME);
        holder.txtSAWS_WS_AVG.setText("풍속 : " + info.SAWS_WS_AVG + " m/s");
        holder.txtSAWS_RN_SUM.setText("강수 : " + info.SAWS_RN_SUM + " mm");
        holder.txtSAWS_SOLAR.setText("일사 : " + info.SAWS_SOLAR);
        holder.txtSAWS_SHINE.setText("일조 : " + info.SAWS_SHINE + " hour");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtSAWS_OBS_TM;    // 측정일시
        TextView txtSTN_NM;         // 지점명
        TextView txtSTN_ID;         // 지점코드
        TextView txtSAWS_TA_AVG;    // 기온
        TextView txtSAWS_HD;        // 습도
        TextView txtCODE;           // 풍향1
        TextView txtNAME;           // 풍향2
        TextView txtSAWS_WS_AVG;    // 풍속
        TextView txtSAWS_RN_SUM;    // 강수
        TextView txtSAWS_SOLAR;     // 일사
        TextView txtSAWS_SHINE;     // 일조

        public Holder(View itemView) {
            super(itemView);
            txtSAWS_OBS_TM = (TextView) itemView.findViewById(R.id.txtSAWS_OBS_TM);
            txtSTN_NM = (TextView) itemView.findViewById(R.id.txtSTN_NM);
            txtSTN_ID = (TextView) itemView.findViewById(R.id.txtSTN_ID);
            txtSAWS_TA_AVG = (TextView) itemView.findViewById(R.id.txtSAWS_TA_AVG);
            txtSAWS_HD = (TextView) itemView.findViewById(R.id.txtSAWS_HD);
            txtCODE = (TextView) itemView.findViewById(R.id.txtCODE);
            txtNAME = (TextView) itemView.findViewById(R.id.txtNAME);
            txtSAWS_WS_AVG = (TextView) itemView.findViewById(R.id.txtSAWS_WS_AVG);
            txtSAWS_RN_SUM = (TextView) itemView.findViewById(R.id.txtSAWS_RN_SUM);
            txtSAWS_SOLAR = (TextView) itemView.findViewById(R.id.txtSAWS_SOLAR);
            txtSAWS_SHINE = (TextView) itemView.findViewById(R.id.txtSAWS_SHINE);
        }
    }
}
```

<br>

## 동작화면

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/ScreenShot_01.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/ScreenShot_02.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/ScreenShot_03.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/RxAndroidBasic09/graphics/ScreenShot_04.png' width='210' height='350' />
