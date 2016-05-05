package com.zxsc.newsday.bean;

import java.util.List;

/**
 * Created by zxsc on 2016/4/7.
 */
public class HotInfo {


    private int errNum;
    private String errMsg;


    private List<RetDataEntity> retData;

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setRetData(List<RetDataEntity> retData) {
        this.retData = retData;
    }

    public int getErrNum() {
        return errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public List<RetDataEntity> getRetData() {
        return retData;
    }

    public static class RetDataEntity {
        private String title;
        private String url;
        private String abstractX;
        private String image_url;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public String getImage_url() {
            return image_url;
        }
    }
}
