package com.mdy.android.retrofitexam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Created by MDY on 2017-08-04.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    LayoutInflater inflater;
    Data data;

    public RecyclerAdapter(Context context, Data data){
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
        Log.e("===================", " 0 " );
//        RoomsData roomsData = data.get(position);
//        Log.e("===================", " 1 " + roomsData);
        holder.txtPrice.setText("$ " +  data.getPrice_per_day());
        holder.txtDescripton.setText(data.getIntroduce());
        holder.txtType.setText(data.getRoom_type());
        House_images[] image = data.getHouse_images();
        Glide.with(inflater.getContext())
                .load(image[0].getImage())
                .into(holder.img);
//        Log.e("===================", " 2 " + roomsData.price_per_day);
//        Log.e("===================", " 3 " + roomsData.introduce);
//        Log.e("===================", " 4 " + roomsData.room_type);
//        Log.e("===================", " 5 " + roomsData.image);

    }

    @Override
    public int getItemCount() {
        return 1;
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
