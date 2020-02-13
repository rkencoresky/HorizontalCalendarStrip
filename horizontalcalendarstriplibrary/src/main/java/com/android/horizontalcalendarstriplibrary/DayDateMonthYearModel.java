package com.android.horizontalcalendarstriplibrary;


import androidx.annotation.Nullable;

public class DayDateMonthYearModel {
    public String date;
    public String month;
    public String year;
    public String day;
    public String monthNumeric;
    public Boolean isToday;

    @Override
    public boolean equals(@Nullable Object obj) {
        DayDateMonthYearModel target = (DayDateMonthYearModel) obj;
        if (target == null)
            return false;
        return target.date.equalsIgnoreCase(this.date)
                && target.year.equalsIgnoreCase(this.year)
                && target.month.equalsIgnoreCase(this.month);
    }
}
