package se.frost.rxgithub.networking;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.frost.rxgithub.BuildConfig;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class NetworkManager {

    private ApiClient apiClient;

    private static volatile NetworkManager instance = null;

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }

        return instance;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    private NetworkManager() {
        apiClient = buildApiClient();
    }

    private ApiClient buildApiClient() {
        return new ApiClient(new Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttpClient())
                .build()
                .create(Api.class)
        );
    }

    private OkHttpClient buildOkHttpClient(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }
}
