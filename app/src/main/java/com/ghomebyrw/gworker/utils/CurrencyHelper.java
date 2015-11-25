package com.ghomebyrw.gworker.utils;

import com.ghomebyrw.gworker.models.Price;
import com.ghomebyrw.gworker.models.PriceItem;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by wewang on 11/21/15.
 */
public final class CurrencyHelper {

    public static final Locale DEFAULT_LOCALE = Locale.US;
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance(DEFAULT_LOCALE);

    public static String formatCurrencyInDefaultLocale(double amount) {
        NumberFormat currencyFormatter =
                NumberFormat.getCurrencyInstance(DEFAULT_LOCALE);
        return currencyFormatter.format(amount);
    }

    public static Price createPriceForUpdate(Price price) {
        int serviceAmount = (int) price.getServicePrice().getAmount() * 100;
        PriceItem servicePrice = new PriceItem(serviceAmount, price.getServicePrice().getCurrencyCode(),
                formatCurrencyInDefaultLocale(price.getServicePrice().getAmount()), Price.PriceType.SERVICE.name());
        int partsAmount = (int) price.getPartsPrice().getAmount() * 100;
        PriceItem partsPrice = new PriceItem(partsAmount, price.getPartsPrice().getCurrencyCode(),
                formatCurrencyInDefaultLocale(price.getPartsPrice().getAmount()), Price.PriceType.PARTS.name(), price.getPartsPrice().getDescription());

        return new Price(servicePrice, partsPrice);
    }
}
