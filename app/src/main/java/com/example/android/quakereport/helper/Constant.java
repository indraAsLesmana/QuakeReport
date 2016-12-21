package com.example.android.quakereport.helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Constant {
    public static final String MAIN_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";

    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    public static final SimpleDateFormat DATE_FORMAT_MMM_DD_YYYY =
            new SimpleDateFormat("MMM DD, yyyy", DEFAULT_LOCALE);

}
