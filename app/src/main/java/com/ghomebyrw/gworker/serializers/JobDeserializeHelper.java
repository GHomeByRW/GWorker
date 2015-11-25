package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.JobStatus;
import com.ghomebyrw.gworker.models.Location;
import com.ghomebyrw.gworker.models.Price;
import com.ghomebyrw.gworker.models.PriceItem;
import com.ghomebyrw.gworker.models.ScheduledDateAndTime;
import com.ghomebyrw.gworker.utils.CurrencyHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by wewang on 11/25/15.
 */
public final class JobDeserializeHelper {
    public static Job deserialize(JsonObject jobJson) throws JsonParseException {
        return new Job(
                UUID.fromString(jobJson.get("id").getAsString()),
                jobJson.get("service").getAsJsonObject().get("name").getAsString(),
                deserializeDateTime(jobJson.get("scheduledDateAndTime").getAsJsonObject()),
                JobStatus.valueOf(jobJson.get("status").getAsString()),
                UUID.fromString(jobJson.get("customerId").getAsString()),
                deserializePrice(jobJson.get("estimatedLowPrice").getAsJsonObject()),
                deserializeLocation(jobJson),
                jobJson.get("timeZone").getAsString(),
                jobJson.get("customerPhoneNumber").getAsString(),
                getOptionalFieldNullSafe(jobJson, "note"),
                jobJson.get("estimatedMinutes").getAsInt(),
                null,
                deserializeFieldworkerFullName(jobJson)
        );
    }

    public static String getOptionalFieldNullSafe(JsonObject jsonObject, String fieldName) {
        JsonElement jsonField = jsonObject.get(fieldName);
        if (jsonField.isJsonNull()) {
            return null;
        } else {
            return jsonField.getAsString();
        }
    }

    public static Price deserializePrice(JsonObject jsonObject) {
        JsonObject servicePrice = jsonObject.get("servicePrice").getAsJsonObject();
        PriceItem laborPrice = new PriceItem(servicePrice.get("amount").getAsInt()/100,
                servicePrice.get("currencyCode").getAsString(),
                servicePrice.get("formattedAmount").getAsString(),
                servicePrice.get("type").getAsString());
        PriceItem partsPrice = null;
        if (!jsonObject.get("partsPrice").isJsonNull()) {
            JsonObject partsPriceJson = jsonObject.get("partsPrice").getAsJsonObject();
            partsPrice = new PriceItem(partsPriceJson.get("amount").getAsInt() / 100,
                    partsPriceJson.get("currencyCode").getAsString(),
                    partsPriceJson.get("formattedAmount").getAsString(),
                    partsPriceJson.get("type").getAsString(),
                    getOptionalFieldNullSafe(partsPriceJson, "description"));
        } else {
            partsPrice = new PriceItem(0.0, CurrencyHelper.DEFAULT_CURRENCY.getCurrencyCode(),
                    CurrencyHelper.formatCurrencyInDefaultLocale(0), Price.PriceType.PARTS.name(), null);
        }
        return new Price(laborPrice, partsPrice);
    }

    public static ScheduledDateAndTime deserializeDateTime(JsonObject jsonObject) {
        try {
            String startTime = jsonObject.get("startTime").getAsString();
            String endTime = jsonObject.get("endTime").getAsString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return new ScheduledDateAndTime(format.parse(startTime),format.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deserializeFieldworkerFullName(JsonObject jobObject) {
        try {
            JsonObject fieldworkerObject = jobObject.get("fieldworker").getAsJsonObject();
            return fieldworkerObject.get("firstName").getAsString() + " " +
                    fieldworkerObject.get("lastName").getAsString();
        } catch (Exception e) {
            // Note - looks like there is a bug in api that sometimes fieldworker names are not properly returned, we won't need to catch exceptions here once the bug is fixed.
            return "";
        }
    }


    public static Location deserializeLocation(JsonObject jobObject) {
        JsonObject locationObject = jobObject.get("location").getAsJsonObject();
        return new Location(locationObject.get("address").getAsString(),
                locationObject.get("lat").getAsDouble(),
                locationObject.get("lon").getAsDouble());
    }
}
