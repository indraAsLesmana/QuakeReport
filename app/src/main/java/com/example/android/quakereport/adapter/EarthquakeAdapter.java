package com.example.android.quakereport.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.model.ContactModel;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class EarthquakeAdapter extends ArrayAdapter<ContactModel>{

    private ContactModel mQuekeData;
    private TextView mMagnitude, mLocation, mDate;

    public EarthquakeAdapter(Context context, ArrayList<ContactModel> quekeData) {
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
        mLocation = (TextView) listItemView.findViewById(R.id.location);
        mDate = (TextView) listItemView.findViewById(R.id.date);

        mMagnitude.setText(mQuekeData.getmId());
        mLocation.setText(mQuekeData.getmEmail());
        mDate.setText(mQuekeData.getmGender());

        return listItemView;
    }
}
