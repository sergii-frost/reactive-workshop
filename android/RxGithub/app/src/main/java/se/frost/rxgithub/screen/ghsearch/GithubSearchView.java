package se.frost.rxgithub.screen.ghsearch;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-27.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public interface GithubSearchView {

    @interface IGithubSearchView {}

    void updateWithSearchResult(String result);
    void showError(Throwable error);
}
