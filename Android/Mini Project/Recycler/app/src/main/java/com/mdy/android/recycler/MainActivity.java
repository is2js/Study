package com.mdy.android.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView) findViewById(R.id.listView);
        // 1. 데이터 정의
        ArrayList<Data> datas = Loader.getData(this);  // this는 MainActivity를 의미하며 MainActivity는 Context를 상속하기 때문에

        // 2. 아답터 생성
        CustomRecycler adapter = new CustomRecycler(datas, this);

        // 3. 연결 (아답터<=>뷰)
        listView.setAdapter(adapter);

        // Recycler를 다 만들고 나서, 레이아웃 매니저를 List를 뿌려주는 매니저를 달면 쭉 List로 보이고,
        // Grid 형태로 뿌려주는 매니저를 달면 똑같은 RecyclerView인데 그것을 격자모양으로 보여준다.
        // 4. 레이아웃 매니저 등록
        listView.setLayoutManager(new LinearLayoutManager(this));  // LinearLayoutManager(this)로 하면 기본이 vertical 이고, 세번째 인자는 리벌스에 대한 것이기 때문에 false로 해준다.
//          listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}



    // Recycler.Adapter를 상속받으면 .Adapter<VH> 제네릭으로 나오는데 VH는 ViewHolder의 약자이다.
    // 강사님이 ViewHolder 이름을 Holder라고 한 이유는 ViewHolder가 이름이 정의된게 있어서 Holder로 한 것이다.
    // ViewHolder 클래스를 만들라고 메세지가 나오는데 전구버튼을 클릭해서 만들면 CustomRecycler 클래스 밖에 만들어진다.
    // 그런데 여기서는 ViewHolder클래스를 CustomRecycler 클래스 안에서만 사용하기 때문에 내부에 만들도록 한다.
    // 그리고 실제로 ViewHolder들은 99%가 Adapter 내에서만 사용된다.
    // 이제 아래 Holder 클래스 만드는 쪽에 메모..

class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.Holder>{

    ArrayList<Data> datas;
    Context context;


    /* 생성자 만들기 */
    // 생성자는 꼭 받아야하는 것이 2개가 필수적으로 있다.
    // (1) data  (2) context
    // context를 받는 이유가 여기서는 조금 다르다.
    // 여기서는 Inflater를 context에서 꺼내쓰지 않는다. 뷰에서 꺼내 쓴다.
    // // Inflater를 꺼내쓰는 방법  (1) context에서 꺼내 쓴다.  (2) view에서 꺼내 쓴다.
    // 이전에 다른 CustomAdapter 클래스는 context에서 Inflater를 꺼내썼다.
    // view에서 꺼내써야 한다고 해도 여기서는 context를 인자로 받긴 해야 한다. (다른 이유로)

        public CustomRecycler(ArrayList<Data> datas, Context context){
            this.datas = datas;
            this.context = context;
        }




    // 아래 onCreateViewHolder 메소드가 이전에 CustomAdapter 클래스의 getView()에서 convertView 가 null일때 홀더를 생성해주던 부분이다.
    // convertView가 null이 아니어서 항상 getView가 호출될 때 값을 세팅해줬던 부분이 onBindViewHolder 메소드 이다.
    // 그래서 onCreateViewHolder 메소드에 ViewHolder 하나 던져주면 화면 사이즈에 맞게, 리스트행이 10줄이면 10개의 ViewHolder가 생성이 된다.
    // 그리고 그 10개의 ViewHolder로 onBindViewHolder 메소드가 돌아가면서 값을 세팅해서 다른 값을 화면에 보여준다.
    // 이렇게 되면 CustomAdapter에 있는 if절이 줄어들기 때문에 코드의 양이 상당히 줄게 된다.

