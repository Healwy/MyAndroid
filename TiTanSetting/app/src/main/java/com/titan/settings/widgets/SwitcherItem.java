package com.titan.settings.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.titan.settings.R;
import com.titan.settings.widgets.TvSelector.Selectable;

public class SwitcherItem extends SettingItem implements Selectable {
    private static final String TAG = "SwitcherItem";
    private boolean mArrowVisble = true;
    private int mCurrentIndex;
    private boolean mEnterShow = false;
    private int mIncrement = 1;
    private ImageView mLeftArrow;
    private onFocusListener mOnFocusListener;
    private OnSwitchListener mOnSwitchListener;
    private String[] mOptions;
    private boolean mRecycle = true;
    public boolean mRepeated = false;
    private ImageView mRightArrow;
    private TextView mValue;

    public interface OnSwitchListener {
        boolean onSwitchTo(int i);
    }

    public interface onFocusListener {
        boolean onFocusChange(boolean z);
    }

    public SwitcherItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SwitcherItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitcherItem(Context context) {
        super(context);
    }

    public SwitcherItem(Context context, boolean useDefaultLayout) {
        super(context);
        if (useDefaultLayout) {
            inflate(context, R.layout.switcher_item_content, this);
            setFocusable(true);
            setFocusableInTouchMode(true);
        }
    }

    public static SwitcherItem createItem(Context context) {
        return createItem(context, null);
    }

    public static SwitcherItem createItem(ViewGroup parent) {
        return createItem(parent.getContext(), parent);
    }

    public static SwitcherItem createItem(Context context, ViewGroup parent) {
        ViewGroup group;
        if (parent instanceof SettingCategory) {
            group = ((SettingCategory) parent).getContentHolder();
        } else {
            group = parent;
        }
        View out = inflate(context, R.layout.switcher_item, group);
        if (group == null) {
            return (SwitcherItem) out;
        }
        return (SwitcherItem) group.getChildAt(group.getChildCount() - 1);
    }

