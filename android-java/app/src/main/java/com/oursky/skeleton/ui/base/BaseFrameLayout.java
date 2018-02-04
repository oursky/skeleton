package com.oursky.skeleton.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseFrameLayout extends FrameLayout {
    // delegate
    protected abstract void onAttach(@NonNull View view);
    protected abstract void onDetach(@NonNull View view);

    //region Lifecycle
    //---------------------------------------------------------------
    public BaseFrameLayout(Context context) {
        super(context);
    }
    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    //---------------------------------------------------------------
    //endregion

    //region Attach / Detach
    //---------------------------------------------------------------
    @Override
    final public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttach(this);
    }
    @Override
    final public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach(this);
    }
    //---------------------------------------------------------------
    //endregion
}
