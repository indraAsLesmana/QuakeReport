package com.example.android.quakereport.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quakereport.R;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class Helpers {

    private static ProgressDialog sProgressDialog;

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

    /**
     * Show progress dialog, can only be called once per tier (show-hide)
     */
    public static void showProgressDialog(Context ctx, int bodyStringId) {
        if(sProgressDialog == null) {
            sProgressDialog = ProgressDialog.show(ctx,
                    ctx.getString(R.string.progress_title_default),
                    ctx.getString(bodyStringId), true, false, null);
        }
    }

    /**
     * This internal function to reduce redundancy showToast function
     */
    private static void initToast(Context context, Toast toast) {
        ViewGroup toastLayout = (ViewGroup)toast.getView();
        TextView toastTextView = (TextView)toastLayout.getChildAt(0);
        float textSize = context.getResources().getDimension(R.dimen.toast_text_size);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        toastTextView.setGravity(Gravity.CENTER);
    }

    /**
     * Show toast from string
     */
    public static void showToast(Context ctx, String str, boolean needLong) {
        Toast toast = Toast.makeText(ctx, str, needLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        initToast(ctx, toast);
        toast.show();
    }

    /**
     * Hide current progress dialog and set to NULL
     */
    public static void hideProgressDialog() {
        if(sProgressDialog != null && sProgressDialog.isShowing()) {
            sProgressDialog.dismiss();
            sProgressDialog = null;     // so it can be called in the next showProgressDialog
        }
    }

    public static void makeLogInfo(String tag, String message) {
            Log.i(tag, message);
    }
}