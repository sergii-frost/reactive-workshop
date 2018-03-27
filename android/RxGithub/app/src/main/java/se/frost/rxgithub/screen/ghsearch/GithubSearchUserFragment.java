package se.frost.rxgithub.screen.ghsearch;

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
import io.reactivex.disposables.CompositeDisposable;
import se.frost.rxgithub.R;
import se.frost.rxgithub.networking.NetworkManager;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class GithubSearchUserFragment extends Fragment implements GithubSearchView {

    public static final String TAG = GithubSearchUserFragment.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private GithubSearchPresenter presenter;

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
        presenter = new GithubSearchPresenter(NetworkManager.getInstance().getGithubApiClient());
        presenter.attachView(this);
        initJsonView();
        initBindings();
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        presenter.detachView();
        super.onDestroyView();
    }

    @IGithubSearchView
    @Override
    public void updateWithSearchResult(String result) {
        jsonView.setSource(result);
    }

    @IGithubSearchView
    @Override
    public void showError(Throwable error) {
        jsonView.setSource("ERROR: " + error.getMessage());
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
                .subscribe(presenter::search));
    }

}
