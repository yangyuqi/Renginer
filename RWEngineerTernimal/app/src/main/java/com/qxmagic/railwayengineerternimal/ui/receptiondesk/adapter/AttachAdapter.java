package com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/4/1 0001.
 * 附件适配器
 */

public class AttachAdapter extends BaseRecyclerViewAdapter<String> {
    public AttachAdapter(List<String> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_gridview_attach;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, String item) {
        holder.getTextView(R.id.text1).setText(item);
    }
}
