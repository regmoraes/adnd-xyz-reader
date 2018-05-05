package com.example.xyzreader.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.ContentValues.TAG;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    // Use default locale format
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    public static final GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

    public static String parsePublishedDate(String publishedDate) {
        try {

            Date date = dateFormat.parse(publishedDate);

            if (!date.before(DateUtils.START_OF_EPOCH.getTime())) {

                return android.text.format.DateUtils.getRelativeTimeSpanString(
                        date.getTime(),
                        System.currentTimeMillis(), android.text.format.DateUtils.HOUR_IN_MILLIS,
                        android.text.format.DateUtils.FORMAT_ABBREV_ALL).toString();
            } else {
                return outputFormat.format(publishedDate);
            }

        } catch (ParseException ex) {
            Log.e(TAG, ex.getMessage());
            Log.i(TAG, "passing today's date");
            return new Date().toString();
        }
    }
}
