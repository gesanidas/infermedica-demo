package com.gesanidas.housemd.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;
/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomsFirebaseJobService extends JobService
{
    private AsyncTask<Void,Void,Void> symptomsSyncTask;


    @Override
    public boolean onStartJob(final JobParameters job)
    {
        symptomsSyncTask=new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                Context context=getApplicationContext();
                SymptomsSyncTask.syncSymptoms(context);

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid)
            {

                jobFinished(job,false);
            }
        };
        symptomsSyncTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job)
    {
        if (symptomsSyncTask!=null)
        {
            symptomsSyncTask.cancel(true);
        }
        return true;
    }

}
