package com.gesanidas.housemd;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.widget.SearchView;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gesanidas.housemd.data.SymptomsContract;
import com.gesanidas.housemd.data.SymptomsCursorAdapter;
import com.gesanidas.housemd.models.Symptom;
import com.gesanidas.housemd.sync.SymptomSyncUtils;
import com.gesanidas.housemd.utils.JsonUtils;
import com.gesanidas.housemd.utils.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,LoaderManager.LoaderCallbacks<Cursor>,SymptomsCursorAdapter.ListItemClickListener
{

    private SymptomsCursorAdapter symptomsCursorAdapter;
    private static final int ID_MOVIE_LOADER = 44;
    final String[] SYMPTOMS_LIST={SymptomsContract.SymptomsEntry.COLUMN_ID, SymptomsContract.SymptomsEntry.COLUMN_NAME};


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
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
        fab.hide();
        chosenSymptoms=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



        symptomsCursorAdapter=new SymptomsCursorAdapter(MainActivity.this,this);


        SymptomSyncUtils.initialize(this);

        recyclerView.setAdapter(symptomsCursorAdapter);
        getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null, this);


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
        fab.hide();
        getSupportLoaderManager().restartLoader(ID_MOVIE_LOADER, null, this);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        fab.hide();
        chosenSymptoms.clear();
    }




    @Override
    public void onClick(int id)
    {


        fab.show();
        Uri uriForSymptomClicked = SymptomsContract.SymptomsEntry.buildSymptomsUri(id);
        Log.i("uri",uriForSymptomClicked.toString());




        Cursor data= getContentResolver().query(uriForSymptomClicked, SYMPTOMS_LIST, null, null, null);
        data.moveToFirst();
        Log.i("cursor size",data.getString(0));

        String ID=data.getString(0);
        String NAME=data.getString(1);
        Symptom symptom=new Symptom(ID,NAME);
        symptom.setChoiceID("present");
        chosenSymptoms.add(symptom);
        data.close();

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

            /*
            String[] projectionColumns={SymptomsContract.SymptomsEntry._ID,SymptomsContract.SymptomsEntry.COLUMN_NAME};

            Cursor cursor=getContentResolver().query(SymptomsContract.SymptomsEntry.CONTENT_URI,projectionColumns,null,null,null);
            cursor.moveToFirst();
            do
            {
                if (cursor.getString(1).contains(query))
                {
                    Log.i("cursor name",cursor.getString(1));
                    String ID=cursor.getString(0);
                    String NAME=cursor.getString(1);
                    Symptom symptom=new Symptom(ID,NAME);
                    searchedSymptoms.add(symptom);


                }
            }while (cursor.moveToNext());

            if (searchedSymptoms.isEmpty())
            {
                Toast.makeText(MainActivity.this,"We couldn't match your symptom,please pick it from the list below",Toast.LENGTH_LONG).show();
            }
            else
            {
                //symptomAdapter.setSymptoms(searchedSymptoms.toArray(new Symptom[searchedSymptoms.size()]));

                String searchedNames[] =new String[searchedSymptoms.size()];
                for (int i=0;i<searchedSymptoms.size();i++)
                {
                    searchedNames[i]=searchedSymptoms.get(i).getName();
                }
                Bundle myBundle=new Bundle();
                myBundle.putStringArray("projection", SYMPTOMS_LIST);
                myBundle.putString("selection", SYMPTOMS_LIST[1]);
                myBundle.putStringArray("selectionArgs", searchedNames);
                getSupportLoaderManager().restartLoader(ID_MOVIE_LOADER,myBundle,this);


            }
            */


            FetchParsingTask fetchParsingTask = new FetchParsingTask();
            fetchParsingTask.execute(query);



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
    public boolean onNavigationItemSelected(MenuItem item)
    {
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





    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args)
    {
        return new AsyncTaskLoader<Cursor>(this)
        {
            Cursor symptoms=null;


            @Override
            public void onStartLoading()
            {
                if (symptoms != null)
                {
                    Log.i("tag","delievered");
                    deliverResult(symptoms);
                }
                else
                {
                    Log.i("tag","not delievered");
                    forceLoad();
                }
            }
            @Override
            public Cursor loadInBackground()
            {


                try
                {
                    if (args==null)
                    {
                        symptoms=getContentResolver().query(SymptomsContract.SymptomsEntry.CONTENT_URI,null,null,null,null);
                    }
                    else
                    {
                        symptoms=getContentResolver().query(SymptomsContract.SymptomsEntry.CONTENT_URI,args.getStringArray("projection"),
                                args.getString("selection"),
                                args.getStringArray("selectionArgs"),null,null);

                    }

                    return symptoms;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }



            public void deliverResult(Cursor data)
            {
                symptoms = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if (data != null)
        {
            symptomsCursorAdapter.swapCursor(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        symptomsCursorAdapter.swapCursor(null);
    }









    public class FetchParsingTask extends AsyncTask<String, String, String>
    {

        ArrayList<Symptom> symptoms=new ArrayList<>();
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
            if (!chosenSymptoms.isEmpty())
            {
                fab.show();
            }

        }

    }
}
