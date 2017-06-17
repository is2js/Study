# Runtime Permission & Content Resolver
 #### [1] 런타임 권한 체크(Runtime Permission)
 #### [2] 컨텐트 리졸버(Content Resolver)
  - 전화번호 데이터 불러오기

<br>

## [1] Runtime Permission - Api 23 이상
- 안드로이드는 마시멜로우(6.0) Api Level 23 이상 부터는 사용자가 접근 권한이 필요한 기능을 수행할 때, 사용자로 하여금 해당 권한을 앱에 허락할 것인지 묻고, 개발자가 아닌 사용자가 자신의 디바이스의 접근 권한을 결정하는 형태로 권한설정 구조를 변경했다.

- 런타임 권한은 권한 설정이 매니패스트에서 하는 설치타임 권한이랑 다르게 소스코드에서 권한 체크를 해줘야 한다. (마시멜로우6.0 부터 적용되었음.)

## Runtime Permission 종류
| Permission Group | Permissions  |
| :----- | :---- |
| CALENDAR  |  READ CALENDAR  |
|           |  WRITE CALENDAR  |
| CAMERA  | CAMERA  |
| CONTACTS  | READ CONTACTS  |
|   | WRITE CONTACTS  |
|   | GET ACCOUNTS  |
| LOCATION  | ACCESS FINE LOCATION  |
|   | ACCESS COARSE LOCATION  |
| MICROPHONE  | RECORD AUDIO  |
| PHONE  | READ PHONE STATE  |
|   | CALL PHONE  |
|   | READ CALL LOG  |
|   | ADD VOICEMAIL  |
|   | USE SIP  |
|   | PROCESS OUTGOING CALLS  |
| SENSORS  | BODY SENSORS  |
| SMS  | SEND SMS  |
|   | RECEIVE SMS  |
|   | READ SMS  |
|   | RECEIVE WAP PUSH  |
|   | RECEIVE MMS  |
| STORAGE  | READ EXTERNAL STORAGE  |
|   | WRITE EXTERNAL STORAGE  |

<br>

## 권한 획득처리

#### (1) 권한 획득하기 전 권한 유효성 체크
- checkSelfPermission(String) != packageManager.PERMISSION_GRANTED
  - 권한을 획득하기 전에 이 권한을 가지고 있는지를 체크하는 것.
- 현재 앱이 특정 권한을 갖고 있는지 확인 가능

#### (2) 설명이 필요할 경우 처리
- shouldShowRequestPermissionRationale(String)
  - 안해줘도 되는데 네가 필요한 권한에 대해 사용자에게 부연설명을 해주는 것.
- 권한 획득이 필요한 이유를 설명해야 한다면 다음 옵션을 추가하여 별도 처리가 가능.
- 사용자가 이전에 권한 요청을 거부한 경우에 true 반환.
- 이 경우, 권한 요청을 위한 대화창에는 '다시 붇지 않기' 체크박스와 함께 표시된다.
- 사용자가 이를 선택하면 이후에 앱이 requestPermissions 메서드를 호출해도 권한 요청 대화창이 표시되지 않고, 바로 사용자가 해당 권한을 거부할 때와 동일하게 콜백 함수가 호출된다.

#### (3) 권한 획득을 위한 API
- Activity.requestPermissions(String[], int)
- 위의 권한 중 Group과 permission 2가지를 선택적으로 던질 수 있다.
- 한번에 1개가 아닌 String[] 배열로 넘겨 한 번에 필요한 permission을 한 번에 획득할 수 있다.

#### (4) 결과처리 - onRequestPermissionResult(int, String[], int[])
- [3]에서 사용자에게 권한획득에 대해 물어본 다음에 그 결과에 대해 사용자가 승인 혹은 거절에 대해서 응답을 하면 `결과처리` 메소드(onRequestPermissionResult)가 실행이 된다.
- 권한 획득에 대항 성공/실패에 대한 정보를 담은 callback.
- 다음 함수 내에서 배열로 전달되므로 필요한 퍼미션이 잘 받아졌는지 확인하여 이후 처리가 가능



## 권한 체크 코드
- 아래와 같은 구조로 권한을 체크할 수 있다.
  ```java
    public final static int REQ_CODE = 100;

    private void checkPermission() {
      // 권한이 없을 경우
      if (checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != packageManager.PERMISSION_GRANTED) {
        // 사용자가 임의로 권한을 취소시킨 경우
        if (shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
          // 권한 재요청
          requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_CODE);
        } else {
          // 권한 요청 (최초 요청)
          requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_CODE);
        }
      }
    }
  ```

- `Manifest.permission.READ_EXTERNAL_STORAGE`
  - 이게 권한이다. (외부 저장소를 읽을 것인지)
- `requestPermissions`이라는 함수를 통해서 팝업을 띄워준다. (권한을 줄 것인지)
  - 확인하면 onRequestPermissionResult() 메소드를 안드로이드 시스템이 띄어준다.



## 결과 처리 코드
- 아래와 같은 구조로 권한을 체크할 수 있다.
  ```java
    @Override
    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults) {
      switch(requestCode){
        case PERMISSIONS_EXT :
          // If request is cancelled, the result arrays are empty.
          if(grantResults.length > 0 && grantResults[0] == packageManager.PERMISSION_GRANTED) {
            // 동의 및 로직 처리
            Log.e(TAG, ">>> 동의함.");
          } else {
            // 동의 안함
            Log.e(TAG, ">>> 동의를 해주셔야 합니다.");
          }
          return;
        default :
        // 예외 케이스
      }
    }
  ```
