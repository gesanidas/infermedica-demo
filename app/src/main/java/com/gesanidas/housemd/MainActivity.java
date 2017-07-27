package com.gesanidas.housemd;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.app.SearchManager;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity implements SymptomAdapter.ListItemClickListener,NavigationView.OnNavigationItemSelectedListener
{



    RecyclerView recyclerView;
    SymptomAdapter symptomAdapter;
    LinearLayoutManager linearLayoutManager;
    FetchInitialDiagnosisTask fetchInitialDiagnosisTask;
    ArrayList<Symptom> symptoms;
    ArrayList<Symptom> chosenSymptoms;

    SharedPreferences sharedPreferences;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fab=(FloatingActionButton)findViewById(R.id.sendButton);
        symptoms=new ArrayList<>();
        chosenSymptoms=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        symptomAdapter=new SymptomAdapter(symptoms,this);
        fetchInitialDiagnosisTask=new FetchInitialDiagnosisTask();
        fetchInitialDiagnosisTask.execute();
        recyclerView.setAdapter(symptomAdapter);


        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!chosenSymptoms.isEmpty())
                {
                    Intent intent = new Intent(MainActivity.this,DiagnosisActivity.class);
                    intent.putExtra("chosen",chosenSymptoms);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"You need to pick at least one symptom",Toast.LENGTH_LONG).show();
                }

            }

        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    protected void onStart()
    {
        super.onStart();
        chosenSymptoms.clear();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        chosenSymptoms.clear();
    }



    @Override
    public void onClick(Symptom symptom)
    {
        symptom.setChoiceID("present");
        chosenSymptoms.add(symptom);



        handleIntent(getIntent());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
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


            FetchParsingTask fetchParsingTask=new FetchParsingTask();
            fetchParsingTask.execute(query);


            /*
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

            */

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_share)
        {

        } else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

















    public class FetchInitialDiagnosisTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            try
            {
                response = NetworkUtils.getAllItems(NetworkUtils.SYMPTOMS);
                symptoms= JsonUtils.parseJsonForSymptoms(MainActivity.this,response);
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

    public class FetchParsingTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            try
            {
                response = NetworkUtils.parseInput(params[0]);
                symptoms= JsonUtils.parseForSymptoms(MainActivity.this,response);
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
            chosenSymptoms.clear();
            for (Symptom s:symptoms)
            {
                chosenSymptoms.add(s);
            }

        }

    }
}
