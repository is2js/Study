package com.mdy.android.servernodejsfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    MainActivity mainActivity;
    private Button btnWrite;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    // MainActivity에서 setFragment()를 통해 listFragment가 commit이 되는 순간,
    // ListFragment의 onAttach()가 호출된다.
    // 그럼 여기를 통해서 MainActivity가 context로 넘어온다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        // 이렇게 하고 나면, 이제 ListFragment에서는 mainActivity의 모든 것(메소드 등)을 가져다 쓸 수 있다. mainAcitivity.~~~
        // 그래서 data를 가져오려고 할때도 mainActivity의 것을 그냥 가져오면 된다.
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
        adapter = new RecyclerAdapter(getContext(), mainActivity.getData());    // mainActivity의 data를 getData()를 통해 가져왔다.
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnWrite = (Button) view.findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener( v->mainActivity.goDetail() );
    }

    public void refresh() {
        adapter.notifyDataSetChanged();
    }
}