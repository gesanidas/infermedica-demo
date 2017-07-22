package com.gesanidas.housemd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gesanidas.housemd.data.ConditionAdapter;
import com.gesanidas.housemd.models.Condition;
import com.gesanidas.housemd.models.NewSymptom;
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
    PostDiagnosisTask postDiagnosisTask;
    HashMap<String,String> mySyms;
    TextView textView,textView2;
    String question,name;
    RadioGroup radioGroup;


    RecyclerView recyclerView;
    ConditionAdapter conditionAdapter;
    LinearLayoutManager linearLayoutManager;
    Condition[] conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        Gson gson = new Gson();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        /*
        Set<String> set = sharedPreferences.getStringSet("mySymptomsSet", null);
        mySyms=new HashMap<>();
        String hashMapString = sharedPreferences.getString("mySymsString",null);
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        mySyms = gson.fromJson(hashMapString, type);

        */
        Intent intent = getIntent();
        mySyms = (HashMap<String, String>) intent.getSerializableExtra("hashMap");



        recyclerView=(RecyclerView) findViewById(R.id.condition_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        conditionAdapter=new ConditionAdapter(conditions);










        fetchDiagnosisTask=new FetchDiagnosisTask();
        fetchDiagnosisTask.execute();


    }



    public void onRadioButtonClicked(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId())
        {
            case R.id.radio_yes:
                if (checked)
                    postDiagnosisTask=new PostDiagnosisTask();
                    postDiagnosisTask.execute("present");
                    radioGroup.clearCheck();
                    break;
            case R.id.radio_no:
                if (checked)
                    postDiagnosisTask=new PostDiagnosisTask();
                    postDiagnosisTask.execute("absent");
                    radioGroup.clearCheck();
                    break;
            case R.id.radio_unknown:
                if (checked)
                    postDiagnosisTask=new PostDiagnosisTask();
                    postDiagnosisTask.execute("unknown");
                    radioGroup.clearCheck();
                    break;
        }
    }



    public class FetchDiagnosisTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            String text=null;
            try
            {
                question = NetworkUtils.getDiagnosis(sharedPreferences.getString("sex","male"),sharedPreferences.getString("age","30"),mySyms);
                text=JsonUtils.parseQuestionText(DiagnosisActivity.this,question);
                name=JsonUtils.parseQuestionName(DiagnosisActivity.this,question);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return text;
        }




        @Override
        protected void onPostExecute (String response)
        {
            super.onPostExecute(response);
            textView.setText(response);
            textView2.setText(name);
        }

    }

    public class PostDiagnosisTask extends AsyncTask<String, String, String>
    {

        ArrayList<NewSymptom> newSymptoms=new ArrayList<>();
        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            String text=null;
            try
            {
                question = NetworkUtils.getDiagnosis(sharedPreferences.getString("sex","male"),sharedPreferences.getString("age","30"),mySyms);
                newSymptoms=JsonUtils.parseNewSymptom(DiagnosisActivity.this,question);
                mySyms.put(newSymptoms.get(0).getId(),params[0]);
                /*
                Gson gson = new Gson();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                String hashMapString = gson.toJson(mySyms);
                editor.putString("mySymsString",hashMapString);
                editor.commit();
                */

                response = NetworkUtils.getDiagnosis(sharedPreferences.getString("sex","male"),sharedPreferences.getString("age","30"),mySyms);
                text=JsonUtils.parseQuestionText(DiagnosisActivity.this,response);
                name=JsonUtils.parseQuestionName(DiagnosisActivity.this,response);
                conditions=JsonUtils.parseJsonForConditions(DiagnosisActivity.this,response);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return text;
        }




        @Override
        protected void onPostExecute (String response)
        {
            super.onPostExecute(response);
            textView.setText(response);
            textView2.setText(name);
            conditionAdapter.setConditions(conditions);
            recyclerView.setAdapter(conditionAdapter);

            Log.i("cond size",String.valueOf(conditions.length));

        }

    }

}
