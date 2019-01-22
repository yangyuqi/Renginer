package com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 变更适配器
 */
public class ChangeAdapter extends BaseRecyclerViewAdapter<ChangeDetailBean> {
    private View mLeftLayout;
    private CheckBox mCheckBox;
    private CheckBoxChangeListener listener;

    private boolean isShowCheckBox;

    public ChangeAdapter(List<ChangeDetailBean> mList, Context mContext) {
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
        return R.layout.item_listview_change;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, final ChangeDetailBean item) {
        if (null == item) {
            return;
        }
        mLeftLayout = holder.getView(R.id.change_left_layout);
        mCheckBox = (CheckBox) holder.getView(R.id.change_checkbox);
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
        holder.getTextView(R.id.change_fault_type).setText(item.id);
        holder.getTextView(R.id.change_number).setText(item.changeTitle);
        holder.getTextView(R.id.change_type).setText(item.changeType);
        holder.getTextView(R.id.change_organization).setText(item.organization);
        holder.getTextView(R.id.change_start_time).setText(item.startTime);
        holder.getTextView(R.id.change_service).setText(item.approve);
//        holder.getTextView(R.id.change_state).setText(item.changeState);
    }

    public interface CheckBoxChangeListener {
        void boxStateChange();
    }
}
