package com.example.android.quakereport.helper;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Helpers {

    /**
     * THis helper convert UnixTimeStamp
     * */
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

    public static DateTime convertUnixTimeJoda (long longtime){
        DateTime _startDate = new DateTime(longtime * 1000L);
//        String formattedDate = Constant.DATE_FORMAT_MMM_DD_YYYY.format(_startDate);
        return _startDate;
    }

}
