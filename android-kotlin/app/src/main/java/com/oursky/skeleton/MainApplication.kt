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
        private var sStoreRef: Int = 0
        var sStore: AppStore? = null
        fun retainStore(context: Context) {
            if (sStoreRef == 0) {
                sStore = AppStore()
                sStore?.load(context)
            }
            sStoreRef++
        }
        fun releaseStore(context: Context) {
            sStoreRef--
            if (sStoreRef == 0) {
                sStore?.save(context)
                sStore = null
            }
        }
        fun store(): AppStore {
            return sStore!!
        }
    }
    //---------------------------------------------------------------
    //endregion
}