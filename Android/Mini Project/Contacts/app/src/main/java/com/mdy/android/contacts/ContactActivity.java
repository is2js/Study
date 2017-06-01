package com.mdy.android.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mdy.android.contacts.domain.Data;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        // 우리가 정의한 메소드이기 때문에 getContents(); 만 호출해도 데이터를 만들어서 리턴까지 해준다.
        // List<Data> = getContacts();  이것을 생략하고 바로 향상된 for문 안에 넣었다.

        for(Data data : getContacts()){
            Log.i("Contacts", "이름="+data.getName() +", tel"+data.getTel());
        }
    }

    public List<Data> getContacts(){

        // 데이터베이스 혹은 content resolver를 통해 가져온 데이터를 적재할
        // 데이터 저장소를 먼저 정의한다.
        List<Data> datas = new ArrayList<>();




        // 일종의 Database(다른 앱에서 만든 DB와 같은 느낌) 관리툴
        // 전화번호부에 이미 만들어져 있는 Content Provider를 통해서
        // 데이터를 가져올 수 있다.
        // ContentResolver는 Content Provider랑 통신하는 도구
        ContentResolver resolver = getContentResolver();

        // 1. 데이터 컨텐츠 URI (자원의 주소)를 정의
        // 전화번호 URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 테이블명과 같이 생각하면 된다.
        // 이너클래스

        // URI 안에는 여러 종류의 데이터들이 있다.

        // 2. 데이터에서 가져올 컬럼명을 정의
        String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                 ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                  ContactsContract.CommonDataKinds.Phone.NUMBER};

        // 3. Content Resolver로 쿼리를 날려서 데이터를 가져온다.
        // 결과값을 Cursor의 형태로 반환한다. ArrayList와 같은 형태
        Cursor cursor = resolver.query(phoneUri, projections, null, null, null); // 3, 4번째는 조건식 같은 것이다.

        // 4. 반복문을 통해 cursor에 담겨있는 데이터를 하나씩 추출한다.
        if(cursor != null) {
            while(cursor.moveToNext()){ // moveToNext() 다음 데이터로 이동, 데이터가 있으면 계속 while문이 동작
                // 4.1 위에 정의한 프로젝션의 컬럼명으로 cursor 있는 인덱스값을 조회하고
                int idIndex = cursor.getColumnIndex(projections[0]);
                int id = cursor.getInt(idIndex);

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
        return datas;
    }
}
