package com.padc.aml.mymeals.events;

import java.util.List;

import com.padc.aml.mymeals.data.vos.MealVO;

/**
 * Created by user on 8/28/2016.
 */
public class DataEvent {
    public static class MealDataLoadedEvent {

        private String extraMessage;
        private List<MealVO> mealList;

        public MealDataLoadedEvent(String extraMessage, List<MealVO> mealList) {
            this.extraMessage = extraMessage;
            this.mealList = mealList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<MealVO> getMealList() {
            return mealList;
        }
    }

    public static class RefreshUserLoginStatusEvent {

    }
}