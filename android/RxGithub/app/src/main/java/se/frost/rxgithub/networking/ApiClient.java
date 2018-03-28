package se.frost.rxgithub.networking;

import io.reactivex.Observable;
import se.frost.rxgithub.model.City;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class ApiClient {

    private Api api;

    public ApiClient(Api api) {
        this.api = api;
    }

    public Observable<City[]> getCities() {
        return api.getCities();
    }
}
