package com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 发布适配器
 */
public class PublishAdapter extends BaseRecyclerViewAdapter<PublishDetailBean> {
    private View mLeftLayout;
    private CheckBox mCheckBox;
    private CheckBoxpublishListener listener;

    private boolean isShowCheckBox;

    public PublishAdapter(List<PublishDetailBean> mList, Context mContext) {
        super(mList, mContext);
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
        notifyDataSetChanged();
    }

    public void setListener(CheckBoxpublishListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_publish;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, final PublishDetailBean item) {
        if(null==item){
            return;
        }
        mLeftLayout = holder.getView(R.id.publish_left_layout);
        mCheckBox = (CheckBox) holder.getView(R.id.publish_checkbox);
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
        holder.getTextView(R.id.publish_fault_type).setText(item.id);
        holder.getTextView(R.id.publish_number).setText(item.publishTitle);
        holder.getTextView(R.id.publish_organization).setText(item.organization);
        holder.getTextView(R.id.publish_start_time).setText(item.startTime);
        holder.getTextView(R.id.publish_service).setText(item.approver);
        holder.getTextView(R.id.publish_priority).setText(item.priority);
        holder.getTextView(R.id.publish_state).setText(item.publishState);
    }

    public interface CheckBoxpublishListener {
        void boxStateChange();
    }
}
