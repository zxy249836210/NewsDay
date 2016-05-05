package com.zxsc.newsday.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zxsc.newsday.R;
import com.zxsc.newsday.base.BaseActivity;
import com.zxsc.newsday.base.ToolBarHelper;

/**
 * Created by zxsc on 2016/3/11.
 */
public abstract class ToolBarActivity extends BaseActivity {
    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;


   public abstract int setViewId();

    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    { if (item.getItemId() == android.R.id.home){
//        finish(); return true ;
//    } return super.onOptionsItemSelected(item);
//    }

    @Override
    public  void setContentLayout(){
        mToolBarHelper = new ToolBarHelper(this,setViewId()) ;
        toolbar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());
        toolbar.setNavigationIcon(R.drawable.btn_return);
        toolbar.setTitleTextColor(0xffffffff);
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         /*自定义的一些操作*/
//        onCreateCustomToolBar(toolbar) ;

    }

    @Override
    public abstract void initView();

    @Override
    public abstract void AfterViewLogic();
}
