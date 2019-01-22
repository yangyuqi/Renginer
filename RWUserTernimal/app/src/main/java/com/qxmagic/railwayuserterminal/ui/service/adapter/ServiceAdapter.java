package com.qxmagic.railwayuserterminal.ui.service.adapter;

import android.content.Context;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/17 0017.
 * 服务合同列表适配器
 */

public class ServiceAdapter extends BaseRecyclerViewAdapter<ContractDetailBean> {

    public ServiceAdapter(List<ContractDetailBean> mList, Context mContext) {
        super(mList, mContext);
    }


    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_service;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, ContractDetailBean item) {
        if (null == item) {
            return;
        }
        holder.getTextView(R.id.item_service_content).setText(item.title);
    }

}
