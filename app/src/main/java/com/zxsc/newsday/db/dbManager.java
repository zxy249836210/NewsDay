package com.zxsc.newsday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zxsc on 2016/3/24.
 */
public class dbManager {
    private static dbManager manager;
    private SQLiteDatabase db;
    public synchronized static dbManager getInstance(Context context){

        if(manager==null){
            manager=new dbManager(context);
        }
        return manager;
    }

    private dbManager(Context context){
     db=new dbHelper(context).getWritableDatabase();
    }

    //添加
    public void addNewToDB(){

    }


}
