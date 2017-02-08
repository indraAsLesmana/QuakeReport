package com.example.android.quakereport.helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Constant {
    public static final String MAIN_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";

    public static final String MAIN_URL_LESSON3 =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String MAIN_URL_DINAMIC =
            "http://earthquake.usgs.gov/fdsnws/event/1/query";


    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    public static final SimpleDateFormat DATE_FORMAT_MMM_DD_YY =
            new SimpleDateFormat("LLL dd, yyyy", DEFAULT_LOCALE);

    public static final SimpleDateFormat DATE_FORMAT_TIME_PM =
            new SimpleDateFormat("hh:mm, a", DEFAULT_LOCALE);

    public static final SimpleDateFormat FULL_DATE_FORMAT =
            new SimpleDateFormat("EEEE, dd MMMM yyyy", DEFAULT_LOCALE);

    /**
     * uniqe ID per activity
     * */
    public final static int EARTHQUEAKE_ACTIVITY_ID = 101;

    /**
     * Key child on firebase server
     * */
    public static final String KEY_MESSAGES = "messages";
    public static final String KEY_CHAT_PHOTOS = "chat_photos";

    /**
     * WaChat Key
     * */
    public static final String KEY_CHATS = "chats";
    public static final String KEY_ROOMS = "rooms";
    public static final String KEY_USERS = "users";


    /**
     * mime type
     * */
    public static final String IMAGE_TYPE = "image/jpeg";
    public static final String PREFERENCE_USER_MAIL = "user-mail";
}
