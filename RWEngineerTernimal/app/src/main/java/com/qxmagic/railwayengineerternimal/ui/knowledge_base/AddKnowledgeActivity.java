package com.qxmagic.railwayengineerternimal.ui.knowledge_base;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerAddKnowledgeComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.AddKnowledgeModule;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IAddKnowledgePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IAddKnowledgeView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.callback.PopWindowChoiceItemCallback;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;
import com.qxmagic.railwayengineerternimal.widget.RepairPullDownPop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加到知识库
 */
public class AddKnowledgeActivity extends BaseActivity<IAddKnowledgePresenter> implements IAddKnowledgeView {
    @BindView(R.id.new_knowledge_title_edit)
    EditText mTitle;
    @BindView(R.id.new_knowledge_author_text)
    TextView mAuthor;
    @BindView(R.id.new_knowledge_date_text)
    TextView mCreateTime;
    @BindView(R.id.new_knowledge_question_pop)
    View mRelatePopView;
    @BindView(R.id.new_knowledge_question_pop_text)
    TextView mPopRelateQuestion;
    @BindView(R.id.new_knowledge_question_text)
    TextView mRelateQuestion;
    @BindView(R.id.new_knowledge_type_text)
    TextView mKnowledgeType;

    @BindView(R.id.custom_content_editText)
    EditText mDescription;

    private ArrayList<String> relateQuestionList = new ArrayList<>();
    private String relateQuestion, knowledgeType;

    private ArrayList<String> knowledgeTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
        initInjector();
        initViews();
        updateViews();
    }

    private void _dealIntent(Intent intent) {
        if (null != intent) {
            relateQuestion = intent.getStringExtra("relateQuestion");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_knowledge;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, getResources().getString(R.string.add_knowledge), 0, false, true);
        if (null != RWETApplication.getInstance().mLoginUser) {
            mAuthor.setText(RWETApplication.getInstance().mLoginUser.sponsor);
        }
        hideSoftInputFromWindow(mTitle);
        hideSoftInputFromWindow(mDescription);
        if (TextUtils.isEmpty(relateQuestion)) {
            mRelatePopView.setVisibility(View.VISIBLE);
            mRelateQuestion.setVisibility(View.GONE);
        } else {
            mRelatePopView.setVisibility(View.GONE);
            mRelateQuestion.setVisibility(View.VISIBLE);
            mRelateQuestion.setText(relateQuestion);
        }
    }

    @Override
    protected void updateViews() {
        mPresenter.getQuestionList();
        mPresenter.getType();
    }

    @Override
    protected void initInjector() {
        DaggerAddKnowledgeComponent.builder().applicationComponent(
                getAppComponent()).addKnowledgeModule(new AddKnowledgeModule(this, mContext)).
                build().inject(this);
    }

    @Override
    public void getReturnResult(String result) {
        //根据返回结果执行相应操作
        if ("okay".equals(result)) {
            showToast("添加知识库成功");
            setResult(RESULT_OK);
            finish();
        } else {
            showToast(result);
        }
    }

    @Override
    public void getRelateQuestionList(ArrayList<String> list) {
        relateQuestionList.clear();
        if (!ListUtil.isEmpty(list)) {
            relateQuestionList.addAll(list);
        }
    }

    @Override
    public void getKnowledgeTypeList(ArrayList<String> list) {
        knowledgeTypeList.clear();
        if (!ListUtil.isEmpty(list)) {
            knowledgeTypeList.addAll(list);
        }
    }


    private void showDatePicker(final TextView editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                editText.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick({R.id.new_knowledge_date_img, R.id.new_knowledge_question_pop, R.id.new_knowledge_type_pop, R.id.add_to_knowledge_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_knowledge_date_img:
                //弹出日期对话框
                showDatePicker(mCreateTime);
                break;
            case R.id.new_knowledge_question_pop:
                RepairPullDownPop mPop = new RepairPullDownPop(mContext, relateQuestionList,
                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
                                relateQuestion = content;
                                if (TextUtils.isEmpty(content))
                                    mPopRelateQuestion.setText("请选择");
                                else
                                    mPopRelateQuestion.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_knowledge_question_pop));
                break;
            case R.id.new_knowledge_type_pop:
                mPop = new RepairPullDownPop(mContext, knowledgeTypeList,
                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
                        new PopWindowChoiceItemCallback() {
                            @Override

                            public void getChoiceItem(int position, String content) {
                                knowledgeType = content;
                                if (TextUtils.isEmpty(content))
                                    mKnowledgeType.setText("请选择");
                                else
                                    mKnowledgeType.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_knowledge_type_pop));
                break;
            case R.id.add_to_knowledge_confirm:
                String title = mTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    showToast("请输入标题");
                    return;
                }
                String description = mDescription.getText().toString().trim();
                if (TextUtils.isEmpty(description)) {
                    showToast("请输入描述");
                    return;
                }
                if (TextUtils.isEmpty(mCreateTime.getText().toString())) {
                    showToast("请选择创建时间");
                    return;
                }
                HashMap<String, String> params = RequestUtil.getCommonParams();
                params.put("knowName", title);
                params.put("createPerson", RWETApplication.getInstance().mLoginUser.sponsor);
                params.put("ctTime", mCreateTime.getText().toString());
                params.put("relatedProblem", relateQuestion);
                params.put("knowType", knowledgeType);
                params.put("knowDes", mDescription.getText().toString().trim());
                mPresenter.addToKnowledge(params);
                break;
            default:
                break;
        }
    }
}
