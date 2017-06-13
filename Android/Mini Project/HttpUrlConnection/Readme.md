# HttpUrlConnection
#### 1. JSON
#### 2. REST API
#### 3. HttpUrlConnection

- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/HttpUrlConnection/app/src/main/java/com/mdy/android/httpurlconnection/MainActivity.java)**

## 1. JSON
#### (1) json 기본형
- 문법
  - json 오브젝트 = { 중괄호와 중괄호 사이 }
- JSON 데이터는
```
{ "변수명" : "값", "변수명2" : "값2", "변수명3" : "값3" }
```

#### (2) json 서브트리
- 문법
```
{ "변수명" : json 오브젝트 }
{ "변수명" : { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" } }
```

- 사용방법
```
json.변수명.서브명    //  <-  값이 꺼내진다.
```


#### (3)) json 배열
- 문법
```
{ "변수명" : json 배열 }
{ "변수명" : [
               { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" }
             , { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" }
             , { "서브명" : "값", "서브명2" : "값2", "서브명3" : "값3" }
             ]
}
```

- 사용방법
```
json.변수명[0].서브명.
json.변수명[1].서브명2
```



## 2. REST API
- 서울 열린데이터 광장
  - http://data.seoul.go.kr/
- 서울시 공중화장실 위치정보
  - http://data.seoul.go.kr/openinf/sheetview.jsp?infId=OA-162&tMenu=11
- Open API
  - 샘플URL (서울시화장실정보)
    - http://openAPI.seoul.go.kr:8088/(인증키)/xml/SearchPublicToiletPOIService/1/5/
    - [Xml] http://openAPI.seoul.go.kr:8088/sample/xml/SearchPublicToiletPOIService/1/5/
    - [JSON] http://openAPI.seoul.go.kr:8088/sample/json/SearchPublicToiletPOIService/1/5/

#### REST API 주소체계 와 Query String 비교
- REST API 주소체계
```
  openApi.seoul.go.kr
  /sample
  /json
  /GeoINfoPoolWGS
  /1
  /5
```

- Query String
```
  openApi.seoul.go.kr
    ? key     = sample
    & type    = json
    & service = GeoINfoPoolWGS
    & start   = 1
    & end     = 5
```

- 서버에서는 REST API 형태와 비 REST API 형태로 들어오는 요청에 대해 각각 응답을 해주도록 설계되어 있다.
- 기존에는 xml로 데이터를 주고 받았었는데 더 간편하고, 데이터의 크기를 줄이기 위해 JSON이 개발되어 사용되고 있다.





## 3. HttpUrlConnection

#### 설정
- manifest에 INTERNET 퍼미션 꼭 해줄 것.
  ```xml
  <uses-permission android:name="android.permission.INTERNET"/>
  ```
- 사용하는 핸드폰 기기의 Wifi를 연결해줄 것
<br>

#### 네트워크는 Sub Thread에서 돌리도록 강제되어 있다.

- 네트워크는 Main Thread에서 돌릴 수 없고, Sub Thread에서 돌리도록 강제되어 있다.
  - Thread를 사용할지 AsyncTask를 사용할지 결정을 해야하는데
    - Thread를 사용할때 데이터를 주고 받는게 handler인데 handler의 기본데이터인 argument, what은 다 integer이다.
    - 그래서  string을 바로 주고 받기는 어렵다. (설계가 그렇게 안되어 있으니까)
    - 그래서 여기서는 AsyncTask를 사용했다.
<br>


#### 인자로 받은 url로 네트워크를 통해 데이터를 가져오는 함수를 만든다.
- ***처음에 네트워크에서 데이터를 가져온 순간에는 무조건 string 이다.***
- 그래서 리턴 타입은 String이다.
<br>

#### HttpURLConnection 객체
- DBHelper랑 비슷한 역할을 한다.
- 네트워크랑 연결해주는 역할을 한다.

