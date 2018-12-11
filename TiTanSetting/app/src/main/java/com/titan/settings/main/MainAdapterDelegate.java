package com.titan.settings.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.base.BaseModel;
import com.titan.settings.base.OnSettingItemClickListener;
import com.titan.settings.base.adapterdelegates.AbsListItemAdapterDelegate;
import com.titan.settings.model.MainModel;

import java.util.List;


public class MainAdapterDelegate extends AbsListItemAdapterDelegate<MainModel, BaseModel,
        MainAdapterDelegate.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private OnSettingItemClickListener mListener;

    public MainAdapterDelegate(Context context, OnSettingItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(BaseModel item, List<BaseModel> items, int position) {
        return item instanceof MainModel;
    }

    @Override
    protected CategoryViewHolder onCreateViewHolder(ViewGroup parent) {
        return new CategoryViewHolder(
                mInflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    protected void onBindViewHolder(final MainModel item, final CategoryViewHolder viewHolder, final int pos) {
        viewHolder.name.setText(item.getMainName());
        viewHolder.icon.setImageResource(item.getIconResNormal());

        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                viewHolder.name.setSelected(hasFocus);
                if (hasFocus) {
                    viewHolder.itemView.setBackgroundResource(R.drawable.common_bg_focus);
                    viewHolder.icon.setImageResource(item.getIconResSelected());
                    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(viewHolder.itemView, "ScaleX", new float[]{0.86f, 1.0f}).setDuration(300);
                    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(viewHolder.itemView, "ScaleY", new float[]{0.86f, 1.0f}).setDuration(300);
                    localObjectAnimator1.setInterpolator(new OvershootInterpolator());
                    localObjectAnimator2.setInterpolator(new OvershootInterpolator());
                    AnimatorSet mFocusAnimator = new AnimatorSet();
                    mFocusAnimator.playTogether(new Animator[]{localObjectAnimator1, localObjectAnimator2});
                    mFocusAnimator.start();

                } else {
                    viewHolder.itemView.setBackgroundResource(0);
                    viewHolder.icon.setImageResource(item.getIconResNormal());
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSettingItemClick(item.getSettingType(), "", pos);
            }
        });
    }

    @Override
    protected void onBindViewHolder(MainModel item, CategoryViewHolder viewHolder, int pos, List<Object> payloads) {
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;

        CategoryViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
