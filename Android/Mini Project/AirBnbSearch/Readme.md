# AirBnbSearch
#### 관계형 데이터베이스(MySQL)를 이용해 원하는 데이터를 검색한다.
  - [다국어지원 준비] res - values - strings.xml
  - 버튼에 다양한 색깔의 폰트 적용하기
  - Retrofit을 이용한 데이터 통신

<br>
<br>
<br>


## [다국어지원 준비] res - values - strings.xml

#### 1. res 폴더를 오른쪽 마우스 클릭하고, New - Android resource directory 클릭하고, Available qualifiers의 Country Code를 클릭하고, 추가한다.

#### 2. 그리고 Mobile country code 를 입력한다.
- 한국의 경우, `410`을 입력하면 된다.
- 예시 코드
  - strings.xml (영어)
  ```xml
  <resources>
    <string name="app_name">AirBnbSearch</string>
    <string name="action_settings">Settings</string>

    <string name="title_select_date">Select Date</string>
    <string name="title_guests">Select Guests</string>
    <string name="title_accommodation_type">Accommodation Types</string>
    <string name="title_price">Price</string>
    <string name="title_amenities">Amenities</string>

    <string name="hint_start_date">Check in</string>
    <string name="hint_end_date">Check out</string>
    <string name="hint_select_Date">Select Date</string>
  </resources>
  ```
  - strings.xml (한국어)
  ```xml
  <resources>
    <string name="app_name">에어비앤비 검색</string>
    <string name="action_settings">설정</string>

    <string name="title_select_date">날짜 선택</string>
    <string name="title_guests">게스트</string>
    <string name="title_accommodation_type">숙소 유형</string>
    <string name="title_price">가격 범위</string>
    <string name="title_amenities">편의시설</string>
  </resources>
  ```


<br>
<br>

## 버튼에 다양한 색깔의 폰트 적용하기
#### 1. Java코드
- StringUtil.java
```java
public class StringUtil {
    public static void setHtmlText(TextView target, String text){
        target.setAllCaps(false); // 모두 대문자로 보이지 않게
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            target.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }else {
            target.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }
    }
}
```


- MainActivity.java
```java
private void setCalendarButtonText(){
    setButtonText(btnCheckIn, getString(R.string.hint_start_date), getString(R.string.hint_select_Date));
    setButtonText(btnCheckOut, getString(R.string.hint_end_date), "-");
}

private void setButtonText(Button btn, String upText, String downText){
    String inText = "<font color='#888888'>" + upText
            + "</font> <br> <font color=\"#fd5a5f\">" + downText + "</font>";
    StringUtil.setHtmlText(btn, inText);
}


...
```

- MainActivity.java 의 onCreate 안에서
```java
setCalendarButtonText();
```


#### 2. xml코드
- 버튼 안에 아래의 코드를 넣어준다. 혹은 Java코드에서 해줘도 된다.
```xml
android:textAllCaps="false"
```


<br>
<br>
<br>

## 서버쪽 코드(Node.js)
- server.js
```JavaScript
// 서버모듈
var http = require("http");
var mysql = require("mysql");
var url = require("url");
var querystring = require("querystring");

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'mydb'	// 데이터베이스
};

// 1-1. 서버를 생성
var server = http.createServer(function(request, response){
    // request : 사용자 요청정보 조회
    // response : 사용자에게 처리결과 응답


    // 1. 요청 url 분석 처리
    // /airbnb/house?checkin=2017-07-27&checkout=2017-07-31&a=1&b=abc

    if(request.url.startsWith("/airbnb/house")){
        var parsedUrl = url.parse(request.url);

        // 쿼리만 뽑아내서 쿼리를 search 객체로 만들어준다.
        var search = querystring.parse(parsedUrl.query);

        // 가. 검색조건이 없는 검색
//        executeQuery(response);
        // 나. 검색조건이 있는 검색

        executeQuery(response, search);
    }else{
        response.writeHead(404, {"Content-Type":"text/html"});
        response.end("404 page not found!");
    }

});



// 2. 쿼리 실행
function executeQuery(response, search){
    var query = "select * from house";
    if(search){
        query = " select * from house where id not in "
              + " (select a.id "
              + "   from house a join reservation b "
              + "     on a.id = b.house_id "
              + "  where b.checkin between '"+search.checkin+"' and '"+search.checkout+"' "
              + "     or b.checkout between '"+search.checkin+"' and '"+search.checkout+"' "
              + "     or (b.checkin <= '"+search.checkin+"' and b.checkout >= '"+search.checkout+"') "
              + " ) ";
        if(search.guests > -1){
            query = query + " and guests > " + search.guests;
        }
        if(search.type > -1){
            query = query + " and type = " + search.type;
        }
        if(search.price_min > -1 && search.price_max > -1){
            query = query + " and price between " + search.price_min + " and " + search.price_max;
        }
        if(search.amenities > -1){ // wifi 여부만 체크
            query = query + " and amenities = " + search.amenities;
        }
        console.log("Query:"+query);
    }
    var con = mysql.createConnection(conInfo);
    con.connect();
    con.query(query, function(err, items, fields){
        if(err){
            console.log(err);
            sendResult(response, err);
        }else{
            console.log(items);
            sendResult(response, items);
        }
        this.end();
    });
}



// 3. 결과값 전송
function sendResult(response, data){
    response.writeHead(200, {"Content-Type":"text/html"});
    response.end(JSON.stringify(data));
}



// 1-2. 서버를 시작
server.listen( 80, function() { // 80포트는 뒤에 포트이름을 생략가능하다.
    console.log("server's running...");
});
```


