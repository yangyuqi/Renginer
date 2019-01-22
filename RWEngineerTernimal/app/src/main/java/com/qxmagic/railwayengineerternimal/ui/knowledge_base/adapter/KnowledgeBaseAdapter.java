package com.qxmagic.railwayengineerternimal.ui.knowledge_base.adapter;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Christian on 2017/3/27 0027.
 * 知识库适配器
 */

public class KnowledgeBaseAdapter extends BaseRecyclerViewAdapter<KnowledgeBean> {
    public KnowledgeBaseAdapter(List<KnowledgeBean> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_listview_knowledge_base;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, KnowledgeBean item) {
        holder.getTextView(R.id.knowledge_base_item_title).setText(item.knowledgeTitle);
        holder.getTextView(R.id.knowledge_item_username_text).setText(item.knowledgeUser);
        holder.getTextView(R.id.knowledge_item_type_text).setText(item.knowledgeType);
        holder.getTextView(R.id.knowledge_item_storage_time_text).setText(item.knowledgeStorageTime);
        holder.getTextView(R.id.knowledge_item_number_text).setText(item.relateQuestion);
    }
}
