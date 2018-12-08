package com.titan.titv.settings.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.titv.settings.R;

public class SmallSettingItem extends SettingItem {
    private static final String TAG = "SmallSettingItem";
    private int FOCUS_TEXT_SIZE = 18;
    private int NORMAL_TEXT_SIZE = 15;
    private boolean mEnterShow = false;
    private EnterListener mListener;
    private TextView mTitle;
    private TextView mValue;

    public interface EnterListener {
        void onEnter();
    }

    public void setEnterListener(EnterListener listener) {
        this.mListener = listener;
    }

    public SmallSettingItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SmallSettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallSettingItem(Context context) {
        super(context);
    }

    public static SmallSettingItem createItem(Context context, ViewGroup parent) {
        ViewGroup group;
        if (parent instanceof SettingCategory) {
            group = ((SettingCategory) parent).getContentHolder();
        } else {
            group = parent;
        }
        View out = inflate(context, R.layout.small_titv_setting_item, group);
        if (group == null) {
            return (SmallSettingItem) out;
        }
        return (SmallSettingItem) group.getChildAt(group.getChildCount() - 1);
    }

    public void setTextSize(int focusSize, int normaleSize) {
        this.FOCUS_TEXT_SIZE = focusSize;
        this.NORMAL_TEXT_SIZE = normaleSize;
    }

    public void setEnterShow(boolean show) {
        this.mEnterShow = show;
    }

    public void onSelecterEnter() {
        super.onSelecterEnter();
    }

    public void onSelectorLeave() {
        super.onSelectorLeave();
    }
}
