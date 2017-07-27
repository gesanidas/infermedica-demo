package com.gesanidas.housemd.sync;


import android.app.IntentService;
import android.content.Intent;
/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomsSyncIntentService  extends IntentService
{
    public SymptomsSyncIntentService()
    {
        super("SymptomsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        SymptomsSyncTask.syncSymptoms(this);
    }
}
