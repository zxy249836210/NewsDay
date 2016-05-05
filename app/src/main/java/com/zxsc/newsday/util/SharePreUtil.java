package com.zxsc.newsday.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zxsc on 2016/3/16.
 */
public class SharePreUtil {
    private String lead_file="lead.data";
    private String menu_file="menu.data";
    private Context context;
    public SharePreUtil(Context context){

        this.context=context;
    }
    public void saveLeadInfo(String versionname){
        SharedPreferences sp=context.getSharedPreferences(lead_file,0);
        Editor editor=sp.edit();
        editor.putString("versionname", versionname);
        editor.commit();

    }
    public String getLeadInfo(){
        SharedPreferences sp=context.getSharedPreferences(lead_file,0);
        return sp.getString("versionname","");

    }

    public void saveKeyMenuList(List<String> list){
        Set<String> set=new HashSet<>();
        for(String s:list){
           set.add(s);
        }
        SharedPreferences sp=context.getSharedPreferences(menu_file,0);
        Editor editor=sp.edit();
        editor.putStringSet("menu", set);
        editor.commit();

    }

    public void saveKeyMenuSet(Set<String> set){
        SharedPreferences sp=context.getSharedPreferences(menu_file,0);
        Editor editor=sp.edit();
        editor.clear();
        editor.putStringSet("menu", set);
        editor.commit();

    }
    public ArrayList<String> getKeyMenuList(){
        SharedPreferences sp=context.getSharedPreferences(menu_file,0);
        Set<String> set=sp.getStringSet("menu", null);
        ArrayList<String> list=new ArrayList<>();
        if(set==null){
            list.add("互联网");
            list.add("安卓");
            list.add("热点");
            list.add("四川");
            list.add("星座");
            list.add("军事");
        }else{
            for(String s:set){
                list.add(s);
            }
        }
        return list;
    }
    public Set<String> getKeyMenuSet(){
        SharedPreferences sp=context.getSharedPreferences(menu_file, 0);
        Set<String> set=sp.getStringSet("menu", null);
        if(set==null){
            set=new HashSet<>();
            set.add("互联网");
            set.add("安卓");
            set.add("热点");
            set.add("四川");
            set.add("星座");
            set.add("军事");
        }
        return set;
    }
}
