package com.ghomebyrw.gworker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wewang on 11/21/15.
 */
public class PriceItem implements Parcelable {
    private double amount;
    private String currencyCode;
    private String formattedAmount;
    private String description;

    public PriceItem() {
    }

    public PriceItem(double amount, String currencyCode, String formattedAmount) {
        this(amount, currencyCode, formattedAmount, null);
    }

    public PriceItem(double amount, String currencyCode, String formattedAmount, String description) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.formattedAmount = formattedAmount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDisplayAmount() {
        return String.format( "%.2f", amount);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    // TODO - directly return formatted amount once format of UAT api is fixed
    public String getFormattedAmount() {
        return "$" + String.format( "%.2f", amount);
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setFormattedAmount(String formattedAmount) {
        this.formattedAmount = formattedAmount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PriceItem{" +
                "amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", formattedAmount='" + formattedAmount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.amount);
        dest.writeString(this.currencyCode);
        dest.writeString(this.formattedAmount);
        dest.writeString(this.description);
    }

    protected PriceItem(Parcel in) {
        this.amount = in.readDouble();
        this.currencyCode = in.readString();
        this.formattedAmount = in.readString();
        this.description = in.readString();
    }

    public static final Creator<PriceItem> CREATOR = new Creator<PriceItem>() {
        public PriceItem createFromParcel(Parcel source) {
            return new PriceItem(source);
        }

        public PriceItem[] newArray(int size) {
            return new PriceItem[size];
        }
    };
}
