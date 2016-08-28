package com.padc.aml.mymeals.data.agents;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.data.models.MealModel;
import com.padc.aml.mymeals.data.responses.MealListResponse;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.utils.CommonInstances;
import com.padc.aml.mymeals.utils.MyMealsConstants;

/**
 * Created by user on 8/28/2016.
 */
public class OkHttpDataAgent implements MealDataAgent {

    private static OkHttpDataAgent objInstance;

    private OkHttpClient mHttpClient;

    private OkHttpDataAgent() {
        mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new OkHttpDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadMeals() {
        Log.d(MyMealsApp.TAG, "OkHttpDataAgent:loadMeals()");
        new AsyncTask<Void, Void, List<MealVO>>() {

            @Override
            protected List<MealVO> doInBackground(Void... voids) {
                Log.d(MyMealsApp.TAG, "OkHttpDataAgent:loadMeals():doInBackground()");
                RequestBody formBody = new FormBody.Builder()
                        .add(MyMealsConstants.PARAM_ACCESS_TOKEN, MyMealsConstants.ACCESS_TOKEN)
                        .build();

                Request request = new Request.Builder()
                        .url(MyMealsConstants.MEAL_BASE_URL + MyMealsConstants.API_GET_MEAL_LIST)
                        .post(formBody)
                        .build();

                try {
                    Response response = mHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d(MyMealsApp.TAG, "OkHttpDataAgent:loadMeals():doInBackground():response.isSuccessful()");
                        String responseString = response.body().string();
                        MealListResponse responseMealList = CommonInstances.getGsonInstance().fromJson(responseString, MealListResponse.class);
                        List<MealVO> mealList = responseMealList.getMealList();
                        return mealList;
                    } else {
                        MealModel.getInstance().notifyErrorInLoadingMeals(response.message());
                    }
                } catch (IOException e) {
                    Log.e(MyMealsApp.TAG, e.getMessage());
                    MealModel.getInstance().notifyErrorInLoadingMeals(e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<MealVO> mealList) {
                super.onPostExecute(mealList);
                if (mealList != null && mealList.size() > 0) {
                    MealModel.getInstance().notifyMealsLoaded(mealList);
                    Log.d(MyMealsApp.TAG, "OkHttpDataAgent:onPostExecute:mealList.size():"+mealList.size());
                }
                else{
                    Log.e(MyMealsApp.TAG, "OkHttpDataAgent:onPostExecute:mealList.size():0");
                }
            }
        }.execute();
    }
}