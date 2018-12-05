package com.titan.titv.settings.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.titv.settings.R;
import com.titan.titv.settings.widgets.TvSelector.Selectable;

public class SettingItem extends RelativeLayout implements Selectable {
    public static final int BOTTOM = 2;
    public static final int CORNER = 3;
    public static final int MID = 1;
    private static final String TAG = "SettingItem";
    public static final int TOP = 0;
    private ImageView bgView;
    private boolean keepValueColor = false;
    private TextView mDesc;
    private String mDescText;
    private OnClickListener mOnClickListener;
    private ImageView mRightArrow;
    private boolean mSmall = false;
    private TextView mTitle;
    private String mTitleText;
    private TextView mValue;

    public SettingItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingItem(Context context) {
        super(context);
    }

    public SettingItem(Context context, boolean useDefaultLayout) {
        super(context);
        if (useDefaultLayout) {
            inflate(context, R.layout.titv_setting_item_content, this);
            setFocusable(true);
            setFocusableInTouchMode(true);
        }
    }

    public static SettingItem createItem(Context context) {
        return createItem(context, null);
    }

    public static SettingItem createItem(ViewGroup parent) {
        return createItem(parent.getContext(), parent);
    }

    public static SettingItem createItem(Context context, ViewGroup parent) {
        ViewGroup group;
        if (parent instanceof SettingCategory) {
            group = ((SettingCategory) parent).getContentHolder();
        } else {
            group = parent;
        }
        View out = inflate(context, R.layout.titv_setting_item, group);
        if (group == null) {
            return (SettingItem) out;
        }
        return (SettingItem) group.getChildAt(group.getChildCount() - 1);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.bgView = (ImageView) findViewById(R.id.bg);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTitle = (TextView) findViewById(R.id.tv_key);
        this.mDesc = (TextView) findViewById(R.id.tv_key_desc);
        this.mValue = (TextView) findViewById(R.id.tv_value);
        this.bgView = (ImageView) findViewById(R.id.bg);
        setOnClickListener(this.mOnClickListener);
    }

    public void setEnabled(boolean enabled, boolean focusable) {
        super.setEnabled(enabled);
        setFocusable(focusable);
        setFocusableInTouchMode(focusable);
    }

    public void setTitle(int id) {
        setTitle(getResources().getString(id));
    }

    public void setTitleAndColor(int id, int color) {
        setTitleAndColor(getResources().getString(id), color);
    }

    public void setTitle(String text) {
        if (this.mTitle == null) {
            this.mTitle = (TextView) findViewById(R.id.tv_key);
        }
        this.mTitle.setText(text);
        this.mTitleText = text;
    }

    public void setTitleAndColor(String text, int color) {
        if (this.mTitle == null) {
            this.mTitle = (TextView) findViewById(R.id.tv_key);
        }
        this.mTitle.setText(text);
        this.mTitle.setTextColor(color);
        this.mTitleText = text;
    }

    public String gitTitleText() {
        return this.mTitleText;
    }

    public void setDesc(int id) {
        setDesc(getResources().getString(id));
    }

    public void setDesc(String text) {
        if (this.mDesc == null) {
            this.mDesc = (TextView) findViewById(R.id.tv_key_desc);
        }
        if (text == null || text.isEmpty()) {
            this.mDesc.setVisibility(8);
            return;
        }
        this.mDesc.setVisibility(0);
        if (text.length() > 60) {
            this.mDesc.setText(text.substring(0, 29) + "..." + text.substring(text.length() - 30, text.length()));
        } else {
            this.mDesc.setText(text);
        }
        this.mDescText = text;
    }

    public String getDescText() {
        return this.mDescText;
    }

    public void setSummary(int id) {
        setDesc(id);
    }

    public void setSummary(String text) {
        setDesc(text);
    }

    public void setValue(int id) {
        setValue(getResources().getString(id));
    }

    public void setValue(String text) {
        if (this.mValue == null) {
            this.mValue = (TextView) findViewById(R.id.tv_value);
        }
        this.mValue.setText(text);
    }

    public void setRightArrowVisibility(int visibility) {
        if (this.mRightArrow == null) {
            this.mRightArrow = (ImageView) findViewById(R.id.iv_arrow);
        }
        if (this.mRightArrow != null) {
            this.mRightArrow.setVisibility(visibility);
        }
    }

    public void keepValueColor() {
        this.keepValueColor = true;
    }

    public void setSmall(boolean small) {
        this.mSmall = small;
    }

    public void setBgDrawable(int index) {
        if (this.bgView == null) {
            this.bgView = (ImageView) findViewById(R.id.bg);
        }
        this.bgView.setVisibility(0);
        if (index == -1) {
            this.bgView.setVisibility(8);
        } else if (index == 0) {
            this.bgView.setImageResource(R.drawable.item_bg_top);
        } else if (index == 1) {
            this.bgView.setImageResource(R.drawable.item_bg_mid);
        } else if (index == 2) {
            this.bgView.setImageResource(R.drawable.item_bg_bottom);
        } else {
            this.bgView.setImageResource(R.drawable.item_bg_corner);
        }
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.mOnClickListener = l;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode != 22) {
            return super.onKeyUp(keyCode, event);
        }
        if (!(this.mRightArrow == null || this.mRightArrow.getVisibility() != 0 || this.mOnClickListener == null)) {
            this.mOnClickListener.onClick(this);
        }
        return true;
    }

    public void onSelecterEnter() {
        Log.d(TAG, "onSelecterEnter");
        if (!(this.mTitle == null || this.keepValueColor)) {
            this.mTitle.setTextColor(getResources().getColor(R.color.titv_setting_item_title_focused));
        }
        if (!(this.mValue == null || this.keepValueColor)) {
            this.mValue.setTextColor(getResources().getColor(R.color.titv_setting_item_title_focused));
        }
        if (this.mDesc != null) {
            this.mDesc.setTextColor(getResources().getColor(R.color.titv_setting_item_summary_focus));
        }
        View view = findViewById(R.id.title);
        if (this.mRightArrow != null) {
            this.mRightArrow.setImageResource(R.drawable.arrow_highlight_right);
        }
    }

    public void onSelectorLeave() {
        Log.d(TAG, "onSelectorLeave");
        if (!(this.mTitle == null || this.keepValueColor)) {
            this.mTitle.setTextColor(getResources().getColor(R.color.titv_setting_item_title_normal));
        }
        if (!(this.mValue == null || this.keepValueColor)) {
            this.mValue.setTextColor(getResources().getColor(R.color.titv_setting_item_title_normal));
        }
        if (this.mDesc != null) {
            this.mDesc.setTextColor(getResources().getColor(R.color.titv_setting_item_summary_normal));
        }
        View view = findViewById(R.id.title);
        if (this.mRightArrow != null) {
            this.mRightArrow.setImageResource(R.drawable.arrow_normal_right);
        }
    }

    private void generateMoveXAnimator(View v, int destinationX, int duration) {
        Log.d(TAG, "generateMoveXAnimator " + v);
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "translationX", new float[]{(float) destinationX});
        oa.setInterpolator(new DecelerateInterpolator());
        oa.setDuration((long) duration);
        oa.start();
    }

    protected void onFocusChanged(boolean arg0, int arg1, Rect arg2) {
        super.onFocusChanged(arg0, arg1, arg2);
    }
}
