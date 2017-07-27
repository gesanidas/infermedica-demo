package com.gesanidas.housemd.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import com.firebase.jobdispatcher.Driver;

import java.util.concurrent.TimeUnit;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.gesanidas.housemd.data.SymptomsContract;

/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomSyncUtils
{
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean sInitialized;

    //  COMPLETED (11) Add a sync tag to identify our sync job
    private static final String SYMPTOMS_SYNC_TAG = "symptoms-sync";


    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context)
    {
        Driver driver=new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher=new FirebaseJobDispatcher(driver);

        Job syncNewsJob=dispatcher.newJobBuilder().setService(SymptomsFirebaseJobService.class)
                .setTag(SYMPTOMS_SYNC_TAG).setConstraints(Constraint.ON_ANY_NETWORK).setLifetime(Lifetime.FOREVER).
                        setRecurring(true).setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS,SYNC_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS)).setReplaceCurrent(true)
                .build();
        dispatcher.schedule(syncNewsJob);


    }


    synchronized public static void initialize(@NonNull final Context context)
    {
        if (sInitialized)
        {
            return;
        }

        sInitialized=true;

        scheduleFirebaseJobDispatcherSync(context);

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                Uri newsQueryUri= SymptomsContract.SymptomsEntry.CONTENT_URI;
                String[] projectionColumns={SymptomsContract.SymptomsEntry._ID};
                String selectionStatement= SymptomsContract.SymptomsEntry.COLUMN_NAME;

                Cursor cursor=context.getContentResolver().query(newsQueryUri,projectionColumns,selectionStatement,null,null);

                if (null == cursor || cursor.getCount() == 0)
                {
                    startImmediateSync(context);
                }

                cursor.close();

                return null;
            }
        }.execute();
    }



    public static void startImmediateSync(@NonNull final Context context)
    {
        Intent intentToSyncimmediately=new Intent(context,SymptomsSyncIntentService.class);
        context.startService(intentToSyncimmediately);
    }
}
