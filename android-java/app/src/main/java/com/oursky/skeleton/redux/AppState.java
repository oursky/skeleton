package com.oursky.skeleton.redux;

import com.yheriatovych.reductor.annotations.CombinedState;

@CombinedState
public interface AppState {
    ViewState view();
    ClientState client();
}
