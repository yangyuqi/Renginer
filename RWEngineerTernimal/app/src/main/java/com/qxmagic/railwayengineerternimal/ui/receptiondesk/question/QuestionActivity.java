package com.qxmagic.railwayengineerternimal.ui.receptiondesk.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionFragment;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

/**
 * Created by Christian on 2017/3/30 0030.
 * 问题界面
 */

public class QuestionActivity extends BaseActivity<IBasePresenter> implements IBaseView {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_question;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "问题", 0, false, true);
        Bundle bundle = new Bundle();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.question_content,
                        Fragment.instantiate(this,
                                QuestionFragment.class.getName(),
                                bundle))
                .commit();
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }
}
