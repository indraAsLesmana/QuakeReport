package com.example.android.quakereport.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.quakereport.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 12/26/16.
 */

public class DataSMK extends AppCompatActivity{
    private ListView listview;
    private final static String MAIN_URL = "http://android-db.6te.net/lokasi_kampus.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        listview = (ListView) findViewById(R.id.list);

        new TaskSync().execute(MAIN_URL);
    }

    private class TaskSync extends AsyncTask <String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url = createNew(urls[0]);
            String jsonResponse = null;
            try {
                jsonResponse = requestHttp(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String jsonHasilDownload) {
            super.onPostExecute(jsonHasilDownload);
            ArrayList<String> listData = new ArrayList<>();
            try {
                JSONArray dataTemp1 = new JSONArray(jsonHasilDownload);
                for (int i = 0; i < dataTemp1.length(); i++) {
                    JSONObject data = dataTemp1.getJSONObject(i);

                    int id = data.getInt("id");
                    String namaKampus = data.getString("alamat_kampus");
                    //sesuaikan

                    //add ke perObject ke Arraylist
                    listData.add(namaKampus);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(DataSMK.this, android.R.layout.simple_list_item_1, listData);
            listview.setAdapter(adapter);
        }

        private String requestHttp(URL url) {
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            String jsonResult = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                inputStream = conn.getInputStream();
                jsonResult = readFromStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonResult;

        }

        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private URL createNew(String urls) {
            URL url = null;
            try {
                url = new URL(urls);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

    }
}
