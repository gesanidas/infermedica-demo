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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DiagnosisActivity extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    FetchDiagnosisTask fetchDiagnosisTask;
    HashMap<String,String> mySyms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        Gson gson = new Gson();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> set = sharedPreferences.getStringSet("mySymptomsSet", null);
        mySyms=new HashMap<>();
        String hashMapString = sharedPreferences.getString("mySymsString",null);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        mySyms = gson.fromJson(hashMapString, type);
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
                response = NetworkUtils.getDiagnosis("male","30",mySyms);
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
