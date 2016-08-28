package com.padc.aml.mymeals.data.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import com.padc.aml.mymeals.data.vos.MealVO;

/**
 * Created by user on 8/28/2016.
 */
public class MealListResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("meal_list")
    private ArrayList<MealVO> meal_list;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MealVO> getMealList() {
        return meal_list;
    }
}
