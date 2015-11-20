package com.ghomebyrw.gworker.models;

/**
 * Created by wewang on 11/19/15.
 */
public class Price {
    private int amount;
    private String currencyCode;
    private String formattedAmount;

    public Price(int amount, String currencyCode, String formattedAmount) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.formattedAmount = formattedAmount;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getFormattedAmount() {
        return formattedAmount;
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", formattedAmount='" + formattedAmount + '\'' +
                '}';
    }
}
