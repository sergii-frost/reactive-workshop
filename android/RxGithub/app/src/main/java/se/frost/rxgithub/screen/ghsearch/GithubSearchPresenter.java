package se.frost.rxgithub.screen.ghsearch;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import se.frost.rxgithub.base.BasePresenter;
import se.frost.rxgithub.networking.GithubApiClient;
import se.frost.rxgithub.util.JsonUtil;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-27.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class GithubSearchPresenter extends BasePresenter<GithubSearchView> {

    private GithubApiClient githubApiClient;

    public GithubSearchPresenter(GithubApiClient githubApiClient) {
        this.githubApiClient = githubApiClient;
    }

    void search(String query) {
        disposables.add(githubApiClient
                .getUserJson(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSearchSuccess, this::onSearchError));
    }

    private void onSearchSuccess(String json) {
        if (isViewAttached()) {
            view.updateWithSearchResult(JsonUtil.toPrettyFormat(json));
        }
    }

    private void onSearchError(Throwable throwable) {
        if (isViewAttached()) {
            view.showError(throwable);
        }
    }
}
