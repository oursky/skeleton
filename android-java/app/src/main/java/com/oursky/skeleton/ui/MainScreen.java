package com.oursky.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import com.oursky.skeleton.R;
import com.oursky.skeleton.client.Login;
import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.model.MyLoginSession;
import com.oursky.skeleton.redux.AppState;
import com.oursky.skeleton.redux.AppStateObservable;
import com.oursky.skeleton.redux.ClientState;
import com.oursky.skeleton.ui.base.BaseController;
import com.oursky.skeleton.widget.ActionBar;
import com.oursky.skeleton.widget.Button;

import static com.oursky.skeleton.helper.ResourceHelper.dp;
import static com.oursky.skeleton.helper.ResourceHelper.color;
import static com.oursky.skeleton.helper.ResourceHelper.font;

public class MainScreen extends BaseController {
    private ActionBar mActionBar;
    private TextView mMessage;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    protected @NonNull
    View onCreateView(Context context) {
        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.VERTICAL);

        mActionBar = new ActionBar(context);
        mActionBar.setTitle(R.string.app_name);
        contentView.addView(mActionBar, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build());

        mMessage = new TextView(context);
        mMessage.setTextSize(24);
        mMessage.setTypeface(font(R.font.barlow_condensed_regular));
        contentView.addView(mMessage, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build());

        Button next = new Button(context);
        next.setTextPadding(dp(32), dp(12), dp(32), dp(12));
        next.setBackgroundColor(color(R.color.main_next_bg));
        next.setTextColor(color(R.color.main_next_text));
        next.setTypeface(font(R.font.barlow_condensed_bold));
        next.setTextSize(24);
        next.setText(R.string.main_next);
        contentView.addView(next, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT)
                                    .setMargins(dp(16), dp(16), dp(16), dp(16))
                                    .build());

        // Register event listener
        next.setOnClickListener(onNextClick);
        return contentView;
    }
    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        Observable<AppState> observable = AppStateObservable.create();
        mSubscriptions.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapTitle)
                .distinctUntilChanged()
                .subscribe(consumeTitle)
        );
        mSubscriptions.add(observable
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapLoginState)
                .subscribe(consumeLoginState)
        );
    }
    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mSubscriptions.clear();
    }
    //---------------------------------------------------------------
    //endregion

    //region UI Events
    //---------------------------------------------------------------
    private final View.OnClickListener onNextClick = (view) -> {
        pushController(new SecondScreen());
    };
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private final Function<AppState,String> mapTitle = (state) -> state.view().title;
    private final Consumer<String> consumeTitle = (title) -> {
        mActionBar.setTitle(title);
    };
    private final Function<AppState,ClientState.APIState<Login.Output>>
            mapLoginState = (state) -> state.client().login;
    private final Consumer<ClientState.APIState<Login.Output>> consumeLoginState = (mapped) -> {
        if (mapped.data == null || mapped.data.me == null) return;
        mMessage.setText(getResources().getString(R.string.main_message, mapped.data.me.name));
    };
    //---------------------------------------------------------------
    //endregion
}
