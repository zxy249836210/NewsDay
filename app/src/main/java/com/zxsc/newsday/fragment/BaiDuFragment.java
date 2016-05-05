package com.zxsc.newsday.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zxsc.newsday.R;
import com.zxsc.newsday.adapter.BaiDuAdapter;
import com.zxsc.newsday.adapter.BaseLoadingAdapter;
import com.zxsc.newsday.base.MyApplication;
import com.zxsc.newsday.bean.BaiDuiInfo;
import com.zxsc.newsday.bean.BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;
import com.zxsc.newsday.util.ConnUrlUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxsc on 2016/3/23.
 */
@SuppressLint("ValidFragment")
public class BaiDuFragment  extends Fragment {
    private MyApplication myapp;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View view;
    private final int ADD_DATA_FLAG=1;
    private final int UPDATE_DATA_FLAG=2;
    private BaiDuAdapter adapter;
    private int nowpage=1;//当前数据的页数
    private int all_page;
    private Gson gson;
    private List<ContentlistBean> list;
    private String strkey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_new_infoimg, container, false);
        strkey= getArguments().getString("keyname");
        myapp= MyApplication.getInstance();
        gson= myapp.getGson();
        list=new ArrayList<ContentlistBean>();
        initView();
        return view;
    }

    private void initView() {

        recyclerView = (RecyclerView)view.findViewById(R.id.newinfo_recyclerview);
        //设置布局管理器 决定线性还是网格
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newinfo_swiperefresh);
        //设置下拉刷新的进度条颜色（最多四种）
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.orange,
                R.color.green, R.color.text_color_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp(UPDATE_DATA_FLAG, 1);
            }
        });

        adapter=new BaiDuAdapter(recyclerView,list,getActivity());
        adapter.setOnLoadingListener(new BaseLoadingAdapter.OnLoadingListener() {
            @Override
            public void loading() {
                getDataFromHttp(ADD_DATA_FLAG,nowpage);
            }
        });
        //设置adapter
        recyclerView.setAdapter(adapter);
        getDataFromHttp(ADD_DATA_FLAG,nowpage);
    }

    public void getDataFromHttp(int type,int page) {
        if(all_page!=0&&nowpage>all_page){
            getDataNoMore();
        }
        final int nowtype=type;
        StringRequest stringRequest = new StringRequest(ConnUrlUtil.findNewByTitle(strkey,page),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            BaiDuiInfo info = gson.fromJson(response,  BaiDuiInfo.class);
                            all_page = info.getShowapi_res_body().getPagebean().getAllPages();
                            getDataSuccess(info.getShowapi_res_body().getPagebean().getContentlist(), nowtype);
                        }catch (Exception e){
                            getDataDefult(nowtype);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDataDefult(nowtype);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", ConnUrlUtil.BAIDU_KEY);
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = null;

                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        myapp.getRequestQueue().add(stringRequest);

    }
    private void getDataSuccess(List<ContentlistBean> contentlist,int type) {
       if(contentlist==null||contentlist.size()<=0){
           return;
       }
        for(int i=0;i<contentlist.size();i++){
            if(contentlist.get(i).getImageurls() == null||
            contentlist.get(i).getImageurls().size()<=0||
                    contentlist.get(i).getImageurls().get(0).getUrl()==null){
                contentlist.remove(i);
                i--;
            }
        }
        switch (type){
            case ADD_DATA_FLAG:
                adapter.setLoadingComplete();
                if(contentlist != null){
                   list.addAll(contentlist);
                    adapter.notifyDataSetChanged();
                }
                nowpage++;
                break;
            case UPDATE_DATA_FLAG:
                Log.e("msg", contentlist.size() + " ");
                if(contentlist!=null){
                    list.clear();
                    list.addAll(contentlist);
                    adapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
                nowpage=2;
                break;
        }
    }
    private void getDataDefult(int type) {
        switch (type){
            case ADD_DATA_FLAG:
                adapter.setLoadingError();
                break;
            case UPDATE_DATA_FLAG:
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }
    private void getDataNoMore() {
        adapter.setLoadingNoMore();
        swipeRefreshLayout.setRefreshing(false);
    }


}


