package com.mdy.android.musiclist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by MDY on 2017-06-19.
 */

public class ListAdapter extends RecyclerView.Adapter<Holder> {

    private List<Music> datas = null;
    Context context;

    public ListAdapter(List<Music> datas, Context context){
        this.datas = datas;
        this.context = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
//        holder.position = position;
        holder.txtId.setText(datas.get(position).id);
        holder.txtTitle.setText(datas.get(position).title);

        Glide.with(context)
                .load(datas.get(position).albumArt)
                .placeholder(R.mipmap.ic_launcher)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.albumImage);
        Log.w("AlbumArt", "datas.get(position).albumArt ========" + datas.get(position).albumArt);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

class Holder extends RecyclerView.ViewHolder{

    int position;
    TextView txtId;
    TextView txtTitle;
    ImageView albumImage;

    public Holder(View itemView) {
        super(itemView);
        txtId = (TextView) itemView.findViewById(R.id.txtId);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        albumImage = (ImageView) itemView.findViewById(R.id.albumImage);
    }
}