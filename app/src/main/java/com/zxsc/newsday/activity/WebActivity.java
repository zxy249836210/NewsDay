package com.zxsc.newsday.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.zxsc.newsday.R;
import com.zxsc.newsday.base.BaseActivity;

public class WebActivity extends BaseActivity {
private WebView webView;
private String url;
    private ProgressBar bar;
private FloatingActionButton actionbtn;
    private Toolbar toolbar;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_web);
        url=getIntent().getStringExtra("url");
        Log.e("msg", url);
    }

    @Override
    public void initView() {
        bar= (ProgressBar) findViewById(R.id.web_progressbar);
        actionbtn= (FloatingActionButton) findViewById(R.id.actionbtn);
        actionbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar.make(actionbtn,"收藏成功",Snackbar.LENGTH_LONG).show();
            }
        });
        toolbar= (Toolbar) findViewById(R.id.web_toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_return);
//        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView= (WebView) findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        if(url!=null) {
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void AfterViewLogic() {

    }
}
