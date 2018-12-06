package com.example.android.sudo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.example.android.sudo.R;
import com.example.android.sudo.SettingsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = "CalendarActivity".getClass().getSimpleName();

    private CalendarView mCalendarView;
    private NavigationView mNavigationView;
    private View mHeaderView;
    private Toolbar mToolbar;
    private List<EventDay> mEventDays;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUpSharedPreference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initActionBar();
        initFab();
        initDrawer();
        initCalendarView();
        initEvents();

    }

    private void setUpSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadThemeFromPreferences(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void initActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        try {
            getSupportActionBar().setTitle(mCalendarView.getCalendarTitleDate());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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

    private void initCalendarView() {
        mCalendarView = findViewById(R.id.calendarView);

        mCalendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                Log.v(LOG_TAG, "initCalendarView: calendarView moved backwards");
                getSupportActionBar().setTitle(mCalendarView.getCalendarTitleDate());
            }
        });

        mCalendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                Log.v(LOG_TAG, "initCalendarView: calendarView moved forward");
                getSupportActionBar().setTitle(mCalendarView.getCalendarTitleDate());
            }
        });
    }

    private void initEvents() {
        mEventDays = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 12);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 16);

        Drawable draw = getResources().getDrawable(R.drawable.test_event);
        draw.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

        mEventDays.add(new EventDay(calendar1, draw));
        mEventDays.add(new EventDay(calendar2, draw));

        mCalendarView.setEvents(mEventDays);
        Log.v(LOG_TAG, "onCreate: number of events added" + mEventDays.size());
    }

    private void setCalendarToday() {
        Calendar cal = Calendar.getInstance();

        try {
            mCalendarView.setDate(cal);
        } catch(OutOfDateRangeException e) {
            e.printStackTrace();
        }
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

            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimary));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimary));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimary));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorAccent));
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: RED themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_blue_theme_key))) {
            setTheme(R.style.AppThemeBlue);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryBlue);
            }

            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryBlue));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimaryBlue));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimaryBlue));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorBlueAccent));
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: BLUE themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_green_theme_key))) {
            setTheme(R.style.AppThemeGreen);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryGreen);
            }


            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryGreen));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimaryGreen));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimaryGreen));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorGreenAccent));
            }


            Log.v(LOG_TAG, "loadThemeFromPreferences: GREEN themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_purple_theme_key))) {
            setTheme(R.style.AppThemePurple);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryPurple);
            }

            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryPurple));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimaryPurple));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimaryPurple));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorPurpleAccent));
            }


            Log.v(LOG_TAG, "loadThemeFromPreferences: PURPLE themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_pink_theme_key))) {
            setTheme(R.style.AppThemePink);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryPink);
            }

            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryPink));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimaryPink));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimaryPink));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorPinkAccent));
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: PINK themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_orange_theme_key))) {
            setTheme(R.style.AppThemeOrange);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryOrange);
            }


            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryOrange));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimaryOrange));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimaryOrange));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorOrangeAccent));
            }

            Log.v(LOG_TAG, "loadThemeFromPreferences: ORANGE themes from Shared Preferences.");
        } else if(sharedPreferenceTheme.equals(getString(R.string.pref_show_mint_theme_key))) {
            setTheme(R.style.AppThemeMint);

            if(mHeaderView != null) {
                mHeaderView.setBackgroundResource(R.color.colorPrimaryMint);
            }

            if(mCalendarView != null) {
                mCalendarView.setSelectionColor(getResources().getColor(R.color.colorPrimaryMint));
                mCalendarView.setEventDayColor(getResources().getColor(R.color.colorPrimaryMint));
                mCalendarView.setWeekDayBarColor(getResources().getColor(R.color.colorPrimaryMint));
                mCalendarView.setTodayColor(getResources().getColor(R.color.colorMintAccent));
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
            Intent intent = new Intent(this, CalendarActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar_today:
                setCalendarToday();
                Log.v(LOG_TAG, "onOptionsItemSelected: clicked the calendar today icon.");
                return true;
            case R.id.action_hide_events:
                // TODO: open the hide events tab
                Log.v(LOG_TAG, "onOptionsItemSelected: clicked the hide events tab.");
                return true;
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
                break;

            case R.id.nav_agenda:

                break;
            case R.id.nav_calendar:
                Log.v(LOG_TAG, "onNavigationItemSelected: navigation calendar item selected");
                Intent calendarIntent = new Intent(this, CalendarActivity.class);
                startActivity(calendarIntent);
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
