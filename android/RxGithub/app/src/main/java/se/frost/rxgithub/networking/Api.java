package se.frost.rxgithub.networking;

import io.reactivex.Observable;
import retrofit2.http.GET;
import se.frost.rxgithub.model.City;

/**
 * RxGithub
 * Created by Manne Öhlund on 2018-03-28.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
interface Api {
    @GET("/Miserlou/c5cd8364bf9b2420bb29/raw/2bf258763cdddd704f8ffd3ea9a3e81d25e2c6f6/cities.json")
    Observable<City[]> getCities();
}
