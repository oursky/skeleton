package com.oursky.skeleton.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.helper.Touchable;

public class Button extends FrameLayout {
    private ImageView mIcon;
    private TextView mText;
    private ImageView.ScaleType mScaleType;
    private boolean mEnabled;

    //region Lifecycle
    //---------------------------------------------------------------
    public Button(Context context) {
        super(context);
        ctor(context, null, 0);
    }
    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctor(context, attrs, 0);
    }
    public Button(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctor(context, attrs, defStyle);
    }
    private void ctor(Context context, AttributeSet attrs, int defStyle) {
        mEnabled = true;
        mScaleType = ImageView.ScaleType.FIT_CENTER;
        mText = new TextView(context);
        mText.setAllCaps(true);
        mText.setBackground(null);
        mText.setGravity(Gravity.CENTER);
        addView(mText, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build());
        Touchable.make(this);
    }
    //---------------------------------------------------------------
    //endregion

    //region UI State Functions
    //---------------------------------------------------------------
    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        if (mEnabled!=b) {
            mEnabled = b;
            setAlpha(mEnabled ? 1.0f : 0.5f);
        }
    }
    @Override
    public void setActivated(boolean activated) {
        super.setActivated(activated);
        if (mIcon!=null) mIcon.setActivated(activated);
    }
    //---------------------------------------------------------------
    //endregion

    //region Text Functions
    //---------------------------------------------------------------
    public void setText(@StringRes int stringId) {
        mText.setText(stringId);
    }
    public void setText(String text) {
        mText.setText(text);
    }
    public void setTextColor(@ColorInt int color) {
        mText.setTextColor(color);
    }
    public void setTextColor(ColorStateList color) {
        mText.setTextColor(color);
    }
    public void setTextSize(float s) {
        mText.setTextSize(s);
    }
    public void setTypeface(Typeface tf) {
        mText.setTypeface(tf, Typeface.NORMAL);
    }
    public void setTypeface(Typeface tf, int style) {
        mText.setTypeface(tf, style);
    }
    public void setAllCaps(boolean b) {
        mText.setAllCaps(b);
    }
    public void setTextPadding(int left, int top, int right, int bottom) {
        mText.setPadding(left, top, right, bottom);
    }
    //---------------------------------------------------------------
    //endregion

    //region Icon Functions
    //---------------------------------------------------------------
    public void setIcon(@DrawableRes int resId) {
        setIcon(resId, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, 0);
    }
    public void setIcon(@DrawableRes int resId, int width, int height) {
        setIcon(resId, width, height, 0);
    }
    public void setIcon(@DrawableRes int resId, int width, int height, int margin) {
        if (mIcon==null) {
            mIcon = new ImageView(getContext());
            mIcon.setScaleType(mScaleType);
            addView(mIcon, LP.frame(width, height, Gravity.CENTER_VERTICAL)
                    .setMargins(margin, margin, margin, margin)
                    .build());
        } else {
            mIcon.setLayoutParams(LP.frame(width, height, Gravity.CENTER_VERTICAL)
                    .setMargins(margin, margin, margin, margin)
                    .build());
        }
        mIcon.setImageResource(resId);
        mIcon.setActivated(super.isActivated());
    }
    public void setScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
        if (mIcon != null) {
            mIcon.setScaleType(scaleType);
        }
    }
    //---------------------------------------------------------------
    //endregion
}
