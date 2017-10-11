package com.example.cardword;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Саша on 10.09.2017.
 */

public interface Link {

    @GET("/api/v1.5/tr.json/translate")
    Call<Words> translate(@Query("key") String key,
                          @Query("text") String text,
                          @Query("lang") String lang);
}
