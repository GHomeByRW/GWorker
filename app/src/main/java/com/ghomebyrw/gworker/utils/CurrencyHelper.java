package com.ghomebyrw.gworker.utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by wewang on 11/21/15.
 */
public final class CurrencyHelper {

    public static final Locale DEFAULT_LOCALE = Locale.US;

    public static String formatCurrencyInDefaultLocale(double amount) {
        NumberFormat currencyFormatter =
                NumberFormat.getCurrencyInstance(DEFAULT_LOCALE);
        return currencyFormatter.format(amount);
    }
}
