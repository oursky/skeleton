package com.oursky.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.oursky.skeleton.R;
import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.helper.Logger;
import com.oursky.skeleton.ui.base.AppBottomPopup;
import com.oursky.skeleton.widget.Button;
import com.oursky.skeleton.widget.Checkbox;

import static com.oursky.skeleton.helper.ResourceHelper.dp;
import static com.oursky.skeleton.helper.ResourceHelper.color;
import static com.oursky.skeleton.helper.ResourceHelper.font;

public class DummyPopup extends AppBottomPopup {
    public interface EventListener {
        void onClose(DummyPopup view);
    }
    private EventListener mListener;

    //region Lifecycle
    //---------------------------------------------------------------
    public DummyPopup(Context context) {
        super(context);
    }
    public DummyPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public DummyPopup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected @NonNull View onCreateView(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setBackgroundColor(color(R.color.white));

        Checkbox checkbox = new Checkbox(context);
        checkbox.setText(R.string.dummy_check);
        checkbox.setTypeface(font(R.font.barlow_condensed_regular));
        checkbox.setTextSize(20);
        checkbox.setButtonDrawable(R.drawable.ic_checkbox, dp(28), dp(28));
        layout.addView(checkbox, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT)
                .setMargins(0, dp(32), 0, dp(32))
                .build());

        Button close = new Button(context);
        close.setTextPadding(dp(16), dp(8), dp(16), dp(8));
        close.setBackgroundColor(color(R.color.main_next_bg));
        close.setTextColor(color(R.color.main_next_text));
        close.setTypeface(font(R.font.barlow_condensed_bold));
        close.setTextSize(24);
        close.setText(R.string.dummy_close);
        layout.addView(close, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT)
                                .setMargins(0, dp(96), 0, dp(96))
                                .build());

        // Register event listener
        checkbox.setOnCheckedChangeListener(onCheckToggle);
        close.setOnClickListener(onCloseClick);
        return layout;
    }
    //---------------------------------------------------------------
    //endregion

    public void setListener(EventListener listener) {
        mListener = listener;
    }

    //region UI Events
    //---------------------------------------------------------------
    private final View.OnClickListener onCloseClick = (view) -> {
        if (mListener!=null) mListener.onClose(DummyPopup.this);
    };
    private final Checkbox.OnCheckedChangeListener onCheckToggle = (view, isChecked) -> {
        Logger.d("DummyPopup", "Checkbox: " + (isChecked?"YES":"NO"));
    };
    //---------------------------------------------------------------
    //endregion

}