<br>
<br>
<br>


## 안드로이드 코드
- Data.java
```java
public class Data {
    public static List<Reservation> data;
}
```

- Reservation.java
```java
public class Reservation {
    String title;
    String user_id;
    int type;
    int guests;
    int price;
    String amenities;
}
```

- Search.java
```java
public class Search {
    public static final int TYPE_ONE = 10;
    public static final int TYPE_TWO = 20;
    public static final int TYPE_ENTIRE = 30;

    public static final String AM_WIFI     = "Wifi";
    public static final String AM_AIRCON   = "Air Conditioner";
    public static final String AM_REFRIGE  = "Refrigerator";
    public static final String AM_PARKING  = "Parking";
    public static final String AM_ELEVATOR = "Elevator";

    public String checkInDate = null;
    public String checkOutDate = null;
    private int guests = 1;
    public void setGuests(int count){
        if(count > 1) guests = count;
    }
    public int getGuests(){
        return guests;
    }
    public int type;
    public int price_min;
    public int price_max;
    public String amenities[];
}
```

- ISearch.java **(인터페이스)**
```java
public interface ISearch {
//    public static final String SERVER = "http://192.168.10.79/";

    public static final String SERVER = "http://172.30.0.108/";
    /**
     * @param checkin
     * @param checkout
     * @param guests
     * @param type          [집유형] 0:스튜디오, 1:투룸, 2:집전체
     * @param price_min
     * @param price_max
     * @param wifi_exist    [와이파이 존재여부] 0:없음, 1:있음
     * @return
     */
    @GET("airbnb/house")
    Observable<ResponseBody> get(
            @Query("checkin") String checkin
            , @Query("checkout") String checkout
            , @Query("guests") int guests
            , @Query("type") int type
            , @Query("price_min") int price_min
            , @Query("price_max") int price_max
            , @Query("amenities") int wifi_exist
    );
}
```

- StringUtil.java
```java
public class StringUtil {
    public static void setHtmlText(TextView target, String text){
        target.setAllCaps(false);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            target.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }else {
            target.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }
    }
}
```

- RecyclerAdapter.java
```java
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{

    LayoutInflater inflater;
    List<Reservation> data;

    public RecyclerAdapter(Context context, List<Reservation> data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Reservation reservation = data.get(position);
        holder.txtTitle.setText("Title : " + reservation.title);
        holder.txtGuests.setText("Guests : " + reservation.guests);
        holder.txtUserId.setText("UserId : " + reservation.user_id);
        holder.txtType.setText("Type : " + reservation.type);
        holder.txtPrice.setText("Price : " + reservation.price);
        holder.txtAmenities.setText("Amenities : " + reservation.amenities);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtUserId;
        TextView txtType;
        TextView txtGuests;
        TextView txtPrice;
        TextView txtAmenities;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtUserId = (TextView) itemView.findViewById(R.id.txtUserId);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtGuests = (TextView) itemView.findViewById(R.id.txtGuests);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtAmenities = (TextView) itemView.findViewById(R.id.txtAmenities);
        }
    }
}
```

- ListActivity.java
```java
public class ListActivity extends AppCompatActivity {

    private TextView txtReservationList;
    private Button btnBack;
    private RecyclerView recyclerView;
    private List<Reservation> data;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setViews();
        setListeners();

        data = Data.data;
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViews(){
        txtReservationList = (TextView) findViewById(R.id.txtReservationList);
        btnBack = (Button) findViewById(R.id.btnBack);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    private void setListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
```

