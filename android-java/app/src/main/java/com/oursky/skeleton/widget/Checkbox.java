package com.oursky.skeleton.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.helper.Touchable;

import static com.oursky.skeleton.helper.ResourceHelper.dp;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Checkbox extends LinearLayout {
    public interface OnCheckedChangeListener {
        void onCheckedChanged(Checkbox view, boolean isChecked);
    }
    private AppCompatCheckBox mCheckbox;
    private TextView mLabel;
    private OnCheckedChangeListener mListener;

    //region Lifecycle
    //---------------------------------------------------------------
    public Checkbox(Context context) {
        super(context);
        ctor(context, null, 0);
    }
    public Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctor(context, attrs, 0);
    }
    public Checkbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctor(context, attrs, defStyle);
    }
    private void ctor(Context context, AttributeSet attrs, int defStyle) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        mCheckbox = new AppCompatCheckBox(context);
        mCheckbox.setButtonDrawable(android.R.color.transparent);
        mLabel = new TextView(context);
        mLabel.setPadding(dp(16), 0, dp(16), 0);
        mLabel.setVisibility(GONE);
        addView(mCheckbox, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build());
        addView(mLabel, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build());
        Touchable.make(this);
        mCheckbox.setOnCheckedChangeListener((view, isChecked) -> {
            if (mListener!=null) mListener.onCheckedChanged(Checkbox.this, isChecked);
        });
        setOnClickListener((view) -> {
            mCheckbox.setChecked(!mCheckbox.isChecked());
        });
    }
    //---------------------------------------------------------------
    //endregion

    // Block all touch event to child, we toggle with onClick over top layout
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    //region Checkbox Functions
    //---------------------------------------------------------------
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mListener = listener;
    }
    public boolean isChecked() {
        return mCheckbox.isChecked();
    }
    public void setChecked(boolean b) {
        mCheckbox.setChecked(b);
    }
    public void setText(String text) {
        mLabel.setText(text);
        mLabel.setVisibility(text.isEmpty() ? GONE : VISIBLE);
    }
    public void setText(@StringRes int text) {
        mLabel.setText(text);
        mLabel.setVisibility(VISIBLE);
    }
    public void setTextColor(@ColorInt int color) {
        mLabel.setTextColor(color);
    }
    public void setTextColor(ColorStateList color) {
        mLabel.setTextColor(color);
    }
    public void setTextSize(float s) {
        mLabel.setTextSize(s);
    }
    public void setTypeface(Typeface tf) {
        mLabel.setTypeface(tf, Typeface.NORMAL);
    }
    public void setTypeface(Typeface tf, int style) {
        mLabel.setTypeface(tf, style);
    }
    public void setAllCaps(boolean b) {
        mLabel.setAllCaps(b);
    }
    //---------------------------------------------------------------
    //endregion

    //region Icon
    //---------------------------------------------------------------
    public void setButtonDrawable(@DrawableRes int resId, int width, int height) {
        ViewGroup.LayoutParams lp = mCheckbox.getLayoutParams();
        lp.width = width;
        lp.height = height;
        mCheckbox.setLayoutParams(lp);
        mCheckbox.setBackgroundResource(resId);
    }
    public void setButtonDrawable(StateListDrawable drawable, int width, int height) {
        ViewGroup.LayoutParams lp = mCheckbox.getLayoutParams();
        lp.width = width;
        lp.height = height;
        mCheckbox.setLayoutParams(lp);
        mCheckbox.setBackground(drawable);
    }
    //---------------------------------------------------------------
    //endregion
}
