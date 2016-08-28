package com.padc.aml.mymeals.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.padc.aml.mymeals.MyMealsApp;
import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.utils.MyMealsConstants;

/**
 * Created by aung on 7/10/16.
 */
public class MealImagesPagerAdapter extends PagerAdapter {

    private List<String> mImages;
    private LayoutInflater mInflater;

    public MealImagesPagerAdapter(String[] images) {
        if (images == null) {
            mImages = new ArrayList<>();
        } else {
            mImages = new ArrayList<>(Arrays.asList(images));
        }
        mInflater = LayoutInflater.from(MyMealsApp.getContext());
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView ivMeal = (ImageView) mInflater.inflate(R.layout.view_item_meal_image, container, false);

        String imageUrl = MyMealsConstants.IMAGE_ROOT_DIR + mImages.get(position);
        Glide.with(ivMeal.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.stock_photo_placeholder)
                .error(R.drawable.stock_photo_placeholder)
                .into(ivMeal);

        ((ViewPager) container).addView(ivMeal);

        return ivMeal;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
