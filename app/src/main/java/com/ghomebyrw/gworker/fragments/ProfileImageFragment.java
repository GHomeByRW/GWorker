package com.ghomebyrw.gworker.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ghomebyrw.gworker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileImageFragment extends Fragment {

    private Uri imageUri;

    @Bind(R.id.ivProfile) ImageView ivProfile;

    public static ProfileImageFragment newInstance(Uri imageUri) {
        ProfileImageFragment fragment = new ProfileImageFragment();
        Bundle args = new Bundle();
        args.putParcelable("imageUri", imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUri = getArguments().getParcelable("imageUri");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_image, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bitmap image = BitmapFactory.decodeFile(imageUri.getPath());
        ivProfile.setImageBitmap(image);
    }
}
