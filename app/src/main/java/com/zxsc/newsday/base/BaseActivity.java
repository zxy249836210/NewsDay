package com.zxsc.newsday.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public  abstract  class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout();
        initView();
        AfterViewLogic();

    }
    //加载布局
    public abstract  void setContentLayout();
    //初始化控件
    public abstract void initView();
    //加载布局后的逻辑
    public abstract  void AfterViewLogic();

    /***关于跳转的方法**/
    public void startActivity(Class<?> tagActivity){
        Intent intent=new Intent(this,tagActivity);
        startActivity(intent);
    }
    /***关于跳转的方法**/
    public void startActivityForResult(Class<?> tagActivity,int requestCode){
        Intent intent=new Intent(this,tagActivity);
        startActivityForResult(intent,requestCode);
    }
    /**弹出土司的方法**/
    public void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    public void showToast(int shu ){
        Toast.makeText(this, shu+"", Toast.LENGTH_SHORT).show();
    }
    /***版本号的获取**/
   public String getAppVersioName(){
        String ver="";
        try {
            ver=getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ver;
    }
    public int getAppVersioCode(){
        int ver=1;
        try {
            ver=getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ver;
    }

}
