## 2017.05.25.(목)

- #### Serialized(직렬화) / Parse
- #### 인텐트(Intent)

---
### Serialized(직렬화) / Parse
- parse라는 것은 string으로 되어 있는 것(데이터)을 `객체화` 시켜주는 것이다.
- parseInt -> `Int 객체로 바꿔준다는 것.`
- 예시
  ```java
    class Data {
      int id;
      String name;
    }
  ```
  - Data를 직렬화하면 다음과 같이 된다.
    - `String serialized = "[int:id]=36, [String:name]=안녕하세요";`
  - `parse`는 이렇게 **직렬화된 것을 정해진 규칙에 의해 객체로 만들어준다.**

#### Serialized(직렬화)의 사용하게 된 배경
- 직렬화(serialized)를 하는 가장 큰 이유는 프로세스를 넘어가면 메모리를 공유할 수 없다.
  - 앱이 달라지면 메모리를 공유할 수 없다. A앱에서 B앱 메모리 공유안된다.
  - 엑셀에서 포토샵 메모리 침범할 수 없다.
  - 컨트롤 C, 컨트롤 V하는 것은 클립보드라는 다른 메모리에 저장했다 그것을 가져오는 것이다.

- 앱 단도 그런데 네트워크 단으로 넘어가보면 네트워크 단을 넘어갔는데 내 데이터(내 객체)를 공유하려면
네트워크를 넘어가면 메모리를 엑세스할 방법이 없다.
  - 다른 컴퓨터의 메모리에 엑세스할 수 없다.


#### 그러면 객체를 공유하려면 어떻게 해야할까?
- 이때 사용하는게 직렬화(serialized)이다.
  - 직렬화를 해서 string으로 만들어주는 것이다.

- 그러면 A시스템, B시스템이 있으면 A시스템에도 Data라는 Class를 가지고 있고, B시스템에도 Data라는 Class를 가지고 있으면 직렬화된 데이터를 B시스템으로 넘기면 B시스템에서는 parse만 하면 마치 내 객체처럼 쓸 수가 있는 것이다.

<br>

> ##### Parsing(파싱)
> 파싱이란 어떤 데이터를 원하는 모양으로 만들어 내는걸 말한다.
>
>>   1)  특정문서(XML 따위)를 읽어 들여서 이를 다른 프로그램이나 서브루틴이 사용할 수 있는 내부 의  표현 방식으로 변환시켜 주는 것이다. XML 문서를 보시면 HTML처럼 <>태그가 보일 것입니다. 사용자가 이렇게 입력하지만 컴터가 알아 볼 수 있도록 바꿔주는 과정을 의미합니다.
>>2)  컴파일러의 일부로써 원시 프로그램의 명령문이나 온라인 명령문, HTML 문서등에서 마크업태그등을 입력으로 받아들여서 구문을 해석 할수 있는 단위로 여러부분으로 분할해 주는 역할을 한다.


- 내가 데이터를 보내기 위해서 객체로 만드는 것은 <직렬화>이다.
파싱은 갖고와서 내가 사용할 수 있게 만드는 것들 파싱이라고 한다. (<역질렬화>에 해당)





---
> ##### 명시적 Intent 예시
```java
Intent intent = new Intent(MainActivity.this, DetailActivity.class);
// DetailActivity.class 라고 정확하게 명시해줬다.
```


> ##### Implicit(암시적) Intent 예시
```java
btnCall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String phoneNumber = txtCall.getText().toString();
        // getText()가 EditText를 반환하기 때문에 toString()으로 해줘야한다.
        Uri uri = Uri.parse("tel:" + phoneNumber);
        // parse라는 것은 스트링으로 되어 있는 것을 객체화 시켜주는 것.
        // parse와 반대되는 것이 toString();
        // tel:  이 글씨는 바뀌면 안된다.
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        // Uri는 안드로이드에 최적화되어있는 자원을 가리키는 주소체계
        // 안드로이드의 자원은 리소드 폴더에 있는 모든 것이 자원이고, 사진을 캡쳐한 다음 갤리러로 넘어가는데 이것도 자원이다.
        // ACTION_DIAL  묵시적으로 정해진 값
        startActivity(intent);
    }
});
```
> ##### Intent Filter 구성 요소
>>  - Action, Category, Data, Type, Component name, Extras
>> 주요 액션 : ACTION_DIAL 등








---
### 인텐트 활용하기

