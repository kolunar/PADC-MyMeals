package com.padc.aml.mymeals.data.models;

import android.util.Log;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.data.agents.HttpUrlConnectionDataAgent;
import com.padc.aml.mymeals.data.agents.MealDataAgent;
import com.padc.aml.mymeals.data.agents.OfflineDataAgent;
import com.padc.aml.mymeals.data.agents.OkHttpDataAgent;
import com.padc.aml.mymeals.data.agents.retrofit.RetrofitDataAgent;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 8/28/2016.
 */
public abstract class BaseModel {

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    protected MealDataAgent dataAgent;

    public BaseModel() {
        initDataAgent(INIT_DATA_AGENT_RETROFIT);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    private void initDataAgent(int initType) {
        switch (initType) {
            case INIT_DATA_AGENT_OFFLINE:
                Log.d(MyMealsApp.TAG, "BaseModel:initDataAgent:INIT_DATA_AGENT_OFFLINE");
                dataAgent = OfflineDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_HTTP_URL_CONNECTION:
                Log.d(MyMealsApp.TAG, "BaseModel:initDataAgent:INIT_DATA_AGENT_HTTP_URL_CONNECTION");
                dataAgent = HttpUrlConnectionDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_OK_HTTP:
                Log.d(MyMealsApp.TAG, "BaseModel:initDataAgent:INIT_DATA_AGENT_OK_HTTP");
                dataAgent = OkHttpDataAgent.getInstance();
                break;
            case INIT_DATA_AGENT_RETROFIT:
                Log.d(MyMealsApp.TAG, "BaseModel:initDataAgent:INIT_DATA_AGENT_RETROFIT");
                dataAgent = RetrofitDataAgent.getInstance();
                break;
        }
    }

    public void onEventMainThread(Object obj) {

    }
}
