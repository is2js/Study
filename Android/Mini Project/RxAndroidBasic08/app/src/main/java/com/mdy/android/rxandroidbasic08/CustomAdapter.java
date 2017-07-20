package com.mdy.android.rxandroidbasic08;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.rxandroidbasic08.domain.Info;

import java.util.List;

/**
 * Created by MDY on 2017-07-20.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{

    List<Info> data = null;
    LayoutInflater inflater = null;

    public CustomAdapter(Context context, List<Info> data){
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Info info = data.get(position);
        holder.txtName.setText(info.name);
        holder.txtTemperature.setText(info.temperature+"");
        holder.txtHumidity.setText(info.humidity+"");
        holder.txtRain.setText(info.rain+"");


//        holder.txtName.setText(rows.);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtTemperature;
        TextView txtHumidity;
        TextView txtRain;

        public Holder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtTemperature = (TextView) itemView.findViewById(R.id.txtTemperature);
            txtHumidity = (TextView) itemView.findViewById(R.id.txtHumidity);
            txtRain = (TextView) itemView.findViewById(R.id.txtRain);
        }
    }
}
