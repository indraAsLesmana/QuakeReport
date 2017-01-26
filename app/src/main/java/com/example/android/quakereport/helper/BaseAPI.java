package com.example.android.quakereport.helper;

import com.example.android.quakereport.model.EarthquakeModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by indraaguslesmana on 1/26/17.
 * I'll try to use Retrofit 2 in here
 */

public interface BaseAPI {
    /*@GET("/v1/jobs/my_jobs")
    void getJobList(@Query("q_name") String qName,
                    @Query("skill_ids") String fSkillIds,
                    @Query("location_ids") String fLocationIds,
                    @Query("start_date") String fStartDate,
                    @Query("end_date") String fEndDate,
                    @Query("min_age") String fMinAge,   *//* need to use string here *//*
                    @Query("max_age") String fMaxAge,   *//* so I can pass null*//*
                    @Query("gender") String fGender,    *//* this one too, to indicate "all" *//*
                    @Query("page") String pPage,        *//* this too for pagination support *//*
                    @Query("limit") String pLimit,      *//* to indicate default state *//*
                    Callback<JobResponses.MultipleJobs> callback);

    query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    this.mMag = mMag;
        this.mPlace = mPlace;
        this.mTime = mTime;
        this.mUrl = mUrl;


        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("eventtype", "earthquake");
        uriBuilder.appendQueryParameter("orderby", orderBy);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("limit", minVieweddata);
    */
    @GET("/fdsnws/event/1/query")
        void getEarthquake(
            @Query("format") String format,
            @Query("eventtype") String event,
            @Query("orderby") String orderBy,
            @Query("minmag") int minMag,
            @Query("limit") int limitView,
            Callback<EarthquakeModel> callback);
}
