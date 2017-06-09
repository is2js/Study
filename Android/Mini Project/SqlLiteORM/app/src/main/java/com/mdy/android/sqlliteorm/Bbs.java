package com.mdy.android.sqlliteorm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by MDY on 2017-06-09.
 */

@DatabaseTable(tableName = "bbs")
public class Bbs {

    @DatabaseField
    private int id;
}