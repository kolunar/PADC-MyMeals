package com.padc.aml.mymeals;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import com.padc.aml.mymeals.data.models.MealModel;

/**
 * Created by user on 8/28/2016.
 */
public class MyMealsApp extends Application {

    public static final String TAG = "MyMealsApp";

    private static Context context;
    private static Bitmap appIcon;
    private static Bitmap mealSight;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //MealSyncAdapter.syncImmediately(getContext());
        //MealSyncAdapter.initializeSyncAdapter(getContext());

        encodeAppIcon();
        encodeMealSight();
    }

    public static Context getContext() {
        return context;
    }

    public static Bitmap getAppIcon() {
        return appIcon;
    }

    public static Bitmap getMealSight() {
        return mealSight;
    }

    private void encodeAppIcon() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                //Encode bitmap for large notification icon
                Context context = MyMealsApp.getContext();
                int largeIconWidth = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width);
                int largeIconHeight = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);

                try {
                    appIcon = Glide.with(context)
                            .load(R.drawable.ic_meal_launcher_icon)
                            .asBitmap()
                            .into(largeIconWidth, largeIconHeight)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void encodeMealSight() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                //Encode bitmap for large notification icon
                Context context = MyMealsApp.getContext();
                int largeIconWidth = context.getResources().getDimensionPixelSize(R.dimen.meal_sight_width);
                int largeIconHeight = context.getResources().getDimensionPixelSize(R.dimen.meal_sight_height);

                try {
                    mealSight = Glide.with(context)
                            .load(MealModel.getInstance().getRandomMealImage())
                            .asBitmap()
                            .into(largeIconWidth, largeIconHeight)
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}