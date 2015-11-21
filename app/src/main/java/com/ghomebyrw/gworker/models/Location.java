package com.ghomebyrw.gworker.models;

/**
 * Created by wewang on 11/20/15.
 */
public class Location {
    private String address;
    private double lat;
    private double lon;

    public Location() {
    }

    public Location(String address, double lat, double lon) {
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
