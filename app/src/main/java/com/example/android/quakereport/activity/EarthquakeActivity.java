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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.quakereport.R;
import com.example.android.quakereport.adapter.EarthquakeAdapter;
import com.example.android.quakereport.helper.Constant;
import com.example.android.quakereport.helper.Helpers;
import com.example.android.quakereport.helper.Utils;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String TAG = EarthquakeActivity.class.getName();
    private ListView earthquakeListView;
    private EarthquakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        /**
         * just one line to excute data ha ha
         * */
        new EarthqueakeTask().execute(Constant.MAIN_URL_LESSON3);

    }

    /**
     * Asyctask for download on background data
     * */
    private class EarthqueakeTask extends AsyncTask<String, Void, ArrayList<EarthquakeModel>>{

        @Override
        protected ArrayList<EarthquakeModel> doInBackground(String... strings) {
            ArrayList<EarthquakeModel> dataEarthqueake = null;
            if (strings.length < 1 || strings[0] != null) {
                dataEarthqueake = Utils.fetchEarthquakeData(strings[0]);
            }

            return dataEarthqueake;
        }

        @Override
        protected void onPostExecute(final ArrayList<EarthquakeModel> dataEarthqueake) {
            super.onPostExecute(dataEarthqueake);

            adapter = new EarthquakeAdapter(EarthquakeActivity.this, dataEarthqueake);

            // Find a reference to the {@link ListView} in the layout
            earthquakeListView = (ListView) findViewById(R.id.list);
            earthquakeListView.setAdapter(adapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    EarthquakeModel dataInThisposition = dataEarthqueake.get(position);

                    if (!TextUtils.isEmpty(dataInThisposition.getmUrl())){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataInThisposition.getmUrl()));
                        startActivity(intent);
                    }else {
                        Toast.makeText(EarthquakeActivity.this, "URL link paged, not evaluabe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
