package com.padc.aml.mymeals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.views.holders.MealViewHolder;

/**
 * Created by aung on 7/16/16.
 */
public class ListViewMealAdapter extends BaseAdapter {

    private List<MealVO> mealList;
    private LayoutInflater inflater;
    private MealViewHolder.ControllerMealItem controllerMealItem;

    public ListViewMealAdapter(List<MealVO> mealList, MealViewHolder.ControllerMealItem controllerMealItem) {
        if (mealList != null) {
            this.mealList = mealList;
        } else {
            this.mealList = new ArrayList<>();
        }
        inflater = LayoutInflater.from(MyMealsApp.getContext());
        this.controllerMealItem = controllerMealItem;
    }

    @Override
    public int getCount() {
        return mealList.size();
    }

    @Override
    public MealVO getItem(int position) {
        return mealList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MealViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.view_item_meal, parent, false);
            viewHolder = new MealViewHolder(convertView, controllerMealItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MealViewHolder) convertView.getTag();
        }

        viewHolder.bindData(getItem(position));
        return convertView;
    }


    public void setNewData(List<MealVO> newMealList) {
        mealList = newMealList;
        notifyDataSetChanged();
    }
}