- 사용자가 허락을 했으면 요청한 퍼미션에 대해서 `packageManager.PERMISSION_GRANTED` 라는 플래그 값으로 넘어오게 된다.



## 런타임 권한 설정
```java
    // 0. 요청코드 세팅
    private final int REQ_CODE = 100;

    // 1. 권한체크 (런타임 권한은 마시멜로우 이상 버전에서만 사용가능)
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 애너테이션
    private void checkPermission(){
        // 1.1 런타임 권한체크
        if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED
                ){
            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.READ_CONTACTS};
            // 1.3 시스템에 권한요청
            requestPermissions(permArr, REQ_CODE);
        }else{
            loadData();
        }
    }

    // 2. 권한체크 후 콜백되는 함수 < 사용자가 확인후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_CODE){
            // 2.1 배열에 넘긴 런타임권한을 체크해서 승인이 됬으면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED ){
                // 2.2 프로그램 실행
                loadData();
            }else{
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
                // 선택 : 1 종료, 2 권한체크 다시 물어보기
                finish();
            }
        }
    }
```



## [2] 컨텐트 리졸버 사용
```java
public class Loader {

/* List<Data> 로 한 이유 */
// 객체지향 설계의 원칙 중에 있는 것이고,
// 구현체에 의존하지 않고, 부모객체나 인터페이스에 의존한다는 원칙을 따른 것이다.
private List<Data> datas = new ArrayList<>();
private Context context;

public Loader(Context context){
    this.context = context;
}

public List<Data> getContacts() {
    // 데이터베이스 혹은 content resolver를 통해 가져온 데이터를 적재할
    // 데이터 저장소를 먼저 정의한다.
    // List<Data> datas = new ArrayList<>();    ->   위로 옮겼다.



    /* 데이터에 접근하기위해 ContentResolver 를 불러온다. */
    // 일종의 Database(다른 앱에서 만든 DB와 같은 느낌) 관리툴
    // 전화번호부에 이미 만들어져 있는 Content Provider를 통해서 데이터를 가져올 수 있다.
    // ContentResolver는 Content Provider랑 통신하는 도구
    ContentResolver resolver = context.getContentResolver();

    // 1. 데이터 컨텐츠 URI (자원의 주소)를 정의
    // 전화번호 URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    //       * 주소록에서 전호번호를 가져오면 주소당 여러개의 전화번호가 존재할 수 있다
    //         HAS_PHONE_NUMBER : 전화번호가 있는지 확인하는 상수
    // 데이터가 있는 테이블 주소
    Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 테이블명과 같이 생각하면 된다.
    // 이너클래스


    // URI는 하나의 테이블이라고 보면 된다. 엑셀의 시트라고 보면 된다.
    // URI 안에는 여러 종류의 데이터들이 있다.

    // 2. 데이터에서 가져올 컬럼명을 정의
    String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID   // 고유값을 구분하기 위한 ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME               // 화면에 표시되는 이름
            , ContactsContract.CommonDataKinds.Phone.NUMBER};                   // 실제 전화번호

    // 3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
    // Content Resolver가 Content Provider한테 이런이런 데이터를 달라고 요청을 하는 것이다.
    // 쿼리를 보내면 하나의 데이터묶음이 리턴이 되는데 데이터묶음의 형태가 Cursor라는 형태의 객체로 되어 있다. (ArrayList와 비슷한 형태, 데이터가 목록형태로 데이터가 들어가 있다.)
    // 결과값을 Cursor의 형태로 반환한다. ArrayList와 같은 형태
    Cursor cursor = resolver.query(phoneUri,    // 데이터의 주소 (URI)
            projections,    // 가져올 데이터 컬럼명 배열 (projection)
            null,           // 조건절에 들어가는 컬럼명들 지정
            null,           // 지정된 컬럼명과 매핑되는 실제 조건 값
            null);          // 정렬


    // 4. 반복문을 통해 cursor에 담겨있는 데이터를 하나씩 추출한다.
    if (cursor != null) {   // cursor에 데이터 존재여부 체크
        while (cursor.moveToNext()) {
            // moveToNext() 다음 데이터로 이동, 데이터가 있으면 계속 while문이 동작
            // moveToNext()가 true, false를 반환하기 때문에 while문으로 하는 것이 적합
            // 4.1 위에 정의한 프로젝션의 컬럼명으로 cursor 있는 인덱스값을 조회하고
            int idIndex = cursor.getColumnIndex(projections[0]);    // projection에서 어떤 컬럼을 가져올지를 Index로 지정
            int id = cursor.getInt(idIndex);
            // int id = cursor.getInt(0);   위의 2줄을 이렇게 해줘도 된다.
            // 그런데 코드의 가독성을 위해 위와 같이 한다.


            int nameIndex = cursor.getColumnIndex(projections[1]);
            String name = cursor.getString(nameIndex);

            int telIndex = cursor.getColumnIndex(projections[2]);
            // 4.2 해당 index를 사용해서 실제값을 가져온다.
            String tel = cursor.getString(telIndex);
            // String tel = cursor.getString(2);  라고 해도 되는데,
            // index값을 불러와 이름으로 해주면 실수하지 않고, 불러올 수 있다.

            // 5. 내가 설계한 Data 클래스에 담아준다.
            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setTel(tel);

            // 6. 여러개의 객체를 담을 수 있는 저장소에 적재한다.
            datas.add(data);
        }
    }
    // * 중요 : 사용 후 close 를 호출하지 않으면 메모리 누수가 발생할 수 있다.
    // 사용 후 커서 자원을 반환한다.
    cursor.close();

    return datas;
  }
}
```
