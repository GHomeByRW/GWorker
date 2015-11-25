package com.ghomebyrw.gworker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.adapters.JobsAdapter;
import com.ghomebyrw.gworker.clients.JobClient;
import com.ghomebyrw.gworker.models.Job;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class JobsFragment extends Fragment {

    private static final String LOG_TAG = JobsFragment.class.getName();

    private JobsAdapter jobsAdapter;
    private List<Job> jobs = new ArrayList<>();
    private JobClient jobClient;

    private static final String FIELDWORKER_ID = "fdf0399e-19cc-4d3a-b027-727fc0522050";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        jobClient = new JobClient();
        ListView lvJobs = (ListView) view.findViewById(R.id.lvJobs);
        jobsAdapter = new JobsAdapter(getContext(), jobs);
        lvJobs.setAdapter(jobsAdapter);
        fetchJobs();
        return view;
    }

    private void fetchJobs() {
        jobClient.fetchJobs(FIELDWORKER_ID, new Callback<List<Job>>() {
            @Override
            public void onResponse(Response<List<Job>> response,
                                   Retrofit retrofit) {
                jobsAdapter.clear();
                jobsAdapter.addAll(response.body());
                Log.i(LOG_TAG, response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, t.getLocalizedMessage());
                Toast.makeText(getContext(), getString(R.string.job_fetch_failure),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
