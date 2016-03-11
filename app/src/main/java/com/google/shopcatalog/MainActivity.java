package com.google.shopcatalog;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.shopcatalog.fragment.FragmentCategoriesList;
import com.google.shopcatalog.fragment.FragmentContacts;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_OFFERS_LIST = "com.google.shop_catalog.offers_list";
    public static final String EXTRA_OFFER = "com.google.shop_catalog.offer";
    public static final String EXTRA_NAME_CATEGORY = "com.google.catalog.name_category";
    public static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new FragmentCategoriesList()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id ==R.id.nav_catalog){
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, new FragmentCategoriesList())
                    .commit();
        } else if(id == R.id.nav_contacts){
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, new FragmentContacts())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
