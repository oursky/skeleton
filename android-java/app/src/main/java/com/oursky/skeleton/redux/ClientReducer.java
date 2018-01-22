package com.oursky.skeleton.redux;

import com.oursky.skeleton.client.Login;
import com.oursky.skeleton.client.WebClient;
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

    // Auth Functions
    // ---------------------------------------------------------------------------------------------
    @AutoReducer.Action(value = ClientAction._Actions.LOGIN_BEGIN, from = ClientAction._Actions.class)
    ClientState loginBegin(ClientState state) {
        if (state.login.inprogress) return state;   // unchanged
        ClientState newstate = new ClientState(state);
        newstate.login = new ClientState.APIState<>(state.login);
        newstate.login.inprogress = true;
        return newstate;
    }
    @AutoReducer.Action(value = ClientAction._Actions.LOGIN_COMPLETE, from = ClientAction._Actions.class)
    ClientState loginComplete(ClientState state, WebClient.Result result, Login.Output data) {
        ClientState newstate = new ClientState(state);
        newstate.login = new ClientState.APIState<>();
        newstate.login.inprogress = false;
        newstate.login.result = result;
        newstate.login.data = data;
        return newstate;
    }
}
