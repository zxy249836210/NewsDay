package com.zxsc.newsday.adapter;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zxsc.newsday.R;
import java.util.List;


/**
 * Created by sunwei on 2015/12/4.
 * Email: lx_sunwei@163.com.
 * Description: recycleView 滑动到底部加载更多
 */
public abstract class BaseLoadingAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BaseLoadingAdapter";

    //是否正在加载
    public boolean mIsLoading = false;
    //正常条目
    private static final int TYPE_NORMAL_ITEM = 0;
    //加载条目
    private static final int TYPE_LOADING_ITEM = 1;
    //第一条
    private static final int TYPE_FIRST_ITEM=2;
    //加载viewHolder
    private LoadingViewHolder mLoadingViewHolder;
    //瀑布流
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private List<T> mTs;
    //首次进入
    private boolean mFirstEnter = true;
    private RecyclerView mRecyclerView;

    public BaseLoadingAdapter(RecyclerView recyclerView, List<T> ts) {

        mTs = ts;

        mRecyclerView = recyclerView;

        setSpanCount(recyclerView);

        //notifyLoading();
    }

    private OnLoadingListener mOnLoadingListener;

    /**
     * 加载更多接口
     */
    public interface OnLoadingListener {
        void loading();
    }

    /**
     * 设置监听接口
     *
     * @param onLoadingListener onLoadingListener
     */
    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        setScrollListener(mRecyclerView);
        mOnLoadingListener = onLoadingListener;
    }
    /**
     * 加载完成
     */
    public void setLoadingComplete() {
        if (mTs.size() > 0 && mTs.get(mTs.size()-1) == null) {
            mIsLoading = false;
            mTs.remove(mTs.size() - 1);
            notifyItemRemoved(mTs.size() - 1);
        }
    }

    /**
     * 没有更多数据
     */
    public void setLoadingNoMore() {
        mIsLoading = false;
        if (mLoadingViewHolder != null) {
            mLoadingViewHolder.progressBar.setVisibility(View.GONE);
            mLoadingViewHolder.tvLoading.setText("已加载完！");
        }
    }

    /**
     * 加载失败
     */
    public void setLoadingError() {
        if (mLoadingViewHolder != null) {
            mIsLoading = false;
            mLoadingViewHolder.progressBar.setVisibility(View.GONE);
            mLoadingViewHolder.tvLoading.setText("加载失败，点击重新加载！");

            mLoadingViewHolder.tvLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadingListener != null) {
                        mIsLoading = true;
                        mLoadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                        mLoadingViewHolder.tvLoading.setText("正在加载...");

                        mOnLoadingListener.loading();
                    }
                }
            });
        }
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    private boolean canScrollDown(RecyclerView recyclerView) {
        return ViewCompat.canScrollVertically(recyclerView, 1);
    }

    /**
     * 设置加载item占据一行
     *
     * @param recyclerView recycleView
     */
    private void setSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager == null) {
            Log.e(TAG, "LayoutManager 为空,请先设置 recycleView.setLayoutManager(...)");
        }

        //网格布局
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (type == TYPE_NORMAL_ITEM) {
                        return 1;
                    } else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            });
        }

        //瀑布流布局
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        }
    }

    /**
     * 显示加载
     */
    private void notifyLoading() {
        if (mTs.size() != 0 && mTs.get(mTs.size()-1) != null) {
            mTs.add(null);
            notifyItemInserted(mTs.size() - 1);
        }
    }

    /**
     * 监听滚动事件
     *
     * @param recyclerView recycleView
     */
    private void setScrollListener(RecyclerView recyclerView) {
        if(recyclerView == null) {
            Log.e(TAG, "recycleView 为空");
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!canScrollDown(recyclerView)) {

                    //首次进入不加载
                    if (!mIsLoading && !mFirstEnter) {

                        notifyLoading();

                        mIsLoading = true;

                        if (mLoadingViewHolder != null) {
                            mLoadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                            mLoadingViewHolder.tvLoading.setText("正在加载...");
                        }

                        if (mOnLoadingListener != null) {
                            mOnLoadingListener.loading();
                        }
                    }
                }

                if (mFirstEnter) {
                    mFirstEnter = false;
                }
            }
        });
    }

    /**
     * 创建viewHolder
     *
     * @param parent viewGroup
     * @return viewHolder
     */
    public abstract RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup parent);
    public abstract RecyclerView.ViewHolder onCreateHeadViewHolder(ViewGroup parent);
    /**
     * 绑定viewHolder
     *
     * @param holder   viewHolder
     * @param position position
     */
    public abstract void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position);
    public abstract void onBindHeadViewHolder(RecyclerView.ViewHolder holder, int position);
    /**
     * 加载布局
     */
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public TextView tvLoading;
        public RelativeLayout llyLoading;

        public LoadingViewHolder(View view) {
            super(view);

            progressBar = (ProgressBar) view.findViewById(R.id.progress_loading);
            tvLoading = (TextView) view.findViewById(R.id.tv_loading);
            llyLoading = (RelativeLayout) view.findViewById(R.id.lly_loading);
        }
    }


    @Override
    public int getItemViewType(int position) {
        T t = mTs.get(position);
        if (t == null) {
            return TYPE_LOADING_ITEM;
        } else if(position==0){
            return TYPE_FIRST_ITEM;
        }else {
            return TYPE_NORMAL_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL_ITEM) {
            return onCreateNormalViewHolder(parent);
        }else if(viewType==TYPE_FIRST_ITEM){
            return onCreateHeadViewHolder(parent);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recycle_foot, parent, false);
            mLoadingViewHolder = new LoadingViewHolder(view);
            return mLoadingViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_NORMAL_ITEM) {
            onBindNormalViewHolder(holder, position);
        } else if(type==TYPE_FIRST_ITEM){
            onBindHeadViewHolder(holder, position);

        }else {

            if (mStaggeredGridLayoutManager != null) {
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        new StaggeredGridLayoutManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);

                mLoadingViewHolder.llyLoading.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTs.size();
    }
}
