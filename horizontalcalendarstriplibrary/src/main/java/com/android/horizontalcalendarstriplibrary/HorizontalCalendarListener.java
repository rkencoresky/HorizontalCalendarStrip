package com.android.horizontalcalendarstriplibrary;


public interface HorizontalCalendarListener {
    void updateMonthOnScroll(DayDateMonthYearModel selectedDate);

    void newDateSelected(DayDateMonthYearModel selectedDate);
}
