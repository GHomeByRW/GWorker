package com.ghomebyrw.gworker.clients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by wewang on 11/19/15.
 */
public class JobClient {
    private static final String BASE_URL = "http://ondemand1-uat.snc1:9000/ondemand/v1/field-workers/fdf0399e-19cc-4d3a-b027-727fc0522050/jobs";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
