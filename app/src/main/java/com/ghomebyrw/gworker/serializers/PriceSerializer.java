package com.ghomebyrw.gworker.serializers;

import com.ghomebyrw.gworker.models.Job;
import com.ghomebyrw.gworker.models.Price;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by wewang on 11/24/15.
 */
public class PriceSerializer implements JsonSerializer<Price> {
    @Override
    public JsonElement serialize(Price src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        JsonElement finalPrice = new Gson().toJsonTree(src);
        object.add("finalPrice", finalPrice);
        return object;
    }
}
