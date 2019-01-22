package com.qxmagic.railwayengineerternimal.ui.configurationitems.adapter;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项列表适配器
 */

public class ConfigurationListAdapter extends BaseRecyclerViewAdapter<ConfigurationBean> {
    public ConfigurationListAdapter(List<ConfigurationBean> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_configuration_list;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, ConfigurationBean item) {
        holder.getTextView(R.id.configuration_item_title).setText(item.configurationName);
        holder.getTextView(R.id.configuration_item_type_text).setText(item.type);
        holder.getTextView(R.id.configuration_item_org_text).setText(item.organization);
        holder.getTextView(R.id.configuration_item_priority_text).setText(item.priority);
        holder.getTextView(R.id.configuration_item_startTime_text).setText(item.startUseTime);
    }
}
