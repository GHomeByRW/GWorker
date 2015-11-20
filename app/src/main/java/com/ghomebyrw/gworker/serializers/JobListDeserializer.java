package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
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
            Job job = new Job(jobJson.get("location").getAsJsonObject().get("address").getAsString(),
                    deserialize(jobJson.get("scheduledDateAndTime").getAsJsonObject())
                    );
            jobs.add(job);
        }

        return jobs;
    }

    public ScheduledDateAndTime deserialize(JsonObject jsonObject) {
        try {
            String startTime = jsonObject.get("startTime").getAsString();
            String endTime = jsonObject.get("endTime").getAsString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            return new ScheduledDateAndTime(format.parse(startTime),format.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
