package com.titan.titv.settings.system.reset;

import android.content.Context;
import android.os.AsyncTask;

import com.titan.platformadapter.TvRestManagerAdapter;

public class ResetPresenter implements IResetContract.Presenter {
    private IResetContract.View mView;
    private Context mContext;
    private TvRestManagerAdapter mTvRestManagerAdapter;
    private AsyncTask<String, Void, Boolean> mAsyncTask = null;
    private final String SETTING_PACKAGE = "com.titan.titv.settings";


    public ResetPresenter(IResetContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
        this.mTvRestManagerAdapter = new TvRestManagerAdapter(context);
    }

    @Override
    public String onFactoryReset() {

        mAsyncTask = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                mView.showProgressDialog();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(String... params) {
                mTvRestManagerAdapter.onFactoryReset();
                return Boolean.TRUE;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                mView.dismissProgressDialog();
                super.onPostExecute(aBoolean);
            }
        };
        mAsyncTask.execute();
        return null;
    }
 
}
