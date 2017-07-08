package com.mdy.android.firebaseauth;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MDY on 2017-07-08.
 */

public class ImageDTO {

    public String imageUrl;
    public String imageName;
    public String title;
    public String description;
    public String uid;
    public String userId;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

}