package com.example.android.quakereport.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.helper.Helpers;
import com.example.android.quakereport.model.EarthquakeModel;

import java.util.List;

/**
 * Created by indraaguslesmana on 12/21/16.
 */

public class EarthquakeAdapter extends
        RecyclerView.Adapter<EarthquakeAdapter.EarthquakeAdapterViewHolder>{

    private Context mContext;
    private List<EarthquakeModel> mQuakeData;


    /*public EarthquakeAdapter(Context context, ArrayList<EarthquakeModel> quekeData) {
        super(context, 0, quekeData);
        *//**
         * 0 is resource id, why 0 ? becouse we custom the view right here getView()
         * *//*
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

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        mImageBackground = (ImageView) listItemView.findViewById(R.id.imagebackground);
        GradientDrawable magnitudeCircle = (GradientDrawable) mImageBackground.getBackground();
        int magnitudeColor = getMagnitudeColor(mQuekeData.getmMag());

        String magnitude = Helpers.formatMagnitude(mQuekeData.getmMag());
        String placeResult = mQuekeData.getmPlace();
        String time = String.valueOf(mQuekeData.getmTime());
        *//**
         * @param placeResult check is contain of or not.
         * *//*
        if (placeResult.contains("of ")){
            String splitResult [] = placeResult.split("of ", 2);
            mLocation_spesific.setText(splitResult[0] + "of");
            mLocation.setText(splitResult[1]);
        }else {
            mLocation_spesific.setText("--");
            mLocation.setText(placeResult);
        }

        *//**
         * fix UnixTimeStamp Respone Value to long
         * *//*
        if (time.length() >= 10){
            time = time.substring(0, 10);
        }
        mDate.setText(Helpers.convertUnixDay(time));
        mDate_time.setText(Helpers.convertUnixTime(time));

        mMagnitude.setText(magnitude);

        */



    /**
         *make change color for diefferent value of magnitude Value
         * *//*
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;
    }*/

    public EarthquakeAdapter(Context context) {
        mContext = context;
    }

    private int getMagnitudeColor (double magnitudeValue) {
        int magnitudeFloor = (int) Math.floor(magnitudeValue);
        int magnitudeColorResourceId;
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(mContext, magnitudeColorResourceId);
    }


    @Override
    public EarthquakeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForlistItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmedietely = false;
        View view = inflater.inflate(layoutIdForlistItem, parent, shouldAttachToParentImmedietely);
        return new EarthquakeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EarthquakeAdapterViewHolder holder, int position) {
        EarthquakeModel earthquakeModel = mQuakeData.get(position);

        String magnitude = Helpers.formatMagnitude(earthquakeModel.getmMag());
        String placeResult = earthquakeModel.getmPlace();
        String time = String.valueOf(earthquakeModel.getmTime());

        if (placeResult.contains("of ")){
            String splitResult [] = placeResult.split("of ", 2);
            holder.mLocationSpesific.setText(splitResult[0] + "of");
            holder.mLocationSpesific.setText(splitResult[1]);
        }else {
            holder.mLocationSpesific.setText("--");
            holder.mLocationSpesific.setText(placeResult);
        }

        holder.mMagnitude.setText(magnitude);
        holder.mPlace.setText(earthquakeModel.getmPlace());

        if (time.length() >= 10){
            time = time.substring(0, 10);
        }

        holder.mDate.setText(Helpers.convertUnixDay(time));
        holder.mDate_time.setText(Helpers.convertUnixTime(time));
    }

    @Override
    public int getItemCount() {
        return mQuakeData.size();
    }

    public class EarthquakeAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView mMagnitude, mDate, mDate_time, mPlace, mLocationSpesific;

        public EarthquakeAdapterViewHolder(View itemView) {
            super(itemView);
            mMagnitude = (TextView) itemView.findViewById(R.id.magnitude);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mDate_time = (TextView) itemView.findViewById(R.id.date_time);
            mPlace = (TextView) itemView.findViewById(R.id.location);
            mLocationSpesific = (TextView) itemView.findViewById(R.id.location_spesific);
        }
    }

    public void setEarthquake(List<EarthquakeModel> quekeData){
        mQuakeData = quekeData;
        notifyDataSetChanged();
    }
}
