package com.zxsc.newsday.bean;

import java.util.List;

/**
 * Created by zxsc on 2016/3/18.
 */
public class NewInfo {

    private String reason;
    private int error_code;

    private List<ResultEntity> result;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public int getError_code() {
        return error_code;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        private String title;
        private String content;
        private String img_width;
        private String full_title;
        private String pdate;
        private String src;
        private String img_length;
        private String img;
        private String url;
        private String pdate_src;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setImg_width(String img_width) {
            this.img_width = img_width;
        }

        public void setFull_title(String full_title) {
            this.full_title = full_title;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public void setImg_length(String img_length) {
            this.img_length = img_length;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setPdate_src(String pdate_src) {
            this.pdate_src = pdate_src;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getImg_width() {
            return img_width;
        }

        public String getFull_title() {
            return full_title;
        }

        public String getPdate() {
            return pdate;
        }

        public String getSrc() {
            return src;
        }

        public String getImg_length() {
            return img_length;
        }

        public String getImg() {
            return img;
        }

        public String getUrl() {
            return url;
        }

        public String getPdate_src() {
            return pdate_src;
        }
    }
}
