package com.titan.titv.settings.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by zj00852 on 2017/09/08.
 */

public class ShowKeyBoardEditText extends EditText implements View.OnFocusChangeListener {

    private Context mContext;
    private InputMethodManager imm;

    public ShowKeyBoardEditText(Context context) {
        this(context, null);
    }

    public ShowKeyBoardEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowKeyBoardEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        setOnFocusChangeListener(this);
        this.setFocusable(true); 
        this.setFocusableInTouchMode(true);
        this.findFocus(); 
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            // this.setFocusable(true); 
            // this.setFocusableInTouchMode(true); 
            // this.requestFocus(); 
            this.findFocus();
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }
}
