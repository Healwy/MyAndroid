package com.titan.titv.settings.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class TVGirdLayoutManager extends GridLayoutManager {

    public TVGirdLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TVGirdLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public TVGirdLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public int getChildCount() {
        return super.getChildCount();
    }

    @Override
    public View getChildAt(int index) {
        return super.getChildAt(index);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public View getFocusedChild() {
        return super.getFocusedChild();
    }

    @Override
    public int getPosition(View view) {
        return super.getPosition(view);
    }

    @Override
    public int getSpanCount() {
        return super.getSpanCount();
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {

        // Need to be called in order to layout new row/column
        View nextFocus = super.onFocusSearchFailed(focused, focusDirection, recycler, state);

        if (nextFocus == null) {
            return null;
        }

        int fromPos = getPosition(focused);

        int nextPos = getNextViewPos(fromPos, focusDirection);

//        if (nextFocus == null) {
//            return findViewByPosition(nextPos);
//        }

        return findViewByPosition(nextPos);

    }


    protected int getNextViewPos(int fromPos, int direction) {
        int offset = calcOffsetToNextView(direction);

        if (hitBorder(fromPos, offset)) {
            return fromPos;
        }

        return fromPos + offset;
    }


    protected int calcOffsetToNextView(int direction) {
        int spanCount = getSpanCount();
        int orientation = getOrientation();

        if (orientation == VERTICAL) {
            switch (direction) {
                case View.FOCUS_DOWN:
                    return spanCount;
                case View.FOCUS_UP:
                    return -spanCount;
                case View.FOCUS_RIGHT:
                    return 1;
                case View.FOCUS_LEFT:
                    return -1;
            }
        } else if (orientation == HORIZONTAL) {
            switch (direction) {
                case View.FOCUS_DOWN:
                    return 1;
                case View.FOCUS_UP:
                    return -1;
                case View.FOCUS_RIGHT:
                    return spanCount;
                case View.FOCUS_LEFT:
                    return -spanCount;
            }
        }

        return 0;
    }

    private boolean hitBorder(int from, int offset) {
        int spanCount = getSpanCount();

        if (Math.abs(offset) == 1) {
            int spanIndex = from % spanCount;
            int newSpanIndex = spanIndex + offset;
            return newSpanIndex < 0 || newSpanIndex >= spanCount;
        } else {
            int newPos = from + offset;
            return newPos < 0 && newPos >= spanCount;
        }
    }
}
