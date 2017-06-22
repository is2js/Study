# HttpFromMyServer
#### 내 서버에서 데이터를 가져오는 실습을 진행한다.
 - HttpURLConnection
 - getInputStream, InputStreamReader, BufferedReader
 - StringBuilder, toString()
 - AsyncTask

#### **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/HttpFromMyServer/app/src/main/java/com/mdy/android/httpfrommyserver/MainActivity.java)**

### 1. 데이터 가져오기
```java
public String getData(String url){
        StringBuilder result = new StringBuilder();
        try {
            Log.e("URL", ""+url);
            // 1. 요청처리
            URL serverUrl = new URL(url);
            // openConnection의 역할 : 주소에 해당하는 서버의 socket을 연결
            HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection(); // openConnection()의 리턴타입이 URLConnection이어서 캐스팅을 해줘야 한다.
            // OutputStream으로 데이터를 요청
            con.setRequestMethod("GET");    // HTTP 프로토콜 통신 방법 중에 GET으로 통신하겠다는 뜻
            // GET이 세팅된 다음에 데이터를 보낸다.



            // 2. 응답처리 (요청을 했으니까 서버에서 응답을 해서 보내줄 것이다.)
            // 응답한 것을 받으려면 Connection에서 InputStream을 열어서 서버가 주는 데이터를 받아서 처리한다.
            // 응답의 유효성 검사 (서버가 정상적으로 처리했다는 것을 확인하기 위해서 responseCode를 받는다.)
            // 파일이 없을때는 400번대 에러나 뜨고, 에러가 나면 500번대 에러가 나고, 성공하면 200(HTTP_OK) OK라고 날아왔다.
            int responseCode = con.getResponseCode();
            Log.e("URL", "responseCode="+responseCode);

            // 상수인 HTTP_OK(200)라는 responseCode가 왔을때만 데이터를 읽겠다고 해주는 것이다.
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = // 줄단위로 데이터를 읽기 위해서 버퍼에 담는다. (물론 속도도 빨라진다.)
                        new BufferedReader(new InputStreamReader(con.getInputStream()));
                                            // 글자를 읽을 수 있는 스트림 리더를 달아준다.(Wrapper를 달아준다.)


                /* 버퍼에서 줄단위로 글 읽는 방법 */
                //   ( br.readLine() != null )  -> br.readLine()이 null이 아닐때까지 읽겠다는 뜻..

                String temp = "";   // 임시로 저장할 수 있는 String 공간
                while( ( temp = br.readLine() ) != null ){
                    // result = result + temp;  // 그냥 String을 사용했으면 이렇게 할텐데, StringBuilder를 사용했기 때문에 문자열을 더해주는 메소드 append()를 사용한다.
                    result.append(temp+"\n");   // readLine을 사용하면 줄단위로 읽게 되는데 String연산을 하면 줄바꿈문자가 없어진다.
                }
                Log.e("URL", "result="+result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
        /* toString() 메소드
        (자바와 같은) 객체지향 언어들은 객체에 toString이라는 메소드를 기본적으로 제공한다.
        일반적으로 toString은 그 객체를 설명해주는 문자열을 리턴한다.
        그리고 객체의 toString을 덮어쓰기(overriding)하면 다른 형식의 문자열을 리턴할 수 있다.
        문자열이 기대되는 곳에서 문자열이 아닌 객체를 사용하면 시스템은 암시적으로 toString을 호출한다.
         */
    }
```

### 2. AsyncTask 사용
```java
  public void networkTask(String url){

      new AsyncTask<String, Void, String>(){

          @Override
          protected String doInBackground(String... params) {
              String result = getData(params[0]);
              return result;
          }

          @Override
          protected void onPostExecute(String r) {
              Log.e("Result", "결과 : " + r);
          }
      }.execute(url);
  }
```

### 3. 호출
- 내 서버의 데이터를 요청
```java
    // 내 서버의 데이터를 요청
    networkTask("http://192.168.10.79:8080/bbb.jsp");

    /* http://127.0.0.1:8080/bbb.jsp 으로 요청을 날리면 안되는 이유 */
    // 위의 주소로 요청을 날리면 안드로이드 폰 자체에 날리는 것이다.
    // 그렇게 하면 안되고 내 PC로 요청을 날려야 하기 때문에
    // 내 PC의 IP주소를 확인하고 요청을 날려야 한다.
```
