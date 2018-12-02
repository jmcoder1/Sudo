package com.applandeo.materialcalendarview.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.utils.AppearanceUtils;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.applandeo.materialcalendarview.utils.DayColorsUtils;
import com.applandeo.materialcalendarview.utils.ImageUtils;
import com.applandeo.materialcalendarview.utils.SelectedDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

class CalendarDayAdapter extends ArrayAdapter<Date> {
    private static final String LOG_TAG = "CalendarDayAdapter".getClass().getSimpleName();
    private CalendarPageAdapter mCalendarPageAdapter;
    private LayoutInflater mLayoutInflater;
    private CalendarProperties mCalendarProperties;

    private Calendar mToday = DateUtils.getCalendar();
    private int mPageMonth;

    // Day TextView which previously had special styling - such as the current day
    private List<TextView> mEventDayTextView = new ArrayList<>();
    private View mLastClickedDayParentView;
    private TextView mLastClickedDayTextView;

    CalendarDayAdapter(CalendarPageAdapter calendarPageAdapter, Context context,
                       CalendarProperties calendarProperties, ArrayList<Date> dates, int pageMonth) {
        super(context, calendarProperties.getItemLayoutResource(), dates);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getItemLayoutResource(), parent,false);
        }

        View dayParent = view.findViewById(R.id.dayParent);
        TextView dayLabel = (TextView) view.findViewById(R.id.dayLabel);
        ImageView dayIcon = (ImageView) view.findViewById(R.id.dayIcon);

        setDayClickedListener(dayParent, dayLabel, dayIcon);
        setSelectorColors();
        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));

        // Loading an image of the event
        if (dayIcon != null) {
            loadIcon(dayIcon, day);
        }

        setLabelColors(dayLabel, dayIcon, dayParent, day);
        dayLabel.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));

        return view;
    }

    private void setLabelColors(TextView dayTextView, View dayIcon, View dayParent, Calendar day) {
        // Settings color for days not in the current month
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(dayTextView, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            dayTextView.setClickable(false);
            dayIcon.setClickable(false);
            dayParent.setClickable(false);
            return;
        }

        // Settings for selected days in the month
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(dayTextView));

            DayColorsUtils.setSelectedDayColors(dayTextView, mCalendarProperties);
            return;
        }

        if(day.equals(mToday)) {
            Log.v(LOG_TAG, "Calendar day today " + Calendar.DAY_OF_WEEK);
            mEventDayTextView.add(dayTextView);
            dayParent.setBackgroundResource(R.drawable.selected_today_day_bg);
        } else if(isEventDay(day)) {
            Log.v(LOG_TAG, "Calendar event day " + Calendar.DAY_OF_WEEK);
            mEventDayTextView.add(dayTextView);
            dayParent.setBackgroundResource(R.drawable.selected_event_day_bg);
        } else {
            dayParent.setBackgroundResource(R.drawable.selected_day_bg);
        }

        DayColorsUtils.setCurrentMonthDayColors(day, mToday, dayTextView, mCalendarProperties);
    }

    private void setSelectorColors() {
        AppearanceUtils.setSelectionColor(getContext().getResources(), mCalendarProperties.getSelectionColor());

        AppearanceUtils.setEventSelectionColor(getContext().getResources(), mCalendarProperties.getEventDayColor());

        AppearanceUtils.setTodaySelectionColor(getContext().getResources(), mCalendarProperties.getTodayColor());
    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC && day.get(Calendar.MONTH)
            == mPageMonth && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth &&
                !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                        || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private boolean isEventDay(Calendar day) {
        return mCalendarProperties.getEventCalendarDays().contains(day);

    }

    private boolean isActiveDay(Calendar day) {
        return !mCalendarProperties.getDisabledDays().contains(day);
    }

    private void loadIcon(ImageView dayIcon, Calendar day) {
        if (mCalendarProperties.getEventDays() == null || !mCalendarProperties.getEventsEnabled()) {
            dayIcon.setVisibility(View.INVISIBLE);
            return;
        }

        Stream.of(mCalendarProperties.getEventDays()).filter(eventDate ->
                eventDate.getCalendar().equals(day)).findFirst().executeIfPresent(eventDay -> {

            ImageUtils.loadImage(dayIcon, eventDay.getImageDrawable());

            // If a day doesn't belong to current month then image is transparent
            if (!isCurrentMonthDay(day) || !isActiveDay(day)) {
                dayIcon.setAlpha(0.12f);
            }

        });
    }

    private void setDayClickedListener(View dayParentView, TextView dayTextView, ImageView dayIcon) {
        View.OnClickListener dayClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Makes sure that triple keeps the selector visible
                if(mLastClickedDayParentView == dayParentView && mLastClickedDayTextView == dayTextView) {
                    return;
                }

                dayParentView.setPressed(true);
                dayTextView.setTypeface(dayTextView.getTypeface(), Typeface.BOLD);

                // Handle clicks on event days so they do not lose their previous styling after being pressed
                // Handle clicks on non event days so they retain their default styling after being pressed
                if(!(mEventDayTextView.contains(dayTextView))) {
                    dayTextView.setTextColor(mCalendarProperties.getSelectionColor());
                }

                if(mLastClickedDayParentView != null && mLastClickedDayTextView != null) {
                    mLastClickedDayParentView.setPressed(false);

                    // Handle clicks on non event days to return to their default styling
                    if(!mEventDayTextView.contains(mLastClickedDayTextView)) {
                        mLastClickedDayTextView.setTextColor(mCalendarProperties.getDaysLabelsColor());
                        mLastClickedDayTextView.setTypeface(Typeface.DEFAULT);
                    }
                }
                mLastClickedDayParentView = dayParentView;
                mLastClickedDayTextView = dayTextView;

            }
        };

        dayTextView.setOnClickListener(dayClickListener);
        dayIcon.setOnClickListener(dayClickListener);
        dayParentView.setOnClickListener(dayClickListener);
    }

}
