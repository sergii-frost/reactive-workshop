package se.frost.rxgithub.screen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.pddstudio.highlightjs.HighlightJsView;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import se.frost.rxgithub.R;
import se.frost.rxgithub.networking.NetworkManager;
import se.frost.rxgithub.util.JsonUtil;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class GithubSearchUserFragment extends Fragment {

    public static final String TAG = GithubSearchUserFragment.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();

    @BindView(R.id.jsonView)
    HighlightJsView jsonView;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    public static GithubSearchUserFragment newInstance() {
        return new GithubSearchUserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_github_user, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initJsonView();
        initBindings();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void initJsonView() {
        jsonView.setTheme(Theme.SOLARIZED_DARK);
        jsonView.setHighlightLanguage(Language.AUTO_DETECT);
    }

    private void initBindings() {
        disposables.add(RxTextView.textChanges(searchEditText)
                .filter(charSequence -> charSequence.length() > 3)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .subscribe(this::search));
    }

    private void search(String user) {
        disposables.add(NetworkManager.getInstance()
                .getGithubApiClient()
                .getUserJson(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSearchSuccess, this::onSearchError));
    }

    private void onSearchSuccess(String json) {
        jsonView.setSource(JsonUtil.toPrettyFormat(json));
    }

    private void onSearchError(Throwable throwable) {
        jsonView.setSource("ERROR: " + throwable.getMessage());
    }
}
