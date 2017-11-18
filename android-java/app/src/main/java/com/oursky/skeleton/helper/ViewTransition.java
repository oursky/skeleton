package com.oursky.skeleton.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.view.View;

@SuppressWarnings("WeakerAccess")
public class ViewTransition {
    public interface OnCompleteListener {
        void onComplete();
    }
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    //region fade
    //---------------------------------------------------------------
    public static void fadeIn(final View v, int duration, final @Nullable OnCompleteListener cb) {
        v.setAlpha(0);
        v.setVisibility(View.VISIBLE);
        ObjectAnimator ani = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
        ani.setDuration(duration);
        if (cb!=null) {
            ani.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }
                @Override
                public void onAnimationEnd(Animator animator) {
                    cb.onComplete();
                }
                @Override
                public void onAnimationCancel(Animator animator) {
                }
                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
        ani.start();
    }
    public static void fadeOut(final View v, int duration, final @Nullable OnCompleteListener cb) {
        ObjectAnimator ani = ObjectAnimator.ofFloat(v, "alpha", 0.0f);
        ani.setDuration(duration);
        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                v.setVisibility(View.INVISIBLE);
                v.setAlpha(1.0f);
                if (cb!=null) cb.onComplete();
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        ani.start();
    }
    public static void fade(View from, View to, int duration, final @Nullable OnCompleteListener cb) {
        fadeOut(from, duration, null);
        fadeIn(to, duration, cb);
    }
    //---------------------------------------------------------------
    //endregion

    //region slide
    //---------------------------------------------------------------
    public static void slideIn(final View v, Direction dir, int translation, int duration, final @Nullable OnCompleteListener cb) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                if (cb!=null) cb.onComplete();
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        switch (dir) {
            case UP: {
                v.setTranslationX(0);
                v.setTranslationY(translation);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f);
                trans.setDuration(duration);
                if (cb!=null) {
                    trans.addListener(animatorListener);
                }
                trans.start();
                break;
            }
            case DOWN: {
                v.setTranslationX(0);
                v.setTranslationY(-translation);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f);
                trans.setDuration(duration);
                if (cb!=null) {
                    trans.addListener(animatorListener);
                }
                trans.start();
                break;
            }
            case LEFT: {
                v.setTranslationX(translation);
                v.setTranslationY(0);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f);
                trans.setDuration(duration);
                if (cb!=null) {
                    trans.addListener(animatorListener);
                }
                trans.start();
                break;
            }
            case RIGHT: {
                v.setTranslationX(-translation);
                v.setTranslationY(0);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f);
                trans.setDuration(duration);
                if (cb!=null) {
                    trans.addListener(animatorListener);
                }
                trans.start();
                break;
            }
        }
    }
    public static void slideOut(final View v, Direction dir, int translation, int duration, final @Nullable OnCompleteListener cb) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                v.setVisibility(View.INVISIBLE);
                v.setTranslationX(0);
                v.setTranslationY(0);
                if(cb!=null) cb.onComplete();
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        v.setTranslationX(0);
        v.setTranslationY(0);
        switch (dir) {
            case UP: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", -translation);
                trans.setDuration(duration);
                trans.addListener(animatorListener);
                trans.start();
                break;
            }
            case DOWN: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", translation);
                trans.setDuration(duration);
                trans.addListener(animatorListener);
                trans.start();
                break;
            }
            case LEFT: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", -translation);
                trans.setDuration(duration);
                trans.addListener(animatorListener);
                trans.start();
                break;
            }
            case RIGHT: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", translation);
                trans.setDuration(duration);
                trans.addListener(animatorListener);
                trans.start();
                break;
            }
        }
    }
    public static void slide(View from, View to, Direction dir, int translation, int duration, final @Nullable OnCompleteListener cb) {
        slideOut(from, dir, translation, duration, null);
        slideIn(to, dir, translation, duration, cb);
    }
    //---------------------------------------------------------------
    //endregion

    //region fadeslide
    //---------------------------------------------------------------
    public static void fadeslideIn(final View v, Direction dir, int translation, int duration, final @Nullable OnCompleteListener cb) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                if (cb!=null) cb.onComplete();
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        switch (dir) {
            case UP: {
                v.setTranslationX(0);
                v.setTranslationY(translation);
                v.setAlpha(0);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                if (cb!=null) {
                    aniSet.addListener(animatorListener);
                }
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
            case DOWN: {
                v.setTranslationX(0);
                v.setTranslationY(-translation);
                v.setAlpha(0);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                if (cb!=null) {
                    aniSet.addListener(animatorListener);
                }
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
            case LEFT: {
                v.setTranslationX(translation);
                v.setTranslationY(0);
                v.setAlpha(0);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                if (cb!=null) {
                    aniSet.addListener(animatorListener);
                }
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
            case RIGHT: {
                v.setTranslationX(-translation);
                v.setTranslationY(0);
                v.setAlpha(0);
                v.setVisibility(View.VISIBLE);
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                if (cb!=null) {
                    aniSet.addListener(animatorListener);
                }
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
        }
    }
    public static void fadeslideOut(final View v, Direction dir, int translation, int duration, final @Nullable OnCompleteListener cb) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                v.setVisibility(View.INVISIBLE);
                v.setTranslationX(0);
                v.setTranslationY(0);
                v.setAlpha(1.0f);
                if(cb!=null) cb.onComplete();
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        v.setTranslationX(0);
        v.setTranslationY(0);
        v.setAlpha(1.0f);
        switch (dir) {
            case UP: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", -translation);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.addListener(animatorListener);
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
            case DOWN: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", translation);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.addListener(animatorListener);
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
            case LEFT: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", -translation);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.addListener(animatorListener);
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
            case RIGHT: {
                ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationX", translation);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f);
                trans.setDuration(duration);
                alpha.setDuration(duration);
                AnimatorSet aniSet = new AnimatorSet();
                aniSet.addListener(animatorListener);
                aniSet.play(trans).with(alpha);
                aniSet.start();
                break;
            }
        }
    }
    public static void fadeslide(View from, View to, Direction dir, int translation, int duration, final OnCompleteListener cb) {
        fadeslideOut(from, dir, translation, duration, null);
        fadeslideIn(to, dir, translation, duration, cb);
    }
    //---------------------------------------------------------------
    //endregion
}
