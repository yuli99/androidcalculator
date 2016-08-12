package com.wei.scientificcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.wei.scientificcalculator.fragments.UnitConvFragment;
import com.wei.scientificcalculator.models.UnitCategory;
import com.wei.scientificcalculator.util.UnitCatalog;


public class UnitsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout navDrawer;
    private UnitCatalog unitCatalog;
    private int unitCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unitCatalog = UnitCatalog.getInstance();
        setContentView(R.layout.activity_units);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // hide keyboard when drawer is open
                super.onDrawerOpened(drawerView);

                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);
            }
        };

        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        // choose Length conversion as default state
        unitCategoryId = UnitCategory.LENGTH;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(unitCategoryId).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(unitCatalog.getCategoryById(unitCategoryId).getLabelResource());
        }

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_units, UnitConvFragment.newInstance(unitCategoryId))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_units, menu);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (navDrawer.isDrawerOpen(GravityCompat.START)) {
            navDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        switch (item.getItemId()) {
            case R.id.menu_calculator:
                finish();
                Intent backToCalculator = new Intent(this, MainActivity.class);
                backToCalculator.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backToCalculator);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks
        switch (item.getItemId()) {
            case R.id.drawer_settings:
                return true;
            default:
                unitCategoryId = getUnitCategoryFromDrawer(item.getItemId());

                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(unitCatalog.getCategoryById(unitCategoryId).getLabelResource());
                }

                getFragmentManager().beginTransaction()
                        .replace(R.id.content_units, UnitConvFragment.newInstance(unitCategoryId))
                        .commit();

                navDrawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }

    @UnitCategory.CategoryIds
    private int getUnitCategoryFromDrawer(int itemId) {

        switch(itemId) {
            case R.id.nav_length:
                return UnitCategory.LENGTH;
            case R.id.nav_area:
                return UnitCategory.AREA;
            case R.id.nav_volume:
                return UnitCategory.VOLUME;
            case R.id.nav_pressure:
                return UnitCategory.PRESSURE;
            case R.id.nav_temperature:
                return UnitCategory.TEMPERATURE;
            case R.id.nav_weight:
                return UnitCategory.WEIGHT;
        }

        return UnitCategory.LENGTH;
    }
}
