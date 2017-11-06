package com.example.cardword;

import com.example.cardword.model.Translate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Саша on 10.09.2017.
 */

public interface Link {

    @GET("/api/v1.5/tr.json/translate")
    Call<Translate> translate(@Query("key") String key,
                              @Query("text") String text,
                              @Query("lang") String lang);
}
