package com.gesanidas.housemd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gesanidas.housemd.utils.JsonUtils;
import com.gesanidas.housemd.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiagnosisActivity extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    FetchDiagnosisTask fetchDiagnosisTask;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> set = sharedPreferences.getStringSet("mySymptomsSet", null);
        List<String> sample=new ArrayList<>(set);
        Log.i("first sym",String.valueOf(sample.size()));
        fetchDiagnosisTask=new FetchDiagnosisTask();
        fetchDiagnosisTask.execute();


    }
















    public class FetchDiagnosisTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            try
            {
                response = NetworkUtils.getDiagnosis("male","30",new ArrayList<>(sharedPreferences.getStringSet("mySymptomsSet", null)));
                Log.i("dfd",response);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }




        @Override
        protected void onPostExecute (String response)
        {
            super.onPostExecute(response);

        }

    }
}
