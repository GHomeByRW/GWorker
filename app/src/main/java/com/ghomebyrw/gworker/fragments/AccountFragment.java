package com.ghomebyrw.gworker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ghomebyrw.gworker.R;
import com.ghomebyrw.gworker.activities.EditProfileActivity;
import com.ghomebyrw.gworker.clients.JobClient;
import com.ghomebyrw.gworker.models.FieldWorker;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AccountFragment extends Fragment {

    static final String TAG = AccountFragment.class.getName();
    static final int EDIT_PROFILE_REQUEST_CODE = 1;
    static final int CAPTURE_IMAGE_REQUEST_CODE = 2;
    static final String PROFILE_IMAGE_FILE = "profile_image.jpg";

    //TODO: determine id from authenticated user
    String fieldWorkerId = "fdf0399e-19cc-4d3a-b027-727fc0522050";

    private FieldWorker fieldWorker;
    @Bind(R.id.accountFragmentLayout) ViewGroup layout;
    @Bind(R.id.tvFirstName) TextView tvFirstName;
    @Bind(R.id.tvLastName) TextView tvLastName;
    @Bind(R.id.tvPhoneNumber) TextView tvPhoneNumber;
    @Bind(R.id.tvEmail) TextView tvEmail;
    @Bind(R.id.rbRating) RatingBar rbRating;
    @Bind(R.id.tvRating) TextView tvRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

        fetchFieldWorker();

        // TODO: fetch average rating from api
        float ratingValue = 3.5f;
        rbRating.setRating(ratingValue);
        tvRating.setText(String.valueOf(ratingValue));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // TODO: set profile image fragment depending on presence of image fetched from the api
        insertProfileImageFragment(new AddProfileImageFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tvEditAction)
    void editProfile() {
        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        intent.putExtra("fieldWorker", fieldWorker);
        startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
    }

    @OnClick(R.id.fabAddProfileImage)
    void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(PROFILE_IMAGE_FILE));

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //TODO: call api to update field worker on back end
            fieldWorker = (FieldWorker) data.getParcelableExtra("fieldWorker");
            bindFieldWorker(fieldWorker);

            // Force layout refresh. Without this, the right alignment of field values
            // isn't properly refreshed if the new value is shorter than the old one.
            // Is there a better way to handle this?
            layout.invalidate();
        } else if (requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri takenPhotoUri = getPhotoFileUri(PROFILE_IMAGE_FILE);
            Fragment fragment = ProfileImageFragment.newInstance(takenPhotoUri);
            insertProfileImageFragment(fragment);
            //TODO: In the future, the profile image should be uploaded to the back end
        }
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
                    Toast.makeText(getContext(), "Unable to fetch account.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Failed to fetch field worker due to exception: " + t);
                //TODO: tell the user about the error more gracefully
                Toast.makeText(getContext(), "Unable to fetch account.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindFieldWorker(FieldWorker fieldWorker) {
        tvFirstName.setText(fieldWorker.getFirstName());
        tvLastName.setText(fieldWorker.getLastName());
        tvPhoneNumber.setText(fieldWorker.getPhoneNumber());
        tvEmail.setText(fieldWorker.getEmail());
    }

    private void insertProfileImageFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.profileImageFragmentContainer, fragment).commitAllowingStateLoss();
    }

    private Uri getPhotoFileUri(String fileName) {
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), TAG);
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
            }
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
