package com.zxsc.newsday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zxsc.newsday.R;
import com.zxsc.newsday.adapter.BaiDuAdapter;
import com.zxsc.newsday.base.MyApplication;
import com.zxsc.newsday.bean.BaiDuiInfo;
import com.zxsc.newsday.ui.SearchView;
import com.zxsc.newsday.util.ConnUrlUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxsc on 2016/4/5.
 */
public class FindFragment extends Fragment implements SearchView.SearchViewListener{
private View view;
    private MyApplication myapp;
    private RecyclerView recyclerView;
    private Gson gson;
    private BaiDuAdapter adapter;
    private List<BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find,null);
        myapp= MyApplication.getInstance();
        gson= myapp.getGson();
        list=new ArrayList<BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean>();
        initData();
        initViews();
        return view;
    }

    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

//    /**
//     * 搜索结果列表adapter
//     */
//    private SearchAdapter resultAdapter;

    /**
     * <a href="http://lib.csdn.net/base/14" class="replace_word" title="MySQL知识库" target="_blank" style="color:#df3434; font-weight:bold;">数据库</a>数据，总数据
     */
//    private List<Bean> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

//    /**
//     * 搜索结果的数据
//     */
//    private List<Bean> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE =2;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
       FindFragment.hintSize = hintSize;
    }



    /**
     * 初始化视图
     */
    private void initViews() {
        recyclerView = (RecyclerView)view.findViewById(R.id.find_recyclerview);
        //设置布局管理器 决定线性还是网格
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new BaiDuAdapter(recyclerView,list,getActivity());
        //设置adapter
        recyclerView.setAdapter(adapter);


        searchView = (SearchView) view.findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

    }

    public void getDataFromHttp(String strkey) {

        StringRequest stringRequest = new StringRequest(ConnUrlUtil.findNewByTitle(strkey,1),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            BaiDuiInfo info = gson.fromJson(response,  BaiDuiInfo.class);
                            getDataSuccess(info.getShowapi_res_body().getPagebean().getContentlist());

                        }catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


    private void getDataSuccess(List<BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist) {
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
                if(contentlist!=null){
                    list.clear();
                    list.addAll(contentlist);
                    adapter.notifyDataSetChanged();
                }

    }


    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
//        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
//     */
//    private void getDbData() {
//        int size = 100;
//        dbData = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            dbData.add(new Bean(R.drawable.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
//        }
//    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        hintData.add("科技");
        hintData.add("正能量");
        hintAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
//            for (int i = 0, count = 0; i < dbData.size()
//                    && count < hintSize; i++) {
//                if (dbData.get(i).getTitle().contains(text.trim())) {
//                    autoCompleteData.add(dbData.get(i).getTitle());
//                    count++;
//                }
//            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
//        if(text!=null&&text.equals("清除历史纪录")){
//            hintData.clear();
//            hintData.add("清除历史纪录");
//        }
//        if (resultData == null) {
//            // 初始化
//            resultData = new ArrayList<>();
//        } else {
//            resultData.clear();
//            for (int i = 0; i < dbData.size(); i++) {
//                if (dbData.get(i).getTitle().contains(text.trim())) {
//                    resultData.add(dbData.get(i));
//                }
//            }
//        }
//        if (resultAdapter == null) {
//            resultAdapter = new SearchAdapter(this, resultData, R.layout.item_bean_list);
//        } else {
//            resultAdapter.notifyDataSetChanged();
//        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
//        getResultData(text);
        getDataFromHttp(text);
//        lvResults.setVisibility(View.VISIBLE);

    }


}
