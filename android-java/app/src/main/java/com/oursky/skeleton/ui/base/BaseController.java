package com.oursky.skeleton.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;

import com.oursky.skeleton.helper.LP;

//! Base class for a screen page
@SuppressWarnings("WeakerAccess")
public abstract class BaseController extends Controller {
    //region Lifecycle
    //---------------------------------------------------------------
    public BaseController() {
        super();
    }
    public BaseController(Bundle args) {
        super(args);
    }
    @Override
    final protected @NonNull View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final Context context = container.getContext();
        View view = onCreateView(context);
        BlockableFrame blockable = new BlockableFrame(context);
        blockable.addView(view, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build());
        return blockable;
    }
    protected abstract @NonNull View onCreateView(Context context);
    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
    }
    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
    }
    //---------------------------------------------------------------
    //endregion

    //region Block clicks while transition
    //---------------------------------------------------------------
    @Override
    protected void onChangeStarted(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeStarted(changeHandler, changeType);
        View view = getView();
        if (view!=null && view instanceof BlockableFrame) ((BlockableFrame)view).setBlockTouch(true);
    }
    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);
        View view = getView();
        if (view!=null && view instanceof BlockableFrame) ((BlockableFrame)view).setBlockTouch(false);
    }
    private static class BlockableFrame extends FrameLayout {
        private boolean mBlockTouch;
        public BlockableFrame(Context context) {
            super(context);
            ctor(context, null, 0);
        }
        public BlockableFrame(Context context, AttributeSet attrs) {
            super(context, attrs);
            ctor(context, attrs, 0);
        }
        public BlockableFrame(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            ctor(context, attrs, defStyle);
        }
        private void ctor(Context context, AttributeSet attrs, int defStyle) {
            mBlockTouch = false;
        }
        public void setBlockTouch(boolean b) {
            mBlockTouch = b;
        }
        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return mBlockTouch || super.onInterceptTouchEvent(ev);
        }
    }
    //---------------------------------------------------------------
    //endregion

    //region Navigation
    //---------------------------------------------------------------
    protected void popController() {
        getRouter().popCurrentController();
    }
    protected void pushController(@NonNull Controller controller) {
        getRouter().pushController(RouterTransaction.with(controller)
                .pushChangeHandler(new HorizontalChangeHandler())
                .popChangeHandler(new HorizontalChangeHandler())
        );
    }
    protected void pushController(@NonNull Controller controller, @NonNull ControllerChangeHandler pushEffect, @NonNull ControllerChangeHandler popEffect) {
        getRouter().pushController(RouterTransaction.with(controller)
                .pushChangeHandler(pushEffect)
                .popChangeHandler(popEffect)
        );
    }
    protected void topController(@NonNull Controller controller, @NonNull ControllerChangeHandler pushEffect, @NonNull ControllerChangeHandler popEffect) {
        getRouter().replaceTopController(RouterTransaction.with(controller)
                .pushChangeHandler(pushEffect)
                .popChangeHandler(popEffect)
        );
    }
    //---------------------------------------------------------------
    //endregion
}
