package com.example.android.quakereport.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.helper.Helpers;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeModel>{

    private EarthquakeModel mQuekeData;
    private TextView mMagnitude, mLocation, mDate;
    private ImageView mImageBackground;
    private TextView mLocation_spesific;
    private TextView mDate_time;

    public EarthquakeAdapter(Context context, ArrayList<EarthquakeModel> quekeData) {
        super(context, 0, quekeData);
        /**
         * 0 is resource id, why 0 ? becouse we custom the view right here getView()
         * */
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        mQuekeData = getItem(position);

        mMagnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        mLocation_spesific = (TextView) listItemView.findViewById(R.id.location_spesific);
        mLocation = (TextView) listItemView.findViewById(R.id.location);
        mDate = (TextView) listItemView.findViewById(R.id.date);
        mDate_time = (TextView) listItemView.findViewById(R.id.date_time);
        mImageBackground = (ImageView) listItemView.findViewById(R.id.imagebackground);

        double magnitude = mQuekeData.getmMag();
        String placeResult = mQuekeData.getmPlace();
        String time = String.valueOf(mQuekeData.getmTime());
        /**
         * @param placeResult check is contain of or not.
         * */
        if (placeResult.contains("of ")){
            String splitResult [] = placeResult.split("of ", 2);
            mLocation_spesific.setText(splitResult[0] + "of");
            mLocation.setText(splitResult[1]);
        }else {
            mLocation_spesific.setText("--");
            mLocation.setText(placeResult);
        }

        /**
         * fix UnixTimeStamp Respone Value to long
         * */
        if (time.length() >= 10){
            time = time.substring(0, 10);
        }
        mDate.setText(Helpers.convertUnixDay(time));
        mDate_time.setText(Helpers.convertUnixTime(time));

        mMagnitude.setText(Helpers.formatMagnitude(magnitude));

        /**
         *make change color for diefferent value of magnitude Value
         * */
        /*float mMagnitudeData = Float.parseFloat(magnitude);

        if (mMagnitudeData <= 6.0){mImageBackground.setImageResource(R.drawable.color_green);
        } else if (mMagnitudeData <= 6.4){mImageBackground.setImageResource(R.drawable.color_dusty_yellow);
        } else if (mMagnitudeData <= 7.0){mImageBackground.setImageResource(R.drawable.color_mustard_yellow);
        } else if (mMagnitudeData <= 9.0 ){mImageBackground.setImageResource(R.drawable.color_red);
        }
*/
        return listItemView;
    }
}