    //  List View에서 convertView == null 일때 처리
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) { // 뷰홀더 안에서 인플레이트하기
        // ViewGroup은 View를 상속한다.

        // parent ViewGroup은 뷰이기 때문에 Context를 갖고 있다. 그래서 아래에서 parent.getContext()를 사용할 수 있다.
        // LayoutInflater는 뷰를 인플레이터를 하려고 쓰는 애이다.


//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);   // 아래 것과 둘 중에 어떤 것을 사용해도 상관없다.
                                                                                                     // 다만 이렇게 하려면 item_list의 최외곽 레이아웃의 height를 wrap_content로 해줘야 한다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        // parent에다 이 뷰 그룹을 넣을 거야, 그러나 지금 당장 넣지는 않을거야(false)
        // Inflater를 꺼내는 방법이 2가지가 있는데, 여기에서는 View에서 꺼내는 방법을 사용했다. (+ Inflater는 Context에서도 꺼내 사용할 수 있다.)
        // LayoutInflater.from 에서 from() 메소드를 들어가보면 1번째 방법의 코드가 작성되어 있다.
        // context를 inflate의 용도로만 사용한다면 굳이 context를 받지 않고, view로 받아서 사용해도 된다.
        // 그러나 여기서는 context를 다른 용도로도 사용할 것이기 때문에 onCreateViewHolder 메소드의 인자로 받은 것이다.

        //  Holder holder = new Holder(view);
        //  return holder;
        // 2줄로 작성되었던 코드를 아래 1줄로 간편하게 작성했다.
        return new Holder(view);
    }

    // [이전에 만든 CustomAdapter와 비교] 항상 getView() 호출될때, 값을 세팅해주는 역할&각 데이터 셀이 나타낼때 호출되는 함수
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // position은 0부터 시작하는 인덱스라고 생각하면 된다. (1이 아니라 0부터 시작한다.)


        // 1. 데이터를 꺼내고
        Data data = datas.get(position);
        // 2. 데이터를 세팅
        holder.setImage(data.resId);
        holder.setNo(data.no);
        holder.setTitle(data.title);

    }

    // 데이터의 전체 개수
    @Override
    public int getItemCount() {
        return datas.size();
    }




    /* ViewHolder 만들기 */
    // ViewHolder 클래스는 RecyclerView.Adapter에 사용할 수 있는 형태로 상위 객체가 미리 정의되어 있다.
    // 그래서 Recycler.ViewHolder 를 상속받아서 만들어야 한다. (extends RecyclerView.ViewHolder)
    // 그리고 나서 ViewHolder(이름: Holder) 클래스에 계속 빨간불이 들어오는데 생성자를 강제로 만들라는 메세지가 뜬다.
    // 그리고 나서 CustomRecycler 클래스에 빨간줄이 나오는 곳으로 가서 Holder에 Alt + Enter를 눌러주면
    // <Holder> 가 <CustomRecycler.Holder> 로 바뀐다. (Holder가 CustomRecycler 안에 있다고 알려주기 위해서)
    // 그리고 나서 BaseAdapter에서 필수로 구현해야 하는 메소드들을 구현했듯이
    // 여기서도 RecyclerView 클래스에 필수적으로 구현해야 하는 메소드들을 구현해준다. (3개)
    // (1) onCreateViewHolder  ,  (2) onBindViewHolder  ,  (3) getItemCount()

    // 그리고 CustomRecycler 클래스도 생성자를 통해 호출을 할 것이다.
    // 그래서 CustomRecycler 클래스를 만들고나서 가장 먼저 해야하는 것이 생성자를 만들어 주는 것이다.
    // public Custom() { }   ->  생성자 쪽으로 이동


    class Holder extends RecyclerView.ViewHolder {  // RecycerView 클래스의 innerClass가 ViewHolder이다.

        ImageView image;
        TextView no;
        TextView title;
        public Holder(View itemView) {
            super(itemView);   // 부모 클래스의 기본생성자가 없기 때문에  super()를 강제로 해야하는 것이다.
            image = (ImageView) itemView.findViewById(R.id.image);
            // 내가 인자로 받아온 것은 itemView이고, 그 뷰에 있는 값에 참조를 해줘야 하기 때문에 .itemView를 해준 것이다.
            // .itemView를 한 것은 위에서 LayoutInflater를 한 것은 itemView를 인플레이트 한 것이기 때문에 그 뷰에 값을 참조해주는 것이다.  -> 이건 바보같은 질문! 그냥 메소드만을 보고 이해하면 된다.
            no = (TextView) itemView.findViewById(R.id.txtNo);
            title = (TextView) itemView.findViewById(R.id.txtTitle);


            // TODO 클릭 리스너 달아보기
//            title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

        public void setImage(int resId){
            image.setImageResource(resId);
        }

        public void setNo(int no){
            this.no.setText(no+"");   // 이름이 겹쳐서 this. 을 앞에 써줬다.
        }

        public void setTitle(String title){
            this.title.setText(title);    // 이름이 겹쳐서 this. 을 앞에 써줬다.
        }

    }
}

class Loader {
    public static ArrayList<Data> getData(Context context) {
        ArrayList<Data> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Data data = new Data();
            data.no = i;
            data.title = "아기사진";

            data.setImage("baby" + i, context);
            result.add(data);
        }
        return result;
    }
}

class Data {
    public int no;
    public String title;
    public String image;
    public int resId;

    public void setImage(String str, Context context) {
        image = str;
        // 문자열로 리소스 아이디 가져오기
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());

    }
}