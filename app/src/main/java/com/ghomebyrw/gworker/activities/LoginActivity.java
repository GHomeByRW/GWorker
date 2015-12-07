package com.ghomebyrw.gworker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.clients.JobClient;
import com.ghomebyrw.gworker.models.LoginInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.etEmailAddress) EditText etEmail;
    @Bind(R.id.etPassword) EditText etPassword;
    @Bind(R.id.btnLogin) Button loginButton;
    private JobClient jobClient;

    private static final String LOG_TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        jobClient = new JobClient();
    }

    @OnClick(R.id.btnLogin)
    void logIn() {
        String emailAddress = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        final LoginInfo logInInfo = new LoginInfo(emailAddress, password);
        if (emailAddress.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.email_password_not_null),
                    Toast.LENGTH_SHORT).show();
        } else {
            jobClient.login(logInInfo, new Callback<Boolean>() {
                @Override
                public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                    Log.i(LOG_TAG, "Login successful");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("logInInfo", logInInfo);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(LOG_TAG, "Login information invalid");
                    Toast.makeText(LoginActivity.this, getString(R.string.email_password_invalid),
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
