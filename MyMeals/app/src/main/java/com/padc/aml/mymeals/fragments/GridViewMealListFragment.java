package com.padc.aml.mymeals.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.adapters.ListViewMealAdapter;
import com.padc.aml.mymeals.data.models.MealModel;
//import com.padc.aml.mymeals.data.persistence.MealsContract;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.utils.MyMealsConstants;
import com.padc.aml.mymeals.views.holders.MealViewHolder;

/**
 * Created by aung on 7/19/16.
 */
public class GridViewMealListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    @BindView(R.id.gv_meals)
    GridView gvMeals;

    private ListViewMealAdapter mAdapter;
    private MealViewHolder.ControllerMealItem mControllerMealItem;

    public static GridViewMealListFragment newInstance() {
        GridViewMealListFragment fragment = new GridViewMealListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mControllerMealItem = (MealViewHolder.ControllerMealItem) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ListViewMealAdapter(null, mControllerMealItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gridview_meal_list, container, false);
        ButterKnife.bind(this, rootView);

        gvMeals.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(MyMealsConstants.MEAL_LIST_LOADER_GRIDVIEW, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}