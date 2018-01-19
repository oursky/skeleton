package com.oursky.skeleton.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardHelper {
    fun showAndFocus(activity: Activity, view: View) {
        // Uncomment below if you want to skip with hardware keyboard
        // if (activity.getResources().getConfiguration().keyboard == Configuration.KEYBOARD_QWERTY) return;
        view.requestFocus()
        view.postDelayed({
            val focused = activity.currentFocus
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(focused, InputMethodManager.SHOW_FORCED)
        }, 500)
        // NOTE: We add a delay here to resolve a race condition which transit from A screen to B screen,
        // where A's onDetach() dismiss keyboard and B's onAttach() want to show keyboard
        // Since we cannot assume the lifecycle managed by Conductor, we add a delay here to make sure A is gone and the keyboard will be shown.
    }
    fun hide(activity: Activity) {
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) view = activity.window.decorView
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
    fun setAdjustMode(activity: Activity, mode: Int) {
        activity.window.setSoftInputMode(mode)
    }
}
