package com.titan.settings.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.titan.settings.R;

public class SmallSwitcherItem extends SwitcherItem {
    private static final String TAG = "SmallSwitcherItem";
    private int FOCUS_TEXT_SIZE = 18;
    private int NORMAL_TEXT_SIZE = 15;
    boolean longPress = false;
    private boolean mEnterShow = false;
    private EnterListener mListener;
    private boolean mLongPressEnable = false;
    private LongPressListener mLongPressListener;
    private TextView mTitle;
    private TextView mValue;

    public interface EnterListener {
        void onEnter();
    }

    public interface LongPressListener {
        void onLongPress();
    }

    public void setLongPressListener(LongPressListener listener) {
        this.mLongPressListener = listener;
    }

    public void setEnterListener(EnterListener listener) {
        this.mListener = listener;
    }

    public SmallSwitcherItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SmallSwitcherItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallSwitcherItem(Context context) {
        super(context);
    }

    public static SmallSwitcherItem createItem(Context context, ViewGroup parent) {
        ViewGroup group;
        if (parent instanceof SettingCategory) {
            group = ((SettingCategory) parent).getContentHolder();
        } else {
            group = parent;
        }
        View out = inflate(context, R.layout.small_switcher_content, group);
        if (group == null) {
            return (SmallSwitcherItem) out;
        }
        return (SmallSwitcherItem) group.getChildAt(group.getChildCount() - 1);
    }

    public void setTextSize(int focusSize, int normaleSize) {
        this.FOCUS_TEXT_SIZE = focusSize;
        this.NORMAL_TEXT_SIZE = normaleSize;
    }

    public void setEnterShow(boolean show) {
        this.mEnterShow = show;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mValue = (TextView) findViewById(R.id.tv_value);
        this.mTitle = (TextView) findViewById(R.id.tv_key);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown keyCode=" + keyCode + ", repeat=" + event.getRepeatCount());
        if ((keyCode == 66 || keyCode == 23) && event.getRepeatCount() == 0 && !this.mEnterShow) {
            if (this.mListener != null) {
                this.mListener.onEnter();
            }
            return true;
        } else if (keyCode != 22 || !this.mLongPressEnable) {
            return super.onKeyDown(keyCode, event);
        } else {
            if (event.getRepeatCount() >= 20) {
                this.longPress = true;
            }
            return true;
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 22 && this.mLongPressEnable) {
            if (!this.longPress) {
                super.right();
            } else if (this.mLongPressListener != null) {
                this.mLongPressListener.onLongPress();
                this.longPress = false;
            } else {
                super.right();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public void setLongPressEnable(boolean enable) {
        this.mLongPressEnable = enable;
    }

    public void onSelecterEnter() {
        super.onSelecterEnter();
    }

    public void onSelectorLeave() {
        super.onSelectorLeave();
    }
}
