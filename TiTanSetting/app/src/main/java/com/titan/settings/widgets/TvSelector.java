package com.titan.settings.widgets;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
//import android.support.v4.media.TransportMediator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class TvSelector extends FrameLayout {
    private static final String TAG = "Selector";
    private LayoutParams mAnimStartLayout;
    private boolean mAttached;
    private boolean mFirstAttached;
    private Handler mHandler;
    private int[] mLastLocations;
    private View mLastTarget;
    private AnimatorUpdateListener mLayoutUpdater = new AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            LayoutParams lp = new LayoutParams(TvSelector.this.mAnimStartLayout);
            float percent = ((Float) animation.getAnimatedValue()).floatValue();
            TvSelector.this.setX(TvSelector.this.mTargetX - (TvSelector.this.mTranslationX * (1.0f - percent)));
            TvSelector.this.setY(TvSelector.this.mTargetY - (TvSelector.this.mTranslationY * (1.0f - percent)));
            lp.width = (int) (((float) lp.width) + (((float) (TvSelector.this.mTargetWidth - lp.width)) * percent));
            lp.height = (int) (((float) lp.height) + (((float) (TvSelector.this.mTargetHeight - lp.height)) * percent));
            TvSelector.this.setLayoutParams(lp);
        }
    };
    private float mOffsetBottom;
    private float mOffsetLeft;
    private Rect mOffsetRect = new Rect();
    private float mOffsetRight;
    private float mOffsetTop;
    private OnFocusChangeListener mOnFocusChangeDelegate = new OnFocusChangeListener() {
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                TvSelector.this.doMoveBy(v, 0, 0, false);
            } else if (v instanceof Selectable) {
                ((Selectable) v).onSelectorLeave();
            }
        }
    };
    private int mPendingSelectionDx;
    private int mPendingSelectionDy;
    private View mPendingSelectionView;
    private Runnable mProcessPendingSelectionRunnable = new Runnable() {
        public void run() {
            if (TvSelector.this.mPendingSelectionView != null && TvSelector.this.mPendingSelectionView.getVisibility() == 0) {
                Log.d(TvSelector.TAG, "mProcessPendingSelectionRunnable...");
                TvSelector.this.doMoveBy(TvSelector.this.mPendingSelectionView, TvSelector.this.mPendingSelectionDx, TvSelector.this.mPendingSelectionDy, false);
            }
        }
    };
    private int mTargetHeight;
    private Rect mTargetRect = new Rect();
    private int mTargetWidth;
    private float mTargetX;
    private float mTargetY;
    private float mTranslationX;
    private float mTranslationY;
    private ValueAnimator mValueAnimator;

    public interface Selectable {
        void onSelecterEnter();

        void onSelectorLeave();
    }

    public TvSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TvSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TvSelector(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.mHandler = new Handler();
    }

    public void setImageResource(int res, LayoutParams params, boolean setOffset) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ScaleType.FIT_XY);
        removeAllViews();
        addView(imageView, params);
        if (setOffset) {
            setImageOffset(res);
        }
    }

    public void setImageResource(int res, LayoutParams params) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(res);
        imageView.setScaleType(ScaleType.FIT_XY);
        removeAllViews();
        addView(imageView, params);
    }

    public void setView(View view, LayoutParams params) {
        removeAllViews();
        addView(view, params);
    }

    public void setImageOffset(int res) {
        Drawable db = getResources().getDrawable(res);
        Rect rect = new Rect();
        db.getPadding(rect);
        setOffset((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
    }

    public void setOffset(float left, float top, float right, float bottom) {
        this.mOffsetLeft = left;
        this.mOffsetRight = right;
        this.mOffsetTop = top;
        this.mOffsetBottom = bottom;
    }

//    public void attachSelectableViews(View rootView) {
//        if (rootView != null) {
//            View view;
//            ViewGroup group = (ViewGroup) rootView;
//            if (group != null) {
//                int count = group.getChildCount();
//                Log.d(TAG, "attachSelectableViews count " + group.getChildCount());
//                for (int i = 0; i < count; i++) {
//                    view = group.getChildAt(i);
//                    if (view instanceof SettingCategory) {
//                        ((SettingCategory) view).refreshItem();
//                    }
//                }
//            }
//            for (View view2 : rootView.getFocusables(TransportMediator.KEYCODE_MEDIA_RECORD)) {
//                view2.setOnFocusChangeListener(this.mOnFocusChangeDelegate);
//            }
//        }
//    }

    public void setAttached(boolean attached) {
        this.mAttached = attached;
        this.mFirstAttached = false;
    }

    public void moveTo(View v) {
        if (v != null) {
            doMoveBy(v, 0, 0, true);
        }
    }

    public void moveBy(View v, int dx, int dy) {
        doMoveBy(v, dx, dy, true);
    }

    public void moveBy(View v, int dx, int dy, boolean useAnimation) {
        doMoveBy(v, dx, dy, useAnimation);
    }

    public void refresh() {
        Log.d(TAG, "refresh mLastTarget " + this.mLastTarget);
        if (this.mLastTarget != null) {
            doMoveBy(this.mLastTarget, 0, 0, false);
        }
    }

    private final void getPosInParent(int[] aPos, View aView) {
        Log.d(TAG, "getPosInParent");
        try {
            ViewGroup parent = (ViewGroup) getParent();
            aView.getDrawingRect(this.mOffsetRect);
            parent.offsetDescendantRectToMyCoords(aView, this.mOffsetRect);
            aPos[0] = this.mOffsetRect.left;
            aPos[1] = this.mOffsetRect.top;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doMoveBy(View v, int dx, int dy, boolean useAnimation) {
        Log.d(TAG, "doMoveBy View : " + v);
        if (!this.mAttached) {
            return;
        }
        if (v instanceof Selectable) {
            if (getVisibility() != 0) {
                useAnimation = false;
                setVisibility(0);
            }
            this.mLastTarget = v;
            int[] currentLocations = new int[2];
            getLocationInWindow(currentLocations);
            this.mLastLocations = currentLocations;
            int[] mParentLocations = new int[2];
            ((View) getParent()).getLocationInWindow(mParentLocations);
            v.getDrawingRect(this.mTargetRect);
            this.mHandler.removeCallbacks(this.mProcessPendingSelectionRunnable);
            if (this.mTargetRect.width() == 0 && this.mTargetRect.height() == 0) {
                Log.d(TAG, "target not layouted yet");
                this.mPendingSelectionView = v;
                this.mPendingSelectionDx = dx;
                this.mPendingSelectionDy = dy;
                this.mHandler.postDelayed(this.mProcessPendingSelectionRunnable, 100);
                return;
            }
            int[] targetLocations = new int[2];
            getPosInParent(targetLocations, v);
            float targetX = (((float) targetLocations[0]) - this.mOffsetLeft) + ((float) dx);
            float targetY = (((float) targetLocations[1]) - this.mOffsetTop) + ((float) dy);
            float translationX = (((float) this.mLastLocations[0]) - targetX) - ((float) mParentLocations[0]);
            float translationY = (((float) this.mLastLocations[1]) - targetY) - ((float) mParentLocations[1]);
            int w = (int) ((((float) this.mTargetRect.width()) + this.mOffsetLeft) + this.mOffsetRight);
            int h = (int) ((((float) this.mTargetRect.height()) + this.mOffsetTop) + this.mOffsetBottom);
            Log.d(TAG, "targetPos=(" + targetX + "," + targetY + ")" + ", w=" + w + ", h=" + h + ", anim=" + (this.mFirstAttached ? useAnimation : false));
            if (this.mValueAnimator != null) {
                this.mValueAnimator.cancel();
                this.mValueAnimator = null;
            }
            if (this.mFirstAttached && useAnimation) {
                this.mAnimStartLayout = (LayoutParams) getLayoutParams();
                this.mTargetX = targetX;
                this.mTargetY = targetY;
                this.mTranslationX = -translationX;
                this.mTranslationY = -translationY;
                this.mTargetWidth = w;
                this.mTargetHeight = h;
                this.mValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                this.mValueAnimator.addUpdateListener(this.mLayoutUpdater);
                final View view = v;
                this.mValueAnimator.addListener(new AnimatorListener() {
                    public void onAnimationStart(Animator arg0) {
                    }

                    public void onAnimationRepeat(Animator arg0) {
                    }

                    public void onAnimationEnd(Animator arg0) {
                        if (view.isFocused()) {
                            ((Selectable) view).onSelecterEnter();
                        }
                    }

                    public void onAnimationCancel(Animator arg0) {
                    }
                });
                this.mValueAnimator.setDuration(130).start();
            } else {
                setX(targetX);
                setY(targetY);
                setLayoutParams(new LayoutParams(w, h));
                ((Selectable) v).onSelecterEnter();
            }
            this.mFirstAttached = true;
            return;
        }
        Log.d(TAG, "not Selectable");
        setVisibility(8);
    }
}
