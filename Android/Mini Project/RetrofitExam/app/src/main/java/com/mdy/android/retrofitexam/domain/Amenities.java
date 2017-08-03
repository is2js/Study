package com.mdy.android.retrofitexam.domain;

/**
 * Created by MDY on 2017-08-03.
 */

public class Amenities
{
    private String name;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+"]";
    }
}