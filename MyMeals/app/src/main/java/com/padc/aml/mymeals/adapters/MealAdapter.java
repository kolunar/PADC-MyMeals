package com.padc.aml.mymeals.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.views.holders.MealViewHolder;

/**
 * Created by aung on 7/6/16.
 */
public class MealAdapter extends RecyclerView.Adapter<MealViewHolder> {

    private LayoutInflater mInflater;
    private List<MealVO> mMealList;
    private MealViewHolder.ControllerMealItem mControllerMealItem;

    public MealAdapter(List<MealVO> mealList, MealViewHolder.ControllerMealItem controllerMealItem) {
        mInflater = LayoutInflater.from(MyMealsApp.getContext());
        mMealList = mealList;
        mControllerMealItem = controllerMealItem;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.view_item_meal, parent, false);
        return new MealViewHolder(itemView, mControllerMealItem);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        holder.bindData(mMealList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMealList.size();
    }

    public void setNewData(List<MealVO> newMealList) {
        mMealList = newMealList;
        notifyDataSetChanged();
    }
}
