package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by wewang on 11/20/15.
 */
public class JobDeserializer implements JsonDeserializer<Job> {
    @Override
    public Job deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobJson = json.getAsJsonObject();
        return JobDeserializeHelper.deserialize(jobJson);
    }
}
