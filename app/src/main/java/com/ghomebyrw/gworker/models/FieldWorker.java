package com.ghomebyrw.gworker.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FieldWorker implements Parcelable {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public FieldWorker() {}

    public FieldWorker(String email, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    protected FieldWorker(Parcel in) {
        this.email = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNumber = in.readString();
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "FieldWorker{" +
                "email=" + email +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", phoneNumber=" + phoneNumber +
                "}";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phoneNumber);
    }

    public static final Parcelable.Creator<FieldWorker> CREATOR = new Parcelable.Creator<FieldWorker>() {
        public FieldWorker createFromParcel(Parcel source) {
            return new FieldWorker(source);
        }

        public FieldWorker[] newArray(int size) {
            return new FieldWorker[size];
        }
    };
}