#### 네트워크처리
- 요청처리 Request
- 응답처리 Response
```java
public String getData(String url) throws Exception {    // <- 요청한 곳에서 Exception 처리를 해준다.
        String result = "";

        /* 네트웍 처리 */
        // 1. 요청처리 Request (서버 url 연결)
        // 1.1 URL 객체 만들기
        URL serverUrl = new URL(url);

        /* HttpURLConnection */
        // DBHelper랑 비슷한 역할을 함.
        // 네트웍이랑 연결해주는 역할

        // 1.2 연결객체 생성
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection(); // url 객체에서 연결을 꺼낸다.
        // 1.3 http method 결정
        con.setRequestMethod("GET");


        //////////// 여기까지는 내가 서버에 무엇인가를 요청한 상태 ////////////


        // 2. 응답처리 Response
        // 2.1 응답코드 분석
        int responseCode = con.getResponseCode();   // int로 응답코드를 넘겨준다.

        // 2.2 정상적인 응답처리
        if(responseCode == HttpURLConnection.HTTP_OK){
            // 정상적인 코드 처리
            // 서버에서 데이터를 정상적으로 보내줄 수 있다는 뜻

            // 읽어온 데이터를 빠르게 처리하기 위해 Buffer에 담는다.
            // BufferReader에 담아놓았을 때, 좋은 점은 줄단위로 읽을 수 있다는 것이다.
            // 여기서는 BufferedReader로 했는데 나중에는 Text만 받는게 아니기 때문에 BufferedInputStream으로 해야 한다.
            // con.getInputStream() 을 BufferedReader가 읽을 수 있는 형태로 Wrapping을 해줘야 한다..
            BufferedReader br = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            // 여기까지 스트림을 열어서 버퍼에 담는 것까지 해준 상태이다.

            String temp = null;
            while( (temp = br.readLine()) != null){
                // br.readLine() 으로 한줄씩 읽는다. (null이 아닐때까지)
                // readLine()은 return이 String이다.
                result += temp;
            }

        // 2.3 오류에 대한 응답처리 (서버에서 데이터를 정상적으로 보낼 수 없을 때)
        } else {
            // 각자 호출측으로 Exception 을 만들어서 넘겨줄 것~~~ (throws로 날려주는 방법이 있음.)
            Log.e("Network", "error_code="+responseCode);
        }
        return result;
    }
```


#### AsyncTask를 처리해주는 newTask 메소드
- AsyncTask를 사용하면 리턴타입이 생길 수 있을까?
  - =>  불가능하다.
- 그 이유는 AsyncTask를 사용한다는 것은 Sub Thread에서 무엇인가를 돌린다는 것인데 return을 해버리면 결과가 나오기 전에 return이 되버린다.
- 그래서 AsyncTask나 Thread를 호출하는 함수는 대부분 리턴타입이 void이다. 리턴을 할 수가 없다.
- 언제끝날지 모르기때문에 기다릴 수 없다. 몇분 안에 끝날지 모르고, 그렇게 기다리면 시스템에 영향을 미치게 된다.

```java
public void newTask(String url) {
        // 서브 thread 를 생성 (리턴타입 void)

        new AsyncTask<String, Void, String>(){

            // 백그라운드 처리 함수
            // AsyncTask의 doInBackground 메소드는 Thread의 run과 같은 역할이다.
            // 쓰레드를 사용해서 쓰레드 안에서 HttpUrlConnection을 통해서 서버에 접속하고 데이터를 가져왔다. (result)
            // 그러면 결과값(result)을 화면에 출력해주려면
            // 쓰레드에서 처리한 값을 화면에 출력해주려면 서브쓰레드에서 처리한 값을 메인쓰레드로 넘겨줘야한다.
            // 그것을 하는 것이 onPostExecute() 이다.
            // 그러면 doInBackground()의 리턴타입이 바뀌고, onPostExecute()의 인자 타입이 String으로 바뀐다.
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                    // getData 함수로 데이터를 가져온다.
                    // getData 함수는 throws Exception을 해줬기 때문에 try ~ catch를 해줘야 한다.
                    result = getData(params[0]);
//                    Log.i("Network", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                textView.setText(result);
            }
        }.execute(url);

    }
```
