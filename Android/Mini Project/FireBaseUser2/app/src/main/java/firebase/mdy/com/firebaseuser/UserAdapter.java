package firebase.mdy.com.firebaseuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import firebase.mdy.com.firebaseuser.domain.User;

/**
 * Created by MDY on 2017-07-03.
 */

public class UserAdapter extends BaseAdapter{

    List<User> data = new ArrayList<>();
    LayoutInflater inflater;

    public UserAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<User> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            holder = new Holder();
            convertView = inflater.inflate(R.layout.item_activity_main, parent, false);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        User user = data.get(position);
        holder.txtEmail.setText(user.email);
        holder.txtName.setText(user.username);

        return convertView;
    }

    class Holder {
        public TextView txtEmail;
        public TextView txtName;
    }
}
