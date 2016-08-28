package com.padc.aml.mymeals.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.utils.MyMealsConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 8/28/2016.
 */
public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_meal_title)
    TextView tvMealTitle;

    @BindView(R.id.iv_meal)
    ImageView ivMeal;

    @BindView(R.id.tv_meal_desc)
    TextView tvMealDesc;

    private ControllerMealItem mController;
    private MealVO mMeal;

    public MealViewHolder(View itemView, ControllerMealItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(MealVO meal) {
        mMeal = meal;
        tvMealTitle.setText(meal.getTitle());
        tvMealDesc.setText(meal.getDesc());

        String imageUrl = MyMealsConstants.IMAGE_ROOT_DIR + meal.getImage();

        Glide.with(ivMeal.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.stock_photo_placeholder)
                .error(R.drawable.stock_photo_placeholder)
                .into(ivMeal);
    }

    @Override
    public void onClick(View view) {
        mController.onTapMeal(mMeal, ivMeal);
    }

    public interface ControllerMealItem {
        void onTapMeal(MealVO meal, ImageView ivMeal);
    }
}
