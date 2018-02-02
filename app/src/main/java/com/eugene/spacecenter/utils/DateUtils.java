package com.eugene.spacecenter.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eugene on 20.01.2018.
 */

public class DateUtils {

    public static String getFormattedCurrentDate() {

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(currentDate);
    }

    public static String getFormattedDate(int year, int month, int day) {
        return year+"-"+month+"-"+day;
    }

    public static String getFormattedDate(long millis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(millis);
    }
}
