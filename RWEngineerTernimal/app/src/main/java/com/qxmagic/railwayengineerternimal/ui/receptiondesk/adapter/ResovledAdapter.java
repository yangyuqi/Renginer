package com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 解决事件适配器
 */
public class ResovledAdapter extends BaseRecyclerViewAdapter<EventDetailBean> {
    private View mLeftLayout;
    private CheckBox mCheckBox;
    private CheckBoxChangeListener listener;

    private boolean isShowCheckBox;

    public ResovledAdapter(List<EventDetailBean> mList, Context mContext) {
        super(mList, mContext);
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
        notifyDataSetChanged();
    }

    public void setListener(CheckBoxChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_resolved;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, final EventDetailBean item) {
        if (item == null) {
            return;
        }
        mLeftLayout = holder.getView(R.id.event_left_layout);
        mCheckBox = (CheckBox) holder.getView(R.id.event_checkbox);
        if (isShowCheckBox) {
            mLeftLayout.setVisibility(View.VISIBLE);
        } else {
            mLeftLayout.setVisibility(View.GONE);
        }
        mCheckBox.setChecked(item.check);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.check = b;
                if (null != listener) {
                    listener.boxStateChange();
                }
            }
        });
        holder.getTextView(R.id.resolved_event_type).setText(item.id);
        holder.getTextView(R.id.resolved_event_number).setText(item.faultType);
//        holder.getTextView(R.id.resolved_fault_type).setText(item.faultType);
        holder.getTextView(R.id.resolved_service_type).setText(item.serviceType);
        holder.getTextView(R.id.resolved_start_time).setText(item.startTime);
        holder.getTextView(R.id.resolved_urgency_level).setText(item.urgencyLevel);
        holder.getTextView(R.id.resolved_appointed_person).setText(item.appointedPerson);
    }

    public interface CheckBoxChangeListener {
        void boxStateChange();
    }
}
