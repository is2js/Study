# ServerNodejsFragment (Fragment)
### Fragment를 이용해서
#### Nodejs 서버를 통해 받는 데이터(MySQl - 관계형DB)를 Retrofit을 이용해 화면에 출력해본다.


<br>
<br>
<br>

## Activity가 아닌 Fragment로 할때 주의할 사항
- fragment에 클릭을 해야할 경우, xml파일(`fragment_detail.xml`, `fragment_list.xml`)에 clickable을 반드시 체크(true)해줘야 한다. (`android:clickable="true"`)
  - 그렇지 않으면 뒤에 있는 fragment의 버튼이 눌릴 수도 있다.

- fragment를 겹쳐서 보이게할 경우, 뒤의 배경을 하얀색으로 설정해줘야 한다. (`android:background="#ffffff"`)
  - 그렇지 않으면 투명색이라 뒤의 것이 보이게 된다.







<br>
<br>

## 소스코드
- #### MainActivity.java
```java
public class MainActivity extends AppCompatActivity {
    private List<Bbs> data = new ArrayList<>();
    ListFragment listFragment;
    DetailFragment detailFragment;

    private static final int FROM_CREATE = 1;
    private static final int FROM_DETAIL = 2;

    public List<Bbs> getData(){
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader(FROM_CREATE);

    }

    private void setFragment(){
        listFragment = new ListFragment();
        detailFragment = new DetailFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, listFragment)
                .commit();
    }

    private void addFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("detailFragment")
                .commit();
    }

    private void popFragment(){
        onBackPressed();
    }

    private void loader(int from) {
        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.read();

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 1. 데이터를 꺼내고
                            String jsonString = responseBody.string();
                            Gson gson = new Gson();
                            Bbs data[] = gson.fromJson(jsonString, Bbs[].class);
                            // 2. 데이터를 아답터에 세팅하고
                            for(Bbs bbs : data){
                                MainActivity.this.data.add(bbs);
                            }
                            // 3. 아답터 갱신
                            // 호출된 곳에 따라 처리가 달라진다.
                            switch (from){
                                case FROM_CREATE:
                                    setFragment();
                                    break;
                                case FROM_DETAIL:
                                    listFragment.refresh();
                                    break;
                            }

                        }
                );
    }

    public void goDetail() {
        addFragment(detailFragment);
    }

    public void goList() {
        popFragment();
        data.clear();
        loader(FROM_DETAIL);
    }
}
```

<br>


- #### ListFragment.java
```java
public class ListFragment extends Fragment {
    MainActivity mainActivity;
    private Button btnWrite;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new RecyclerAdapter(getContext(), mainActivity.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnWrite = (Button) view.findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener( v->mainActivity.goDetail() );
    }

    public void refresh() {
        adapter.notifyDataSetChanged();
    }
}
```

<br>


- #### DetailFragment.java
```java
public class DetailFragment extends Fragment {
    private MainActivity mainActivity;

    private EditText editTitle;
    private EditText editAuthor;
    private EditText editContent;
    private Button btnPost;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        editTitle = (EditText) view.findViewById(R.id.editTitle);
        editAuthor = (EditText) view.findViewById(R.id.editAuthor);
        editContent = (EditText) view.findViewById(R.id.editContent);
        btnPost = (Button) view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(v->{
            String title = editTitle.getText().toString();
            String author = editAuthor.getText().toString();
            String content = editContent.getText().toString();

            postData(title, author, content);
        });
    }

    private void postData(String title, String author, String content){
        // 0. 입력할 객체 생성
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        // 1. 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(IBbs.SERVER)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2. 서비스 연결
        IBbs myServer = client.create(IBbs.class);

        // 3. 서비스의 특정 함수 호출 -> Observable 생성
        Gson gson = new Gson();
        // bbs 객체를 수동으로 전송하기 위해서는
        // bbs 객체 -> json String 변환
        // RequestBody 에 미디어타입과, String 으로 벼환된 데이터를 담아서 전송
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                gson.toJson(bbs)
        );

        Observable<ResponseBody> observable = myServer.write(body);

        // 4. subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            //String result = responseBody.string(); // 결과코드를 넘겨서 처리...
                            resetInput();
                            mainActivity.goList();
                        }
                );
    }

    private void resetInput() {
        editTitle.setText("");
        editAuthor.setText("");
        editContent.setText("");
    }
}
```

<br>


- #### RecyclerAdapter.java
```java
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    LayoutInflater inflater;
    List<Bbs> data;

    public RecyclerAdapter(Context context, List<Bbs> data){
        this.data = data;
//        this.inflater = LayoutInflater.from(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);
        holder.setTitle(bbs.title);
        holder.setContent(bbs.content);
        holder.setAuthor(bbs.author);
        holder.setDate(bbs.date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDate;
        TextView txtAuthor;
        TextView txtContent;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
        }

        public void setTitle(String title){
            txtTitle.setText("제목 : " + title);
        }
        public void setDate(String date){
            txtDate.setText("날짜 : " + date);
        }
        public void setAuthor(String author){
            txtAuthor.setText("저자 : " + author);
        }
        public void setContent(String content){
            txtContent.setText("내용 : " + content);
        }
    }
}
```

<br>


- #### IBbs.java (인터페이스)
```java
public interface IBbs {
    public static final String SERVER = "http://192.168.10.79/";    // Retrofit을 사용할 때, 반드시 주소 뒤에 슬래시(/)를 붙여줘야 한다.
    @GET("bbs")
    public Observable<ResponseBody> read();

    @POST("bbs")
    public Observable<ResponseBody> write(@Body RequestBody body);

    @PUT("bbs")
    public Observable<ResponseBody> update(Bbs bbs);

    @DELETE("bbs")
    public Observable<ResponseBody> delete(Bbs bbs);
}
```

<br>


- #### Bbs.java
```java
public class Bbs {
    int id;
    String title;
    String content;
    String author;
    String date;
}
```

<br>
