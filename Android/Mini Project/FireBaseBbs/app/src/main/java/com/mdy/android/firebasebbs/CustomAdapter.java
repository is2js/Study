package com.mdy.android.firebasebbs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.firebasebbs.domain.Bbs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MDY on 2017-07-03.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    List<Bbs> data = new ArrayList<>();
    LayoutInflater inflater;

    public CustomAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Bbs> data){
        this.data = data;
    }

    @Override
    public CustomAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);

        holder.title.setText(bbs.title);
        holder.author.setText(bbs.author);

        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        holder.date.setText(sdf.format(currentTime));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView date;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            author = (TextView) itemView.findViewById(R.id.txtAuthor);
            date = (TextView) itemView.findViewById(R.id.txtDate);

        }
    }
}
