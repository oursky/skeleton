package com.oursky.skeleton.helper;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexboxLayout;

//! LayoutParams Builder
@SuppressWarnings("WeakerAccess")
public class LP {
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    private static int toSpec(int value) {
        switch (value) {
            case 0:
            case MATCH_PARENT:
            case WRAP_CONTENT:
                return value;
            default:
                if (Build.VERSION.SDK_INT >= 21) {  // 5.0
                    return View.MeasureSpec.makeMeasureSpec(value, View.MeasureSpec.EXACTLY);
                } else {
                    return value;                   // Android 4 bugged with makeMeasureSpec
                }
        }
    }
    // ViewGroup
    public static class ViewGroupLayoutBuilder {
        private ViewGroup.LayoutParams mParams;
        ViewGroupLayoutBuilder(int width, int height) {
            mParams = new ViewGroup.LayoutParams(toSpec(width), toSpec(height));
        }
        public ViewGroup.LayoutParams build() {
            return mParams;
        }
    }
    // FrameLayout
    public static class FrameLayoutBuilder {
        private FrameLayout.LayoutParams mParams;
        FrameLayoutBuilder(int width, int height, int gravity) {
            mParams = new FrameLayout.LayoutParams(toSpec(width), toSpec(height), gravity);
        }
        public FrameLayoutBuilder setMargins(int left, int top, int right, int bottom) {
            mParams.setMargins(left, top, right, bottom);
            return this;
        }
        public FrameLayoutBuilder setGravity(int gravity) {
            mParams.gravity = gravity;
            return this;
        }
        public FrameLayout.LayoutParams build() {
            return mParams;
        }
    }
    // LinearLayout
    public static class LinearLayoutBuilder {
        private LinearLayout.LayoutParams mParams;
        LinearLayoutBuilder(int width, int height) {
            mParams = new LinearLayout.LayoutParams(toSpec(width), toSpec(height));
        }
        LinearLayoutBuilder(int width, int height, int gravity) {
            mParams = new LinearLayout.LayoutParams(toSpec(width), toSpec(height));
            mParams.gravity = gravity;
        }
        public LinearLayoutBuilder setWeight(int weight) {
            mParams.weight = weight;
            return this;
        }
        public LinearLayoutBuilder setMargins(int left, int top, int right, int bottom) {
            mParams.setMargins(left, top, right, bottom);
            return this;
        }
        public LinearLayoutBuilder setGravity(int gravity) {
            mParams.gravity = gravity;
            return this;
        }
        public LinearLayout.LayoutParams build() {
            return mParams;
        }
    }
    // Relative
    public static class RelativeLayoutBuilder {
        private RelativeLayout.LayoutParams mParams;
        RelativeLayoutBuilder(int width, int height) {
            mParams = new RelativeLayout.LayoutParams(width, height);
        }
        public RelativeLayoutBuilder setMargins(int left, int top, int right, int bottom) {
            mParams.setMargins(left, top, right, bottom);
            return this;
        }
        public RelativeLayoutBuilder alignParentEnd() {
            mParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            return this;
        }
        public RelativeLayoutBuilder alignParentBottom() {
            mParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
            return this;
        }
        public RelativeLayoutBuilder toLeftOf(int id) {
            mParams.addRule(RelativeLayout.LEFT_OF, id);
            return this;
        }
        public RelativeLayoutBuilder toRightOf(int id) {
            mParams.addRule(RelativeLayout.RIGHT_OF, id);
            return this;
        }
        public RelativeLayout.LayoutParams build() {
            return mParams;
        }
    }
    // Flexbox
    public static class FlexboxLayoutBuilder {
        private FlexboxLayout.LayoutParams mParams;
        FlexboxLayoutBuilder(int width, int height) {
            mParams = new FlexboxLayout.LayoutParams(toSpec(width), toSpec(height));
        }
        public FlexboxLayoutBuilder setMargins(int left, int top, int right, int bottom) {
            mParams.setMargins(left, top, right, bottom);
            return this;
        }
        public FlexboxLayoutBuilder setBasisPercent(float percent) {
            mParams.setFlexBasisPercent(percent);
            return this;
        }
        public FlexboxLayoutBuilder setAlignSelf(int value) {
            mParams.setAlignSelf(value);
            return this;
        }
        public FlexboxLayout.LayoutParams build() {
            return mParams;
        }
    }
    public static ViewGroupLayoutBuilder vg(int width, int height) {
        return new ViewGroupLayoutBuilder(width, height);
    }
    public static FrameLayoutBuilder frame(int width, int height) {
        return new FrameLayoutBuilder(width, height, 0);
    }
    public static FrameLayoutBuilder frame(int width, int height, int gravity) {
        return new FrameLayoutBuilder(width, height, gravity);
    }
    public static LinearLayoutBuilder linear(int width, int height) {
        return new LinearLayoutBuilder(width, height);
    }
    public static LinearLayoutBuilder linear(int width, int height, int gravity) {
        return new LinearLayoutBuilder(width, height, gravity);
    }
    public static RelativeLayoutBuilder relative(int width, int height) {
        return new RelativeLayoutBuilder(width, height);
    }
    public static FlexboxLayoutBuilder flexbox(int width, int height) {
        return new FlexboxLayoutBuilder(width, height);
    }
}
