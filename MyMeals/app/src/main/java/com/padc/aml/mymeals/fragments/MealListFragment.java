package com.padc.aml.mymeals.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.adapters.MealAdapter;
import com.padc.aml.mymeals.data.models.MealModel;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.events.DataEvent;
import com.padc.aml.mymeals.utils.MyMealsConstants;
import com.padc.aml.mymeals.views.holders.MealViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A placeholder fragment containing a simple view.
 */
public class MealListFragment extends Fragment {

    @BindView(R.id.rv_meals)
    RecyclerView rvMeals;

    private MealAdapter mMealAdapter;
    private MealViewHolder.ControllerMealItem controllerMealItem;


    public static MealListFragment newInstance() {
        MealListFragment fragment = new MealListFragment();
        return fragment;
    }

    private BroadcastReceiver mDataLoadedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO instructions when the new data is ready.
            String extra = intent.getStringExtra("key-for-extra");
            Toast.makeText(getContext(), "Extra : " + extra, Toast.LENGTH_SHORT).show();

            List<MealVO> newMealList = MealModel.getInstance().getMealList();
            mMealAdapter.setNewData(newMealList);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerMealItem = (MealViewHolder.ControllerMealItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_list, container, false);
        ButterKnife.bind(this, rootView);

        Log.d(MyMealsApp.TAG, "MealListFragment:onCreateView():");
        List<MealVO> mealList = MealModel.getInstance().getMealList();
        mMealAdapter = new MealAdapter(mealList, controllerMealItem);
        rvMeals.setAdapter(mMealAdapter);

        int gridColumnSpanCount = getResources().getInteger(R.integer.meal_list_grid);
        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), gridColumnSpanCount));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity().getSupportLoaderManager().initLoader(MyMealsConstants.MEAL_LIST_LOADER, null, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mDataLoadedBroadcastReceiver, new IntentFilter(MealModel.BROADCAST_DATA_LOADED));

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mDataLoadedBroadcastReceiver);

        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    public void onEventMainThread(DataEvent.MealDataLoadedEvent event) {
        String extra = event.getExtraMessage();
        Toast.makeText(getContext(), "MealListFragment:onEventMainThread:getExtraMessage(): " + extra, Toast.LENGTH_LONG).show();

        //List<MealVO> newMealList = MealModel.getInstance().getMealList();
        List<MealVO> newMealList = event.getMealList();
        mMealAdapter.setNewData(newMealList);
    }
}

