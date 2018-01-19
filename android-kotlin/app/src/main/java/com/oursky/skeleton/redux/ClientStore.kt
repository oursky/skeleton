package com.oursky.skeleton.redux

import redux.api.Reducer
import redux.api.Store

class ClientStore {
    // State
    data class State(
        val list: ArrayList<Int> = ArrayList()
    )
    // Actions
    sealed class Action {
        class Append(val value: Int): Action()
    }
    // Reducer
    companion object {
        private val reducer = Reducer<State> { state, action ->
            when (action) {
                is Action.Append -> {
                    val newstate = state.copy()
                    newstate.list.add(action.value)
                    newstate
                } else -> state
            }
        }
        fun createStore(): Store<State> {
            return redux.createStore(reducer, State())
        }
    }
}
