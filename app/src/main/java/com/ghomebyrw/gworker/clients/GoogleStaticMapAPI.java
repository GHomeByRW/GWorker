package com.ghomebyrw.gworker.clients;

import android.util.Log;

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


    public static String getStaticMapURL(Location location) {
        String unencodedURL = new StringBuilder(BASE_URL + "zoom=15&size=600x300&markers=size:mid|color:red|")
                .append(location.getLat())
                .append(",")
                .append(location.getLon())
                .append("&sensor=false")
                .append("&key=" + GOOGLE_STATIC_MAP_API_KEY).toString();

        try {
            URL url = new URL(unencodedURL);
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
