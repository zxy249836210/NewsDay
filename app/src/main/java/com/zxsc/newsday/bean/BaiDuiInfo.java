package com.zxsc.newsday.bean;

import java.util.List;

/**
 * Created by tinyyoung on 2016/3/22.
 */


public class BaiDuiInfo {

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {

        private PagebeanBean pagebean;
        private int ret_code;

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public static class PagebeanBean {
            private int allNum;
            private int allPages;
            private int currentPage;
            private int maxResult;

            private List<ContentlistBean> contentlist;

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<ContentlistBean> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentlistBean> contentlist) {
                this.contentlist = contentlist;
            }

            public static class ContentlistBean {
                private String channelId;
                private String channelName;
                private String desc;
                private String link;
                private String long_desc;
                private String nid;
                private String pubDate;
                private int sentiment_display;
                private String source;
                private TagsBean tags;
                private String title;
                private List<ImageEntity> imageurls;

                public ContentlistBean(String channelId, String channelName, String desc, String link, String long_desc, String nid, String pubDate, int sentiment_display, TagsBean tags, String source, String title, List<ImageEntity> imageurls) {
                    this.channelId = channelId;
                    this.channelName = channelName;
                    this.desc = desc;
                    this.link = link;
                    this.long_desc = long_desc;
                    this.nid = nid;
                    this.pubDate = pubDate;
                    this.sentiment_display = sentiment_display;
                    this.tags = tags;
                    this.source = source;
                    this.title = title;
                    this.imageurls = imageurls;
                }

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public String getChannelName() {
                    return channelName;
                }

                public void setChannelName(String channelName) {
                    this.channelName = channelName;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getLong_desc() {
                    return long_desc;
                }

                public void setLong_desc(String long_desc) {
                    this.long_desc = long_desc;
                }

                public String getNid() {
                    return nid;
                }

                public void setNid(String nid) {
                    this.nid = nid;
                }

                public String getPubDate() {
                    return pubDate;
                }

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public int getSentiment_display() {
                    return sentiment_display;
                }

                public void setSentiment_display(int sentiment_display) {
                    this.sentiment_display = sentiment_display;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public TagsBean getTags() {
                    return tags;
                }

                public void setTags(TagsBean tags) {
                    this.tags = tags;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<ImageEntity> getImageurls() {
                    return imageurls;
                }

                public void setImageurls(List<ImageEntity> imageurls) {
                    this.imageurls = imageurls;
                }
                public static class ImageEntity {
                    private int height;
                    private String url;
                    private int width;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }
                }

                public static class TagsBean {

                    private List<OwnBean> own;
                    private List<?> tag;

                    public List<OwnBean> getOwn() {
                        return own;
                    }

                    public void setOwn(List<OwnBean> own) {
                        this.own = own;
                    }

                    public List<?> getTag() {
                        return tag;
                    }

                    public void setTag(List<?> tag) {
                        this.tag = tag;
                    }

                    public static class OwnBean {
                        private String name;
                        private int type;

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public int getType() {
                            return type;
                        }

                        public void setType(int type) {
                            this.type = type;
                        }
                    }
                }
            }
        }
    }
}

