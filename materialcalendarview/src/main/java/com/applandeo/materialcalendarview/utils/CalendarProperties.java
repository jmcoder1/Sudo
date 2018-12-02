package com.applandeo.materialcalendarview.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.exceptions.ErrorsMessages;
import com.applandeo.materialcalendarview.exceptions.UnsupportedMethodsException;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.applandeo.materialcalendarview.listeners.OnSelectionAbilityListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class contains all properties of the calendar
 * <p>
 * Created by Mateusz Kornakiewicz on 30.10.2017.
 */

public class CalendarProperties {

    /**
     * A number of months (pages) in the calendar
     * 2401 months means 1200 months (100 years) before and 1200 months after the current month
     */
    public static final int CALENDAR_SIZE = 2401;
    public static final int FIRST_VISIBLE_PAGE = CALENDAR_SIZE / 2;

    private int mCalendarType, mSelectionColor, mTodayColor, mEventDayColor, mItemLayoutResource,
            mDisabledDaysLabelsColor, mPagesColor, mWeekDayBarColor, mWeekDayLabelColor,
            mToolbarColor, mDaysLabelsColor, mAnotherMonthsDaysLabelsColor;

    private boolean mEventsEnabled;

    private Calendar mFirstPageCalendarDate = DateUtils.getCalendar();
    private Calendar mCalendar, mMinimumDate, mMaximumDate;

    private OnDayClickListener mOnDayClickListener;
    private OnSelectDateListener mOnSelectDateListener;
    private OnSelectionAbilityListener mOnSelectionAbilityListener;
    private OnCalendarPageChangeListener mOnPreviousPageChangeListener;
    private OnCalendarPageChangeListener mOnForwardPageChangeListener;

    private List<EventDay> mEventDays = new ArrayList<>();
    private List<Calendar> mDisabledDays = new ArrayList<>();
    private List<SelectedDay> mSelectedDays = new ArrayList<>();
    private List<Calendar> mEventCalendarDays = new ArrayList<>();

    private Context mContext;

    public CalendarProperties(Context context) {
        mContext = context;
    }

    public int getCalendarType() {
        return mCalendarType;
    }

    public void setCalendarType(int calendarType) {
        mCalendarType = calendarType;
    }

    public boolean getEventsEnabled() {
        return mEventsEnabled;
    }

    public void setEventsEnabled(boolean eventsEnabled) {
        mEventsEnabled = eventsEnabled;
    }

    public void setEventCalendarDays(List<Calendar> eventCalendarDays) {
        mEventCalendarDays = eventCalendarDays;
    }

