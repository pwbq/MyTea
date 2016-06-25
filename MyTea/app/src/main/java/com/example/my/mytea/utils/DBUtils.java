package com.example.my.mytea.utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.my.mytea.sqlite.OpenSQliteHelper;

public class DBUtils {
    private OpenSQliteHelper helper;

    public DBUtils(Context context) {
        super();
        helper = new OpenSQliteHelper(context);
    }
    // 插入操作(把表名作为参数传进来)
    public long insert(String name, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.insert(name, null, values);
    }
    // 删除操作
    // DELETE FROM student WHERE 字段1 = 值1 AND 字段2 = 值2
    public int delete(String name, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(name, whereClause, whereArgs);
    }
    // 更改操作
    // UPDATE student SET 字段1 = 值1, 字段2 = 值2 WHERE 字段3 = 值3 AND 字段4 = 值4
    public int update(String name, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.update(name, values, whereClause, whereArgs);
    }
    // 查询所有
    // SELECT * FROM student
    public Cursor queryAll(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(name, null, null, null, null, null, null);
    }
    // 按字段查询 只有在columns中有的字段才能通过cursor遍历
    // SELECT 字段1,字段2 FROM student
    public Cursor queryField(String name, String[] columns) {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(name, columns, null, null, null, null, null);
    }
    // 按记录查询
    // SELECT * FROM student WHERE 字段1 = 值1, 字段2 = 值2
    public Cursor queryCondition(String name, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(name, null, selection, selectionArgs, null, null, null);
    }
}
