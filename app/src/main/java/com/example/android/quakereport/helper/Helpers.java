package com.example.android.quakereport.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Helpers {

    /**
     * THis helper convert UnixTimeStamp
     */
    public static String convertUnixDay(String longtime) {
        long unixSeconds = Long.parseLong(longtime);
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        Constant.DATE_FORMAT_MMM_DD_YY.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = Constant.DATE_FORMAT_MMM_DD_YY.format(date);
        return formattedDate;
    }

    public static String convertUnixTime(String longtime) {
        long unixSeconds = Long.parseLong(longtime);
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        Constant.DATE_FORMAT_MMM_DD_YY.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = Constant.DATE_FORMAT_TIME_PM.format(date);
        return formattedDate;
    }

    public static DateTime convertUnixTimeJoda(long longtime) {
        DateTime _startDate = new DateTime(longtime * 1000L);
//        String formattedDate = Constant.DATE_FORMAT_MMM_DD_YYYY.format(_startDate);
        return _startDate;
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    public static String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * cheking netWork connectivity
     */
    public static boolean checkingNeworkStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}