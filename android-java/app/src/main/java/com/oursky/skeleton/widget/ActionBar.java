package com.oursky.skeleton.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oursky.skeleton.R;
import com.oursky.skeleton.helper.LP;

import static com.oursky.skeleton.helper.ResourceHelper.dimen;
import static com.oursky.skeleton.helper.ResourceHelper.dp;
import static com.oursky.skeleton.helper.ResourceHelper.font;
import static com.oursky.skeleton.helper.ResourceHelper.color;

public class ActionBar extends LinearLayout {
    private LinearLayout mLeft, mRight;
    private FrameLayout mCenter;
    private TextView mTitle;

    //region Lifecycle
    //---------------------------------------------------------------
    public ActionBar(Context context) {
        super(context);
        ctor(context, null, 0);
    }
    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctor(context, attrs, 0);
    }
    public ActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctor(context, attrs, defStyle);
    }
    private void ctor(Context context, AttributeSet attrs, int defStyle) {
        LinearLayout bar = new LinearLayout(context);
        bar.setPadding(dp(4), dp(4), dp(4), dp(4));
        bar.setOrientation(HORIZONTAL);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        mLeft = new LinearLayout(context);
        mLeft.setOrientation(HORIZONTAL);
        mLeft.setGravity(Gravity.CENTER_VERTICAL);
        bar.addView(mLeft, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build());
        mCenter = new FrameLayout(context);
        bar.addView(mCenter, LP.linear(0, LP.WRAP_CONTENT)
                           .setWeight(1)
                           .build());
        mRight = new LinearLayout(context);
        mRight.setOrientation(HORIZONTAL);
        mRight.setGravity(Gravity.CENTER_VERTICAL);
        bar.addView(mRight, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build());

        View line = new View(context);
        line.setBackgroundColor(0x60000000);

        setOrientation(VERTICAL);
        addView(bar, LP.linear(LP.MATCH_PARENT, dimen(R.dimen.actionbar_height)).build());
        addView(line, LP.linear(LP.MATCH_PARENT, 1).build());
    }
    //---------------------------------------------------------------
    //endregion

    //region Title
    //---------------------------------------------------------------
    public void raiiTitle() {
        if (mTitle != null) return;
        mTitle = new TextView(getContext());
        mTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        mTitle.setTypeface(font(R.font.barlow_condensed_medium));
        mTitle.setTextColor(color(R.color.actionbar_title));
        mTitle.setTextSize(20);
        mCenter.addView(mTitle, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER)
                                  .build());
    }
    public void setTitle(@StringRes int title) {
        raiiTitle();
        mTitle.setText(title);
    }
    public void setTitle(String title) {
        raiiTitle();
        mTitle.setText(title);
    }
    public void setTitleView(View v) {
        mCenter.addView(mTitle, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER)
                                  .build());
    }
    //---------------------------------------------------------------
    //endregion

    //region Button
    //---------------------------------------------------------------
    public void addLeftButton(@DrawableRes int icon, OnClickListener listener) {
        Button button = new Button(getContext());
        button.setIcon(icon, dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon));
        button.setOnClickListener(listener);
        mLeft.addView(button, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                                .setMargins(0, 0, dp(16), 0)
                                .build());
    }
    public void addLeftPadding() {
        View view = new View(getContext());
        mLeft.addView(view, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                              .setMargins(0, 0, dp(16), 0)
                              .build());
    }
    public void addRightButton(@DrawableRes int icon, OnClickListener listener) {
        Button button = new Button(getContext());
        button.setIcon(icon, dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon));
        button.setOnClickListener(listener);
        mRight.addView(button, 0, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                                           .setMargins(0, 0, dp(16), 0)
                                           .build());
    }
    public void addRightPadding() {
        View view = new View(getContext());
        mRight.addView(view, 0, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                                         .setMargins(0, 0, dp(16), 0)
                                         .build());
    }
    //---------------------------------------------------------------
    //endregion
}
