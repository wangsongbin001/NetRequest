package com.innotech.mydemo.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innotech.mydemo.R;
import com.innotech.mydemo.main.base.V2BaseFragment;
import com.innotech.mydemo.main.model.NewsItem;
import com.innotech.mydemo.widget.baserv.CommonAdapter;
import com.innotech.mydemo.widget.baserv.RBaseAdapter;
import com.innotech.mydemo.widget.baserv.RMultiItemTypeAdapter;
import com.innotech.mydemo.widget.baserv.RViewHolder;
import com.innotech.mydemo.widget.baserv.base.ViewHolder;
import com.innotech.netrequest.util.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author wang
 * 1,使用SmartSwipeLayout（https://github.com/scwang90/SmartRefreshLayout/tree/master）
 * 1,1 配置grale中配置
 * 1.2 Application中设置全局样式
 * 2,封装RecyclerLayout
 * 3,
 */
public class V2NewsFragment extends V2BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private List<NewsItem> mDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_news, null);
        ButterKnife.bind(this, view);
        initViews();
        refreshLayout.autoRefresh(300);
        return view;
    }

    private void initViews() {
        mDatas = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(3000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(3000);
            }
        });
    }

    //刷新数据
    private void refresh() {
        mDatas.clear();
        for (int i = 0; i < 10; i++) {
            mDatas.add(new NewsItem("title-" + i,
                    "可以看到我们这里通过viewholder根据控件的id拿到控件，然后再进行数据绑定和事件操作，我们还能做些什么简化呢？",
                    "1992-02-08"));
        }
//        recyclerView.setAdapter(new RBaseAdapter<NewsItem>(mActivity, mDatas, R.layout.n_rv_item) {
//            @Override
//            public void convert(RViewHolder holder, NewsItem item, int position) {
//                holder.setText(R.id.tv_title, item.title);
//                holder.setText(R.id.tv_tips, item.tips);
//                holder.setText(R.id.tv_time, item.time);
//            }
//        });
        recyclerView.setAdapter(new RMultiItemTypeAdapter<NewsItem>(mActivity, mDatas,
                new RMultiItemTypeAdapter.RMultiItemTypeInterface() {
                    @Override
                    public int getItemViewType(Object item, int position) {
                        if(0 == position || 4 == position){
                            return 1;
                        }
                        return 0;
                    }

                    @Override
                    public int getLayoutId(int viewType) {
                        if(0 == viewType){
                            return R.layout.n_rv_item;
                        }else{
                            return R.layout.p_list_item;
                        }
                    }
                }) {
            @Override
            public void convert(RViewHolder holder, NewsItem item, int position) {
                if(1 == getItemViewType(position)){
                    holder.setText(R.id.tv_title, item.tips);
                }else{
                    holder.setText(R.id.tv_title, item.title);
                    holder.setText(R.id.tv_tips, item.tips);
                    holder.setText(R.id.tv_time, item.time);
                }
            }
        });
    }


    //加载更多
    private void loadMore() {

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
