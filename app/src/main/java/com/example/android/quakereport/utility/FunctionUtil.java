package com.example.android.quakereport.utility;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by frensky on 06/02/2017.
 */

public class FunctionUtil {

    public static String readFileFromAssets(String filename, Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename), "UTF-8"));
            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine);
                mLine = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