```java
// 웹페이지 띄우기
Uri uri = Uri.parse("http://www.google.com");
Intent it  = new Intent(Intent.ACTION_VIEW,uri);
startActivity(it);

// 구글맵 띄우기
Uri uri = Uri.parse("geo:38.899533,-77.036476");
Intent it = new Intent(Intent.Action_VIEW,uri);
startActivity(it);


// 구글 길찾기 띄우기
Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=출발지주소&daddr=도착지주소&hl=ko");
Intent it = new Intent(Intent.ACTION_VIEW,URI);
startActivity(it);


// 전화 걸기
Uri uri = Uri.parse("tel:xxxxxx");
Intent it = new Intent(Intent.ACTION_DIAL, uri);
startActivity(it);


Uri uri = Uri.parse("tel.xxxxxx");
Intent it = new Intent(Intent.ACTION_CALL,uri);
// 퍼미션을 잊지 마세요. <uses-permission id="android.permission.CALL_PHONE" />


// SMS/MMS 발송
Intent it = new Intent(Intent.ACTION_VIEW);
it.putExtra("sms_body", "The SMS text");
it.setType("vnd.android-dir/mms-sms");
startActivity(it);


// SMS 발송
Uri uri = Uri.parse("smsto:0800000123");
Intent it = new Intent(Intent.ACTION_SENDTO, uri);
it.putExtra("sms_body", "The SMS text");
startActivity(it);


// MMS 발송
Uri uri = Uri.parse("content://media/external/images/media/23");
Intent it = new Intent(Intent.ACTION_SEND);
it.putExtra("sms_body", "some text");
it.putExtra(Intent.EXTRA_STREAM, uri);
it.setType("image/png");
startActivity(it);


// 이메일 발송
Uri uri = Uri.parse("mailto:xxx@abc.com");
Intent it = new Intent(Intent.ACTION_SENDTO, uri);
startActivity(it);


Intent it = new Intent(Intent.ACTION_SEND);
it.putExtra(Intent.EXTRA_EMAIL, "me@abc.com");
it.putExtra(Intent.EXTRA_TEXT, "The email body text");
it.setType("text/plain");
startActivity(Intent.createChooser(it, "Choose Email Client"));


Intent it = new Intent(Intent.ACTION_SEND);
String[] tos = {"me@abc.com"};
String[] ccs = {"you@abc.com"};
it.putExtra(Intent.EXTRA_EMAIL, tos);
it.putExtra(Intent.EXTRA_CC, ccs);
it.putExtra(Intent.EXTRA_TEXT, "The email body text");
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");
it.setType("message/rfc822");
startActivity(Intent.createChooser(it, "Choose Email Client"));


// extra 추가하기
Intent it = new Intent(Intent.ACTION_SEND);
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");
sendIntent.setType("audio/mp3");
startActivity(Intent.createChooser(it, "Choose Email Client"));


// 미디어파일 플레이 하기
Intent it = new Intent(Intent.ACTION_VIEW);
Uri uri = Uri.parse("file:///sdcard/song.mp3");
it.setDataAndType(uri, "audio/mp3");
startActivity(it);


Uri uri = Uri.withAppendedPath(
  MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
Intent it = new Intent(Intent.ACTION_VIEW, uri);
startActivity(it);


// 설치 어플 제거
Uri uri = Uri.fromParts("package", strPackageName, null);
Intent it = new Intent(Intent.ACTION_DELETE, uri);
startActivity(it);


// APK파일을 통해 제거하기
Uri uninstallUri = Uri.fromParts("package", "xxx", null);
returnIt = new Intent(Intent.ACTION_DELETE, uninstallUri);


// APK파일 설치
Uri installUri = Uri.fromParts("package", "xxx", null);
returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri);


// 음악 파일 재생
Uri playUri = Uri.parse("file:///sdcard/download/everything.mp3");
returnIt = new Intent(Intent.ACTION_VIEW, playUri);


// 첨부파일을 추가하여 메일 보내기
Intent it = new Intent(Intent.ACTION_SEND);
it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");
it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/eoe.mp3");
sendIntent.setType("audio/mp3");
startActivity(Intent.createChooser(it, "Choose Email Client"));


// 마켓에서 어플리케이션 검색
Uri uri = Uri.parse("market://search?q=pname:pkg_name");
Intent it = new Intent(Intent.ACTION_VIEW, uri);
startActivity(it);
// 패키지명은 어플리케이션의 전체 패키지명을 입력해야 합니다.


// 마켓 어플리케이션 상세 화면
Uri uri = Uri.parse("market://details?id=어플리케이션아이디");
Intent it = new Intent(Intent.ACTION_VIEW, uri);
startActivity(it);
// 아이디의 경우 마켓 퍼블리싱사이트의 어플을 선택후에 URL을 확인해보면 알 수 있습니다.


// 구글 검색
Intent intent = new Intent();
intent.setAction(Intent.ACTION_WEB_SEARCH);
intent.putExtra(SearchManager.QUERY,"searchString")
startActivity(intent);
```
