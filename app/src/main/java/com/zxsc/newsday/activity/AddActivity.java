package com.zxsc.newsday.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zxsc.newsday.R;
import com.zxsc.newsday.base.BaseActivity;
import com.zxsc.newsday.ui.FlowLayout;
import com.zxsc.newsday.util.SharePreUtil;

import java.util.Set;

public class AddActivity extends BaseActivity {
    private Toolbar toolbar;
    private FlowLayout flowlayout;
    private Set<String> set;
    private SharePreUtil  shareutil;
    private ListView listview;
    private String[] sz={"互联网","安卓","热点","四川","星座","军事"};
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_add);
    }

    @Override
    public void initView() {
        shareutil=new SharePreUtil(this);
        set=shareutil.getKeyMenuSet();
        listview= (ListView) findViewById(R.id.add_listiew);
        toolbar= (Toolbar) findViewById(R.id.web_toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_return);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    @Override
    public void AfterViewLogic() {
           flowlayout = (FlowLayout) findViewById(R.id.flowlayout);
           flowlayout.getSetData(set);

        ArrayAdapter<String> adapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,sz);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                set.add(sz[position]);
                flowlayout.getSetData(set);
            }
        });

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shareutil.saveKeyMenuSet(set);
    }
}
