package com.qxmagic.railwayengineerternimal.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/27 0027.
 * 各种详情适配器
 */

public class CommonDetailAdapter extends BaseRecyclerViewAdapter<CommonDetailBean> {
    public CommonDetailAdapter(List<CommonDetailBean> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_configuration_detail;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, CommonDetailBean item) {
//        LinearLayout mainView = (LinearLayout) holder.getView(R.id.configuration_detail_item_view);
//        if ("描述：".equals(item.key)) {
//            mainView.setOrientation(LinearLayout.VERTICAL);
//        } else {
//            mainView.setOrientation(LinearLayout.HORIZONTAL);
//        }
        holder.getTextView(R.id.item_key).setText(item.key);
        holder.getTextView(R.id.item_value).setText(item.value);
    }
}
