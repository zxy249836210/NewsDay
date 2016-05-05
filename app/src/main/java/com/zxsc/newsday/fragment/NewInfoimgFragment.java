package com.zxsc.newsday.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.zxsc.newsday.R;
import com.zxsc.newsday.adapter.NewinfoRrecyclerAdapter;
import com.zxsc.newsday.base.MyApplication;
import com.zxsc.newsday.bean.NewInfo;
import com.zxsc.newsday.util.ConnUrlUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewInfoimgFragment extends Fragment {

private MyApplication myapp;
private String aboutInfo;
private TextView tv;
private SwipeRefreshLayout swipeRefreshLayout;
private RecyclerView recyclerView;
private View view;
private String url_str;//新闻搜索拼接网址
private NewinfoRrecyclerAdapter adapter;
private Gson gson;
private final int ADD_DATA_FLAG=1;
private final int UPDATE_DATA_FLAG=2;
Button button;
    public NewInfoimgFragment(String aboutInfo){
        url_str= ConnUrlUtil.getJuHeNewUrl(aboutInfo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_new_infoimg, container, false);

        myapp=MyApplication.getInstance();
        gson=new Gson();
        initView();
        return view;
    }

    private void initView() {
        //初始化适配器
        adapter=new NewinfoRrecyclerAdapter(getContext(), myapp.getRequestQueue());
//        adapter.setOnItemClickListener(new NewinfoRrecyclerAdapter.OnRecycleItmeClickListener() {
//            @Override
//            public void onItemClick(View view, String url) {
//              Intent intent=new Intent(getContext(), WebActivity.class);
//                intent.putExtra("url",url);
//                startActivity(intent);
//            }
//
//        });
        recyclerView = (RecyclerView)view.findViewById(R.id.newinfo_recyclerview);
        //设置布局管理器 决定线性还是网格
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置adapter
        recyclerView.setAdapter(adapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newinfo_swiperefresh);
        //设置下拉刷新的进度条颜色（最多四种）
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.orange,
                R.color.green, R.color.text_color_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromHttp(UPDATE_DATA_FLAG);
            }
        });
        // 这句话是为了，第一次进入页面的时候显示加载进度条
//        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromHttp(ADD_DATA_FLAG);
    }
    public void getDataFromHttp( int type) {
        swipeRefreshLayout.setRefreshing(true);
        final int nowtype=type;
        StringRequest stringRequest = new StringRequest(url_str,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        NewInfo info =gson.fromJson(response, NewInfo.class);
                        getDataSuccess(info,nowtype);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDataDefult();

            }
        });
        myapp.getRequestQueue().add(stringRequest);
    }
    private void getDataSuccess(NewInfo info,int type) {
    switch (type){
        case ADD_DATA_FLAG:
            adapter.addData(info.getResult());
            break;
        case UPDATE_DATA_FLAG:
            adapter.updateData(info.getResult());
            break;
    }
        swipeRefreshLayout.setRefreshing(false);
    }
    private void getDataDefult() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
