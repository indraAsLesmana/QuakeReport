package com.example.android.quakereport.helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Constant {
    public static final String MAIN_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";

    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    public static final SimpleDateFormat DATE_FORMAT_MMM_DD_YY =
            new SimpleDateFormat("LLL dd, yyyy", DEFAULT_LOCALE);

    public static final SimpleDateFormat DATE_FORMAT_TIME_PM =
            new SimpleDateFormat("hh:mm, a", DEFAULT_LOCALE);

    public static final SimpleDateFormat FULL_DATE_FORMAT =
            new SimpleDateFormat("EEEE, dd MMMM yyyy", DEFAULT_LOCALE);

}
