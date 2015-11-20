package com.ghomebyrw.gworker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.adapters.JobsAdapter;
import com.ghomebyrw.gworker.clients.JobClient;
import com.ghomebyrw.gworker.models.Job;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class JobsActivity extends AppCompatActivity {

    private ListView lvJobs;
    private JobsAdapter jobsAdapter;
    private List<Job> jobs = new ArrayList<>();
    private JobClient jobClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        jobClient = new JobClient();
        lvJobs= (ListView) findViewById(R.id.lvJobs);
        jobsAdapter = new JobsAdapter(this, jobs);
        lvJobs.setAdapter(jobsAdapter);
        fetchJobs();
    }

    private void fetchJobs() {
        jobClient.fetchJobs("fdf0399e-19cc-4d3a-b027-727fc0522050", new Callback<List<Job>>() {
            @Override
            public void onResponse(Response<List<Job>> response,
                                   Retrofit retrofit) {

                Log.i("jobs", response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("jobs", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
