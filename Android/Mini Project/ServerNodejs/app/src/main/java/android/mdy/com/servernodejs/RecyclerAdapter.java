package android.mdy.com.servernodejs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MDY on 2017-07-25.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    LayoutInflater inflater;
    List<Bbs> data;

    public RecyclerAdapter(Context context, List<Bbs> data){
        this.data = data;
//        this.inflater = LayoutInflater.from(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        holder.setContent(bbs.content);
        holder.setAuthor(bbs.author);
        holder.setDate(bbs.date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDate;
        TextView txtAuthor;
        TextView txtContent;

        public Holder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
        }

        public void setTitle(String title){
            txtTitle.setText("제목 : " + title);
        }
        public void setDate(String date){
            txtDate.setText("날짜 : " + date);
        }
        public void setAuthor(String author){
            txtAuthor.setText("저자 : " + author);
        }
        public void setContent(String content){
            txtContent.setText("내용 : " + content);
        }
    }
}
