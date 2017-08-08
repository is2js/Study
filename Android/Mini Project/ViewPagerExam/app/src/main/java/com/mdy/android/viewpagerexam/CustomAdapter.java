package com.mdy.android.viewpagerexam;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mdy.android.viewpagerexam.domain.House_images;
import com.mdy.android.viewpagerexam.domain.RoomsData;

import java.util.List;

import static com.mdy.android.viewpagerexam.R.id.img;

/**
 * Created by MDY on 2017-08-08.
 */

public class CustomAdapter extends PagerAdapter {

    public List<RoomsData> data;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<RoomsData> data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.e("==========", "data.size()========================================" + data.size());
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_list, container, false);
        Log.e("==========", "포지션" + position);
        RoomsData roomsData = data.get(position);

//        ImageView img = (ImageView) view.findViewById(R.id.img);
        ImageView img1 = (ImageView) view.findViewById(img);

        House_images[] images = roomsData.getHouse_images();
        if(images.length > 0){
            Log.e("image",images[0].getImage());
        }


        GlideApp
                .with(inflater.getContext())
                .load(images.length > 0 ? images[0].getImage() : null)
                .centerCrop()
                .fallback(R.mipmap.ic_launcher)
                .into(img1);
        ((ViewPager) container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager) container).removeView((View) object);
    }
}
