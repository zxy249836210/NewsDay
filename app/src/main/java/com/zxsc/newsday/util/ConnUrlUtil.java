package com.zxsc.newsday.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by zxsc on 2016/3/17.
 */
public class ConnUrlUtil {
    public static String JuHe="http://op.juhe.cn/onebox/news/query?key=需要去申请KEY&q=";
    public static String getJuHeNewUrl(String keyName){
        try {
            keyName=java.net.URLEncoder.encode(keyName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            keyName="";
        }
        return "http://op.juhe.cn/onebox/news/query?key=需要去申请KEY&q="+keyName;

    }

    /**百度API接口**/
    public static String BAIDU_URL="http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static String BAIDU_KEY="需要去申请KEY";
    public static String BAIDU_HOTURL="http://apis.baidu.com/songshuxiansheng/news/news";

    public static String findNewByTitle(String title,int page){

//        Log.e("msg"+(BAIDU_URL+"?title="+title+"page="+page));
//        return BAIDU_URL+"?title="+changeToUcode(title)+"&page="+page;
        return BAIDU_URL+"?title="+changeToUcode(title)+"&page="+page;
    }
    public static String findNewByID(String id,int page){
        return BAIDU_URL+"?channelId="+id+"&page="+page;
    }
    public static String findNewByCName(String cname,int page){
        return BAIDU_URL+"?channelName="+changeToUcode(cname)+"&page="+page;
    }

    public static String findHotNew(){
        return BAIDU_HOTURL;
    }

    public static String changeToUcode(String keyName){

        try {
            keyName=java.net.URLEncoder.encode(keyName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            keyName="";
        }

        return keyName;
    }


}
