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

public class UserAdapter extends BaseAdapter {

    List<User> data;
    LayoutInflater inflater;

    public UserAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<>();
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
            holder.email = (TextView) convertView.findViewById(R.id.txtEmail);
            holder.username = (TextView) convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        User user = data.get(position);
        holder.email.setText(user.getEmail());
        holder.username.setText(user.getUsername());

        return convertView;
    }

    class Holder {
        TextView email;
        TextView username;
    }
}
