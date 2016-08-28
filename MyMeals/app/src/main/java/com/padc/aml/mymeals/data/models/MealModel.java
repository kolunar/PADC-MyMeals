package com.padc.aml.mymeals.data.models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.data.agents.MealDataAgent;
import com.padc.aml.mymeals.data.agents.HttpUrlConnectionDataAgent;
import com.padc.aml.mymeals.data.agents.OfflineDataAgent;
import com.padc.aml.mymeals.data.agents.OkHttpDataAgent;
import com.padc.aml.mymeals.data.agents.retrofit.RetrofitDataAgent;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.events.DataEvent;

/**
 * Created by user on 8/28/2016.
 */
public class MealModel extends BaseModel {

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    private static MealModel objInstance;

    private List<MealVO> mMealList;

    private MealDataAgent dataAgent;

    private MealModel() {
        mMealList = new ArrayList<>();
        //initDataAgent(INIT_DATA_AGENT_OFFLINE);
        //initDataAgent(INIT_DATA_AGENT_RETROFIT);
        //initDataAgent(INIT_DATA_AGENT_OK_HTTP);
        //initDataAgent(INIT_DATA_AGENT_HTTP_URL_CONNECTION);
        initDataAgent(INIT_DATA_AGENT_RETROFIT);
        dataAgent.loadMeals();
    }

/*    public void loadMeals() {
        dataAgent.loadMeals();
    }*/

    public static MealModel getInstance() {
        Log.d(MyMealsApp.TAG, "MealModel.getInstance():");
        if (objInstance == null) {
            objInstance = new MealModel();
        }
        return objInstance;
    }

    private void initDataAgent(int initType) {
        switch (initType) {
            case INIT_DATA_AGENT_OFFLINE:
                Log.d(MyMealsApp.TAG, "MealModel:initDataAgent:INIT_DATA_AGENT_OFFLINE");
                dataAgent = OfflineDataAgent.getInstance();

                break;
            case INIT_DATA_AGENT_HTTP_URL_CONNECTION:
                Log.d(MyMealsApp.TAG, "MealModel:initDataAgent:INIT_DATA_AGENT_HTTP_URL_CONNECTION");
                dataAgent = HttpUrlConnectionDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_OK_HTTP:
                Log.d(MyMealsApp.TAG, "MealModel:initDataAgent:INIT_DATA_AGENT_OK_HTTP");
                dataAgent = OkHttpDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_RETROFIT:
                Log.d(MyMealsApp.TAG, "MealModel:initDataAgent:INIT_DATA_AGENT_RETROFIT");
                dataAgent = RetrofitDataAgent.getInstance();
                break;
        }
    }

    public List<MealVO> getMealList() {
        return mMealList;
    }

    public MealVO getMealByName(String mealName) {
        for (MealVO meal : mMealList) {
            if (meal.getTitle().equals(mealName))
                return meal;
        }

        return null;
    }

    public String getRandomMealImage(){

        return null;
    }

    public void notifyMealsLoaded(List<MealVO> mealList) {
        if(mealList != null) {
            //Notify that the data is ready - using LocalBroadcast
            mMealList = mealList;

            //keep the data in persistent layer.
            //MealVO.saveMeals(mMealList);

            broadcastMealLoadedWithEventBus();
            //broadcastMealLoadedWithLocalBroadcastManager();
        }
    }

    public void notifyErrorInLoadingMeals(String message) {

    }

    private void broadcastMealLoadedWithLocalBroadcastManager() {
        //Toast.makeText(MyMealsApp.getContext(), "broadcastMealLoadedWithLocalBroadcastManager", Toast.LENGTH_LONG).show();
        Log.d(MyMealsApp.TAG, "MealModel:broadcastMealLoadedWithLocalBroadcastManager()");
        Intent intent = new Intent(BROADCAST_DATA_LOADED);
        intent.putExtra("key-for-extra", "extra-in-broadcast");
        LocalBroadcastManager.getInstance(MyMealsApp.getContext()).sendBroadcast(intent);
    }

    private void broadcastMealLoadedWithEventBus() {
        //Toast.makeText(MyMealsApp.getContext(), "broadcastMealLoadedWithEventBus", Toast.LENGTH_LONG).show();
        Log.d(MyMealsApp.TAG, "MealModel:broadcastMealLoadedWithEventBus()");
        EventBus.getDefault().post(new DataEvent.MealDataLoadedEvent("extra-in-broadcast", mMealList));
        //EventBus.getDefault().post(new DataEvent.MealDataLoadedEvent("extra-in-broadcast"));
    }

    public void setStoredData(List<MealVO> mealList) {
        mMealList = mealList;
    }
}