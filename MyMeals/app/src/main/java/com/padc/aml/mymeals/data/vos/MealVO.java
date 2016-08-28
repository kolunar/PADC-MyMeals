package com.padc.aml.mymeals.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.padc.aml.mymeals.MyMealsApp;

/**
 * Created by user on 8/28/2016.
 */
public class MealVO {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("img_url")
    private String img_url;

    @SerializedName("ingredients")
    private String[] ingredients;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return name;
    }

    public String getDesc() {
        return description;
    }

    public String getImage() {
        return img_url;
    }

    public void setImage(String img_url) {
        this.img_url = img_url;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }


}