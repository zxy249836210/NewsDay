package com.zxsc.newsday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zxsc on 2016/3/24.
 */
public class dbHelper extends SQLiteOpenHelper{
    public static final int FIRST_DB_VERSION = 1000;
    public static final int DB_VERSION=1000;
    public static final String DB_NAME="conn.db";
    public static final String TABLE_NAME="NEWS";
    public static final String NEW_ID="_id";
    public static final String NEW_TITLE="title";
    public static final String NEW_CONT="cont";
    public static final String NEW_URL="url";
    public static final String NEW_IMGURL="imgurl";
    public static final String NEW_TIME="time";
    public static final String NEW_COMEFROM="comfrom";

    public static final String CREATE_TABLE_SQL=
            "Create Table "+TABLE_NAME+"(" +
            NEW_ID+ " INT PRIMARY KEY  NOT NULL, " +
            NEW_TITLE+" TEXT NOT NULL, " +
            NEW_CONT+" TEXT NOT NULL, " +
            NEW_URL+" TEXT NOT NULL, " +
            NEW_IMGURL+" TEXT NOT NULL, " +
            NEW_TIME+" TEXT NOT NULL, " +
            NEW_COMEFROM+" TEXT NOT NULL, " +
            ");";

    public dbHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_SQL);
//        onUpgrade(db, FIRST_DB_VERSION, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// 使用for实现跨版本升级数据库
//        for (int i = oldVersion; i < newVersion; i++) {
//            switch (i) {
//                case 1000:
//                    upgradeToVersion1001(db);
//                    break;
//                case 1001:
//                    upgradeToVersion1002(db);
//                    break;
//                default:
//                    break;
//            }
//        }
    }
//    private void upgradeToVersion1001(SQLiteDatabase db){
//// favorite表新增1个字段
//        String sql1 = "ALTER TABLE "+TABLE_NAME+" ADD COLUMN deleted VARCHAR";
//        db.execSQL(sql1);
//    }
//    private void upgradeToVersion1002(SQLiteDatabase db){
//// favorite表新增2个字段,添加新字段只能一个字段一个字段加，sqlite有限制不予许一条语句加多个字段
//        String sql1 = "ALTER TABLE "+TABLE_NAME+" ADD COLUMN message VARCHAR";
//        String sql2 = "ALTER TABLE "+TABLE_NAME+" ADD COLUMN type VARCHAR";
//        db.execSQL(sql1);
//        db.execSQL(sql2);
//    }
}
