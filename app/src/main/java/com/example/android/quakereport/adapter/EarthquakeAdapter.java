package com.example.android.quakereport.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private Context mContext;
    private List<EarthquakeModel> mQuakeData;
    final private EarthquakeAdapterOnClickHandler mClickHandler;

    public EarthquakeAdapter(Context context, EarthquakeAdapterOnClickHandler onClick) {
        mContext = context;
        mClickHandler = onClick;
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
        /** @param earthquakeModel get perdata item position*/
        EarthquakeModel earthquakeModel = mQuakeData.get(position);

        /** converting for helpers*/
        String magnitude = Helpers.formatMagnitude(earthquakeModel.getmMag());
        String placeResult = earthquakeModel.getmPlace();
        String time = String.valueOf(earthquakeModel.getmTime());
        int magnitudeColor = getMagnitudeColor(earthquakeModel.getmMag());

        /** set value */
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
        holder.magnitudeCircle.setColor(magnitudeColor);
    }

    @Override
    public int getItemCount() {
        return mQuakeData.size();
    }

    public class EarthquakeAdapterViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        TextView mMagnitude, mDate, mDate_time, mPlace, mLocationSpesific;
        ImageView mImageBackground;
        GradientDrawable magnitudeCircle;

        public EarthquakeAdapterViewHolder(View itemView) {
            super(itemView);
            mImageBackground = (ImageView) itemView.findViewById(R.id.imagebackground);
            magnitudeCircle = (GradientDrawable) mImageBackground.getBackground();
            mMagnitude = (TextView) itemView.findViewById(R.id.magnitude);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mDate_time = (TextView) itemView.findViewById(R.id.date_time);
            mPlace = (TextView) itemView.findViewById(R.id.location);
            mLocationSpesific = (TextView) itemView.findViewById(R.id.location_spesific);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosisition = getAdapterPosition();
            EarthquakeModel itemOnlick = mQuakeData.get(adapterPosisition);
            mClickHandler.onClickItem(itemOnlick, adapterPosisition);
        }
    }

    public void setEarthquake(List<EarthquakeModel> quekeData){
        mQuakeData = quekeData;
        notifyDataSetChanged();
    }

    public interface EarthquakeAdapterOnClickHandler {
        void onClickItem(EarthquakeModel weatherForDay, int adapterPosisition);
    }

}
