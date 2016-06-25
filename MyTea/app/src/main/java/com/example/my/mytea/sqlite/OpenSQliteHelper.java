package com.example.my.mytea.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by my on 2016/6/23.
 */
public class OpenSQliteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mytea.db";
    private static final int VERSION = 1;
    public OpenSQliteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS [collect] (  [_id] INTEGER PRIMARY KEY , [title] TEXT(100), [time] TEXT(100));";
        db.execSQL(sql);
         sql = "CREATE TABLE IF NOT EXISTS [browse] (  [_id] INTEGER PRIMARY KEY , [title] TEXT(100), [time] TEXT(100));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
