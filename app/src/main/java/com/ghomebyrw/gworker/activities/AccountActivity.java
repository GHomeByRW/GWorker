package com.ghomebyrw.gworker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.clients.JobClient;
import com.ghomebyrw.gworker.models.FieldWorker;

import java.io.IOException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AccountActivity extends AppCompatActivity {

    public static final String TAG = AccountActivity.class.getName();

    //TODO: determine id from authenticated user
    String fieldWorkerId = "fdf0399e-19cc-4d3a-b027-727fc0522050";

    private FieldWorker fieldWorker;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvPhoneNumber;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvEditAction = (TextView) findViewById(R.id.tvEditAction);
        tvEditAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, EditProfileActivity.class);
                intent.putExtra("fieldWorker", fieldWorker);
                startActivity(intent);
            }
        });

        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvEmail = (TextView) findViewById(R.id.tvEmail);

        Log.d(TAG, "onCreate");
        fetchFieldWorker();
    }

    private void fetchFieldWorker() {
        JobClient client = new JobClient();
        client.fetchFieldWorker(fieldWorkerId, new Callback<FieldWorker>() {
            @Override
            public void onResponse(Response<FieldWorker> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    fieldWorker = response.body();
                    Log.d(TAG, "Successfully fetched field worker: " + fieldWorker);
                    bindFieldWorker(fieldWorker);
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        errorBody = "***unable to decode error body***";
                    }
                    Log.d(TAG, String.format("Failed to fetch field worker: code: %s, body: %s", response.code(), errorBody));
                    //TODO: tell the user about the error more gracefully
                    Toast.makeText(AccountActivity.this, "Unable to fetch account.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Failed to fetch field worker due to exception: " + t);
                //TODO: tell the user about the error more gracefully
                Toast.makeText(AccountActivity.this, "Unable to fetch account.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindFieldWorker(FieldWorker fieldWorker) {
        tvFirstName.setText(fieldWorker.getFirstName());
        tvLastName.setText(fieldWorker.getLastName());
        tvPhoneNumber.setText(fieldWorker.getPhoneNumber());
        tvEmail.setText(fieldWorker.getEmail());
    }
}
