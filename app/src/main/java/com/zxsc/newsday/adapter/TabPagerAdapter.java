package com.zxsc.newsday.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by zxsc on 2016/3/16.
 */
public class TabPagerAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> list;
    ArrayList<String>  taglist;
    public TabPagerAdapter(FragmentManager fm,ArrayList<Fragment> list,ArrayList<String>  taglist) {
        super(fm);
        this.list=list;
        this.taglist=taglist;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position>taglist.size()){
            return "未加载";
        }
        return taglist.get(position);
    }
}