    public void setIncrement(int increment) {
        this.mIncrement = increment;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate mLeftArrow  mRightArrow");
        this.mValue = (TextView) findViewById(R.id.tv_value);
        this.mLeftArrow = (ImageView) findViewById(R.id.iv_switcher_left_arrow);
        this.mRightArrow = (ImageView) findViewById(R.id.iv_switcher_right_arrow);
        this.mLeftArrow.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (SwitcherItem.this.isFocused()) {
                    SwitcherItem.this.left();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            SwitcherItem.this.mLeftArrow.setImageResource(R.drawable.triangle_hightlight_left_normal);
                        }
                    }, 200);
                    return;
                }
                SwitcherItem.this.requestFocus();
            }
        });
        this.mRightArrow.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (SwitcherItem.this.isFocused()) {
                    SwitcherItem.this.right();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            SwitcherItem.this.mRightArrow.setImageResource(R.drawable.triangle_hightlight_right_normal);
                        }
                    }, 200);
                    return;
                }
                SwitcherItem.this.requestFocus();
            }
        });
        this.mValue.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (SwitcherItem.this.isFocused()) {
                    SwitcherItem.this.enter();
                } else {
                    SwitcherItem.this.requestFocus();
                }
            }
        });

    }

    public void setEnterShow(boolean show) {
        this.mEnterShow = show;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown keyCode=" + keyCode + ", repeat=" + event.getRepeatCount());
        if (keyCode == 21) {
            if (this.mRepeated || event.getRepeatCount() == 0) {
                left();
                return true;
            }
        } else if (keyCode == 22) {
            if (this.mRepeated || event.getRepeatCount() == 0) {
                right();
                return true;
            }
        } else if ((keyCode == 66 || keyCode == 23) && event.getRepeatCount() == 0 && !this.mEnterShow) {
            enter();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp keyCode=" + keyCode);
        if (keyCode == 21) {
            this.mLeftArrow.setImageResource(R.drawable.triangle_hightlight_left_normal);
            return true;
        } else if (keyCode != 22) {
            return super.onKeyDown(keyCode, event);
        } else {
            this.mRightArrow.setImageResource(R.drawable.triangle_hightlight_right_normal);
            return true;
        }
    }

    public int getCurrentIndex() {
        return this.mCurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.mCurrentIndex = currentIndex;
        if (this.mCurrentIndex >= 0 && this.mCurrentIndex < this.mOptions.length) {
            setValue(this.mOptions[this.mCurrentIndex]);
        }
    }

    public String[] getOptions() {
        return this.mOptions;
    }

    public void setOptions(String[] options) {
        this.mOptions = options;
    }

    public OnSwitchListener getOnSwitchListener() {
        return this.mOnSwitchListener;
    }

    public void setOnSwitchListener(OnSwitchListener mOnSwitchListener) {
        this.mOnSwitchListener = mOnSwitchListener;
    }

    public void setOnFocusListener(onFocusListener mFocusListener) {
        this.mOnFocusListener = mFocusListener;
    }

    public void right() {
        if (this.mRightArrow == null) {
            Log.d(TAG, "mRightArrow is null");
        }
        this.mRightArrow.setImageResource(R.drawable.triangle_hightlight_right_focus);
        playSoundEffect(0);
        int newIndex = this.mCurrentIndex;
        if (newIndex != this.mOptions.length - 1) {
            newIndex += this.mIncrement;
            if (newIndex >= this.mOptions.length) {
                newIndex = this.mOptions.length - 1;
            }
        } else if (this.mRecycle) {
            newIndex = 0;
        } else {
            return;
        }
        boolean switchSuccess = true;
        if (this.mOnSwitchListener != null) {
            switchSuccess = this.mOnSwitchListener.onSwitchTo(newIndex);
        }
        if (switchSuccess) {
            this.mCurrentIndex = newIndex;
            if (this.mCurrentIndex >= 0 && this.mCurrentIndex < this.mOptions.length) {
                setValue(this.mOptions[this.mCurrentIndex]);
            }
        }
    }

    public void setRecycle(boolean recycle) {
        this.mRecycle = recycle;
    }

    public void setRepeated(boolean repeat) {
        this.mRepeated = repeat;
    }

    public void setArrowVisible(boolean visible) {
        this.mArrowVisble = visible;
        if (!(this.mLeftArrow == null || isFocused())) {
            this.mLeftArrow.setVisibility(8);
        }
        if (this.mRightArrow != null && !isFocused()) {
            this.mRightArrow.setVisibility(8);
        }
    }

    public void left() {
        this.mLeftArrow.setImageResource(R.drawable.triangle_hightlight_left_focus);
        playSoundEffect(0);
        int newIndex = this.mCurrentIndex;
        if (newIndex != 0) {
            newIndex -= this.mIncrement;
            if (newIndex < 0) {
                newIndex = 0;
            }
        } else if (this.mRecycle) {
            newIndex = this.mOptions.length - 1;
        } else {
            return;
        }
        boolean switchSuccess = true;
        if (this.mOnSwitchListener != null) {
            switchSuccess = this.mOnSwitchListener.onSwitchTo(newIndex);
        }
        if (switchSuccess) {
            this.mCurrentIndex = newIndex;
            if (this.mCurrentIndex >= 0 && this.mCurrentIndex < this.mOptions.length) {
                setValue(this.mOptions[this.mCurrentIndex]);
            }
        }
    }

    private void enter() {
//        SettingOptionDialog dialog = new SettingOptionDialog(getContext());
//        dialog.getWindow().setType(2003);
//        dialog.setTitle(gitTitleText());
//        dialog.setOptions(this.mOptions);
//        dialog.setCurrentIndex(this.mCurrentIndex);
//        dialog.show();
//        dialog.setOnSelectListener(new OnSelectListener() {
//            public void onSelect(int index) {
//                if (SwitcherItem.this.mCurrentIndex != index) {
//                    boolean switchSuccess = true;
//                    if (SwitcherItem.this.mOnSwitchListener != null) {
//                        switchSuccess = SwitcherItem.this.mOnSwitchListener.onSwitchTo(index);
//                    }
//                    if (switchSuccess) {
//                        SwitcherItem.this.mCurrentIndex = index;
//                        SwitcherItem.this.setValue(SwitcherItem.this.mOptions[SwitcherItem.this.mCurrentIndex]);
//                    }
//                }
//            }
//        });
    }

    public void onSelecterEnter() {
        super.onSelecterEnter();
        if (this.mOnFocusListener != null) {
            this.mOnFocusListener.onFocusChange(true);
        }
        if (!this.mArrowVisble) {
            this.mLeftArrow.setVisibility(0);
            this.mRightArrow.setVisibility(0);
        }
        this.mLeftArrow.setImageResource(R.drawable.triangle_hightlight_left_normal);
        this.mRightArrow.setImageResource(R.drawable.triangle_hightlight_right_normal);
    }

    public void onSelectorLeave() {
        super.onSelectorLeave();
        if (this.mOnFocusListener != null) {
            this.mOnFocusListener.onFocusChange(false);
        }
        if (!this.mArrowVisble) {
            this.mLeftArrow.setVisibility(8);
            this.mRightArrow.setVisibility(8);
        }
        this.mLeftArrow.setImageResource(R.drawable.triangle_normal_left_normal);
        this.mRightArrow.setImageResource(R.drawable.triangle_normal_right_normal);
    }

    private void generateMoveXAnimator(View v, int destinationX, int duration) {
        Log.d(TAG, "generateMoveXAnimator " + v);
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "translationX", new float[]{(float) destinationX});
        oa.setInterpolator(new DecelerateInterpolator());
        oa.setDuration((long) duration);
        oa.start();
    }
}