- MainActivity.java
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private Button btnCheckIn, btnCheckOut;
    private CalendarView calendarView;


    private Search search;
    private TextView txtGuest;
    private Button btnGuestMinus;
    private Button btnGuestPlus;

    private static final int CHECK_IN = 10;
    private static final int CHECK_OUT = 20;
    private int checkStatus = CHECK_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data.data = new ArrayList<>();

        setViews();
        setCalendarButtonText();
        setListeners();
        init();
    }



    private void init() {
        search = new Search();
    }

    private void setViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        txtGuest = (TextView) findViewById(R.id.txtGuest);
        btnGuestMinus = (Button) findViewById(R.id.btnGuestMinus);
        btnGuestPlus = (Button) findViewById(R.id.btnGuestPlus);
    }

    private void setListeners(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.i("Calendar","year:"+year+", month:"+month+", dayOfMonth:"+dayOfMonth);
                String theDay = String.format("%d-%02d-%02d", year, month, dayOfMonth);
//                String theDay = year + "-" + month + "-" + dayOfMonth;
                switch (checkStatus){
                    case CHECK_IN :
                        search.checkInDate = theDay;
                        setButtonText(btnCheckIn, getString(R.string.hint_start_date), search.checkInDate);
                        break;
                    case CHECK_OUT :
                        search.checkOutDate = theDay;
                        setButtonText(btnCheckOut, getString(R.string.hint_end_date), search.checkOutDate);
                        break;
                }
            }
        });

        btnCheckIn.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);
        fab.setOnClickListener(this);
        btnGuestMinus.setOnClickListener(this);
        btnGuestPlus.setOnClickListener(this);
    }

    private void setCalendarButtonText(){
        // 버튼에 다양한 색깔의 폰트 적용하기
        // 위젯의 android:textAllCaps="false" 적용 필요
        setButtonText(btnCheckIn, getString(R.string.hint_start_date), getString(R.string.hint_select_Date));
        setButtonText(btnCheckOut, getString(R.string.hint_end_date), "-");
    }

    private void setButtonText(Button btn, String upText, String downText){
        String inText = "<font color='#888888'>" + upText
                + "</font> <br> <font color=\"#fd5a5f\">" + downText + "</font>";
        StringUtil.setHtmlText(btn, inText);
    }


    private void search(){
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(ISearch.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        ISearch myServer = client.create(ISearch.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.get(
                search.checkInDate,
                search.checkOutDate,
                search.getGuests(),
                -1,
                -1,
                -1,
                -1
        );

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 1. 데이터를 꺼내고
                            String jsonString = responseBody.string();
                            Log.e("RESULT",""+jsonString);

                            Gson gson = new Gson();

                            Reservation data[] = gson.fromJson(jsonString, Reservation[].class);

                            // 2. 아답터에 세팅하고
                            Log.w("Data.data", "==================== Data.data" + Data.data);
                            if(Data.data != null){
                                Data.data.clear();
                            }

                            for(Reservation reservation : data){
                                Data.data.add(reservation);
                            }

                            Intent intent = new Intent(MainActivity.this, ListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCheckIn:
                checkStatus = CHECK_IN;
                setButtonText(btnCheckIn, getString(R.string.hint_start_date), getString(R.string.hint_select_Date));
                setButtonText(btnCheckOut, getString(R.string.hint_end_date), search.checkOutDate);
                break;
            case R.id.btnCheckOut:
                checkStatus = CHECK_OUT;
                setButtonText(btnCheckOut, getString(R.string.hint_end_date), getString(R.string.hint_select_Date));
                setButtonText(btnCheckIn, getString(R.string.hint_start_date), search.checkInDate);
                break;
            case R.id.fab: // 검색 전송
                search();
                break;
            case R.id.btnGuestMinus:
                search.setGuests(search.getGuests()-1);
                txtGuest.setText(search.getGuests() + "");
                break;
            case R.id.btnGuestPlus:
                search.setGuests(search.getGuests()+1);
                txtGuest.setText(search.getGuests() + "");
                break;
        }
    }
}
```



<br>
<br>
<br>

## 동작화면

- 초기 화면

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/init1.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/init2.png' width='210' height='350' />

- 검색하는 화면

 <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/search1.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/search2.png' width='210' height='350' />

- 검색 후 화면
  - 선택한 날짜에 예약이 되어있지 않은 숙소만 검색이 된다.

<img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/afterSearch1.png' width='210' height='350' /> <img src = 'https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/afterSearch2.png' width='210' height='350' />

<br>

#### [기타 화면]

##### 1-1) MySQl 화면 ***(house 테이블)***

![mysql_house](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/mysql_house.png)

<br>
##### 1-2) MySQl 화면 ***(reservation 테이블)***

![mysql_reservation](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/mysql_reservation.png)

<br>

##### 2) 안드로이드 Log

![mysql_reservation](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/android_log.PNG)

<br>

##### 3) Node.js 콘솔창

![mysql_reservation](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/AirBnbSearch/graphics/server_console.PNG)
