package com.misyulya.rssnewsapplication.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.misyulya.rssnewsapplication.R;

public class MyButton extends Button {
    private int mTextColor;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
        mTextColor = a.getInt(R.styleable.MyButton_textColor, 0);
        a.recycle();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyButton, defStyleAttr, 0);
        mTextColor = a.getInt(R.styleable.MyButton_textColor, 0);
        a.recycle();
    }
}
