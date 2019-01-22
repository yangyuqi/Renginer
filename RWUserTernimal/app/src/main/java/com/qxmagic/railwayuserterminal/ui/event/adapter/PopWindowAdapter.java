package com.qxmagic.railwayuserterminal.ui.event.adapter;

import android.content.Context;
import android.view.View;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/22 0022.
 * pop window下拉弹窗适配器
 */

public class PopWindowAdapter extends BaseRecyclerViewAdapter<String> {
    private int position = -1;
    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    public PopWindowAdapter(List<String> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_popwindow;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, String item) {
        View mMain = holder.getView(R.id.item_listView_pop_layout);
        int index=getPosition();
        if (position == index) {
            mMain.setSelected(true);
        } else {
            mMain.setSelected(false);
        }
        holder.getTextView(R.id.item_listView_pop_text).setText(item);
    }
}
