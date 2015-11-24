package com.ghomebyrw.gworker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wewang on 11/24/15.
 */
public class LoginInfo implements Parcelable {
    private String userName;
    private String password;

    public LoginInfo() {
    }

    public LoginInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.password);
    }

    protected LoginInfo(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
