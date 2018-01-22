package com.oursky.skeleton.redux

import redux.api.Reducer
import redux.api.Store

class ViewStore {
    // State
    data class State(
        var title: String = ""
    )
    // Actions
    sealed class Action {
        class SetTitle(val title: String) : Action()
    }
    companion object {
        // Reducer
        private val reducer = Reducer<State> { state, action ->
            when (action) {
                is Action.SetTitle -> {
                    state.copy(title = action.title)
                } else -> state
            }
        }
        fun createStore(): Store<State> {
            return redux.createStore(reducer, State())
        }
    }
}
