package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.JobStatus;
import com.ghomebyrw.gworker.models.Price;
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
                    deserializePrice(jobJson.get("acceptedPrice").getAsJsonObject()),
                    jobJson.get("location").getAsJsonObject().get("address").getAsString(),
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
        return new Price(jsonObject.get("amount").getAsInt(),
                jsonObject.get("currencyCode").getAsString(),
                jsonObject.get("formattedAmount").getAsString());
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
        JsonObject fieldworkerObject = jobObject.get("fieldworker").getAsJsonObject();
        return fieldworkerObject.get("firstName").getAsString() +
                fieldworkerObject.get("lastName").getAsString();


    }
}
