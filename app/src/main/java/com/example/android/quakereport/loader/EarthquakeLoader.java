package com.example.android.quakereport.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.android.quakereport.helper.Helpers;
import com.example.android.quakereport.helper.Utils;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 12/27/16.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeModel>> {
    private static final String TAG = EarthquakeLoader.class.getSimpleName();

    private String mUrl;
    private static String lastUrl = null;
    private static ArrayList<EarthquakeModel> dataEartchquake;
    private ProgressBar mProgresbar;

    public EarthquakeLoader(Context context, String stringUrl, ProgressBar progressBar) {
        super(context);
        mUrl = stringUrl;
        mProgresbar = progressBar;
    }

    @Override
    protected void onStartLoading() {
        if (lastUrl != null) {
            if (dataEartchquake != null &&
                    mUrl.trim().equals(lastUrl.trim())) {
                deliverResult(dataEartchquake);
                Helpers.makeLogInfo(TAG,"Load with Cache");
                return;
            }
        }
        mProgresbar.setVisibility(View.VISIBLE);
        lastUrl = mUrl.trim();
        forceLoad();
    }

    @Override
    public ArrayList<EarthquakeModel> loadInBackground() {
        if (!TextUtils.isEmpty(mUrl)) {
            Helpers.makeLogInfo(TAG,"Network Load");
            dataEartchquake = Utils.fetchEarthquakeData(mUrl);
        }
        return dataEartchquake;
    }

    @Override
    public void deliverResult(ArrayList<EarthquakeModel> data) {
        dataEartchquake = data;
        super.deliverResult(data);
    }
}
