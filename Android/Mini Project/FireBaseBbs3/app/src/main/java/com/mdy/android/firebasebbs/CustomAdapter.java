package com.mdy.android.firebasebbs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.firebasebbs.domain.Bbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDY on 2017-07-04.
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
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = data.get(position);

        holder.setTitle(bbs.title);
        holder.setAuthor(bbs.author);
        holder.setDate(bbs.date);

        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private int position;
        private TextView txtTitle, txtAuthor, txtDate;

        public Holder(final View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ReadActivity.class);
                    intent.putExtra("LIST_POSITION", position);
                    v.getContext().startActivity(intent);
                }
            });
        }
        public void setPosition(int position){
            this.position = position;
        }
        public void setTitle(String title){
            txtTitle.setText(title);
        }
        public void setAuthor(String author){
            txtAuthor.setText(author);
        }
        public void setDate(String date){
            txtDate.setText(date);
        }
    }
}
