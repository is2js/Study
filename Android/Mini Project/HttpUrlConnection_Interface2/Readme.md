# HttpUrlConnection_Interface2
- #### 메소드를 기능별로 분리한다.
- #### Scroll Listener
- #### final 사용

- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/HttpUrlConnection_Interface2/app/src/main/java/com/mdy/android/httpurlconnection_interface2/MainActivity.java)**

<br>


## Scroll Listener
#### Scroll Listener
- AbsListView는 ListView와 GridView가 상속받고 있는 객체이다.
- ListView와 GridView가 사용할 수 있게 되어 있다.
```java
    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener()
    {
      ...
    }
```

#### 구현 메소드 (1) onScrollStateChanged
```java
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
      ...
    }
```
- onScrollStateChanged => 현재 스크롤이 끝까지 가서 더이상 움직이지 않는다. 그럴때 상태를 체크해준다.
- ex)
```java
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                Log.i("Scroll", "현재 스크롤이 idle 상태입니다.");   
                // => 스크롤을 움직이다 멈추면 멈춘 순간에 Log 메세지가 찍힌다.
        }
```


#### 구현 메소드 (2) onScroll
```java
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
      ...
    }
```
- onScroll() 메소드의 인자들
  - firstVisibleItem = 현재 보여지는 첫번째 아이템의 번호(index) / 제일 앞에 있는 아이템의 index를 넘겨준다.
  - visibleItemCount = 현재 화면에 보여지는 아이템의 개수
  - totalItemCount   = 리스트에 담겨있는 전체 아이템의 개수 / 전체 아이템의 개수

<br>


## final 사용

```java
  public class MainActivity extends AppCompatActivity implements TaskInterface, OnMapReadyCallback{

    final List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

      ...

    }
  }
```
- 아답터에서 사용할 데이터 공간을 정의했다.
- final을 붙여주면 datas는 다른 곳에서 new를 통해 생성할 수 없다.
- datas의 메모리 공간이 바뀌지 않는다.
- 아답터에 딱 들어가는 순간 이 datas의 데이터가 빠지고 나갈 때, 아답터를 notify해주면 변경사항이 무조건 반영이 된다. (final을 해줬기 때문에)
- 그러나 final을 안해주고, 다른 곳에서 new를 통해 datas를 새롭게 생성하면 메모리 주소가 바뀌어서 아답터가 notify를 해줄 수 없게 된다.
