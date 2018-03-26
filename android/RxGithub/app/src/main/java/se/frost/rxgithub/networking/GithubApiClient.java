package se.frost.rxgithub.networking;

import io.reactivex.Observable;
import se.frost.rxgithub.model.User;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class GithubApiClient {

    private GithubApi api;

    public GithubApiClient(GithubApi api) {
        this.api = api;
    }

    public Observable<User> getUser(String user) {
        return api.getUser(user);
    }

    public Observable<String> getUserJson(String user) {
        return api.getUserJson(user);
    }
}
