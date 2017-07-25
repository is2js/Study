package android.mdy.com.servernodejs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MDY on 2017-07-25.
 */

public class RecyclerAdapter extends RecyclerView.Adapter {

    public RecyclerAdapter(){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder{

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
            txtTitle.setText(title);
        }
        public void setDate(String date){
            txtDate.setText(date);
        }
        public void setAuthor(String author){
            txtAuthor.setText(author);
        }
        public void setContent(String content){
            txtContent.setText(content);
        }
    }
}
