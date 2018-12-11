package com.titan.settings.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.titan.settings.R;
import com.titan.settings.utils.LogUtils;

import static android.view.KeyEvent.KEYCODE_DPAD_RIGHT;

public class TvRecyclerView extends RecyclerView {

    private static final float DEFAULT_SELECT_SCALE = 1.04f;

    private static final int SCROLL_FOLLOW = 1;

    protected Drawable mDrawableFocus;
    public boolean mIsDrawFocusMoveAnim;
    protected float mSelectedScaleValue;
    private float mFocusMoveAnimScale;

    private int mSelectedPosition;
    private View mNextFocused;

    protected View mSelectedItem;
    private Scroller mScrollerFocusMoveAnim;
    private boolean mIsFollowScroll;

    private int mScreenWidth;
    private int mScreenHeight;
    private boolean mIsAutoProcessFocus;
    private int mOrientation;


    public TvRecyclerView(Context context) {
        this(context, null);
    }

    public TvRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setAttributeSet(attrs);
    }

    private void init() {
        mScrollerFocusMoveAnim = new Scroller(getContext());
        mIsDrawFocusMoveAnim = false;
        mSelectedPosition = 0;
        mNextFocused = null;
        mIsFollowScroll = false;
        mOrientation = HORIZONTAL;
        mScreenWidth = (int) (getContext().getResources().getDimension(R.dimen.list_view_width));
        mScreenHeight = (int) (getContext().getResources().getDimension(R.dimen.list_view_height));
        setChildrenDrawingOrderEnabled(true);
    }

    private void setAttributeSet(AttributeSet attrs) {
        TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.TvRecyclerView);
        int type = typeArray.getInteger(R.styleable.TvRecyclerView_scrollMode, 0);
        if (type == 1) {
            mIsFollowScroll = true;
        }
        final Drawable drawable = typeArray.getDrawable(R.styleable.TvRecyclerView_focusDrawable);
        if (drawable != null) {
            setFocusDrawable(drawable);
        }

        mSelectedScaleValue = typeArray.getFloat(R.styleable.TvRecyclerView_focusScale, DEFAULT_SELECT_SCALE);

        mIsAutoProcessFocus = typeArray.getBoolean(R.styleable.TvRecyclerView_isAutoProcessFocus, true);
        if (!mIsAutoProcessFocus) {
            mSelectedScaleValue = 1.0f;
        } else {
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        }
        typeArray.recycle();
    }


    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (layoutManager instanceof LinearLayoutManager) {
            mOrientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
    }

    public void setViewWidth(int width) {
        mScreenWidth = width;
    }

    public void setViewHeight(int height) {
        mScreenHeight = height;
    }

    public void setSelectedScale(float scale) {
        if (scale >= 1.0f) {
            mSelectedScaleValue = scale;
        }
    }

    public void setIsAutoProcessFocus(boolean isAuto) {
        mIsAutoProcessFocus = isAuto;
        if (!isAuto) {
            mSelectedScaleValue = 1.0f;
        } else {
            if (mSelectedScaleValue == 1.0f) {
                mSelectedScaleValue = DEFAULT_SELECT_SCALE;
            }
        }
    }

    public void setFocusDrawable(Drawable focusDrawable) {
        mDrawableFocus = focusDrawable;
    }

    public void setScrollMode(int mode) {
        mIsFollowScroll = mode == SCROLL_FOLLOW;
    }

    /**
     * When call this method, you must ensure that the location of the view has been inflate
     *
     * @param position selected item position
     */
    public void setItemSelected(int position) {
        mSelectedPosition = position;
        //  LogUtils.d("mSelectedPosition : " + mSelectedPosition);
        int firstVisibleItems = ((LinearLayoutManager) this.getLayoutManager()).findFirstVisibleItemPosition();
        View selectedView = getChildAt(mSelectedPosition - firstVisibleItems);
        if (selectedView == null) {
            LogUtils.d("selectedView == null");
            return;
        }
        mSelectedItem = selectedView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int position = indexOfChild(mSelectedItem);
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {
                if (position > i) {
                    position = i;
                }
                return position;
            }
            if (i == position) {
                return childCount - 1;
            }
        }
        return i;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //  LogUtils.d();
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if (mSelectedItem == null) {
                mSelectedItem = getChildAt(mSelectedPosition);
            }
            try {
                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    mNextFocused = FocusFinder.getInstance().findNextFocus(this, mSelectedItem, View.FOCUS_LEFT);
                } else if (keyCode == KEYCODE_DPAD_RIGHT) {
                    mNextFocused = FocusFinder.getInstance().findNextFocus(this, mSelectedItem, View.FOCUS_RIGHT);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    mNextFocused = FocusFinder.getInstance().findNextFocus(this, mSelectedItem, View.FOCUS_UP);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    mNextFocused = FocusFinder.getInstance().findNextFocus(this, mSelectedItem, View.FOCUS_DOWN);
                }
            } catch (Exception e) {
                LogUtils.e("dispatchKeyEvent: get next focus item error: " + e.getMessage());
                mNextFocused = null;
            }
        }

        if (!mIsAutoProcessFocus) {
            processMoves(event.getKeyCode());
            if (mNextFocused != null) {
                mSelectedItem = mNextFocused;
            } else {
                mSelectedItem = getFocusedChild();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (processMoves(keyCode)) {
                    return true;
                }
                break;

            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void computeScroll() {
        if (mScrollerFocusMoveAnim.computeScrollOffset()) {
            if (mIsDrawFocusMoveAnim) {
                mFocusMoveAnimScale = ((float) (mScrollerFocusMoveAnim.getCurrX())) / 100;
            }
            postInvalidate();
        } else {
            if (mIsDrawFocusMoveAnim) {
                if (mNextFocused != null) {
                    mSelectedItem = mNextFocused;
                    mSelectedPosition = getChildAdapterPosition(mSelectedItem);
                }
                mIsDrawFocusMoveAnim = false;
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                postInvalidate();
            }
        }
    }

    private boolean processMoves(int keycode) {
        if (mNextFocused == null || !hasFocus()) {
            return false;
        } else {
            if (mIsDrawFocusMoveAnim) {
                return true;
            }
            if (mNextFocused == null) {
                return false;
            }

            if (!mIsFollowScroll) {
                boolean isVisible = isVisibleChild(mNextFocused);
                boolean isHalfVisible = isHalfVisibleChild(mNextFocused);
                if (isHalfVisible || !isVisible) {
                    smoothScrollView(keycode);
                }
            } else {
                boolean isOver = isOverHalfScreen(mNextFocused, keycode);
                if (isOver) {
                    smoothScrollView(keycode);
                }
            }
            if (mIsAutoProcessFocus) {
                startFocusMoveAnim();
            } else {
                invalidate();
            }
            return true;
        }
    }

    private void smoothScrollView(int keycode) {
        int scrollDistance = getScrollDistance(keycode);
        LogUtils.d("scrollDistance : " + scrollDistance);
        if ((keycode == KEYCODE_DPAD_RIGHT || keycode == KeyEvent.KEYCODE_DPAD_LEFT)
                && mOrientation == HORIZONTAL) {
            scrollBy(scrollDistance, 0);
        } else if ((keycode == KeyEvent.KEYCODE_DPAD_UP || keycode == KeyEvent.KEYCODE_DPAD_DOWN)
                && mOrientation == VERTICAL) {
            scrollBy(0, scrollDistance);
        }
    }

    private int getScrollDistance(int keyCode) {
        int distance = 0;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                distance = mNextFocused.getLeft() +
                        mNextFocused.getWidth() / 2 - mScreenWidth / 2;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                distance = mNextFocused.getLeft()
                        - mScreenWidth / 2 + mNextFocused.getWidth() / 2;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                distance = mNextFocused.getBottom() -
                        mNextFocused.getHeight() / 2 - mScreenHeight / 2;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                distance = mNextFocused.getTop() +
                        mNextFocused.getHeight() / 2 - mScreenHeight / 2;
                break;
            default:
                break;
        }
        return distance;
    }

    private boolean isHalfVisibleChild(View child) {
        if (child != null) {
            Rect ret = new Rect();
            boolean isVisible = child.getLocalVisibleRect(ret);
            if (mOrientation == HORIZONTAL) {
                return isVisible && (ret.width() < child.getWidth());
            } else {
                return isVisible && (ret.height() < child.getHeight());
            }
        }
        return false;
    }

    private boolean isVisibleChild(View child) {
        if (child != null) {
            Rect ret = new Rect();
            return child.getLocalVisibleRect(ret);
        }
        return false;
    }

    private boolean isOverHalfScreen(View child, int keycode) {
        Rect ret = new Rect();
        Rect listviewRect = new Rect();
        this.getGlobalVisibleRect(listviewRect);
        boolean visibleRect = child.getGlobalVisibleRect(ret);
        if (visibleRect && keycode == KEYCODE_DPAD_RIGHT) {
            if (ret.right > mScreenWidth / 2) {
                return true;
            }
        } else if (visibleRect && keycode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (ret.left < mScreenWidth / 2) {
                return true;
            }
        } else if (visibleRect && keycode == KeyEvent.KEYCODE_DPAD_UP) {
            if (ret.top < listviewRect.top + mScreenHeight / 2) {
                return true;
            }
        } else if (visibleRect && keycode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (ret.bottom > mScreenHeight / 2) {
                return true;
            }
        }
        return false;
    }

    private void startFocusMoveAnim() {
        setLayerType(View.LAYER_TYPE_NONE, null);
        mIsDrawFocusMoveAnim = true;
        mScrollerFocusMoveAnim.startScroll(0, 0, 100, 100, 200);
        invalidate();
    }

    /**
     * When the TvRecyclerView width is determined, the returned position is correct
     *
     * @return selected view position
     */
    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    View getSelectedView() {
        return mSelectedItem;
    }

    public float getSelectedScaleValue() {
        return mSelectedScaleValue;
    }

    public Drawable getDrawableFocus() {
        return mDrawableFocus;
    }

    public View getNextFocusView() {
        return mNextFocused;
    }

    public float getFocusMoveAnimScale() {
        return mFocusMoveAnimScale;
    }

}
