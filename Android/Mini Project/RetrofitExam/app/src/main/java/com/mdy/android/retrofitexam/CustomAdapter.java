package com.mdy.android.retrofitexam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdy.android.retrofitexam.domain.Data;
import com.mdy.android.retrofitexam.domain.House_images;

import java.util.List;

/**
 * Created by MDY on 2017-08-04.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    List<Data> data;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<Data> data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rooms_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Data ad = data.get(position);
        holder.txtPrice.setText(ad.getPrice_per_day());
        holder.txtDescripton.setText(ad.getIntroduce());
        holder.txtType.setText(ad.getRoom_type());
        House_images[] images = ad.getHouse_images();
        Glide.with(inflater.getContext())
                .load(images.length > 0 ? images[0].getImage() : null)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txtPrice, txtDescripton, txtType, txtReview, txtReviewCount;
        ImageView img;
        ImageButton imgBtnHeart;
        RatingBar ratingBar;

        public Holder(View itemView) {
            super(itemView);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtDescripton = (TextView) itemView.findViewById(R.id.txtDescription);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtReview = (TextView) itemView.findViewById(R.id.txtReview);
            txtReviewCount = (TextView) itemView.findViewById(R.id.txtReviewCount);
            img = (ImageView) itemView.findViewById(R.id.img);
            imgBtnHeart = (ImageButton) itemView.findViewById(R.id.imgBtnHeart);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
