package com.oursky.skeleton.redux

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import redux.api.Store

class AppStore {
    var client = ClientStore.createStore()
    var view = ViewStore.createStore()

    fun dispatch(action: Any) {
        client.dispatch(action)
        view.dispatch(action)
    }
    fun<S> observe(store: Store<S>): Observable<S> {
        return Observable.create<S>(object : ObservableOnSubscribe<S> {
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
}