package com.example.android.sudo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.sudo.R;
import com.example.android.sudo.SettingsActivity;

public class OverviewActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = "OverviewActivity".getClass().getSimpleName();

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private NavigationView mNavigationView;
    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpSharedPreference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        initActionBar();
        initFab();
        initDrawer();
    }

    private void setUpSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadThemeFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void initActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    private void initFab() {
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void initDrawer() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mHeaderView = mNavigationView.getHeaderView(0);
    }

    private void loadThemeFromPreferences(SharedPreferences sharedPreferences) {
        String sharedPreferenceTheme = sharedPreferences.getString(getString(R.string.pref_theme_key),
                getString(R.string.pref_show_red_theme_label));

        Log.v(LOG_TAG, "loadThemeFromPreferences: load theme from preferences.");

        if(sharedPreferenceTheme.equals(getString(R.string.pref_show_red_theme_key))) {
            setTheme(R.style.AppThemeRed);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: RED themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_blue_theme_key))) {
            setTheme(R.style.AppThemeBlue);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryBlue);
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: BLUE themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_green_theme_key))) {
            setTheme(R.style.AppThemeGreen);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryGreen);
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: GREEN themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_purple_theme_key))) {
            setTheme(R.style.AppThemePurple);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryPurple);
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: PURPLE themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_pink_theme_key))) {
            setTheme(R.style.AppThemePink);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryPink);
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: PINK themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_orange_theme_key))) {
            setTheme(R.style.AppThemeOrange);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryOrange);
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: ORANGE themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_mint_theme_key))) {
            setTheme(R.style.AppThemeMint);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryMint);
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: MINT themes from Shared Preferences.");
        }
    }

    private void loadNightModeFromPreferences(SharedPreferences sharedPreferences) {
        /*String sharedPreferenceTheme = sharedPreferences.getString(getString(R.string.pref_theme_key),
                getString(R.string.pref_show_default_theme_label));

        if(sharedPreferenceTheme.equals(getString(R.string.pref_show_default_theme_key))) {
        } else {

        }*/
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_theme_key))) {
            loadThemeFromPreferences(sharedPreferences);
            Intent intent = new Intent(this, OverviewActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.v(LOG_TAG, "onOptionsItemSelected:: opened Settings activity");
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id) {
            case R.id.nav_overview:
                Log.v(LOG_TAG, "onNavigationItemSelected: navigation overview item selected");
                Intent overviewIntent = new Intent(this, OverviewActivity.class);
                startActivity(overviewIntent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.nav_agenda:

                break;
            case R.id.nav_calendar:
                Log.v(LOG_TAG, "onNavigationItemSelected: navigation calendar item selected");
                Intent calendarIntent = new Intent(this, CalendarActivity.class);
                startActivity(calendarIntent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;

            case R.id.nav_statistics:
                break;
            default:
                Log.v(LOG_TAG, "onNavigationItemSelected: default item selected");
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpSharedPreference();

    }

}
