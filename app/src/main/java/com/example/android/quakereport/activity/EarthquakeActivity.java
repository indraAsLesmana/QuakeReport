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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quakereport.R;
import com.example.android.quakereport.adapter.EarthquakeAdapter;
import com.example.android.quakereport.helper.Constant;
import com.example.android.quakereport.loader.EarthquakeLoader;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<EarthquakeModel>> {

    public static final String TAG = EarthquakeActivity.class.getName();
    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;
    private ListView earthquakeListView;
    private TextView mEmpetyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        earthquakeListView = (ListView) findViewById(R.id.list);
        mEmpetyView = (TextView)findViewById(R.id.textEmpety);
        earthquakeListView.setEmptyView(mEmpetyView);
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null, this);

        //try inline code, work perfectly
//        getLoaderManager().initLoader(Constant.EARTHQUEAKE_ACTIVITY_ID, null,this);

    }

    /**
     * prevent always load asynctask on rotate phone with Loader
     * */
    @Override
    public Loader<ArrayList<EarthquakeModel>> onCreateLoader(int i, Bundle bundle) {

        return new EarthquakeLoader(this, Constant.MAIN_URL_LESSON3);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeModel>> loader,
                               final ArrayList<EarthquakeModel> earthquakeModels) {

        if (earthquakeModels != null && !earthquakeModels.isEmpty()) {
            mAdapter = new EarthquakeAdapter(EarthquakeActivity.this, earthquakeModels);

            // Find a reference to the {@link ListView} in the layout
            earthquakeListView.setAdapter(mAdapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    EarthquakeModel dataInThisposition = earthquakeModels.get(position);

                    if (!TextUtils.isEmpty(dataInThisposition.getmUrl())) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataInThisposition.getmUrl()));
                        startActivity(intent);
                    } else {
                        Toast.makeText(EarthquakeActivity.this, "URL link paged, not evaluabe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthquakeModel>> loader) {
        mAdapter.clear();
    }

}
