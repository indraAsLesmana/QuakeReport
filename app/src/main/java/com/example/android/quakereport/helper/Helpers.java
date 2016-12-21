package com.example.android.quakereport.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Helpers {

    /**
     * THis helper convert UnixTimeStamp
     * */
    public static String convertUnixTime(String longtime) {
        long unixSeconds = Long.parseLong(longtime);
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        String formattedDate = Constant.DATE_FORMAT_MMM_DD_YYYY.format(date);
        return formattedDate;
    }
}
