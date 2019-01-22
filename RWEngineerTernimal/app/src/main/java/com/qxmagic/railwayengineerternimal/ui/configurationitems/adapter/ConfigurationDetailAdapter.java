package com.qxmagic.railwayengineerternimal.ui.configurationitems.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项详情适配器
 */

public class ConfigurationDetailAdapter extends BaseRecyclerViewAdapter<ConfigurationBean> {
    public ConfigurationDetailAdapter(List<ConfigurationBean> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_configuration_detail;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, ConfigurationBean item) {
        LinearLayout mainView = (LinearLayout) holder.getView(R.id.configuration_detail_item_view);
        if ("描述：".equals(item.key)) {
            mainView.setOrientation(LinearLayout.VERTICAL);
        } else {
            mainView.setOrientation(LinearLayout.HORIZONTAL);
        }
        holder.getTextView(R.id.item_key).setText(item.key);
        holder.getTextView(R.id.item_value).setText(item.value);
    }
}
