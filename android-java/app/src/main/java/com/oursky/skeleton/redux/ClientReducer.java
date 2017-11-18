package com.oursky.skeleton.redux;

import java.util.ArrayList;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

@AutoReducer
public abstract class ClientReducer implements Reducer<ClientState> {
    public static ClientReducer create() {
        return new ClientReducerImpl();
    }
    @AutoReducer.InitialState
    ClientState initialState() {
        return new ClientState();
    }

    // Main Page (Switch with Bottom-Bar)
    // ---------------------------------------------------------------------------------------------
    @AutoReducer.Action(value = ClientAction._Actions.ADDVALUE, from = ClientAction._Actions.class)
    ClientState addValue(ClientState state, int value) {
        ClientState newstate = new ClientState(state);
        newstate.list = new ArrayList<>(state.list);    // force changing pointer
        newstate.list.add(value);
        return newstate;
    }
}
