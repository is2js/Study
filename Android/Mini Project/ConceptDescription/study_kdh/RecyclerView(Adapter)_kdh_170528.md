# RecyclerView의 Adapter
* RecyclerView 는 ListView의 향상된 형태로서 ListView가 제공하지 않는 다양한 기능을 제공할 뿐만 아니라 사용 효율성도 더 좋습니다.

### BaseAdapter 와 RecyclerView.Adapter의 비교

BaseAdapter는 아래의 메소드에 대한 구현 의무가 있습니다.
```Java
class CustomAdapter extends BaseAdapter{
  int getCount(){}
  long getItemId(){}
  Object getItem(){}
  View getView(){}
    ...
}
```
RecyclerView.Adapter는 아래의 메소드에 대한 구현 의무가 있습니다.
```Java
class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>{
  int getItemCount(){}
  ViewHolder onCreateViewHolder(){}
  void onBindViewHolder(){}
    ...
  class ViewHolder extends RecyclerView.ViewHolder{
      ViewHolder(View itemView){
        super(itemView);
        ...
      }
  }
}
```
두 어뎁터 모두 리스트를 보여주는 뷰 (ListView, RecyclerView)가 필요로 하는 데이터를 기반으로 만든 뷰를 전달해 줘야한다는 의무를 가지고 있습니다. 하지만 두 어뎁터의 동작의 핵심요소가 다릅니다.

BaseAdapter에서의 핵심요소는 getView()메소드를 통해 전달되는 **View** 입니다.
RecyclerView.Adapter의 핵심요소는 onCreateViewHolder(){}를 통해 전달되는 **ViewHolder** 입니다.

BaseAdapter에서도 ViewHolder class 를 만들어 View에 Tag를 달아주는 형태의 패턴이 많이 사용됩니다.
(이러한 패턴을 통해 findViewById()를 이용한 참조 찾기 횟수를 줄일 수 있어 시스템 자원의 절약을 가지고 올 수 있습니다.)(
```Java
public View getView(){
  ...
  ViewHolder viewHolder = new ViewHolder;
  viewHolder.title = view.findViewById(R.id.title);
  viewHolder.button = view.findViewById(R.id.button);
  view.setTag(viewHolder)
  return view;
}

class ViewHolder{
  TextView title;
  Button button;
}
```
RecyclerView.Adapter에서는 이렇 ViewHolder 패턴이 의무화 되고 ViewHolder가 View 내의 하부 View의 참조만을 갖는 것이 아닌 View의 참조도 갖게 하여 전체 동작을 ViewHolder 중심으로 진행합니다.

그렇기에 어뎁터가 만들어서 주는 객체는 View가 아닌 View와 View의 하위 뷰의 참조를 갖고 있는 ViewHolder가 됩니다.

```Java
public ViewHolder onCreateViewHolder(){
  View view = inflater.inflate(R.layout.list_item, null);
  return new ViewHolder(view);
}

class ViewHolder extends RecyclerView.ViewHolder{
  TextView title;
  Button button;
  ViewHolder(View itemView){
    title = itemView.findViewById(R.id.title);
    button = itemView.findViewById(R.id.button);
  }
}

```

### RecyclerView.Adapter의 동작 방식
RecyclerView.Adapter의 구현의 의무가 있는 세 메소드 getItemCount,onCreateViewHolder, onBindViewHolder 중 BaseAdapter와 구분되는 메소드는 onCreateViewHolder와 onBindViewHolder입니다.

여러분은 ListView에서 사용되는 BaseAdapter에서 getView에게 전달되는 convertView의 null 상태에 따라 조건 분기하여 뷰가 재활용 된다는 것을 아실 겁니다.

결론적으로 말하면, onCreateViewHolder는 재활용 될 객체가 없어서 처음 객체를 생성하는 convertView == null 인 조건에서 시행되는 메소드라 할 수 있습니다.
onBindViewHolder는 새로 만들어진 ViewHolder나 재활용된 ViewHolder에 데이터를 반영하는 메소드입니다.
```Java
public View getView(int position, View convertView, ViewGroup parent){
  if(convertView == null){
    //재활용 뷰가 없기에 새 뷰를 만듭니다.
    //onCreateViewHolder()가 이부분의 로직에 해당합니다.
    View view = inflater.inflate(R.layout.item_list, null);
    ViewHolder viewHolder = new ViewHolder();
    ...
    //이후 데이터를 반영하는 onBindViewHolder()가 이어진다 생각 할 수 있습니다.
    viewHolder.title.setText(data.title);
    viewHolder.button.setOnClickListener(listener);
  }else{
    //재활용 뷰가 있기에 바로 데이터 반영을 합니다.
    //onBindViewHolder()가 이에 해당합니다.
    viewHolder.title.setText(data.title);
    viewHolder.button.setOnClickListener(listener);
  }
}
```
그렇기에 각각의 메소드는 다음과 같은 로직이 주를 이룹니다.
```Java
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
  // 뷰를 만듭니다.
  View view = inflater.inflate(R.layout.item_list,null);
  // 새로만든 뷰의 참조를 가지고 있을 ViewHolder를 만들어 이를 반환합니다.
  return new ViewHolder(view);
}

public void onBindViewHolder(ViewHolder holder, int position){
  //holder에 담겨 있는 참조들에 position에 맏는 데이터를 반영합니다.
  holder.title.setText(datas[position].title);
  holder.button.setOnClickListener(listener);
  ...
}
ViewHolder extends RecyclerView.ViewHolder{
  TextView title;
  Button button;
  ViewHolder(View itemView){
    //상속한 RecyclerView.ViewHolder에는 빈 생성자가 없기에 이를 상속한 하위 클래스는 반드시 상속한 클래스의 생성자를 호출해야 합니다.
    super(itemView);
    //ViewHolder의 생성자에서 하위 뷰의 참조를 찾습니다.
    title = itemView.findViewById(R.id.title);
    button = itemView.findViewById(R.id.button);
  }
}

```
