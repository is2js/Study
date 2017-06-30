package com.mdy.android.serverconnection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.serverconnection.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDY on 2017-06-30.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    // 1. 데이터 공간 정의
    List<Bbs> data = new ArrayList<>();

    // 2. 데이터를 세팅하는 함수 생성
    public void setData(List<Bbs> data){
        this.data = data;
    }

    public CustomAdapter() {

    }

    // 4. 리스트의 크기만큼 ViewHolder의 개수가 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);

        holder.txtId.setText(bbs.id+"");
        holder.txtTitle.setText(bbs.title);
        holder.txtContent.setText(bbs.content);
        holder.txtAuthor.setText(bbs.author);
        holder.txtDate.setText(bbs.date);
    }

    // 3. 데이터의 크기를 아답터에 제공 > 아답터가 리스트의 크기를 결정
    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txtId, txtTitle, txtContent, txtAuthor, txtDate;

        public Holder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txtId);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        }
    }
}
