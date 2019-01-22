package com.qxmagic.railwayengineerternimal.ui.configurationitems;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerConfigurationComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ConfigurationModule;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.iview.IConfigurationView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项fragment
 */
public class ConfigurationFragment extends BaseFragment<IBasePresenter> implements IConfigurationView {
    @BindView(R.id.configuration_items_count)
    TextView mConfigurationCount;

    @Override
    public void getConfigurationCount(String count) {
        if (TextUtils.isEmpty(count)) {
            mConfigurationCount.setText("共0个配置项");
        } else {
            String countText = "共" + count + "个配置项";
            SpannableStringBuilder builder = new SpannableStringBuilder(countText);
            builder.setSpan(new ForegroundColorSpan(Color.RED),
                    countText.indexOf(count),
                    countText.indexOf(count) + count.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mConfigurationCount.setText(builder);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_configuration_items;
    }

    @Override
    protected void initInjector() {
        DaggerConfigurationComponent.builder().applicationComponent(getAppComponent()).configurationModule(new ConfigurationModule(this, mContext)).build().inject(this);
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initFragmentTitle(mRootView, "配置项", R.mipmap.common_back_icon, 0, null, null, true, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    protected void updateViews() {
    }

    @OnClick(R.id.configuration_items_view)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.configuration_items_view:
                //跳转至配置项列表
                startActivity(new Intent(mContext, ConfigurationListActivity.class));
                break;
            default:
                break;
        }
    }

}
