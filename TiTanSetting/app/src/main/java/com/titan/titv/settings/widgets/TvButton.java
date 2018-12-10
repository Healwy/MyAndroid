package com.titan.titv.settings.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

import com.titan.titv.settings.R;


public class TvButton extends Button {

    private Context mContext;

    public TvButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        setGravity(Gravity.CENTER);
        setIncludeFontPadding(false);
        setFocusable(true);
        setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color));
        setBackground(ContextCompat.getDrawable(mContext, R.drawable.item_bg_mid));

    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        // if (focused) {
        //     setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color));
        // } else {
        //     setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color_selected));
        // }
    }

}
