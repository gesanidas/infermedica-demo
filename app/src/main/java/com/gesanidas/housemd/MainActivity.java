package com.gesanidas.housemd;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.app.SearchManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gesanidas.housemd.data.SymptomAdapter;
import com.gesanidas.housemd.models.Symptom;
import com.gesanidas.housemd.utils.JsonUtils;
import com.gesanidas.housemd.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
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
    HashMap<String,String> mySyms;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fab=(FloatingActionButton)findViewById(R.id.sendButton);
        mySyms=new HashMap<>();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        symptomAdapter=new SymptomAdapter(symptoms,this);
        fetchEmployeesTask=new FetchEmployeesTask();
        fetchEmployeesTask.execute();
        recyclerView.setAdapter(symptomAdapter);


        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!mySyms.isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this,DiagnosisActivity.class);
                    intent.putExtra("hashMap", mySyms);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"You need to pick at least one symptom",Toast.LENGTH_LONG).show();
                }

            }

        });
    }




    @Override
    protected void onStart()
    {
        super.onStart();
        mySyms.clear();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mySyms.clear();
    }



    @Override
    public void onClick(Symptom symptom)
    {
        /*
        Gson gson = new Gson();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        String hashMapString = sharedPreferences.getString("mySymsString",gson.toJson(new HashMap<String,String>()));
        mySyms = gson.fromJson(hashMapString, type);
        if(mySyms!=null) mySyms.put(symptom.getId(),"present");
        hashMapString = gson.toJson(mySyms);
        editor.putString("mySymsString",hashMapString);
        editor.commit();
        */

        mySyms.put(symptom.getId(),"present");

        Log.i("added",symptom.getId());
        Log.i("size is",String.valueOf(mySyms.size()));

        handleIntent(getIntent());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search for a symptom");

        return true;
    }

    //menu function:implements the user actions through intents or signing out
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onNewIntent(Intent intent)
    {

        handleIntent(intent);
    }


    private void handleIntent(Intent intent)
    {
        ArrayList<Symptom> searchedSymptoms=new ArrayList<>();

        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if(query!=null)
            {
                for (Symptom s:symptoms)
                {
                    if (s.getCommonName().contains(query))
                    {
                        searchedSymptoms.add(s);
                    }
                }
                if (searchedSymptoms.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"We couldn't match your symptom,please pick it from the list below",Toast.LENGTH_LONG).show();
                }
                else
                {
                    symptomAdapter.setSymptoms(searchedSymptoms.toArray(new Symptom[searchedSymptoms.size()]));
                }

            }

        }

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
