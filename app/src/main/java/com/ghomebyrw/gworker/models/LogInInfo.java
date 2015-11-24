package com.ghomebyrw.gworker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wewang on 11/24/15.
 */
public class LogInInfo implements Parcelable {
    private String userName;
    private String password;

    public LogInInfo() {
    }

    public LogInInfo(String userName, String password) {
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

    protected LogInInfo(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
    }

    public static final Creator<LogInInfo> CREATOR = new Creator<LogInInfo>() {
        public LogInInfo createFromParcel(Parcel source) {
            return new LogInInfo(source);
        }

        public LogInInfo[] newArray(int size) {
            return new LogInInfo[size];
        }
    };
}
