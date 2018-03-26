package se.frost.rxgithub.networking;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import se.frost.rxgithub.model.User;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
interface GithubApi {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String user);

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{user}")
    Observable<String> getUserJson(@Path("user") String user);

}
