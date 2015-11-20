package com.ghomebyrw.gworker.clients;

import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.serializers.JobListDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

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
                .addConverterFactory(GsonConverterFactory.create(getDeserializer()))
                .build();
        TooltimeAPI service = retrofit.create(TooltimeAPI.class);
        service.fetchJobs(fieldworkerId).enqueue(httpHandler);
    }

    private Gson getDeserializer() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new JobListDeserializer())
                .create();

        return gson;
    }
}
