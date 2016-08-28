package com.padc.aml.mymeals.data.agents.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.padc.aml.mymeals.data.responses.MealListResponse;
import com.padc.aml.mymeals.utils.MyMealsConstants;

/**
 * Created by aung on 7/9/16.
 */
public interface MealApi {

    @FormUrlEncoded
    @POST(MyMealsConstants.API_GET_MEAL_LIST)
    Call<MealListResponse> loadMeals(
            @Field(MyMealsConstants.PARAM_ACCESS_TOKEN) String param);

}