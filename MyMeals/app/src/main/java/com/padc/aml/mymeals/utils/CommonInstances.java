package com.padc.aml.mymeals.utils;

import com.google.gson.Gson;

/**
 * Created by user on 8/28/2016.
 */
public class CommonInstances {

    private static Gson gson = new Gson();

    public static Gson getGsonInstance() {
        return gson;
    }
}
