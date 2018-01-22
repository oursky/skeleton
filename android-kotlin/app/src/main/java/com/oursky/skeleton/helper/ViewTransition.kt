package com.oursky.skeleton.helper

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

@Suppress("unused", "MemberVisibilityCanBePrivate")
object ViewTransition {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    //region fade
    //---------------------------------------------------------------
    fun fadeIn(v: View, duration: Int, cb: (() -> Unit)? = null) {
        v.alpha = 0f
        v.visibility = View.VISIBLE
        val ani = ObjectAnimator.ofFloat(v, "alpha", 1.0f)
        ani.duration = duration.toLong()
        if (cb != null) {
            ani.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    cb.invoke()
                }
                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
        }
        ani.start()
    }
    fun fadeOut(v: View, duration: Int, cb: (() -> Unit)? = null) {
        val ani = ObjectAnimator.ofFloat(v, "alpha", 0.0f)
        ani.duration = duration.toLong()
        ani.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                v.visibility = View.INVISIBLE
                v.alpha = 1.0f
                cb?.invoke()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        ani.start()
    }
    fun fade(from: View, to: View, duration: Int, cb: (() -> Unit)? = null) {
        fadeOut(from, duration, null)
        fadeIn(to, duration, cb)
    }
    //---------------------------------------------------------------
    //endregion

    //region slide
    //---------------------------------------------------------------
    fun slideIn(v: View, dir: Direction, translation: Int, duration: Int, cb: (() -> Unit)? = null) {
        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                cb?.invoke()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        }
        when (dir) {
            ViewTransition.Direction.UP -> {
                v.translationX = 0f
                v.translationY = translation.toFloat()
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f)
                trans.duration = duration.toLong()
                if (cb != null) {
                    trans.addListener(animatorListener)
                }
                trans.start()
            }
            ViewTransition.Direction.DOWN -> {
                v.translationX = 0f
                v.translationY = (-translation).toFloat()
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f)
                trans.duration = duration.toLong()
                if (cb != null) {
                    trans.addListener(animatorListener)
                }
                trans.start()
            }
            ViewTransition.Direction.LEFT -> {
                v.translationX = translation.toFloat()
                v.translationY = 0f
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f)
                trans.duration = duration.toLong()
                if (cb != null) {
                    trans.addListener(animatorListener)
                }
                trans.start()
            }
            ViewTransition.Direction.RIGHT -> {
                v.translationX = (-translation).toFloat()
                v.translationY = 0f
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f)
                trans.duration = duration.toLong()
                if (cb != null) {
                    trans.addListener(animatorListener)
                }
                trans.start()
            }
        }
    }
    fun slideOut(v: View, dir: Direction, translation: Int, duration: Int, cb: (() -> Unit)? = null) {
        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                v.visibility = View.INVISIBLE
                v.translationX = 0f
                v.translationY = 0f
                if (cb != null) cb()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        }
        v.translationX = 0f
        v.translationY = 0f
        when (dir) {
            ViewTransition.Direction.UP -> {
                val trans = ObjectAnimator.ofFloat(v, "translationY", -translation.toFloat())
                trans.duration = duration.toLong()
                trans.addListener(animatorListener)
                trans.start()
            }
            ViewTransition.Direction.DOWN -> {
                val trans = ObjectAnimator.ofFloat(v, "translationY", translation.toFloat())
                trans.duration = duration.toLong()
                trans.addListener(animatorListener)
                trans.start()
            }
            ViewTransition.Direction.LEFT -> {
                val trans = ObjectAnimator.ofFloat(v, "translationX", -translation.toFloat())
                trans.duration = duration.toLong()
                trans.addListener(animatorListener)
                trans.start()
            }
            ViewTransition.Direction.RIGHT -> {
                val trans = ObjectAnimator.ofFloat(v, "translationX", translation.toFloat())
                trans.duration = duration.toLong()
                trans.addListener(animatorListener)
                trans.start()
            }
        }
    }
    fun slide(from: View, to: View, dir: Direction, translation: Int, duration: Int, cb: (() -> Unit)?) {
        slideOut(from, dir, translation, duration, null)
        slideIn(to, dir, translation, duration, cb)
    }
    //---------------------------------------------------------------
    //endregion

    //region fadeslide
    //---------------------------------------------------------------
    fun fadeslideIn(v: View, dir: Direction, translation: Int, duration: Int, cb: (() -> Unit)? = null) {
        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                cb?.invoke()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        }
        when (dir) {
            ViewTransition.Direction.UP -> {
                v.translationX = 0f
                v.translationY = translation.toFloat()
                v.alpha = 0f
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f)
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                if (cb != null) {
                    aniSet.addListener(animatorListener)
                }
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
            ViewTransition.Direction.DOWN -> {
                v.translationX = 0f
                v.translationY = (-translation).toFloat()
                v.alpha = 0f
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationY", 0.0f)
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                if (cb != null) {
                    aniSet.addListener(animatorListener)
                }
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
            ViewTransition.Direction.LEFT -> {
                v.translationX = translation.toFloat()
                v.translationY = 0f
                v.alpha = 0f
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f)
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                if (cb != null) {
                    aniSet.addListener(animatorListener)
                }
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
            ViewTransition.Direction.RIGHT -> {
                v.translationX = (-translation).toFloat()
                v.translationY = 0f
                v.alpha = 0f
                v.visibility = View.VISIBLE
                val trans = ObjectAnimator.ofFloat(v, "translationX", 0.0f)
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                if (cb != null) {
                    aniSet.addListener(animatorListener)
                }
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
        }
    }
    fun fadeslideOut(v: View, dir: Direction, translation: Int, duration: Int, cb: (() -> Unit)? = null) {
        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                v.visibility = View.INVISIBLE
                v.translationX = 0f
                v.translationY = 0f
                v.alpha = 1.0f
                cb?.invoke()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        }
        v.translationX = 0f
        v.translationY = 0f
        v.alpha = 1.0f
        when (dir) {
            ViewTransition.Direction.UP -> {
                val trans = ObjectAnimator.ofFloat(v, "translationY", -translation.toFloat())
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                aniSet.addListener(animatorListener)
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
            ViewTransition.Direction.DOWN -> {
                val trans = ObjectAnimator.ofFloat(v, "translationY", translation.toFloat())
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                aniSet.addListener(animatorListener)
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
            ViewTransition.Direction.LEFT -> {
                val trans = ObjectAnimator.ofFloat(v, "translationX", -translation.toFloat())
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                aniSet.addListener(animatorListener)
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
            ViewTransition.Direction.RIGHT -> {
                val trans = ObjectAnimator.ofFloat(v, "translationX", translation.toFloat())
                val alpha = ObjectAnimator.ofFloat(v, "alpha", 0.0f)
                trans.duration = duration.toLong()
                alpha.duration = duration.toLong()
                val aniSet = AnimatorSet()
                aniSet.addListener(animatorListener)
                aniSet.play(trans).with(alpha)
                aniSet.start()
            }
        }
    }
    fun fadeslide(from: View, to: View, dir: Direction, translation: Int, duration: Int, cb: (() -> Unit)? = null) {
        fadeslideOut(from, dir, translation, duration, null)
        fadeslideIn(to, dir, translation, duration, cb)
    }
    //---------------------------------------------------------------
    //endregion
}
