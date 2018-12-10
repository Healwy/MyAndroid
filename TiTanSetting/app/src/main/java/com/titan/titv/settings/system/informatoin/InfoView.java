package com.titan.titv.settings.system.informatoin;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.titan.titv.settings.R;
import com.titan.titv.settings.base.BaseModel;
import com.titan.titv.settings.base.BaseView;

import java.util.List;

public class InfoView extends BaseView implements IInfoContract.View {

    private final InfoPresenter mPresenter;
    private TextView mTitleFirst;
    private RecyclerView mRecyclerView;
    private InfoAdapter mAdapter;
    private List<BaseModel> mData;

    public InfoView(Application app) {
        super(app);
        mPresenter = new InfoPresenter(this, mContext);
    }

    @Override
    protected int getViewResId() {
        return R.layout.view_system_info;
    }

    @Override
    public void onDataLoaded(List<BaseModel> data) {
        mData = data;
    }

    @Override
    public void onCreate() {
        this.mTitleFirst = (TextView) mContentView.findViewById(R.id.title_first);
        this.mTitleFirst.setText(R.string.system_information);
        this.mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recycler_view);
        initData();
        this.mAdapter = new InfoAdapter(mContext, mData);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.requestFocusFromTouch();
    }

    @Override
    protected void initData() {
        mPresenter.loadData();
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

}
