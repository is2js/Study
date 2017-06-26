package com.mdy.android.httpbbs2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDY on 2017-06-26.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{
    List<Bbs> list;

    public CustomAdapter(){
        list = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = list.get(position);

        holder.txtId.setText(Integer.toString(bbs.id));
//        holder.txtId.setText(bbs.id+"");
        holder.txtTitle.setText(bbs.title);
        holder.txtContent.setText(bbs.content);
        holder.txtAuthor.setText(bbs.author);
        holder.txtDate.setText(bbs.date);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(List<Bbs> list) {
        this.list = list;
    }


    public class Holder extends RecyclerView.ViewHolder {
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
