package com.oursky.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import com.oursky.skeleton.R;
import com.oursky.skeleton.client.Login;
import com.oursky.skeleton.helper.KeyboardHelper;
import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.redux.AppState;
import com.oursky.skeleton.redux.AppStateObservable;
import com.oursky.skeleton.redux.ClientAction;
import com.oursky.skeleton.redux.ClientState;
import com.oursky.skeleton.ui.base.BaseController;
import com.oursky.skeleton.widget.ActionBar;
import com.oursky.skeleton.widget.Button;

import static com.oursky.skeleton.MainApplication.store;
import static com.oursky.skeleton.helper.ResourceHelper.color;
import static com.oursky.skeleton.helper.ResourceHelper.dp;
import static com.oursky.skeleton.helper.ResourceHelper.font;

public class LoginScreen extends BaseController {
    private CompositeDisposable mSubscriptions = new CompositeDisposable();
    private EditText mName, mPass;
    private Button mSubmit;

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    protected @NonNull
    View onCreateView(Context context) {
        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.VERTICAL);

        ActionBar actionbar = new ActionBar(context);
        actionbar.setTitle(R.string.login_title);
        contentView.addView(actionbar, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build());

        mName = new EditText(context);
        mName.setTextSize(20);
        mName.setTypeface(font(R.font.barlow_condensed_regular));
        mName.setHint(R.string.login_name_hint);
        contentView.addView(mName, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build());

        mPass = new EditText(context);
        mPass.setTextSize(20);
        mPass.setTypeface(font(R.font.barlow_condensed_regular));
        mPass.setHint(R.string.login_pass_hint);
        contentView.addView(mPass, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build());

        mSubmit = new Button(context);
        mSubmit.setTextPadding(dp(32), dp(12), dp(32), dp(12));
        mSubmit.setBackgroundColor(color(R.color.main_next_bg));
        mSubmit.setTextColor(color(R.color.main_next_text));
        mSubmit.setTypeface(font(R.font.barlow_condensed_bold));
        mSubmit.setTextSize(24);
        mSubmit.setText(R.string.login_submit);
        contentView.addView(mSubmit, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT)
                .setMargins(dp(16), dp(16), dp(16), dp(16))
                .build());

        // Register event listener
        mSubmit.setOnClickListener(onSubmitClick);
        return contentView;
    }
    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mSubscriptions.add(AppStateObservable.create()
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
    private final View.OnClickListener onSubmitClick = (view) -> {
        KeyboardHelper.hide(getActivity());
        final String name = mName.getText().toString();
        final String pass = mPass.getText().toString();
        final Login.Input input = new Login.Input(name, pass);
        if (validateForm(input)) {
            ClientAction.login(store(), input);
        } else {
            Toast.makeText(getApplicationContext(), R.string.login_formerr, Toast.LENGTH_SHORT).show();
        }
    };
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private final Function<AppState,ClientState.APIState<Login.Output>>
            mapLoginState = (state) -> state.client().login;
    private final Consumer<ClientState.APIState<Login.Output>> consumeLoginState = (state) -> {
        boolean enableUI = !state.inprogress;
        mSubmit.setEnabled(enableUI);
        mName.setEnabled(enableUI);
        mPass.setEnabled(enableUI);
    };
    //---------------------------------------------------------------
    //endregion

    //region Form Validation
    private boolean validateForm(@NonNull Login.Input input) {
        if (input.name.isEmpty()) return false;
        if (input.pass.isEmpty()) return false;
        return true;
    }
    //endregion
}
