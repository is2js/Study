# Adapter

* 어뎁터는 필요로 하는 형식이 아닌 무언가를 필요로 되는 형식으로 바꿔주는 일을 합니다.
  + 어뎁터를 사용할 객체인 ListView (또는 RecyclerView)는 각 position마다 View를 필요로 합니다.
  + 사용자가 가지고 있는 것은 View의 묶음이 아닌 데이터의 묶음입니다.(List<Data>)
  + 리스트뷰가 사용할 수 있도록 데이터를 적절히 변화시켜야합니다(Adapt)
```Mermaid
graph LR
데이터 -- 어뎁터 --> 뷰
```
* 220V에 110V를 끼기위해 사용되는 어뎁터를 떠올릴 수 있습니다.

```Mermaid
graph LR
110V -- 어뎁터 --> 220V
```
### ListView의 재활용
* ListView는 자신이 표현해야 하는 아이템 만큼 뷰를 가지지 않습니다. 화면을 충분히 그릴 정도의 뷰만을 만들고, 이를 재활용하는 방식을 이용합니다.

* 리스트에 표현될 아이템이 100개라 해도, 화면을 그리는데 10개가 필요하다면, 특별한 경우가 아니라면 10개 + 여유분 정도만 만듭니다.

 ex)
 >1. 화면에 최대 아이템 10개만 보여줄 수 있다면, 처음 리스트는 10개의 아이템을 만들어 화면을 구성합니다.
 >2. 스크롤을 하면서, 0번째 뷰가 화면에서 사라지면, 이를 재활용 가능한 상태로 둡니다.
 >3. 11번째 뷰를 보여줄 떄, 재활용 가능한 상태의 뷰 (0번째)를 재활용하여 11번째 뷰로 보여줍니다.


### Adapter의 getView()메소드
 * 어뎁터(BaseAdapter)를 상속할 때 다음 네 개의 메소드를 구현할 의무가 생깁니다.
 ```Java
    class CustomAdapter extends BaseAdapter{
      public int getCount(){}
      public Object getItem(int position){}
      public long getItemId(int position){}
      public View getView(int position View convertView, ViewGroup parent){}
    }
 ```

   이 중 필요로 하는 형식으로 바꿔주는 일에 해당하는 메소드는 getView()에 해당합니다.
   리스트뷰는 각각의 포지션을 표현 해야 할 때 자신이 가지고 있는 어뎁터에게 각 포지션에 맞는 뷰를 줄 것을 getView()라는 메소드를 호출해 요청합니다.

* getView()는 int position, View convertView, ViewGroup parent 타입에 해당하는 3개의 매개변수를 받습니다. 각각의 매개변수는 ListView가 뷰를 호출할 때 필요로 하는 정보가 됩니다.
  1. **int position** : 리스트에서 몇번째에 해당하는 아이템을 표현하기 위한 뷰인지 알립니다.
  2. **View convertView** : 앞서서 설명한 재활용 가능한 상태의 뷰입니다. ListView가 getView()를 호출할 때 자신이 가지고 있는 재활용 가능한 뷰를 넘겨줍니다. 없을 경우 null을 보냅니다.
  3. **ViewGroup parent** 만들어질 뷰의 부모뷰입니다. (ListView가 어뎁터를 사용하고 있다면, ListView가 parent로 넘겨집니다.
    )
### Context와 LayoutInflater

* **Context** :
  + 안드로이드 OS가 제공해주는 시스템 기능,서비스를 사용자(프로그래머)가 사용할 수 있도록 해주는 일종의 연결 포인트입니다.
  + 안드로이드의 많은 객체들은 Context가 제공해주는 기능, 서비스에 기반해 생존합니다. 그런면에서 Context는 안드로이드의 구성요소의 생존 환경(context)를 제공한다 할 수 있습니다.
* **LayoutInflater** :
  + Context가 제공해주는 서비스 중 LAYOUT_INFLATE_SERVICE 기능을 담당하는 객체입니다.
  + LAYOUT_INFLATE_SERVICE는 사전에 정의된 xml에 따라서 뷰 객체를 생성해주는 서비스입니다. (activity_main.xml -> 진짜 화면에 보이는 뭔가!)
  + findViewById()는 View를 만드는 메소드가 아닙니다. LayoutInflater를 통해 만들어진 뷰의 **참조를 찾는** 메소드입니다.


### null 분기와 ViewHolder 패턴
어뎁터의 getView()메소드의 권장되는 구현 패턴은 아래의 예제처럼, convertView !=null분기와 ViewHolder를 사용한 패턴입니다.
```Java
class CustomAdapter extends BaseAdapter{
...
  public View getView(int position View convertView, ViewGroup parent){
    if(convertView != null){
      ViewHolder viewHolder = convertView.getTag
      viewHolder.title.setText(data[position].getTitle());
      return convertView;
    }else{
      View view = inflater.inflate(R.layout.item_list,null);
      ViewHolder viewHolder = new ViewHolder;
      viewHolder.title = view.findViewById(R.id.title);
      view.setTag(viewHolder);
      return view;
    }
  }

  class ViewHolder {
    TextView title;
  }
}
```
* convertView != null 분기는 위에서 설명된 ListView의 재활용을 위한 분기입니다. convertView 가 null이란 것은 재활용할 뷰가 없다는 것을 의미하므로 그때는 새 뷰를 만들지만 (inflater.inflate()) 그렇지 않다면, 넘겨받은 뷰를 재활용해 필요한 데이터를 반영해 해당 뷰를 반환하는 것이 적절합니다.


* ViewHolder 패턴은 View 내의 자녀 뷰들의 참조를 유지하기 위한 패턴입니다.
view.findViewById()를 이용해 자녀 뷰의 참조를 찾지만, 매번 재활용된 convertView도 findViewById()를 통해 참조를 찾는 것은 비효율 적입니다.
* 이를 보완한 것이 ViewHolder라는 참조를 갖는(TextView title)는 클래스를 만들어 해당 클래스를 view의 tag로 넣어두는(setTag()) ViewHolder 패턴입니다. 이로 시스템 자원을 더욱 효율적으로 사용할 수 있습니다.
