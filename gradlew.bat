package com.applandeo.materialcalendarview.builders;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.utils.CalendarProperties;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Mateusz Kornakiewicz on 12.10.2017.
 */
public class DatePickerBuilder {
    private Context mContext;
    private CalendarProperties mCalendarProperties;

    public DatePickerBuilder(Context context, OnSelectDateListener onSelectDateListener) {
        mContext = context;
        mCalendarProperties = new CalendarProperties(context);
        mCalendarProperties.setCalendarType(CalendarView.ONE_DAY_PICKER);
        mCalendarProperties.setOnSelectDateListener(onSelectDateListener);
    }

    public DatePicker build() {
        return new DatePicker(mContext, mCalendarProperties);
    }

    public DatePickerBuilder pickerType(int calendarType) {
        mCalendarProperties.setCalendarType(calendarType);
        return this;
    }

    public DatePickerBuilder date(Calendar calendar) {
        mCalendarProperties.setCalendar(calendar);
        return this;
    }

    public DatePickerBuilder headerColor(@ColorRes int color) {
        mCalendarProperties.setHeaderColor(color);
        return this;
    }

    public DatePickerBuilder headerLabelColor(@ColorRes int color) {
        mCalendarProperties.setHeaderLabelColor(color);
        return this;
    }

    public DatePickerBuilder previousButtonSrc(@DrawableRes int drawable) {
        mCalendarProperties.setPreviousButtonSrc(ContextCompat.getDrawable(mContext, drawable));
        return this;
    }

    public DatePickerBuilder forwardButtonSrc(@DrawableRes int drawable) {
        mCalendarProperties.setForwardButtonSrc(ContextCompat.getDrawable(mContext, drawable));
        return this;
    }

    public DatePickerBuilder selectionColor(@ColorRes int