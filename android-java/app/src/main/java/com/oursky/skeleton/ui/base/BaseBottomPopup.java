package com.oursky.skeleton.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.oursky.skeleton.R;
import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.helper.ViewTransition;

import static com.oursky.skeleton.helper.ResourceHelper.color;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseBottomPopup extends FrameLayout {
    private static final int ANIMATION_DURATION = 250;
    private View mDimBackground, mContentView;
    private boolean mVisible;
    private boolean mInTransition;

    protected abstract @NonNull View onCreateView(Context context);
    protected void onAttach(@NonNull View view) {}
    protected void onDetach(@NonNull View view) {}

    //region Lifecycle
    //---------------------------------------------------------------
    public BaseBottomPopup(Context context) {
        super(context);
    }
    public BaseBottomPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BaseBottomPopup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    // NOTE: It is bad to call abstract function from constructor, as child's member is not properly instantiated during super construction execution.
    //       So, We will do raii to create our view before onAttach
    private void raii() {
        if (mContentView!=null) return;
        final Context context = getContext();
        mVisible = false;
        mInTransition = false;
        mDimBackground = new View(context);
        mDimBackground.setBackgroundColor(color(R.color.immersive_background));
        mDimBackground.setClickable(true);
        addView(mDimBackground, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build());
        mContentView = onCreateView(context);
        addView(mContentView, LP.frame(LP.MATCH_PARENT, LP.WRAP_CONTENT, Gravity.BOTTOM).build());
        mDimBackground.setVisibility(INVISIBLE);
        mContentView.setVisibility(INVISIBLE);
    }
    //---------------------------------------------------------------
    //endregion

    //region Attach / Detach
    //---------------------------------------------------------------
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        raii();
        onAttach(mContentView);
    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach(mContentView);
    }
    //---------------------------------------------------------------
    //endregion

    //region Visibility
    //---------------------------------------------------------------
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mInTransition || super.onInterceptTouchEvent(ev);
    }
    public boolean isVisible() {
        return mVisible;
    }
    public void show(boolean visible) {
        if (mInTransition || mVisible==visible) return;
        mVisible = visible;
        if (mVisible) {
            mInTransition = true;
            ViewTransition.fadeIn(mDimBackground, ANIMATION_DURATION, () -> mInTransition = false);
            ViewTransition.slideIn(mContentView, ViewTransition.Direction.UP, mContentView.getHeight(), ANIMATION_DURATION, null);
        } else {
            mInTransition = true;
            ViewTransition.fadeOut(mDimBackground, ANIMATION_DURATION, () -> mInTransition = false);
            ViewTransition.slideOut(mContentView, ViewTransition.Direction.DOWN, mContentView.getHeight(), ANIMATION_DURATION, null);
        }
    }
    //---------------------------------------------------------------
    //endregion
}
