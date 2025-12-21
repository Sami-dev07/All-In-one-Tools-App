package com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.objects;



import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.utils.DateUtils;

import java.util.Calendar;


public class CalendarDate {

    private int mDay;
    private int mMonth;
    private int mYear;


    CalendarDate(int day, int month, int year) {
        mDay = day;
        mMonth = month;
        mYear = year;
    }

    public CalendarDate(Calendar calendar) {
        this(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR));
    }

    public CalendarDate(CalendarDate other) {
        this(other.getDay(),
                other.getMonth(),
                other.getYear());
    }

    public int getDay() {
        return mDay;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getYear() {
        return mYear;
    }

    public Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(mYear, mMonth, mDay);
        return calendar;
    }

    public long getMillis() {
        return getCalender().getTimeInMillis();
    }


    public int getDayOfWeek() {
        return getCalender().get(Calendar.DAY_OF_WEEK);
    }

    public boolean isToday() {
        Calendar today = Calendar.getInstance();

        return mYear == today.get(Calendar.YEAR) &&
                mMonth == today.get(Calendar.MONTH) &&
                mDay == today.get(Calendar.DAY_OF_MONTH);
    }

    public boolean isDateEqual(CalendarDate other) {
        return mYear == other.getYear() &&
                mMonth == other.getMonth() &&
                mDay == other.getDay();
    }

    public void addDays(int value) {
        Calendar calendar = getCalender();
        calendar.add(Calendar.DATE, value);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        return dayToString() + "/" + monthToString() + "/" + yearToString();
    }

    public String dayToString() {
        if (mDay < 10) {
            return "0" + mDay;
        } else {
            return "" + mDay;
        }
    }

    public String monthToString() {
        if ((mMonth + 1) < 10) {
            return "0" + (mMonth + 1);
        } else {
            return "" + (mMonth + 1);
        }
    }

    public String yearToString() {
        return String.valueOf(mYear);
    }


    public String monthToStringName() {
        return DateUtils.monthToString(mMonth);
    }

    public String dayOfWeekToStringName() {
        return DateUtils.dayOfWeekToString(getCalender().get(Calendar.DAY_OF_WEEK));
    }

}
