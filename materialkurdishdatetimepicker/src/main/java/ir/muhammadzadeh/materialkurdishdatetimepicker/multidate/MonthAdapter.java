/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.muhammadzadeh.materialkurdishdatetimepicker.multidate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import ir.muhammadzadeh.materialkurdishdatetimepicker.multidate.MonthView.OnDayClickListener;
import ir.muhammadzadeh.materialkurdishdatetimepicker.utils.KurdishCalendar;

/**
 * An adapter for a list of {@link MonthView} items.
 */
public abstract class MonthAdapter extends BaseAdapter implements OnDayClickListener {

    private static final String TAG = "SimpleMonthAdapter";

    private final Context mContext;
    protected final DatePickerController mController;

    private final ArrayList<KurdishCalendar> mSelectedDays;

    protected static int WEEK_7_OVERHANG_HEIGHT = 7;
    protected static final int MONTHS_IN_YEAR = 12;

    /**
     * A convenience class to represent a specific date.
     */
    public static class CalendarDay {
        private KurdishCalendar mKurdishCalendar;
        int year;
        int month;
        int day;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(KurdishCalendar calendar) {
            year = calendar.getKurdishYear();
            month = calendar.getKurdishMonth();
            day = calendar.getKurdishDay();
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public void set(CalendarDay date) {
            year = date.year;
            month = date.month;
            day = date.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        private void setTime(long timeInMillis) {
            if (mKurdishCalendar == null) {
                mKurdishCalendar = new KurdishCalendar();
            }
            mKurdishCalendar.setTimeInMillis(timeInMillis);
            month = mKurdishCalendar.getKurdishMonth();
            year = mKurdishCalendar.getKurdishYear();
            day = mKurdishCalendar.getKurdishDay();
        }

        public boolean same(CalendarDay date) {
            return date.day == day && date.year == year && date.month == month;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }

        public KurdishCalendar getKurdishCalendar() {
            if (mKurdishCalendar != null)
                return mKurdishCalendar;
            else {
                mKurdishCalendar = new KurdishCalendar();
                mKurdishCalendar.setKurdishDate(year, month, day);
                return mKurdishCalendar;
            }
        }
    }

    public MonthAdapter(Context context,
                        DatePickerController controller) {
        mContext = context;
        mController = controller;
        mSelectedDays = mController.getSelectedDays();
        /*ArrayList<KurdishCalendar> persianCalendars = mController.getSelectedDays();
        for (KurdishCalendar row : persianCalendars)
            mSelectedDays.add(new CalendarDay(row));*/
    }

    @Override
    public int getCount() {
        return ((mController.getMaxYear() - mController.getMinYear()) + 1) * MONTHS_IN_YEAR;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MonthView v;
        HashMap<String, Object> drawingParams = null;
        if (convertView != null) {
            v = (MonthView) convertView;
            // We store the drawing parameters in the view so it can be recycled
            drawingParams = (HashMap<String, Object>) v.getTag();
        } else {
            v = createMonthView(mContext);
            // Set up the new view
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            v.setLayoutParams(params);
            v.setClickable(true);
            v.setOnDayClickListener(this);
        }
        if (drawingParams == null) {
            drawingParams = new HashMap<>();
        }
        drawingParams.clear();

        final int month = position % MONTHS_IN_YEAR;
        final int year = position / MONTHS_IN_YEAR + mController.getMinYear();

        ArrayList<Integer> days = new ArrayList<>();
        for (KurdishCalendar persianCalendar : mSelectedDays)
            if (isSelectedDayInMonth(new CalendarDay(persianCalendar), year, month))
                days.add(persianCalendar.getKurdishDay());


        // Invokes requestLayout() to ensure that the recycled view is set with the appropriate
        // height/number of weeks before being displayed.
        v.reuse();

        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_DAYS, days);
        drawingParams.put(MonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(MonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(MonthView.VIEW_PARAMS_WEEK_START, mController.getFirstDayOfWeek());
        v.setMonthParams(drawingParams);
        v.invalidate();
        return v;
    }

    public abstract MonthView createMonthView(Context context);

    private boolean isSelectedDayInMonth(CalendarDay selectedDay, int year, int month) {
        return selectedDay.year == year && selectedDay.month == month;
    }


    public void onDayClick(MonthView view, CalendarDay day) {
        if (day != null) {
            onDayTapped(day);
        }
    }

    /**
     * Maintains the same hour/min/sec but moves the day to the tapped day.
     *
     * @param day The day that was tapped
     */
    protected void onDayTapped(CalendarDay day) {
        mController.tryVibrate();
        notifySelectedDays(day);
        notifyDataSetChanged();
        mController.onDaysOfMonthSelected(mSelectedDays);
    }

    private void notifySelectedDays(CalendarDay day) {
        KurdishCalendar toRemove = null;
        for (KurdishCalendar calendarDay : mSelectedDays)
            if (day.same(new CalendarDay(calendarDay))) {
                toRemove = calendarDay;
                break;
            }

        if (mSelectedDays.size() > 1 && toRemove != null)
            mSelectedDays.remove(toRemove);
        else {
            mSelectedDays.add(day.getKurdishCalendar());
            Collections.sort(mSelectedDays, new Comparator<KurdishCalendar>() {
                @Override
                public int compare(KurdishCalendar o1, KurdishCalendar o2) {
                    return o1.getTimeInMillis() > o2.getTimeInMillis() ? 1 : 0;
                }
            });
        }
    }
}
