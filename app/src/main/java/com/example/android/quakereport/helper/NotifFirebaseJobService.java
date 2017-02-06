package com.example.android.quakereport.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by indraaguslesmana on 2/6/17.
 */

public class NotifFirebaseJobService extends JobService{

    private AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Context context = NotifFirebaseJobService.this;
                ChatRemiderTask.executeTask(context, ChatRemiderTask.ACTION_TO_CHAT_THREAD);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                jobFinished(job, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
