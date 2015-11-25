package com.ghomebyrw.gworker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ghomebyrw.gworker.utils.CurrencyHelper;

/**
 * Created by wewang on 11/19/15.
 */
public class Price implements Parcelable {
    private PriceItem servicePrice;
    private PriceItem partsPrice;

    public enum PriceType {
        SERVICE,
        PARTS
    }

    public Price() {
    }

    public Price(PriceItem servicePrice, PriceItem partsPrice) {
        this.servicePrice = servicePrice;
        this.partsPrice = partsPrice;
    }

    public PriceItem getServicePrice() {
        return servicePrice;
    }

    public PriceItem getPartsPrice() {
        return partsPrice;
    }

    public String getFormattedAmount() {
        double amount = servicePrice.getAmount();
        if (partsPrice != null) {
            amount += partsPrice.getAmount();
        }
        return CurrencyHelper.formatCurrencyInDefaultLocale(amount);
    }

    @Override
    public String toString() {
        return "Price{" +
                "servicePrice=" + servicePrice +
                ", partsPrice=" + partsPrice +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.servicePrice, 0);
        dest.writeParcelable(this.partsPrice, 0);
    }

    protected Price(Parcel in) {
        this.servicePrice = in.readParcelable(PriceItem.class.getClassLoader());
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
