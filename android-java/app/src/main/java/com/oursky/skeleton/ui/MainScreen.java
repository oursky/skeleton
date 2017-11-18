package com.oursky.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import com.oursky.skeleton.R;
import com.oursky.skeleton.design.Font;
import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.redux.AppState;
import com.oursky.skeleton.ui.base.AppController;
import com.oursky.skeleton.widget.Button;

import static com.oursky.skeleton.helper.ResourceHelper.dp;
import static com.oursky.skeleton.helper.ResourceHelper.color;
import static com.oursky.skeleton.helper.ResourceHelper.font;

public class MainScreen extends AppController {
    private TextView mTitle;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    protected @NonNull
    View onCreateView(Context context) {
        FrameLayout layout = new FrameLayout(context);
        mTitle = new TextView(context);
        mTitle.setTextSize(32);
        mTitle.setTypeface(font(Font.BARLOW_BOLD));
        layout.addView(mTitle, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER).build());

        Button next = new Button(context);
        next.setTextPadding(dp(32), dp(12), dp(32), dp(12));
        next.setBackgroundColor(color(R.color.main_next_bg));
        next.setTextColor(color(R.color.main_next_text));
        next.setTypeface(font(Font.BARLOW_BOLD));
        next.setTextSize(24);
        next.setText(R.string.main_next);
        layout.addView(next, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER|Gravity.BOTTOM)
                               .setMargins(0, dp(16), 0, dp(16))
                               .build());

        // Register event listener
        next.setOnClickListener(onNextClick);
        return layout;
    }
    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mSubscriptions.add(createAppStateObservable()
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapTitle)
                .subscribe(consumeTitle)
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
        mTitle.setText(title);
    };
    //---------------------------------------------------------------
    //endregion
}
