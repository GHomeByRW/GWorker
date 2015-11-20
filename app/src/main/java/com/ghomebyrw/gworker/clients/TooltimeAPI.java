package com.ghomebyrw.gworker.clients;

import com.ghomebyrw.gworker.models.Job;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by wewang on 11/20/15.
 */
public interface TooltimeAPI {
    @Headers("X-Auth-Token: admin-token")
    @GET("field-workers/{id}/jobs")
    Call<List<Job>> fetchJobs(@Path("id") String id);
}
