package com.oursky.skeleton.redux

import android.content.Context
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import redux.api.Store

@Suppress("unused", "UNUSED_PARAMETER")
class AppStore {
    var client = ClientStore.createStore()
    var view = ViewStore.createStore()

    //region Pesistence
    fun load(context: Context) {
        // TODO: Load from persistent stoage and fire restore actions
    }
    fun save(context: Context) {
        // TODO: Save state to persistent stoage
    }
    //endregion

    //region Redux glue code
    fun dispatch(action: Any) {
        when (action) {
            is ClientStore.Action -> client.dispatch(action)
            is ViewStore.Action -> view.dispatch(action)
        }
    }
    fun <S> observe(store: Store<S>): Observable<S> {
        return Observable.create(object : ObservableOnSubscribe<S> {
            private var mSubscribe: Store.Subscription? = null
            @Throws(Exception::class)
            override fun subscribe(emitter: ObservableEmitter<S>) {
                mSubscribe = store.subscribe({ emitter.onNext(store.state) })
                emitter.setCancellable {
                    if (mSubscribe != null) {
                        mSubscribe!!.unsubscribe()
                        mSubscribe = null
                    }
                }
                // Sync initial state
                emitter.onNext(store.state)
            }
        })
    }
    //endregion
}
