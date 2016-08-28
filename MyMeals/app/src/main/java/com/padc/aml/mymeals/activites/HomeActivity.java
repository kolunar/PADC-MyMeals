package com.padc.aml.mymeals.activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.padc.aml.mymeals.R;
import com.padc.aml.mymeals.data.vos.MealVO;
import com.padc.aml.mymeals.fragments.GridViewMealListFragment;
import com.padc.aml.mymeals.fragments.ListViewMealListFragment;
import com.padc.aml.mymeals.fragments.MealListFragment;
import com.padc.aml.mymeals.views.holders.MealViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity
        implements MealViewHolder.ControllerMealItem,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.fab_search)
    FloatingActionButton fabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Menu leftMenu = navigationView.getMenu();
        //MMFontUtils.applyMMFontToMenu(leftMenu);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigateToRecyclerView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        fabSearch.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            case R.id.my_meals_recycler_view:
                navigateToRecyclerView();
                return true;
            case R.id.my_meals_list_view:
                navigateToListView();
                return true;
            case R.id.my_meals_grid_view:
                navigateToGridView();
                return true;
        }
        return false;
    }

    private void navigateToListView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, ListViewMealListFragment.newInstance())
                .commit();
    }

    private void navigateToRecyclerView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, MealListFragment.newInstance())
                .commit();
    }

    private void navigateToGridView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, GridViewMealListFragment.newInstance())
                .commit();
    }

    @Override
    public void onTapMeal(MealVO meal, ImageView ivMeal) {

    }
}
