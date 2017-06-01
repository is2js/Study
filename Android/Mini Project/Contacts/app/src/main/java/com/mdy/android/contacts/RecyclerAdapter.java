package com.mdy.android.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.contacts.domain.Data;

import java.util.List;

/**
 * Created by MDY on 2017-06-01.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{

    List<Data> datas;

    getContacts();


    public RecyclerAdapter(List<Data> datas){
        this.datas = datas;
    }



    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // position은 0부터 시작하는 인덱스라고 생각하면 된다. (1이 아니라 0부터 시작한다.)


        // 1. 데이터를 꺼내고
        final Data data = datas.get(position);

        // 2. 데이터를 세팅
        holder.setTextName(data.getName());
        holder.setTextPhoneNumber(data.getTel());


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    /* Holder 클래스 */
    class Holder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPhoneNumber;

        /* Holder 생성자 */
        public Holder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.txtName);
            textPhoneNumber = (TextView) itemView.findViewById(R.id.txtPhoneNumber);
        }

        public void setTextName(String name) {
            textName.setText(name);
        }

        public void setTextPhoneNumber(String phoneNumber){
            textPhoneNumber.setText(phoneNumber);
        }




    }
}
