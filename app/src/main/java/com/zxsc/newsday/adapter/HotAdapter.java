package com.zxsc.newsday.adapter;


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

import com.zxsc.newsday.bean.HotInfo;
import com.zxsc.newsday.adapter.HotAdapter.ItemViewHolder;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxsc on 2016/4/7.
 */
public class HotAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnClickListener {
    List<HotInfo.RetDataEntity> list;
    private DisplayImageOptions options;//图片加载设置
   public HotAdapter(){
     list=new ArrayList<HotInfo.RetDataEntity>();
       options = new DisplayImageOptions.Builder().cacheInMemory(true).showStubImage(R.drawable.img_news_loding)// 设置图片在下载期间显示的图片
               .showImageOnLoading(R.drawable.img_news_loding)
               .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
               .bitmapConfig(Bitmap.Config.RGB_565).build();
  }

    public void updateData(List<HotInfo.RetDataEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recycle_hot, null);

        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.item_text.setText(list.get(position).getTitle());
        ImageLoader.getInstance().displayImage(list.get(position).getImage_url(), holder.item_img, options);
        holder.itemView.setTag(list.get(position).getUrl());
        holder.itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onItemClick(v);
        }
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener( OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v);
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView item_img;
        TextView item_text;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_img= (ImageView) itemView.findViewById(R.id.item_img);
            item_text=(TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
