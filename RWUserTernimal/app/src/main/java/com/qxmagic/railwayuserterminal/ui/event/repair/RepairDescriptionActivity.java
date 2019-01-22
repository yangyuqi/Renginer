package com.qxmagic.railwayuserterminal.ui.event.repair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IBaseView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;

import butterknife.BindView;

/**
 * 报修描述界面
 */
public class RepairDescriptionActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.repair_description_edit)
    EditText mDescription;

    private String description;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            description = mDescription.getText().toString().trim();
            if (TextUtils.isEmpty(description)) {
                showToast("请输入描述");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("description", description);
            setResult(RESULT_OK,intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateViews();
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_repair_description;
    }

    @Override
    protected void initViews() {
        description = getIntent().getStringExtra("description");
        CommonTitleUtil.initCommonTitle(this, "报修描述", "保存", false, false, listener);
        hideSoftInputFromWindow(mDescription);
        mDescription.setText(description);
        if (!TextUtils.isEmpty(description)) {
            mDescription.setSelection(description.length());
        }
    }

    @Override
    protected void updateViews() {
    }

    @Override
    protected void initInjector() {

    }
}
