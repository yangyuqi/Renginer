package com.qxmagic.railwayuserterminal.ui.event.adapter;

import android.content.Context;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 未解决事件适配器
 */

public class UnresovledAdapter extends BaseRecyclerViewAdapter<EventDetailBean> {
    public UnresovledAdapter(List<EventDetailBean> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_unresolved;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, EventDetailBean item) {
        if (item == null) {
            return;
        }
        holder.getTextView(R.id.unresolved_event_type).setText(item.id);
        holder.getTextView(R.id.unresolved_fault_type).setText(item.faultType);
        holder.getTextView(R.id.unresolved_service_type).setText(item.eventState);
        holder.getTextView(R.id.unresolved_start_time).setText(item.startTime);
        holder.getTextView(R.id.unresolved_urgency_level).setText(item.urgencyLevel);
        holder.getTextView(R.id.unresolved_appointed_person).setText(item.dealMan);
    }
}
