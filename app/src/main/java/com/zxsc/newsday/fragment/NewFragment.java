package com.zxsc.newsday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zxsc.newsday.activity.AddActivity;
import com.zxsc.newsday.activity.MainActivity;
import com.zxsc.newsday.R;
import com.zxsc.newsday.adapter.TabPagerAdapter;
import com.zxsc.newsday.adapter.TableViewPagerAdapter;
import com.zxsc.newsday.util.SharePreUtil;

import java.util.ArrayList;

/**
 */
public class NewFragment extends Fragment {


    private ViewPager fragment_viewpager;
    private TabLayout fragment_tablayout;
    private TabPagerAdapter tableadapter;
    private ArrayList<Fragment> list;
    private SharePreUtil shareutil;
    private ArrayList<String> taglist;
    private ImageView add_img;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_main_fragment, container, false);
        shareutil=new SharePreUtil(getContext());
        list=new ArrayList<>();
        taglist=shareutil.getKeyMenuList();

        for(String title:taglist){
            Fragment fragment=new BaiDuFragment();
            Bundle bundle= new Bundle();
            bundle.putString("keyname",title);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        //对两个控件进行findviewbyID
        fragment_tablayout = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);
        fragment_viewpager = (ViewPager)view.findViewById(R.id.vp_FindFragment_pager);
        fragment_viewpager.setOffscreenPageLimit(5);
        MainActivity activity= (MainActivity) getActivity();
        tableadapter=new TabPagerAdapter
                (activity.getSupportFragmentManager(),
                        list,taglist);
        fragment_viewpager.setAdapter(tableadapter);
        fragment_tablayout
                .setupWithViewPager(fragment_viewpager);

        add_img= (ImageView) view.findViewById(R.id.add_img);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,1);
//                getActivity().finish();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


    }
}
