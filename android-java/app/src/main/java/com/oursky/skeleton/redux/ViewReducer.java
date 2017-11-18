package com.oursky.skeleton.redux;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

@AutoReducer
public abstract class ViewReducer implements Reducer<ViewState> {
    public static ViewReducer create() {
        return new ViewReducerImpl();
    }
    @AutoReducer.InitialState
    ViewState initialState() {
        return new ViewState();
    }

    // ---------------------------------------------------------------------------------------------
    @AutoReducer.Action(value = ViewAction._Actions.SETTITLE, from = ViewAction._Actions.class)
    ViewState setTitle(ViewState state, String title) {
        if (state.title.equals(title)) return state;   // unchanged
        ViewState newstate = new ViewState(state);
        newstate.title = title;
        return newstate;
    }
}
