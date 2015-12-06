package com.ghomebyrw.gworker.fragments;

import android.location.Location;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class JobsFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String LOG_TAG = JobsFragment.class.getName();

    private JobsAdapter jobsAdapter;
    private List<Job> jobs = new ArrayList<>();
    private JobClient jobClient;

    private static final String FIELDWORKER_ID = "fdf0399e-19cc-4d3a-b027-727fc0522050";

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        jobClient = new JobClient();
        ListView lvJobs = ButterKnife.findById(view, R.id.lvJobs);
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

    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "connected google location api");
        // Connect the client.
        mGoogleApiClient.connect();
    }

    public void onStop() {
        // Disconnecting the client invalidates it.
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onConnected(Bundle dataBundle) {
        // Get last known recent location.
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        // Note that this can be NULL if last location isn't already known.
        if (mCurrentLocation != null) {
            // Print current location if not null
            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            jobsAdapter.setFieldworkerLocation(new com.ghomebyrw.gworker.models.Location(null, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(getContext(), "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(getContext(), "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, "location changed");
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        jobsAdapter.setFieldworkerLocation(new com.ghomebyrw.gworker.models.Location(null, location.getLatitude(), location.getLongitude()));
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getContext(), getString(R.string.failure_location_sensor), Toast.LENGTH_SHORT).show();;
    }
}
