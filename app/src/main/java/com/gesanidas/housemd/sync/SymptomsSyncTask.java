package com.gesanidas.housemd.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.gesanidas.housemd.data.SymptomsContract;
import com.gesanidas.housemd.utils.JsonUtils;
import com.gesanidas.housemd.utils.NetworkUtils;

/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomsSyncTask
{
    synchronized public static void syncSymptoms(Context context)
    {



        try
        {
            String response = NetworkUtils.getAllItems(NetworkUtils.SYMPTOMS);
            ContentValues[] cv= JsonUtils.getContentValues(context,response);
            if (cv!=null && cv.length!=0)
            {
                ContentResolver contentResolver=context.getContentResolver();
                //contentResolver.delete(SymptomsContract.SymptomsEntry.CONTENT_URI,null,null);
                contentResolver.bulkInsert(SymptomsContract.SymptomsEntry.CONTENT_URI, cv);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
