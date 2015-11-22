package com.ghomebyrw.gworker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ghomebyrw.gworker.utils.CurrencyHelper;

/**
 * Created by wewang on 11/19/15.
 */
public class Price implements Parcelable {
    private PriceItem laborPrice;
    private PriceItem partsPrice;

    public Price() {
    }

    public Price(PriceItem laborPrice, PriceItem partsPrice) {
        this.laborPrice = laborPrice;
        this.partsPrice = partsPrice;
    }

    public PriceItem getLaborPrice() {
        return laborPrice;
    }

    public PriceItem getPartsPrice() {
        return partsPrice;
    }

    public String getFormattedAmount() {
        return CurrencyHelper.formatCurrencyInDefaultLocale(laborPrice.getAmount() + partsPrice.getAmount());
    }

    @Override
    public String toString() {
        return "Price{" +
                "laborPrice=" + laborPrice +
                ", partsPrice=" + partsPrice +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.laborPrice, 0);
        dest.writeParcelable(this.partsPrice, 0);
    }

    protected Price(Parcel in) {
        this.laborPrice = in.readParcelable(PriceItem.class.getClassLoader());
        this.partsPrice = in.readParcelable(PriceItem.class.getClassLoader());
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
