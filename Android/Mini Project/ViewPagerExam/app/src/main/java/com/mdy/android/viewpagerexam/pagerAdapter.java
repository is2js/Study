package com.mdy.android.viewpagerexam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdy.android.viewpagerexam.fragment.FirstFragment;
import com.mdy.android.viewpagerexam.fragment.SecondFragment;
import com.mdy.android.viewpagerexam.fragment.ThirdFragment;

/**
 * Created by MDY on 2017-08-08.
 */

public class pagerAdapter extends FragmentStatePagerAdapter{


    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
