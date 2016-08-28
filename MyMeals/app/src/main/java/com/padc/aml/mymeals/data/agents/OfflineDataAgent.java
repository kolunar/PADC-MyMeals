package com.padc.aml.mymeals.data.agents;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.data.models.MealModel;
import com.padc.aml.mymeals.data.responses.MealListResponse;
import com.padc.aml.mymeals.utils.CommonInstances;
import com.padc.aml.mymeals.utils.JsonUtils;

/**
 * Created by user on 7/9/2016.
 */
public class OfflineDataAgent implements MealDataAgent {
    @Override
    public void loadMeals() {
        try {
            String meals = JsonUtils.getInstance().loadDummyData("my_meals.json");

/*            Type listType = new TypeToken<List<MealVO>>() {
            }.getType();
            List<MealVO> mealList = CommonInstances.getGsonInstance().fromJson(meals, listType);*/
            Log.d(MyMealsApp.TAG, "OfflineDataAgent:loadMeals():meals:"+meals);
            MealListResponse mealList = CommonInstances.getGsonInstance().fromJson(meals, MealListResponse.class);

            MealModel.getInstance().notifyMealsLoaded(mealList.getMealList());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static OfflineDataAgent objInstance;

    private OfflineDataAgent() {

    }
    public static OfflineDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new OfflineDataAgent();
        }
        return objInstance;
    }
}
