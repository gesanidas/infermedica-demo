package com.gesanidas.housemd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gesanidas.housemd.data.SymptomAdapter;
import com.gesanidas.housemd.models.Symptom;
import com.gesanidas.housemd.utils.JsonUtils;
import com.gesanidas.housemd.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SymptomAdapter.ListItemClickListener
{



    RecyclerView recyclerView;
    SymptomAdapter symptomAdapter;
    LinearLayoutManager linearLayoutManager;
    FetchEmployeesTask fetchEmployeesTask;
    private Symptom[] symptoms;
    SharedPreferences sharedPreferences;
    Set<String> mySymptomps;
    HashMap<String,String> mySyms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mySymptomps=new HashSet<>();
        mySyms=new HashMap<>();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        symptomAdapter=new SymptomAdapter(symptoms,this);
        fetchEmployeesTask=new FetchEmployeesTask();
        fetchEmployeesTask.execute();
        recyclerView.setAdapter(symptomAdapter);
    }


    public void get(View view)
    {
        Intent intent = new Intent(MainActivity.this,DiagnosisActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(Symptom symptom)
    {
        Gson gson = new Gson();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        String hashMapString = sharedPreferences.getString("mySymsString",gson.toJson(new HashMap<String,String>()));
        mySyms = gson.fromJson(hashMapString, type);
        if(mySyms!=null) mySyms.put(symptom.getId(),"present");
        hashMapString = gson.toJson(mySyms);
        editor.putString("mySymsString",hashMapString);
        editor.commit();

        Log.i("added",symptom.getId());
        Log.i("size is",String.valueOf(mySyms.size()));
        /*

        Log.i("s",symptom.getName());
        mySymptomps = sharedPreferences.getStringSet("mySymptomsSet", new HashSet<String>());
        mySymptomps.add(symptom.getId());
        Log.i("added",symptom.getId());
        Log.i("size is",String.valueOf(mySymptomps.size()));
        editor.putStringSet("mySymptomsSet", mySymptomps);
        editor.commit();
        */
    }


    public class FetchEmployeesTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            try
            {
                response = NetworkUtils.getAllItems(NetworkUtils.SYMPTOMS);
                symptoms= JsonUtils.parseJsonForSymptoms(MainActivity.this,response);
                Log.i("dfd",symptoms[0].getName());
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
                symptomAdapter.setSymptoms(symptoms);

            }

    }
}
