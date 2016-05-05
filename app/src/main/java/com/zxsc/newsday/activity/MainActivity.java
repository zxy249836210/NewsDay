package com.zxsc.newsday.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zxsc.newsday.R;
import com.zxsc.newsday.base.BaseActivity;
import com.zxsc.newsday.base.MyApplication;
import com.zxsc.newsday.fragment.FindFragment;
import com.zxsc.newsday.fragment.HotFragment;
import com.zxsc.newsday.fragment.NewFragment;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton[] radio_btns = new RadioButton[3];
    private FragmentTransaction ftransaction;
    private FragmentManager ftmanager;
    private Fragment[] fragments = new Fragment[3];
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationview;
    private  OnekeyShare oks;
    private TextView me_text;
    private boolean Login_Statce;
    private CircleImageView login_img;
    private MyApplication app;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main_layout);
        app=MyApplication.getInstance();
        ftmanager = getSupportFragmentManager();
        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("每天都有新资讯");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("下载我！每天都离万事通更近一点！");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                oks.show(this);
                break;

        }
        return true;
    }



    @Override
    public void initView() {
        login_img= (CircleImageView) findViewById(R.id.login_img);
        me_text= (TextView) findViewById(R.id.me_text);
        me_text.setOnClickListener(this);
        toolbar= (Toolbar) findViewById(R.id.id_tool_bar);
        drawer = (DrawerLayout) findViewById(R.id.drawerlayou);
        navigationview= (NavigationView) findViewById(R.id.navigation_view);
        navigationview.setItemIconTintList(null);
        navigationview.setNavigationItemSelectedListener(

               new NavigationView.OnNavigationItemSelectedListener() {

                   @Override
                   public boolean onNavigationItemSelected(MenuItem menuItem) {

//                       menuItem.setChecked(true);

                       drawer.closeDrawers();
                       switch (menuItem.getItemId()){

                           case R.id.drawer_me:

                               startActivity(AboutmeActivity.class);
                               break;
                       }
                       return true;
                   }
               });
        toolbar.setTitle("NewsDay");//设置Toolbar标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name,R.string.app_name);
        mDrawerToggle.syncState();
        drawer.setDrawerListener(mDrawerToggle);


        radio_btns[0] = (RadioButton) findViewById(R.id.main_radio_new);
        radio_btns[1] = (RadioButton) findViewById(R.id.main_radio_about);
        radio_btns[2] = (RadioButton) findViewById(R.id.main_radio_user);
        radio_btns[0].setOnClickListener(this);
        radio_btns[1].setOnClickListener(this);
        radio_btns[2].setOnClickListener(this);
        choseFragment(0);
    }

    public void cleanFragment() {
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                ftransaction.hide(fragments[i]);
            }
        }
    }


    public void choseFragment(int index) {
        ftransaction = ftmanager.beginTransaction();
        cleanFragment();
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new NewFragment();
                    break;
                case 1:
                    fragments[index] = new HotFragment();
                    break;
                case 2:
                    fragments[index] = new FindFragment();
                    break;
            }
            ftransaction.add(R.id.main_framelayout, fragments[index]);
        } else {
            ftransaction.show(fragments[index]);
        }
        ftransaction.commit();
    }


    @Override
    public void AfterViewLogic() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_radio_new:
                choseFragment(0);
                break;
            case R.id.main_radio_about:
                choseFragment(1);
                break;
            case R.id.main_radio_user:
                choseFragment(2);
                break;
            case R.id.me_text:
                startActivityForResult(LoginActivity.class, 1);
//                drawer.closeDrawers();
              break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

           if(resultCode==2) {
               Login_Statce = true;
               me_text.setText("退出登录");
           }
        if(resultCode==3){
            Login_Statce = true;
            me_text.setText("退出登录");
            String icon_path=data.getStringExtra("usericon");
            ImageRequest imageRequest=new ImageRequest(
                    icon_path, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    login_img.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    login_img.setImageResource(R.drawable.login_defult_img);
                }
            });
            app.getRequestQueue().add(imageRequest);

        }


    }
}
