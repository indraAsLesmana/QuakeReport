package com.example.android.quakereport.helper;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();
    private static HttpURLConnection CONN;
    private static InputStream IN;

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            CONN = (HttpURLConnection) url.openConnection();
            CONN.setRequestMethod("GET");
            CONN.setReadTimeout(10000 /* milliseconds */);
            CONN.setConnectTimeout(15000 /* milliseconds */);
            CONN.connect();
            // read the response
            IN = new BufferedInputStream(CONN.getInputStream());
            response = readFromStream(IN);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static void cleanDisconect () {
        if (CONN != null){
            CONN.disconnect();
        }

        if (IN != null){
            try {
                IN.close();
            } catch (IOException e) {
                Log.e(TAG, "error close inputstream :" + e.getMessage());
            }
        }
    }

    /**
     * source from artilce internet, ini tam
     * */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * FROM UDACITY
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
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

}
