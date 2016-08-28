package com.padc.aml.mymeals.data.agents.retrofit;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.data.agents.MealDataAgent;
import com.padc.aml.mymeals.data.models.MealModel;
import com.padc.aml.mymeals.data.responses.MealListResponse;
import com.padc.aml.mymeals.utils.CommonInstances;
import com.padc.aml.mymeals.utils.MyMealsConstants;

/**
 * Created by user on 8/28/2016.
 */
public class RetrofitDataAgent implements MealDataAgent {

    private static RetrofitDataAgent objInstance;

    private final MealApi theApi;

    private RetrofitDataAgent() { // using Builder pattern
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyMealsConstants.MEAL_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(MealApi.class);
    }

    public static RetrofitDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadMeals() {
        //Toast.makeText(MyMealsApp.getContext(), "RetroitDataAgent:loadMeals(): ", Toast.LENGTH_LONG).show();
        Log.d(MyMealsApp.TAG, "RetroitDataAgent:loadMeals():");
        Call<MealListResponse> loadMealCall = theApi.loadMeals(MyMealsConstants.ACCESS_TOKEN);
        loadMealCall.enqueue(new Callback<MealListResponse>() {
            @Override
            public void onResponse(Call<MealListResponse> call, Response<MealListResponse> response) {
                Log.d(MyMealsApp.TAG, "RetroitDataAgent:loadMeals():loadMealCall:onResponse():");
                MealListResponse mealListResponse = response.body();
                if (mealListResponse == null) {
                    Log.d(MyMealsApp.TAG, "RetroitDataAgent:loadMeals():loadMealCall:onResponse():mealListResponse");
                    MealModel.getInstance().notifyErrorInLoadingMeals(response.message());
                } else {
                    Log.d(MyMealsApp.TAG, "RetroitDataAgent:loadMeals():loadMealCall:onResponse():null");
                    MealModel.getInstance().notifyMealsLoaded(mealListResponse.getMealList());
                }
            }

            @Override
            public void onFailure(Call<MealListResponse> call, Throwable throwable) {
                Log.e(MyMealsApp.TAG, "RetroitDataAgent:loadMeals():loadMealCall:onFailure():");
                MealModel.getInstance().notifyErrorInLoadingMeals(throwable.getMessage());
            }
        });
    }

}
