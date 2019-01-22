package com.qxmagic.railwayengineerternimal.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.PopWindowAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.callback.PopWindowChoiceItemCallback;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/22 0022.
 * 下拉列表pop window
 */
public class RepairPullDownPop extends MyPopupWindow {
    private PopWindowChoiceItemCallback callback;
    private RecyclerView mRecyclerView;
    private PopWindowAdapter mAdapter;
    private ArrayList<String> mList = new ArrayList<>();
    private int position = -1;

    /**
     * <默认构造函数>
     *
     * @param mContext 上下文
     * @param callback item点击回调
     */
    public RepairPullDownPop(Context mContext, ArrayList<String> mList, int width, PopWindowChoiceItemCallback callback) {
        super(mContext, R.layout.common_popwindow_layout);
        if (!ListUtil.isEmpty(mList)) {
            this.mList.clear();
            this.mList.addAll(mList);
            String cancel = "取消";
            this.mList.add(cancel);
        }
        this.callback = callback;
        initSharePopupWindow(width);
        initViews();
        bindListener();
    }

    @Override
    public void initViews() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.pop_recyclerView);
        mAdapter = new PopWindowAdapter(mList, mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setSelectedPosition(int position) {
        if (null != mAdapter) {
            this.position = position;
            mAdapter.setPosition(position);
        }
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void bindListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {

                if (pos == mList.size() - 1) {
                    callback.getChoiceItem(pos, "");
                    dismissWindow();
                } else {
                    if (null != callback) {
                        callback.getChoiceItem(pos, (String) item);
                    }
                    dismissWindow();
                }
            }
        });
    }
}
