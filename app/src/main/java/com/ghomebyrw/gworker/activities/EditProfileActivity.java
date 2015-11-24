package com.ghomebyrw.gworker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.models.FieldWorker;

public class EditProfileActivity extends AppCompatActivity {

    public static final String TAG = EditProfileActivity.class.getName();

    //TODO: determine id from authenticated user
    String fieldWorkerId = "fdf0399e-19cc-4d3a-b027-727fc0522050";

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhoneNumber;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);

        FieldWorker fieldWorker = (FieldWorker) getIntent().getParcelableExtra("fieldWorker");
        bindFieldWorker(fieldWorker);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindFieldWorker(FieldWorker fieldWorker) {
        etFirstName.setText(fieldWorker.getFirstName());
        etLastName.setText(fieldWorker.getLastName());
        etPhoneNumber.setText(fieldWorker.getPhoneNumber());
        etEmail.setText(fieldWorker.getEmail());
    }
}
