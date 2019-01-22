package com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 问题适配器
 */
public class QuestionAdapter extends BaseRecyclerViewAdapter<QuestionDetailBean> {
    private View mLeftLayout;
    private CheckBox mCheckBox;
    private CheckBoxChangeListener listener;

    private boolean isShowCheckBox;

    public QuestionAdapter(List<QuestionDetailBean> mList, Context mContext) {
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
        return R.layout.item_listview_question;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, final QuestionDetailBean item) {
        if(item==null){
            return;
        }
        mLeftLayout = holder.getView(R.id.question_left_layout);
        mCheckBox = (CheckBox) holder.getView(R.id.question_checkbox);
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
        holder.getTextView(R.id.question_type).setText(item.id);
        holder.getTextView(R.id.question_number).setText(item.questionTitle);
        holder.getTextView(R.id.question_organization).setText(item.organization);
        holder.getTextView(R.id.question_start_time).setText(item.startTime);
        holder.getTextView(R.id.question_service).setText(item.serviceType);
        holder.getTextView(R.id.question_priority).setText(item.urgencyLevel);
        holder.getTextView(R.id.question_state).setText(item.questionState);
    }

    public interface CheckBoxChangeListener {
        void boxStateChange();
    }
}
