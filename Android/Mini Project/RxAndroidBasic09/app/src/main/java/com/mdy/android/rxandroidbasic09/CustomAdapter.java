package com.mdy.android.rxandroidbasic09;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdy.android.rxandroidbasic09.domain.Info;

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
        holder.txtSAWS_OBS_TM.setText("측정일시 : " + info.SAWS_OBS_TM);
        holder.txtSTN_NM.setText("지점명 : " + info.STN_NM);
        holder.txtSTN_ID.setText("지점코드 : " + info.STN_ID);
        holder.txtSAWS_TA_AVG.setText("기온 : " + info.SAWS_TA_AVG + " 도");
        holder.txtSAWS_HD.setText("습도 : " + info.SAWS_HD + " %");
        holder.txtCODE.setText("풍향1 : " + info.CODE);
        holder.txtNAME.setText("풍향2 : " + info.NAME);
        holder.txtSAWS_WS_AVG.setText("풍속 : " + info.SAWS_WS_AVG + " m/s");
        holder.txtSAWS_RN_SUM.setText("강수 : " + info.SAWS_RN_SUM + " mm");
        holder.txtSAWS_SOLAR.setText("일사 : " + info.SAWS_SOLAR);
        holder.txtSAWS_SHINE.setText("일조 : " + info.SAWS_SHINE + " hour");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtSAWS_OBS_TM;    // 측정일시
        TextView txtSTN_NM;         // 지점명
        TextView txtSTN_ID;         // 지점코드
        TextView txtSAWS_TA_AVG;    // 기온
        TextView txtSAWS_HD;        // 습도
        TextView txtCODE;           // 풍향1
        TextView txtNAME;           // 풍향2
        TextView txtSAWS_WS_AVG;    // 풍속
        TextView txtSAWS_RN_SUM;    // 강수
        TextView txtSAWS_SOLAR;     // 일사
        TextView txtSAWS_SHINE;     // 일조

        public Holder(View itemView) {
            super(itemView);
            txtSAWS_OBS_TM = (TextView) itemView.findViewById(R.id.txtSAWS_OBS_TM);
            txtSTN_NM = (TextView) itemView.findViewById(R.id.txtSTN_NM);
            txtSTN_ID = (TextView) itemView.findViewById(R.id.txtSTN_ID);
            txtSAWS_TA_AVG = (TextView) itemView.findViewById(R.id.txtSAWS_TA_AVG);
            txtSAWS_HD = (TextView) itemView.findViewById(R.id.txtSAWS_HD);
            txtCODE = (TextView) itemView.findViewById(R.id.txtCODE);
            txtNAME = (TextView) itemView.findViewById(R.id.txtNAME);
            txtSAWS_WS_AVG = (TextView) itemView.findViewById(R.id.txtSAWS_WS_AVG);
            txtSAWS_RN_SUM = (TextView) itemView.findViewById(R.id.txtSAWS_RN_SUM);
            txtSAWS_SOLAR = (TextView) itemView.findViewById(R.id.txtSAWS_SOLAR);
            txtSAWS_SHINE = (TextView) itemView.findViewById(R.id.txtSAWS_SHINE);
        }
    }
}