package com.ghomebyrw.gworker.clients;

import com.ghomebyrw.gworker.models.Job;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by wewang on 11/19/15.
 */
public class JobClient {
    private static final String BASE_URL = "http://ondemand1-uat.snc1:9000/ondemand/v1/";

    public void fetchJobs(String fieldworkerId, Callback<List<Job>> httpHandler) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TooltimeAPI service = retrofit.create(TooltimeAPI.class);
        service.fetchJobs(fieldworkerId).enqueue(httpHandler);
    }
}
