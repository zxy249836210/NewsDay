package com.zxsc.newsday.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by zxsc on 2016/3/11.
 */
public class TableViewPagerAdapter  extends FragmentPagerAdapter{

    Context context;
    private String tabTitles[] = new String[]{"四川","娱乐","IT","体育","国际","游戏","tab7","tab8","tab9","tab10"};
    private ArrayList<Fragment> list;

    public TableViewPagerAdapter(FragmentManager fm,Context context,ArrayList<Fragment> list) {
        super(fm);
        this.context=context;
        this.list=list;
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
        return tabTitles[position];
    }
}
