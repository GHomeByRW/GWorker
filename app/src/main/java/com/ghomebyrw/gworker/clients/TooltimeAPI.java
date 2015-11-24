package com.ghomebyrw.gworker.clients;

import com.ghomebyrw.gworker.models.FieldWorker;
import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.LogInInfo;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by wewang on 11/20/15.
 */
public interface TooltimeAPI {
    @Headers("X-Auth-Token: admin-token")
    @GET("fieldworkers/{id}/jobs")
    Call<List<Job>> fetchJobs(@Path("id") String id);

    @Headers("X-Auth-Token: admin-token")
    @PATCH("jobs/{id}")
    @FormUrlEncoded
    Call<Job> updateJobPrice(@Path("id") String id);

    @Headers("X-Auth-Token: admin-token")
    @GET("fieldworkers/{id}")
    Call<FieldWorker> fetchFieldWorker(@Path("id") String id);

    // TODO - update once API is ready
    @Headers("X-Auth-Token: admin-token")
    @POST("fieldworkers/login")
    Call<Boolean> logIn(@Body LogInInfo loginInfo);
}
