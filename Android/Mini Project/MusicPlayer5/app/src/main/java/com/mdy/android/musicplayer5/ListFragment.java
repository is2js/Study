package com.mdy.android.musicplayer5;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdy.android.musicplayer5.domain.Music;

public class ListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    public ListFragment() {
    }

    @SuppressWarnings("unused")
    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // 데이터를 초기화
            Music music = Music.getInstance();  // getInstance()를 static으로 해줘서 가능
            music.loader(getContext()); // 데이터를 미리 로드 해둔다.
            //music.getItems();   // 로드한 데이터를 가져온다.

            // Fragment에는 getContext()메소드가 있다. Activity에는 getBaseContext() 메소드가 있다.


            // 데이터를 세팅
            ListAdapter adapter = new ListAdapter(music.getItems(), mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    // onAttach가 될때 onAttach를 통해서 액티비티가 담겨서 넘어온다. context로 쌓여서
    // 여기에 context로 넘어오는게 Activity인데 다형성때문에 Context로 캐스팅이 되서 사용하는 것이다.
    // 그러면 액티비티가 넘어왔다는 것은 ListAdapter에서
    // 메인액티비티가 implements ListFragment.OnListFragmentInteractionalListener를 구현을 해서,
    // onListFragmentInteraction() 메소드를 Override했다.
    // 그러면 궁극적으로 ListFragment에서 구현한 mListener를 ListAdapter에다 넘겨줬으니까
    // ListAdapter에서 이 리스너(mListener)의 어떤 함수를 실행시키면
            // public void goDetail(int position){
            //     mListener.onListFragmentInteraction();
            // }
    // 메인액티비티의 함수를 직접 실행할 수 있다. ListFragment를 건너뛰고  (미리 구현이 되어 있어서)
    // 그러면 우리가 해야할 것은 뷰페이저가 있는 프레그먼트만 add를 해주면 된다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void goDetailInteraction(int position);
    }
}
