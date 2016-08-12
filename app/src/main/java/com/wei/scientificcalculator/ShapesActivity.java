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

import com.wei.scientificcalculator.fragments.ShapeCalFragment;
import com.wei.scientificcalculator.models.Shape;
import com.wei.scientificcalculator.util.ShapeCatalog;


public class ShapesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout navDrawer;
    private ShapeCatalog shapeCatalog;
    private int shapeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shapeCatalog = ShapeCatalog.getInstance();
        setContentView(R.layout.activity_shapes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
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

        // choose sphere as default shape
        shapeId = Shape.SPHERE;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(shapeId).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(shapeCatalog.getShapeById(shapeId).getLabelResource());
        }

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_shapes, ShapeCalFragment.newInstance(shapeId))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_shapes, menu);
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
                shapeId = getShapeFromDrawer(item.getItemId());

                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(shapeCatalog.getShapeById(shapeId).getLabelResource());
                }

                getFragmentManager().beginTransaction()
                        .replace(R.id.content_shapes, ShapeCalFragment.newInstance(shapeId))
                        .commit();

                navDrawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }

    @Shape.ShapeIds
    private int getShapeFromDrawer(int itemId) {

        switch(itemId) {
            case R.id.nav_sphere:
                return Shape.SPHERE;
            case R.id.nav_cube:
                return Shape.CUBE;
            case R.id.nav_cone:
                return Shape.CONE;
            case R.id.nav_cylinder:
                return Shape.CYLINDER;
            case R.id.nav_capsule:
                return Shape.CAPSULE;
            case R.id.nav_ellipsoid:
                return Shape.ELLIPSOID;
        }

        return Shape.SPHERE;
    }
}
