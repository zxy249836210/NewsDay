package com.zxsc.newsday.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zxsc.newsday.R;
import com.zxsc.newsday.activity.WebActivity;
import com.zxsc.newsday.bean.BaiDuiInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;

import java.util.List;

/**
 * Created by zxsc on 2016/3/23.
 */
public class BaiDuAdapter extends BaseLoadingAdapter<ContentlistBean> implements View.OnClickListener {
   private List<ContentlistBean> list;//数据源
   private DisplayImageOptions options;//图片加载设置
    Context context;
    public BaiDuAdapter(RecyclerView recyclerView,List<ContentlistBean> list,Context context) {
        super(recyclerView, list);
        this.context=context;
        this.list=list;
        options = new DisplayImageOptions.Builder().cacheInMemory(true).showStubImage(R.drawable.img_news_loding)// 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.img_news_lodinglose)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.img_news_lodinglose)// 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }
    @Override
     public RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recycle_newinfo, null);
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeadViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recycle_head, null);
        return new HeadViewHolder(view);
    }



    @Override
    public void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemholder = (ItemViewHolder) holder;
            itemholder.item_recyclenews_title.setText(list.get(position).getTitle());
            itemholder.itemt_recyclenews_time.setText(list.get(position).getPubDate());
            itemholder.itemt_recyclenews_content.setText(list.get(position).getDesc());
            ImageLoader.getInstance().displayImage(list.get(position).getImageurls().get(0).getUrl(), itemholder.item_recyclenews_img, options);
            itemholder.itemView.setTag(position);
            itemholder.itemView.setOnClickListener(this);

    }

    @Override
    public void onBindHeadViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeadViewHolder headholder= (HeadViewHolder) holder;
        headholder.head_content.setText(list.get(position).getTitle());
        ImageLoader.getInstance().displayImage(list.get(position).getImageurls().get(0).getUrl(), headholder.head_img, options);
        headholder.itemView.setTag(position);
        headholder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", list.get((int)v.getTag()).getLink());
        context.startActivity(intent);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView item_recyclenews_title, itemt_recyclenews_time, itemt_recyclenews_content;
        private ImageView item_recyclenews_img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_recyclenews_img = (ImageView) itemView.findViewById(R.id.item_recyclenews_img);
            item_recyclenews_title = (TextView) itemView.findViewById(R.id.item_recyclenews_title);
            itemt_recyclenews_time = (TextView) itemView.findViewById(R.id.item_recycle_time);
            itemt_recyclenews_content = (TextView) itemView.findViewById(R.id.item_recycle_content);
        }
    }


    class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView head_content;
        private ImageView head_img;

        public HeadViewHolder(View itemView) {
            super(itemView);
            head_img = (ImageView) itemView.findViewById(R.id.item_head_img);
            head_content = (TextView) itemView.findViewById(R.id.item_head_text);
        }
    }
}
