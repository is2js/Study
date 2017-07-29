package com.mdy.android.airbnbsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MDY on 2017-07-29.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{

    LayoutInflater inflater;
    List<Reservation> data;

    public RecyclerAdapter(Context context, List<Reservation> data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Reservation reservation = data.get(position);
        holder.txtTitle.setText("Title : " + reservation.title);
        holder.txtGuests.setText("Guests : " + reservation.guests);
        holder.txtUserId.setText("UserId : " + reservation.user_id);
        holder.txtType.setText("Type : " + reservation.type);
        holder.txtPrice.setText("Price : " + reservation.price);
        holder.txtAmenities.setText("Amenities : " + reservation.amenities);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtUserId;
        TextView txtType;
        TextView txtGuests;
        TextView txtPrice;
        TextView txtAmenities;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtUserId = (TextView) itemView.findViewById(R.id.txtUserId);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtGuests = (TextView) itemView.findViewById(R.id.txtGuests);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtAmenities = (TextView) itemView.findViewById(R.id.txtAmenities);
        }
    }
}
