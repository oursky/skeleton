package com.oursky.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import com.oursky.skeleton.R;
import com.oursky.skeleton.helper.LP;
import com.oursky.skeleton.redux.AppState;
import com.oursky.skeleton.redux.AppStateObservable;
import com.oursky.skeleton.ui.base.BaseController;
import com.oursky.skeleton.widget.ActionBar;
import com.oursky.skeleton.widget.Button;

import static com.oursky.skeleton.helper.ResourceHelper.color;
import static com.oursky.skeleton.helper.ResourceHelper.dp;
import static com.oursky.skeleton.helper.ResourceHelper.font;

public class SecondScreen extends BaseController {
    private DummyPopup mPopup;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    protected @NonNull
    View onCreateView(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        ActionBar actionbar = new ActionBar(context);
        actionbar.addLeftButton(R.drawable.ic_back, onBackClick);
        actionbar.addRightPadding();
        actionbar.setTitle(R.string.app_name);
        layout.addView(actionbar, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build());

        Button popup = new Button(context);
        popup.setTextPadding(dp(32), dp(12), dp(32), dp(12));
        popup.setBackgroundColor(color(R.color.main_next_bg));
        popup.setTextColor(color(R.color.main_next_text));
        popup.setTypeface(font(R.font.barlow_condensed_bold));
        popup.setTextSize(24);
        popup.setText(R.string.main_popup);
        layout.addView(popup, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT)
                                .setMargins(0, dp(64), 0, 0)
                                .build());

        mPopup = new DummyPopup(context);

        FrameLayout contentView = new FrameLayout(context);
        contentView.addView(layout, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build());
        contentView.addView(mPopup, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build());

        // Register event listener
        popup.setOnClickListener(onPopupClick);
        mPopup.setListener(onPopupEvent);
        return contentView;
    }
    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mSubscriptions.add(AppStateObservable.create()
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
    @Override
    public boolean handleBack() {
        if (mPopup.isVisible()) {
            mPopup.show(false);
            return true;
        } return super.handleBack();
    }
    private final View.OnClickListener onBackClick = (view) -> {
        if (mPopup.isVisible()) {
            mPopup.show(false);
        } else {
            popController();
        }
    };
    private final View.OnClickListener onPopupClick = (view) -> {
        mPopup.show(true);
    };
    private final DummyPopup.EventListener onPopupEvent = new DummyPopup.EventListener() {
        @Override
        public void onClose(DummyPopup view) {
            mPopup.show(false);
        }
    };
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private final Function<AppState,String> mapTitle = (state) -> state.view().title;
    private final Consumer<String> consumeTitle = (title) -> {
    };
    //---------------------------------------------------------------
    //endregion
}
