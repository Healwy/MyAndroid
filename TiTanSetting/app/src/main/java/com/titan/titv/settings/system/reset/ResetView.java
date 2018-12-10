package com.titan.titv.settings.system.reset;

import android.app.Application;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.base.BaseView;

import java.util.List;

public class ResetView extends BaseView implements IResetContract.View {
    private final ResetPresenter mPresenter;
    private TextView mTitleFirst;
    private Button btnCancel;
    private Button btnOK;
    private List<BaseModel> mData;
    private LinearLayout mProgressLL;
    private RelativeLayout mLL;
    private TextView mTextView;

    public ResetView(Application app) {
        super(app);
        mPresenter = new ResetPresenter(this, mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_system_reset;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.system_restore);
        this.mProgressLL = (LinearLayout) mContentView.findViewById(R.id.progress_view);
        this.mTextView = (TextView) mContentView.findViewById(R.id.textview);
        this.mLL = (RelativeLayout) mContentView.findViewById(R.id.relativelayout);
        this.btnCancel = (Button) findViewById(R.id.btn_cancel);
        this.btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.btnOK = (Button) findViewById(R.id.btn_ok);
        this.btnOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onFactoryReset();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initFocus() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void showProgressDialog() {
        mProgressLL.setVisibility(View.VISIBLE);
        mLL.setVisibility(View.INVISIBLE);
        mTextView.setText("");
    }

    @Override
    public void dismissProgressDialog() {
        mProgressLL.setVisibility(View.GONE);
    }
}
