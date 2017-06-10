package com.ekta.marvel.network;

import android.bluetooth.BluetoothClass;
import android.support.compat.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ekta.marvel.network.Endpoints.MARVEL_ENDPOINT;

/**
 * Created by Ekta on 11-06-2017.
 */

public class RetrofilBuilder {
    static Retrofit retrofit;

    public Retrofit getRetrofit() {
        if (retrofit == null) {

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
            clientBuilder.readTimeout(60, TimeUnit.SECONDS);


            retrofit = new Retrofit.Builder()
                    .baseUrl(MARVEL_ENDPOINT)
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
