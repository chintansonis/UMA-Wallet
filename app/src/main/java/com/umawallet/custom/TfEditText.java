package com.umawallet.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.umawallet.helper.Functions;


public class TfEditText extends android.support.v7.widget.AppCompatEditText {

    public TfEditText(Context context) {
        super(context);
        if (!isInEditMode()) {
            init();
        }
    }

    public TfEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        try {
            setTypeface(Functions.getFontType(getContext(), FontType.Regular.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
