package com.applandeo.materialcalendarview.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.TextView;

import com.applandeo.materialcalendarview.R;

import java.util.ArrayList;
import java.util.List;


public final class AppearanceUtils {

    public static void setWeekDayLabels(View view, int color, int firstDayOfWeek) {

        List<TextView> labels = new ArrayList<>();
        labels.add((TextView) view.findViewById(R.id.mondayLabel));
        labels.add((TextView) view.findViewById(R.id.tuesdayLabel));
        labels.add((TextView) view.findViewById(R.id.wednesdayLabel));
        labels.add((TextView) view.findViewById(R.id.thursdayLabel));
        labels.add((TextView) view.findViewById(R.id.fridayLabel));
        labels.add((TextView) view.findViewById(R.id.saturdayLabel));
        labels.add((TextView) view.findViewById(R.id.sundayLabel));

        String[] weekDay = view.getContext().getResources().getStringArray(R.array.material_calendar_day_week_day_array);
        for (int i = 0; i < 7; i++) {
            TextView label = labels.get(i);
            label.setText(weekDay[(i + firstDayOfWeek - 1) % 7]);

            if (color != 0) {
                label.setTextColor(color);
            }
        }
    }


    public static void setSelectionColor(Resources res, int color) {
        if(color == 0) {
            return;
        }
        Drawable draw = res.getDrawable(R.drawable.selected_day_bg_shape);
        DrawableCompat.setTint(draw, color);

    }

    public static void setEventSelectionColor(Resources res, int color) {
        if(color == 0) {
            return;
        }
        Drawable draw = res.getDrawable(R.drawable.selected_event_day_bg_shape);
        DrawableCompat.setTint(draw, color);

    }
    public static void setTodaySelectionColor(Resources res, int color) {
        if(color == 0) {
            return;
        }
        Drawable draw = res.getDrawable(R.drawable.selected_today_day_bg_shape);
        DrawableCompat.setTint(draw, color);

    }


    public static void setToolbarColor(View view, int color) {
        if(color == 0) {
            return;
        }

        view.findViewById(R.id.calendarToolbar).setBackgroundColor(color);
    }

    public static void setWeekDayBarColor(View view, int color) {
        if (color == 0) {
            return;
        }

        view.findViewById(R.id.weekDayBar).setBackgroundColor(color);
    }

    public static void setPagesColor(View view, int color) {
        if (color == 0) {
            return;
        }

        view.findViewById(R.id.calendarViewPager).setBackgroundColor(color);
    }

    private AppearanceUtils() {
    }

}
