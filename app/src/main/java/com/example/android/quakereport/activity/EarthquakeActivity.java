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
import android.support.annotation.NonNull;
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
import com.example.android.quakereport.model.UserProfileModel;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;
import java.util.Arrays;

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

    private static boolean onSelect;
    private ArrayList<String> onSelectedMode;

    //Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    private String mUsername;
    private int RC_SIGN_IN = 3214; // uniqe id for startActivityForResult
    private ChildEventListener childEventListener;

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

        //Firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();

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

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(EarthquakeActivity.this);
        sharedPreferences.
                registerOnSharedPreferenceChangeListener(this);

        onSelect = false;

        /**
         * -----------------------
         * Handle User Login State
         * -----------------------
         * */
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    // user login
                    onSignInInitialize(user);

                    //load data after sign in success
                    if (Helpers.checkingNeworkStatus(EarthquakeActivity.this)){
                        LoaderManager loaderManager = getLoaderManager();

                        loaderManager.initLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null,
                                EarthquakeActivity.this);

                        //try inline code, work perfectly
                        //getLoaderManager().initLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null,this);
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        mEmpetyView.setText(getResources().getString(
                                R.string.no_internet_connection));
                    }

                }else {
                    // user not loggin

                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(
                                            AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(
                                            AuthUI.GOOGLE_PROVIDER).build()))
                            .setIsSmartLockEnabled(false)
                            .build(), RC_SIGN_IN);
                }
            }
        };
    }

    private void onSignInInitialize(FirebaseUser user) {
        mUsername = user.getEmail();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "You're sign in", Toast.LENGTH_SHORT).show();
                    return;
                case RESULT_CANCELED:
                    Toast.makeText(this, "You're cancel sign in", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                default:
                    Toast.makeText(this, "Undifiend result", Toast.LENGTH_SHORT).show();
            }
        }
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
        mRecycleview.setVisibility(View.INVISIBLE);
        return new EarthquakeLoader(this, uriBuilder.toString(), mProgressBar);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeModel>> loader,
                               final ArrayList<EarthquakeModel> earthquakeModels) {

        mProgressBar.setVisibility(View.INVISIBLE);
        mRecycleview.setVisibility(View.VISIBLE);

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
            case R.id.action_settings: //go to setting
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_sort: // sort data
                String result = sharedPreferences.getString(
                        getString(R.string.settings_order_by_key),
                        getString(R.string.settings_order_by_default));

                if (result.matches(getString(R.string.settings_order_by_magnitude_value))){
                    setItByTime(true);
                }else {
                    setItByTime(false);
                }
                return true;
            case R.id.action_select: // action select

                if (onSelect){item.setIcon(R.drawable.ic_check_box_white_24dp);
                    onSelect = false;
                }else {item.setIcon(R.drawable.ic_indeterminate_check_box_white_24dp);
                    onSelect = true;
                }
                return true;
            case R.id.action_chat: // go to chat
                intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(EarthquakeModel weatherForDay,
                            EarthquakeAdapter.EarthquakeAdapterViewHolder itemView,
                            int adapterPosisition) {
        if (!onSelect){ //onSelectMode off
            if (weatherForDay.getmUrl().isEmpty()){
                return;
            }
            openWebPage(weatherForDay.getmUrl());
        }else {

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_min_dataview_key))||
                key.equals(getString(R.string.settings_min_magnitude_key))||
                key.equals(getString(R.string.settings_order_by_key))){
            getLoaderManager().restartLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null, this);
            Helpers.makeLogInfo(TAG, "Shared Preferences Changes!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
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

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
