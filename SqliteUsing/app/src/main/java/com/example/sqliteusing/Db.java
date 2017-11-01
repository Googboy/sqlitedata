package com.example.sqliteusing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 潘硕 on 2017/11/1.
 */

public class Db extends SQLiteOpenHelper {
    public Db(Context context) {
        super(context, "Db", null, 1);//数据库版本
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT DEFAULT \"\","+             //\"\"代表是空字符 ，也可以用null
                "sex TEXT DEFAULT \"\")");
    }

    //如果当前传入的数据库版本号比上一次创建的版本高，SQLiteOpenHelper就会调用onUpgrade()方法。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
