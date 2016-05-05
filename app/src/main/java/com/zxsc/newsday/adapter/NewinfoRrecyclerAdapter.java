package com.zxsc.newsday.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.zxsc.newsday.R;
import com.zxsc.newsday.activity.WebActivity;
import com.zxsc.newsday.bean.NewInfo.ResultEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxsc on 2016/3/18.
 */
public class NewinfoRrecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_ITEM=1;
    private final int TYPE_FOOTER=0;
    private List<ResultEntity> list ;
//    手机内存的获取方式：
    int MAXMEMONRY = (int) (Runtime.getRuntime() .maxMemory() / 1024);
    LruCache<String ,Bitmap> lruCache=new LruCache<String,Bitmap>(MAXMEMONRY/10){
        //
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    ImageLoader.ImageListener imageListener;
    ImageLoader.ImageCache imgcache=new ImageLoader.ImageCache() {
        @Override
        public Bitmap getBitmap(String s) {

            return lruCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {

            lruCache.put(s, bitmap);

        }

    };

    ImageLoader imgloader;
Context context;
    public NewinfoRrecyclerAdapter(Context context, RequestQueue requestQueue){
        list=new ArrayList<ResultEntity>();
        this.context=context;
        imgloader=new ImageLoader(requestQueue,imgcache);

    }

    public void updateData(List<ResultEntity> list){
        if(list==null){
            return;
        }
        this.list=list;
        notifyDataSetChanged();
    }
    public void addData(List<ResultEntity> list){
        if(list==null){
            return;
        }
            this.list.addAll(list);

            notifyDataSetChanged();
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recycle_newinfo, null);
            return new ItemViewHolder(view);
        }
        // type == TYPE_FOOTER 返回footerView
//        else if (viewType == TYPE_FOOTER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.item_recycle_foot, null);
////            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
////                    LayoutParams.WRAP_CONTENT));
//            return new FootViewHolder(view);
//        }
        return null;
    }


//    @Override
//    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()){
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof  ItemViewHolder) {
            ItemViewHolder itemholder = (ItemViewHolder) holder;
            itemholder.item_recyclenews_title.setText(list.get(position).getTitle());
            itemholder.itemt_recyclenews_time.setText(list.get(position).getPdate_src());
            itemholder.itemt_recyclenews_content.setText(list.get(position).getContent());
            //将数据保存在itemView的Tag中，以便点击时进行获取
//            holder.itemView.setTag(list.get(position).getUrl());
//            itemholder.item_recyclenews_img.setDefaultImageResId(R.drawable.news_unselected);
//            itemholder.item_recyclenews_img.setErrorImageResId(R.drawable.news_unselected);
//            itemholder.item_recyclenews_img.setImageUrl(list.get(position).getImg(), imgloader);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", list.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
     private  TextView item_recyclenews_title,itemt_recyclenews_time,itemt_recyclenews_content;
     private ImageView item_recyclenews_img;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_recyclenews_img= (ImageView) itemView.findViewById(R.id.item_recyclenews_img);
            item_recyclenews_title= (TextView) itemView.findViewById(R.id.item_recyclenews_title);
            itemt_recyclenews_time= (TextView) itemView.findViewById(R.id.item_recycle_time);
            itemt_recyclenews_content= (TextView) itemView.findViewById(R.id.item_recycle_content);

        }

//        @Override
//        public void onClick(View v) {
//            if (mOnItemClickListener!=null){
//                //注意这里使用getTag方法获取数据
////                mOnItemClickListener.onItemClick(v,(DataModel)v.getTag());
//                mOnItemClickListener.onItemClick(v,(String)v.getTag());
//            }
//        }
    }
//        class FootViewHolder extends RecyclerView.ViewHolder{
//           private TextView item_recycle_foottv;
//            public FootViewHolder(View itemView) {
//                super(itemView);
//                item_recycle_foottv= (TextView) itemView.findViewById(R.id.item_recycle_foottv);
//                item_recycle_foottv.setText("加载更多");
//            }
//        }

   public interface OnRecycleItmeClickListener{
       void onItemClick(View view,String url);
    }
    private  OnRecycleItmeClickListener mOnItemClickListener = null;
    public void setOnItemClickListener( OnRecycleItmeClickListener listener) {
        this.mOnItemClickListener = listener;
    }





}
