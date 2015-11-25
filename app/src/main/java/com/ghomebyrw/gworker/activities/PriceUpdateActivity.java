package com.ghomebyrw.gworker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.clients.JobClient;
import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.Price;

import java.util.UUID;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PriceUpdateActivity extends AppCompatActivity {

    private EditText tvLaborPrice;
    private EditText tvPartsPrice;
    private EditText tvPartsDescription;
    private TextView tvLaborCostDisplay;
    private TextView tvPartsCostDisplay;
    private TextView tvTotalCostDisplay;
    private Price price;
    private UUID jobId;
    private Button saveButton;
    private JobClient jobClient;

    private static final String LOG_TAG = PriceUpdateActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jobClient = new JobClient();
        jobId = UUID.fromString(getIntent().getStringExtra("jobId"));
        tvLaborPrice = (EditText) findViewById(R.id.etEditLaborCost);
        tvPartsPrice = (EditText) findViewById(R.id.etEditPartsCost);
        tvPartsDescription = (EditText) findViewById(R.id.etEditPartsDescription);
        tvLaborCostDisplay = (TextView) findViewById(R.id.tvDisplayLaborPrice);
        tvPartsCostDisplay = (TextView) findViewById(R.id.tvDisplayPartsPrice);
        tvTotalCostDisplay = (TextView) findViewById(R.id.tvDisplayTotalPrice);
        saveButton = (Button) findViewById(R.id.btnUpdate);

        final Price oldPrice = getIntent().getParcelableExtra("price");
        price = oldPrice;
        tvLaborPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLaborPrice.setText("");
            }
        });
        tvLaborPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLaborCostDisplay.setText("$" + tvLaborPrice.getText());
            }
        });
        tvLaborPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    price.getServicePrice().setAmount(Double.parseDouble(tvLaborPrice.getText().toString()));
                    tvTotalCostDisplay.setText(price.getFormattedAmount());
                }
            }
        });
        tvLaborPrice.setText(oldPrice.getServicePrice().getDisplayAmount());

        tvPartsPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPartsPrice.setText("");
            }
        });
        tvPartsPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvPartsCostDisplay.setText("$" + tvPartsPrice.getText());
            }
        });
        tvPartsPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    price.getPartsPrice().setAmount(Double.parseDouble(tvPartsPrice.getText().toString()));
                    tvTotalCostDisplay.setText(price.getFormattedAmount());
                }
            }
        });
        tvPartsPrice.setText(oldPrice.getPartsPrice().getDisplayAmount());

        tvPartsDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPartsDescription.setText("");
            }
        });
        tvPartsDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    price.getPartsPrice().setDescription(tvPartsDescription.getText().toString());
                }
            }
        });
        tvPartsDescription.setText(oldPrice.getPartsPrice().getDescription());

        tvLaborCostDisplay.setText(oldPrice.getServicePrice().getFormattedAmount());
        tvPartsCostDisplay.setText(oldPrice.getPartsPrice().getFormattedAmount());
        tvTotalCostDisplay.setText(oldPrice.getFormattedAmount());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrice();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_price_update, menu);
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

    public void updatePrice() {
        price.getServicePrice().setAmount(Double.parseDouble(tvLaborPrice.getText().toString()));
        price.getPartsPrice().setAmount(Double.parseDouble(tvPartsPrice.getText().toString()));
        price.getPartsPrice().setDescription(tvPartsDescription.getText().toString());

        jobClient.updateJobPrice(jobId.toString(), price, new Callback<Job>() {
            @Override
            public void onResponse(Response<Job> response, Retrofit retrofit) {
                Log.i(LOG_TAG, "succeeded");
                Intent intent = new Intent(PriceUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(LOG_TAG, t.getLocalizedMessage());
                Toast.makeText(PriceUpdateActivity.this, getString(R.string.price_update_failure),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}