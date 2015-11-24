package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.JobStatus;
import com.ghomebyrw.gworker.models.Location;
import com.ghomebyrw.gworker.models.Price;
import com.ghomebyrw.gworker.models.PriceItem;
import com.ghomebyrw.gworker.models.ScheduledDateAndTime;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wewang on 11/20/15.
 */
public class JobListDeserializer implements JsonDeserializer<List<Job>> {
    @Override
    public List<Job> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Job> jobs = new ArrayList<>();

        JsonArray array = json.getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject jobJson = array.get(i).getAsJsonObject();
            Job job = new Job(
                    jobJson.get("service").getAsJsonObject().get("name").getAsString(),
                    deserializeDateTime(jobJson.get("scheduledDateAndTime").getAsJsonObject()),
                    JobStatus.valueOf(jobJson.get("status").getAsString()),
                    UUID.fromString(jobJson.get("customerId").getAsString()),
                    deserializePrice(jobJson.get("estimatedLowPrice").getAsJsonObject()),
                    deserializeLocation(jobJson),
                    jobJson.get("timeZone").getAsString(),
                    jobJson.get("customerPhoneNumber").getAsString(),
                    getNoteNullSafe(jobJson),
                    jobJson.get("estimatedMinutes").getAsInt(),
                    null,
                    deserializeFieldworkerFullName(jobJson)
                    );
            jobs.add(job);
        }

        return jobs;
    }

    public String getNoteNullSafe(JsonObject jobObject) {
        JsonElement note = jobObject.get("note");
        if (note.isJsonNull()) {
            return null;
        } else {
            return note.getAsString();
        }
    }

    public Price deserializePrice(JsonObject jsonObject) {
        PriceItem laborPrice = new PriceItem(jsonObject.get("amount").getAsInt()/100,
                jsonObject.get("currencyCode").getAsString(),
                jsonObject.get("formattedAmount").getAsString());
        // TODO - read correct parts price once UAT is deployed with updates
        PriceItem partsPrice = new PriceItem(jsonObject.get("amount").getAsInt()/100,
                jsonObject.get("currencyCode").getAsString(),
                jsonObject.get("formattedAmount").getAsString());
        return new Price(laborPrice, partsPrice);
    }

    public ScheduledDateAndTime deserializeDateTime(JsonObject jsonObject) {
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

    public String deserializeFieldworkerFullName(JsonObject jobObject) {
        try {
            JsonObject fieldworkerObject = jobObject.get("fieldworker").getAsJsonObject();
            return fieldworkerObject.get("firstName").getAsString() + " " +
                    fieldworkerObject.get("lastName").getAsString();
        } catch (Exception e) {
            // Note - looks like there is a bug in api that sometimes fieldworker names are not properly returned, we won't need to catch exceptions here once the bug is fixed.
            return "";
        }
    }


    public Location deserializeLocation(JsonObject jobObject) {
        JsonObject locationObject = jobObject.get("location").getAsJsonObject();
        return new Location(locationObject.get("address").getAsString(),
                locationObject.get("lat").getAsDouble(),
                locationObject.get("lon").getAsDouble());
    }
}
