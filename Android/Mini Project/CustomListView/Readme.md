## 2017.05.24.(목)

### CustomListView
---
#### 주요 학습 내용
- ListView에 CustomAdapter를 연결하였음.
- 이 프로젝트는 MVP 패턴을 이용하였음.

### MVP는 각각 Class로 만들어진다.
- View는 Holder 클래스이다.
  - MVP에서 **View의 역할은 화면에 출력하고, 입력을 받는 역할이다.**
  - 그래서 onClickListener를 View에다 달아준다.
- Presenter는 Adapter 클래스이다.
  - MVP라면 Presenter내부에 뷰에 있는 속성을 직접 엑세스해서 그 함수를 사용하는게 없어야 한다.
    - findViewById / setText 와 같은 것들은 View로 보낸다.
- Model은 Data 클래스 이다.

<br>

- 로직이 들어가려면 메소드가 있어야 한다.


<br>
<br>



>> ##### [참고] M(데이터를 가져오고) -> C(가공을 하고) > V(화면에 뿌려준다)
>>> 이전에 작성했던 코드에서 MVC 패턴을 이용했을 때는
>>> CustomAdapter역할이 Presenter(데이터 가져오고, 화면 뿌려주고, 컨트롤하고)였다.

> 1. 데이터 정의
```java
    datas = Loader.getData(this);
```
> 2. 아답터 생성 (아답터는 Presenter 이다.)
```java
    CustomAdapter adapter = new CustomAdapter(datas, this);
```
> 3. 뷰에 아답터 연결
>> - 뷰에 넣을 데이터를 조작해주는게 아답터이다. 데이터의 변경 사항이 있어도 아답터가 처리를 해준다. (레이아웃 등)
```java
      ListView listView = (ListView) findViewById(R.id.listView);
      listView.setAdapter(adapter);
```
>>

- MVP 패턴을 이용하였음.
  - listView.setOnItemClickListener를 Adapter 안으로 이동시켰다.
    (Adapter는 Presenter이기 때문에)
```java
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        Data data = datas.get(position);

        intent.putExtra(DATA_KEY, position);
        intent.putExtra(DATA_RES_ID, data.resId);
        intent.putExtra(DATA_TITLE, data.title);

        startActivity(intent);
    }
});
```
- MVP 패턴 이용
  - Holder가 MVP에서 View에 해당한다.
```java
class Holder {
    int position;
    TextView no;
    TextView title;
    ImageView image;
    int resId;

    public Holder(View view, final Context context){
//            no = (TextView) view.findViewById(R.id.txtNo);

        // 아래 있는 txtNo, txtTitle, imageView 는 Item_list.xml에 있는 위젯들의 id값이다.
        no = (TextView) view.findViewById(R.id.txtNo);
        title = (TextView) view.findViewById(R.id.txtTitle);
        image = (ImageView) view.findViewById(R.id.imageView);
        // 1. 이미지뷰에 onClickListener를 달고 상세페이지로 이동시킨다.
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra(DATA_KEY, Holder.this.position);
                intent.putExtra(DATA_RES_ID, resId);
                intent.putExtra(DATA_TITLE, title.getText().toString());

                context.startActivity(intent);
            }
        });
        // 2. Title 텍스트뷰에 onClickListener를 달고 Toast로 내용을 출력한다.
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, title.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setNo(int no){
        this.no.setText(no + "");
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setImage(int resId){
        this.resId = resId;
        this.image.setImageResource(resId);
    }

    public void setPostion(int position) {
        this.position = position;
    }
}
```


---
