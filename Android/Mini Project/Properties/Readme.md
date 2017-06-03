## 2017.05.30.(화)

## Properties
### [1] Properties
* 안드로이드에서 3가지 정도를 쓸 수 있는데, Properties, Preference, SharedPreferences이다.
  > - 프로퍼티는 속성값을 저장해놓고, 나중에 가져다 쓰기 위해서 사용한다.
  > - 로직은 전혀 없고, 속성값만 저장해놓고, 어떤 프로그램이 속성을 읽고 값을 가져다 쓸 수 있게 도와주는 역할을 한다.
  > - 이름을 던져서 값을 조회하기 위한 것이다.
  > - 프로퍼티는 자바에서 만든 것이다.

### [2] SharedPreferences
> #### 2.1 SharedPreferences란?
>> - 간단한 값 저장에 DB를 사용하기에는 복잡하기 때문에 SharedPreferences를 사용하면 적합하다.
>> - 보통 초기 설정값이나 자동로그인 여부 등 간단한 값을 저장하기 위해 사용한다.
>> - 어플리케이션에 파일 형태로 데이터를 저장한다.
>>    => data/data/패키지명/shared_prefs/SharedPreference이름.xml 위치에 저장
>> - 어플리케이션이 삭제되기 전까지 보존된다.
>
>
>
> #### 2.2 사용법
> ```java
>   import android.content.SharedPreferences;
> ```
> #### <SharedPreferences 인스턴스 얻기>
> - **getPreferences(int mode)**
>   - 하나의 액티비티에서만 사용하는 SharedPreferences를 생성한다.
>   - 생성되는 SharedPreferences 파일은 해당 액티비티이름으로 생성된다.
>   - 하나의 액티비티에서만 사용할 수 있지만 getSharedPreferences()를 사용하면 다른 액티비티에서도 사용가능하다.
>
> - **getSharedPreferences(String name, int mode)**
>   - 특정 이름을 가진 SharedPreferences를 생성한다.
>   - 주로 애플리케이션 전체에서 사용한다.
>
> #### <SharedPreferences에 데이터 저장하기>
> - 먼저 데이터를 기록하기 위해 SharedPreferences.Editor 인스턴스를 얻어야 한다.
> ```java
>   SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
>
>   SharedPreferences.Editor editor = test.edit();
>
>   editor.putString("First", infoFirst); //First라는 key값으로 infoFirst 데이터를 저장한다.
>
>   editor.putString("Second", infoSecond); //Second라는 key값으로 infoSecond 데이터를 저장한다.
>
>   editor.commit(); //완료한다.
> ```
> - 저장할 수 있는 데이터 타입
>   - **Boolean, Integer, Float, Long, String**
> #### <SharedPreferences에 데이터 불러오기>
> - 데이터를 불러오기 위해서 getInt()나 getString() 메서드를 사용하여 불러와야 한다.
> ```java
>    getInt(KEY, VALUE)
> ```
> - 첫번째 인자는 데이터의 키, 두번째 인자는 해당값이 없을경우 반환할 값을 넣어준다.
> ```java
>    SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
>
>    int firstData = test.getInt("First", 0);
> ```
>
> #### <SharedPreferences에 데이터 삭제하기>
> (1) 특정 데이터 삭제
> ```java
>    SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
>
>    SharedPreferences.Editor editor = test.edit();
>
>    editor.remove("test");
>
>    editor.commit();
> ```
>
> (2) 모든 데이터 삭제
> ```java
>    SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
>
>    SharedPreferences.Editor editor = test.edit();
>
>    editor.clear();
>
>    editor.commit();
> ```
