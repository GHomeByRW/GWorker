package com.ghomebyrw.gworker.clients;

import android.util.Log;

import com.ghomebyrw.gworker.fragments.JobsFragment;
import com.ghomebyrw.gworker.models.Location;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by wewang on 11/21/15.
 */
public class GoogleStaticMapAPI {
    private static final String GOOGLE_STATIC_MAP_API_KEY = "AIzaSyD0LjZVQc0XVfqDA-7zux865hsYXcUhc4s";
    private static final String BASE_URL = "http://maps.googleapis.com/maps/api/staticmap?";
    private static final String LOG_TAG = JobsFragment.class.getName();

    public static String getStaticMapURL(Location jobLocation, Location fieldworkerLocation) {
       StringBuilder unencodedURL = new StringBuilder(BASE_URL + "size=600x300")
                .append("&markers=size:mid|color:red|").append(jobLocation.getLat()).append(",").append(jobLocation.getLon());
        if (fieldworkerLocation != null) {
            unencodedURL.append("&markers=size:mid|color:green|").append(fieldworkerLocation.getLat()).append(",").append(fieldworkerLocation.getLon());
        } else {
            Log.i(LOG_TAG, "Cannot retrieve current field worker location");
        }
        unencodedURL.append("&sensor=false&key=" + GOOGLE_STATIC_MAP_API_KEY);

        try {
            URL url = new URL(unencodedURL.toString());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());
            return uri.toASCIIString();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            Log.e("StaticMap", "Cannot encode url properly");
        }
        return null;
    }
}
