package com.mdy.android.rxandroidbasic08;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDY on 2017-07-20.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

    List<String> rows = new ArrayList<>();
    LayoutInflater inflater = null;

    public CustomAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> rows){
        this.rows = rows;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(rows.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
