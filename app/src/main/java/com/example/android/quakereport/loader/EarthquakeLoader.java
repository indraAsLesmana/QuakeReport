package com.example.android.quakereport.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.example.android.quakereport.helper.Utils;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 12/27/16.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeModel>> {
    private String mUrl;

    public EarthquakeLoader(Context context, String stringUrl) {
        super(context);
        mUrl = stringUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<EarthquakeModel> loadInBackground() {
        ArrayList<EarthquakeModel> dataEarthqueake = null;

        if (!TextUtils.isEmpty(mUrl)){
            dataEarthqueake = Utils.fetchEarthquakeData(mUrl);
            //dataEarthqueake = null; //testing progress bar, if data return null and change View with setEmpetiView
        }

        return dataEarthqueake;
    }
}
