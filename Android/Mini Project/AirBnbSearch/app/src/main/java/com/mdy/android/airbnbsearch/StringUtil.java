package com.mdy.android.airbnbsearch;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by MDY on 2017-07-27.
 */

public class StringUtil {
    public static void setHtmlText(TextView target, String text){
        target.setAllCaps(false);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            target.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }else {
            target.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);
        }
    }
}