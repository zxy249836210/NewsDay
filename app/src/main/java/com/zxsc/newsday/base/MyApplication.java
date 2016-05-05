package com.zxsc.newsday.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by zxsc on 2016/3/17.
 */

public class MyApplication extends Application{
    private RequestQueue requestQueue;
    private static MyApplication myApplication;
    private Gson gson;

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }
    public static MyApplication getInstance() {

        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        ShareSDK.initSDK(this);
        requestQueue= Volley.newRequestQueue(this);
        gson=new Gson();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                        // .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        requestQueue.cancelAll(this);
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
