package com.ekta.marvel.network;

import com.google.gson.JsonElement;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Ekta on 11-06-2017.
 */

public interface IdentityService {
    @GET
    Observable<JsonElement> callApi(@Url String url);

}
