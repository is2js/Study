# ViewPager
ViewPager에 대해서 알아본다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/WebView/app/src/main/java/com/mdy/android/viewpager/MainActivity.java)**

## ViewPager, TabLayout 추가
- ViewPager
```xml
  <android.support.v4.view.ViewPager
      android:id="@+id/pager"
      android:layout_width="match_parent"
      android:layout_height="0dp"
      android:layout_marginEnd="0dp"
      android:layout_marginLeft="0dp"
      android:layout_marginRight="0dp"
      android:layout_marginStart="0dp"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintHorizontal_bias="0.0"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      android:layout_marginTop="0dp"
      app:layout_constraintTop_toBottomOf="@+id/tab"
      app:layout_constraintVertical_bias="0.0"></android.support.v4.view.ViewPager>
```
- TabLayout
```xml
  <android.support.design.widget.TabLayout
      android:id="@+id/tab"
      android:layout_width="match_parent"
      android:layout_height="48dp"
      app:layout_constraintHorizontal_bias="0.0"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      app:layout_constraintTop_toTopOf="parent"
      android:layout_marginTop="0dp">
```

## PagerAdapter 클래스
- PagerAdapter 클래스 만들기
```java
  class PagerAdapter extends FragmentStatePagerAdapter {
      List<Fragment> datas;

      // List<Fragment> datas 넘겨줄 필요가 있어서 인자에 추가했다.
      // 그리고 아답터 만들때도 new PagerAdapter( ,  ) 두번째 인자에 datas를 넣어준다.
      public PagerAdapter(FragmentManager fm, List<Fragment> datas) {
          super(fm);
          this.datas = datas;
      }

      @Override
      public Fragment getItem(int position) {
          return datas.get(position);
      }

      @Override
      public int getCount() {
          return datas.size();
      }
  }
```


## ViewPager와 TabLayout의 연결
- ViewPager와 TabLayout을 연결해준다.
```java
// 1. ViewPager 위젯 연결
pager = (ViewPager) findViewById(R.id.pager);
// 1.1 TabLayout 위젯 연결
tab = (TabLayout) findViewById(R.id.tab);
tab.addTab(tab.newTab().setText("One"));
tab.addTab(tab.newTab().setText("Two"));
tab.addTab(tab.newTab().setText("Three"));
tab.addTab(tab.newTab().setText("Four"));

// 2. 프래그먼트들 생성
one = new OneFragment();
two = new TwoFragment();
three = new ThreeFragment();
four = new FourFragment();


// 3. 프래그먼트를 datas 저장소에 담은 후
// 모든 Fragment는 Fragment를 상속받기 때문에 제네릭에 Fragment를 써준다.
List<Fragment> datas = new ArrayList<>();
datas.add(one);
datas.add(two);
datas.add(three);
datas.add(four);

// 4. 프래그먼트 매니저와 함께 아답터에 전달
PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), datas);

// 5. 아답터를 페이저 위젯에 연결
pager.setAdapter(adapter);


// 6. pager가 변경되었을 때 tab을 변경해주는 리스너 연결
pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

// 7. tab이 변경되었을 때 pager를 변경해주는 리스너 연결
tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
```
