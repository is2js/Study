package com.mdy.android.contacts.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDY on 2017-06-01.
 */

public class Loader {


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



        // 일종의 Database(다른 앱에서 만든 DB와 같은 느낌) 관리툴
        // 전화번호부에 이미 만들어져 있는 Content Provider를 통해서
        // 데이터를 가져올 수 있다.
        // ContentResolver는 Content Provider랑 통신하는 도구
        ContentResolver resolver = context.getContentResolver();

        // 1. 데이터 컨텐츠 URI (자원의 주소)를 정의
        // 전화번호 URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
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