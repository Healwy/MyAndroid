package com.titan.titv.settings.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.titv.settings.R;

import java.util.ArrayList;

public class SettingCategory extends RelativeLayout {
    ViewGroup mContentHolder;
    TextView mTitle;

    public SettingCategory(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SettingCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingCategory(Context context) {
        super(context);
    }

    public static SettingCategory createNewCategory(ViewGroup parent) {
        ViewGroup v = (ViewGroup) inflate(parent.getContext(), R.layout.titv_settings_category, parent);
        return (SettingCategory) v.getChildAt(v.getChildCount() - 1);
    }

    public void addItem(SettingItem item, int height, int width) {
        ViewGroup contentHolder = getContentHolder();
        LayoutParams lp = new LayoutParams(width, height);
        lp.addRule(17);
        contentHolder.addView(item, lp);
    }

    public void addItem(SettingItem item) {
        getContentHolder().addView(item, new LayoutParams(getResources().getDimensionPixelSize(R.dimen.setting_item_width), getResources().getDimensionPixelSize(R.dimen.setting_item_height)));
    }

    public void removeItem(SettingItem item) {
        getContentHolder().removeView(item);
    }

    public void removeAllItems() {
        getContentHolder().removeAllViews();
    }

    public void setTitle(String text) {
        if (this.mTitle == null) {
            this.mTitle = (TextView) findViewById(R.id.categoryTitle);
        }
        this.mTitle.setText(text);
        if (text == null || text.isEmpty()) {
            this.mTitle.setVisibility(8);
        } else {
            this.mTitle.setVisibility(0);
        }
    }

    public ViewGroup getContentHolder() {
        if (this.mContentHolder == null) {
            this.mContentHolder = (ViewGroup) findViewById(R.id.categoryContentHolder);
        }
        return this.mContentHolder;
    }

    public void refreshItem() {
        ViewGroup groupList = getContentHolder();
        int itemCount = groupList.getChildCount();
        ArrayList<SettingItem> list = new ArrayList();
        for (int i = 0; i < itemCount; i++) {
            View view = groupList.getChildAt(i);
            if ((view instanceof SettingItem) && view.getVisibility() == 0) {
                list.add((SettingItem) view);
            }
        }
        int size = list.size();
        int j = 0;
        while (j < size) {
            if (j == 0 && j == size - 1) {
                ((SettingItem) list.get(j)).setBgDrawable(3);
            } else if (j == 0) {
                ((SettingItem) list.get(j)).setBgDrawable(0);
            } else if (j == size - 1) {
                ((SettingItem) list.get(j)).setBgDrawable(2);
            } else {
                ((SettingItem) list.get(j)).setBgDrawable(1);
            }
            j++;
        }
    }
}
