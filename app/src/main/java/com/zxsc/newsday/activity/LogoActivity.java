package com.zxsc.newsday.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zxsc.newsday.R;
import com.zxsc.newsday.base.BaseActivity;
import com.zxsc.newsday.util.SharePreUtil;

public class LogoActivity extends BaseActivity{
private SharePreUtil sputil;
private ImageView logo_img;

    @Override
    public void setContentLayout() {
        sputil=new SharePreUtil(this);
        String versio=sputil.getLeadInfo();
        if (versio.equals("")&&!versio.equals(getAppVersioName())){
            startActivity(LeadActivity.class);
            finish();
        }
        setContentView(R.layout.activity_logo);

    }



    @Override
    public void initView() {
    logo_img= (ImageView) findViewById(R.id.logo_img);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.alpha_logo);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        logo_img.startAnimation(animation);

    }

    @Override
    public void AfterViewLogic() {

    }
}
