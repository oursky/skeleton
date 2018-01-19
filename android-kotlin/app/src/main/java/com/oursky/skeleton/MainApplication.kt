package com.oursky.skeleton

import android.app.Application
import android.content.Context
import com.oursky.skeleton.helper.ResourceHelper
import com.oursky.skeleton.redux.*
import java.lang.ref.WeakReference

class MainApplication : Application() {
    //region Lifecycle
    //---------------------------------------------------------------
    override fun onCreate() {
        super.onCreate()
        ResourceHelper.context = WeakReference(applicationContext)
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux Store
    //---------------------------------------------------------------
    companion object {
        private var storeRef: Int = 0
        var store: AppStore? = null
        fun retainStore(context: Context) {
            if (storeRef == 0) {
                store = AppStore()
                // ReduxPersistence.load()
            }
            storeRef++
        }
        fun releaseStore(context: Context) {
            storeRef--
            if (storeRef == 0) {
                // ReduxPersistence.save()
                store = null
            }
        }
    }
    //---------------------------------------------------------------
    //endregion
}