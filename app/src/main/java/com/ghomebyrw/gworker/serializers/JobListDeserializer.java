package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
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
            jobs.add(JobDeserializeHelper.deserialize(jobJson));
        }

        return jobs;
    }

}
