package com.titan.titv.settings.widgets;

import android.content.Context;
import android.graphics.Rect;
//import android.support.v4.media.TransportMediator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;
import java.lang.reflect.Field;

public class TvScrollViewEx extends ScrollView {
    private int mMaxHeight = -1;
    private OnScrollStateChangeListener mOnScrollStateChangeListener;
    private final Rect mTempRect = new Rect();

    public interface OnScrollStateChangeListener {
        void onScrollWithoutChildFocusChange(View view, int i);
    }

    public TvScrollViewEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TvScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TvScrollViewEx(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getChildAt(0).getHeight();
        if (this.mMaxHeight >= 0 && height > this.mMaxHeight) {
            setMeasuredDimension(widthMeasureSpec, this.mMaxHeight);
        }
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
    }

    public void setOnScrollStateChangeListener(OnScrollStateChangeListener l) {
        this.mOnScrollStateChangeListener = l;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean result = super.dispatchKeyEvent(event);
        if (event.getAction() == 0 && ((event.getKeyCode() == 19 || event.getKeyCode() == 20) && result)) {
            playSoundEffect(0);
        }
        if (event.getKeyCode() == 93) {
            try {
                Field field = KeyEvent.class.getDeclaredField("mKeyCode");
                field.setAccessible(true);
                field.set(event, Integer.valueOf(20));
                field.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        if (event.getKeyCode() == 92) {
            try {
                Field field = KeyEvent.class.getDeclaredField("mKeyCode");
                field.setAccessible(true);
                field.set(event, Integer.valueOf(19));
                field.setAccessible(false);
            } catch (NoSuchFieldException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e22) {
                e22.printStackTrace();
            }
        }
        if (event.getKeyCode() == 140) {
            try {
                Field field = KeyEvent.class.getDeclaredField("mKeyCode");
                field.setAccessible(true);
                field.set(event, Integer.valueOf(21));
                field.setAccessible(false);
            } catch (NoSuchFieldException e32) {
                e32.printStackTrace();
            } catch (IllegalAccessException e222) {
                e222.printStackTrace();
            }
        }
        if (event.getKeyCode() == 141) {
            try {
                Field field = KeyEvent.class.getDeclaredField("mKeyCode");
                field.setAccessible(true);
                field.set(event, Integer.valueOf(22));
                field.setAccessible(false);
            } catch (NoSuchFieldException e322) {
                e322.printStackTrace();
            } catch (IllegalAccessException e2222) {
                e2222.printStackTrace();
            }
        }
        return result;
    }

//    public boolean arrowScroll(int direction) {
//        View currentFocused = findFocus();
//        if (currentFocused == this) {
//            currentFocused = null;
//        }
//        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
//        int maxJump = getMaxScrollAmount();
//        if (nextFocused == null || !isWithinDeltaOfScreen(nextFocused, maxJump, getHeight())) {
//            int scrollDelta = maxJump;
//            if (direction == 33 && getScrollY() < scrollDelta) {
//                scrollDelta = getScrollY();
//            } else if (direction == TransportMediator.KEYCODE_MEDIA_RECORD && getChildCount() > 0) {
//                int daBottom = getChildAt(0).getBottom();
//                int screenBottom = (getScrollY() + getHeight()) - getPaddingBottom();
//                if (daBottom - screenBottom < maxJump) {
//                    scrollDelta = daBottom - screenBottom;
//                }
//            }
//            if (scrollDelta == 0) {
//                return false;
//            }
//            if (!(this.mOnScrollStateChangeListener == null || currentFocused == null)) {
//                int i;
//                OnScrollStateChangeListener onScrollStateChangeListener = this.mOnScrollStateChangeListener;
//                if (direction == TransportMediator.KEYCODE_MEDIA_RECORD) {
//                    i = -scrollDelta;
//                } else {
//                    i = scrollDelta;
//                }
//                onScrollStateChangeListener.onScrollWithoutChildFocusChange(currentFocused, i);
//                if (direction != TransportMediator.KEYCODE_MEDIA_RECORD) {
//                    scrollDelta = -scrollDelta;
//                }
//                doScrollY(scrollDelta);
//            }
//        } else {
//            nextFocused.getDrawingRect(this.mTempRect);
//            offsetDescendantRectToMyCoords(nextFocused, this.mTempRect);
//            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
//            nextFocused.requestFocus(direction);
//        }
//        if (currentFocused != null && currentFocused.isFocused() && isOffScreen(currentFocused)) {
//            Log.e("Selector", "requestFocus");
//            int descendantFocusability = getDescendantFocusability();
//            setDescendantFocusability(131072);
//            requestFocus();
//            setDescendantFocusability(descendantFocusability);
//        }
//        return true;
//    }

    private void doScrollY(int delta) {
        if (delta != 0) {
            smoothScrollBy(0, delta);
        }
    }

    private boolean isOffScreen(View descendant) {
        return !isWithinDeltaOfScreen(descendant, 0, getHeight());
    }

    private boolean isWithinDeltaOfScreen(View descendant, int delta, int height) {
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        if (this.mTempRect.bottom + delta < getScrollY() || this.mTempRect.top - delta > getScrollY() + height) {
            return false;
        }
        return true;
    }
}
