/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quakereport.R;
import com.example.android.quakereport.adapter.EarthquakeAdapter;
import com.example.android.quakereport.helper.Constant;
import com.example.android.quakereport.helper.Helpers;
import com.example.android.quakereport.loader.EarthquakeLoader;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<EarthquakeModel>>,
        EarthquakeAdapter.EarthquakeAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = EarthquakeActivity.class.getName();
    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;
    private RecyclerView mRecycleview;
    private TextView mEmpetyView;
    private ProgressBar mProgressBar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        /**
         * initial all view
         * */
        mProgressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        mEmpetyView = (TextView)findViewById(R.id.textEmpety);
        mRecycleview = (RecyclerView) findViewById(R.id.list);

        /*
         *  This value should be true if you want to reverse your layout. Generally, this is only
         *  true with horizontal lists that need to support a right-to-left layout.
         */
        boolean shouldReverseLayout = false;

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(EarthquakeActivity.this,
                LinearLayoutManager.VERTICAL,
                shouldReverseLayout);

        mRecycleview.setLayoutManager(layoutManager);
        mAdapter = new EarthquakeAdapter(EarthquakeActivity.this, this);

        if (Helpers.checkingNeworkStatus(this)){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null, this);

            //try inline code, work perfectly
            //getLoaderManager().initLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null,this);
        }else {
            mProgressBar.setVisibility(View.GONE);
            mEmpetyView.setText(getResources().getString(R.string.no_internet_connection));
        }

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(EarthquakeActivity.this);
        sharedPreferences.
                registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * prevent always load asynctask on rotate phone with Loader
     * */
    @Override
    public Loader<ArrayList<EarthquakeModel>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String minVieweddata = sharedPrefs.getString(
                getString(R.string.settings_min_dataview_key),
                getString(R.string.settings_min_data_view_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(Constant.MAIN_URL_DINAMIC);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("eventtype", "earthquake");
        uriBuilder.appendQueryParameter("orderby", orderBy);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("limit", minVieweddata);

        Log.i(TAG, uriBuilder.toString());
        return new EarthquakeLoader(this, uriBuilder.toString(), mProgressBar);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeModel>> loader,
                               final ArrayList<EarthquakeModel> earthquakeModels) {

        mProgressBar.setVisibility(View.INVISIBLE);

        if (earthquakeModels != null && !earthquakeModels.isEmpty()) {
            mAdapter.setEarthquake(earthquakeModels);
            mRecycleview.setAdapter(mAdapter);
            return;
        }

        mEmpetyView.setText(getResources().getString(R.string.data_not_found));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthquakeModel>> loader) {
        mAdapter.setEarthquake(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_sort:

                String result = sharedPreferences.getString(
                        getString(R.string.settings_order_by_key),
                        getString(R.string.settings_order_by_default));

                if (result.matches(getString(R.string.settings_order_by_magnitude_value))){
                    setItByTime(true);
                }else {
                    setItByTime(false);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(EarthquakeModel weatherForDay, int adapterPosisition) {
        Toast.makeText(EarthquakeActivity.this, weatherForDay.getmUrl() + "position: "+ adapterPosisition,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_min_dataview_key))||
                key.equals(getString(R.string.settings_min_magnitude_key))||
                key.equals(getString(R.string.settings_order_by_key))){
            getLoaderManager().restartLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setItByTime (boolean bool) {
        if (bool){
            sharedPreferences.edit().putString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_most_recent_value)
            ).apply();
        }else {
            sharedPreferences.edit().putString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default)
            ).apply();
        }
    }
}
