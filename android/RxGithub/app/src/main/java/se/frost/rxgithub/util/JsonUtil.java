package se.frost.rxgithub.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-26.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public class JsonUtil {

    /**
     * Convert a JSON string to pretty print version
     * @param jsonString
     * @return
     */
    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(json);
    }

}