    public List<Calendar> getEventCalendarDays() {
        return mEventCalendarDays;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public OnSelectDateListener getOnSelectDateListener() {
        return mOnSelectDateListener;
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        mOnSelectDateListener = onSelectDateListener;
    }

    public int getSelectionColor() {
        if (mSelectionColor == 0) {
            return ContextCompat.getColor(mContext, R.color.defaultColor);
        }

        return mSelectionColor;
    }

    public void setSelectionColor(int selectionColor) {
        mSelectionColor = selectionColor;
    }

    public int getTodayColor() {
        if (mTodayColor == 0) {
            return ContextCompat.getColor(mContext, R.color.defaultColor);
        }

        return mTodayColor;
    }

    public void setTodayColor(int todayColor) {
        mTodayColor = todayColor;
    }

    public void setEventDayColor(int eventDayColor) {
        mEventDayColor = eventDayColor;
    }

    public int getEventDayColor() {
        if (mEventDayColor == 0) {
            return ContextCompat.getColor(mContext, R.color.defaultColor);
        }
        return mEventDayColor;
    }

    public Calendar getMinimumDate() {
        return mMinimumDate;
    }

    public void setMinimumDate(Calendar minimumDate) {
        mMinimumDate = minimumDate;
    }

    public Calendar getMaximumDate() {
        return mMaximumDate;
    }

    public void setMaximumDate(Calendar maximumDate) {
        mMaximumDate = maximumDate;
    }

    public OnSelectionAbilityListener getOnSelectionAbilityListener() {
        return mOnSelectionAbilityListener;
    }

    public void setOnSelectionAbilityListener(OnSelectionAbilityListener onSelectionAbilityListener) {
        mOnSelectionAbilityListener = onSelectionAbilityListener;
    }

    public int getItemLayoutResource() {
        return mItemLayoutResource;
    }

    public void setItemLayoutResource(int itemLayoutResource) {
        mItemLayoutResource = itemLayoutResource;
    }

    public OnCalendarPageChangeListener getOnPreviousPageChangeListener() {
        return mOnPreviousPageChangeListener;
    }

    public void setOnPreviousPageChangeListener(OnCalendarPageChangeListener onPreviousButtonClickListener) {
        mOnPreviousPageChangeListener = onPreviousButtonClickListener;
    }

    public OnCalendarPageChangeListener getOnForwardPageChangeListener() {
        return mOnForwardPageChangeListener;
    }

    public void setOnForwardPageChangeListener(OnCalendarPageChangeListener onForwardButtonClickListener) {
        mOnForwardPageChangeListener = onForwardButtonClickListener;
    }

    public Calendar getFirstPageCalendarDate() {
        return mFirstPageCalendarDate;
    }

    public OnDayClickListener getOnDayClickListener() {
        return mOnDayClickListener;
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }

    public List<EventDay> getEventDays() {
        return mEventDays;
    }

    public void setEventDays(List<EventDay> eventDays) {
        mEventDays = eventDays;
    }

    public List<Calendar> getDisabledDays() {
        return mDisabledDays;
    }

    public void setDisabledDays(List<Calendar> disabledDays) {
        mSelectedDays.removeAll(disabledDays);

        mDisabledDays = Stream.of(disabledDays)
                .map(calendar -> {
                    DateUtils.setMidnight(calendar);
                    return calendar;
                }).toList();
    }

    public List<SelectedDay> getSelectedDays() {
        return mSelectedDays;
    }

    public void setSelectedDay(Calendar calendar) {
        setSelectedDay(new SelectedDay(calendar));
    }

    public void setSelectedDay(SelectedDay selectedDay) {
        mSelectedDays.clear();
        mSelectedDays.add(selectedDay);
    }

    public void setSelectedDays(List<Calendar> selectedDays) {
        if (mCalendarType == CalendarView.ONE_DAY_PICKER) {
            throw new UnsupportedMethodsException(ErrorsMessages.ONE_DAY_PICKER_MULTIPLE_SELECTION);
        }

        if(mCalendarType == CalendarView.RANGE_PICKER && !DateUtils.isFullDatesRange(selectedDays)){
            throw new UnsupportedMethodsException(ErrorsMessages.RANGE_PICKER_NOT_RANGE);
        }

        mSelectedDays = Stream.of(selectedDays)
                .map(calendar -> {
                    DateUtils.setMidnight(calendar);
                    return new SelectedDay(calendar);
                }).filterNot(value -> mDisabledDays.contains(value.getCalendar()))
                .toList();
    }

    public int getDisabledDaysLabelsColor() {
        if (mDisabledDaysLabelsColor == 0) {
            return ContextCompat.getColor(mContext, R.color.nextMonthDayColor);
        }

        return mDisabledDaysLabelsColor;
    }

    public void setDisabledDaysLabelsColor(int disabledDaysLabelsColor) {
        mDisabledDaysLabelsColor = disabledDaysLabelsColor;
    }

    public int getPagesColor() {
        return mPagesColor;
    }

    public void setPagesColor(int pagesColor) {
        mPagesColor = pagesColor;
    }

    public int getWeekDayBarColor() {
        return mWeekDayBarColor;
    }

    public void setWeekDayBarColor(int weekDayBarBarColor) {
        mWeekDayBarColor = weekDayBarBarColor;
    }

    public int getWeekDayLabelColor() {
        return mWeekDayLabelColor;
    }

    public void setWeekDayLabelColor(int weekDayLabelColor) {
        mWeekDayLabelColor = weekDayLabelColor;
    }

    public int getDaysLabelsColor() {
        if (mDaysLabelsColor == 0) {
            return ContextCompat.getColor(mContext, R.color.currentMonthDayColor);
        }

        return mDaysLabelsColor;
    }

    public void setDaysLabelsColor(int daysLabelsColor) {
        mDaysLabelsColor = daysLabelsColor;
    }

    public void setToolbarColor(int toolbarColor) {
        mToolbarColor = toolbarColor;
    }

    public int getToolbarColor() {
        return mToolbarColor;
    }

    public int getAnotherMonthsDaysLabelsColor() {
        if (mAnotherMonthsDaysLabelsColor == 0) {
            return ContextCompat.getColor(mContext, R.color.nextMonthDayColor);
        }

        return mAnotherMonthsDaysLabelsColor;
    }

    public void setAnotherMonthsDaysLabelsColor(int anotherMonthsDaysLabelsColor) {
        mAnotherMonthsDaysLabelsColor = anotherMonthsDaysLabelsColor;
    }
}
