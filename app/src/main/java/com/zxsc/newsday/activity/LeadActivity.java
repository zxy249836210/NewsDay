package com.zxsc.newsday.activity;


import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.zxsc.newsday.R;
import com.zxsc.newsday.adapter.LeadPagerAdapter;
import com.zxsc.newsday.base.BaseActivity;
import com.zxsc.newsday.util.SharePreUtil;


import java.util.ArrayList;

public class LeadActivity extends BaseActivity implements View.OnClickListener{
    private ViewPager viewpager;
    private LeadPagerAdapter adapter;
    private ArrayList<View> list;
    private Button lead_btn;
    private SharePreUtil sputil;
    private int[] imgs={R.drawable.lead_1,R.drawable.lead_2,R.drawable.lead_3};
    private Animation anim_in,anim_out;
    @Override
    public void setContentLayout() {
    setContentView(R.layout.activity_lead);
        sputil=new SharePreUtil(this);
        list=new ArrayList<>();
        View view;
        ImageView imgview;
        for(int i=0;i<3;i++){
             view=LayoutInflater.from(this).inflate(R.layout.lead_layout, null);
            imgview= (ImageView) view.findViewById(R.id.lead_img);
            imgview.setBackgroundResource(imgs[i]);
            list.add(view);
        }

        anim_in= AnimationUtils.loadAnimation(this,R.anim.view_alpha_in);
        anim_out=AnimationUtils.loadAnimation(this,R.anim.view_alpha_out);
    }

    @Override
    public void initView() {
        lead_btn= (Button) findViewById(R.id.lead_btn);
        lead_btn.setOnClickListener(this);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        adapter=new LeadPagerAdapter(list);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    lead_btn.startAnimation(anim_in);
                    lead_btn.setVisibility(View.VISIBLE);

                }else {

                    lead_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void AfterViewLogic() {



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lead_btn:
                sputil.saveLeadInfo(getAppVersioName());
                startActivity(LogoActivity.class);
                finish();
            break;
        }
    }
}
