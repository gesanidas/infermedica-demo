package com.gesanidas.housemd.utils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.gesanidas.housemd.data.SymptomsContract;
import com.gesanidas.housemd.models.Condition;
import com.gesanidas.housemd.models.Symptom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class JsonUtils
{
    public static ArrayList<Symptom> parseJsonForSymptoms(Context context,String  inputString) throws JSONException
    {
        final String ID="id";
        final String NAME="name";
        final String COMMON_NAME="common_name";
        final String SEX_FILTER="sex_filter";
        final String CATEGORY="category";
        final String SERIOUSNESS="seriousness";

        JSONArray data=new JSONArray(inputString);
        ArrayList<Symptom> symptoms=new ArrayList<>();

        for (int i=0;i<data.length();i++)
        {
            String id,name,commonName,sexFilter,category,seriousness;
            JSONObject article = data.getJSONObject(i);
            id=article.getString(ID);
            name=article.getString(NAME);
            commonName=article.getString(COMMON_NAME);
            sexFilter=article.getString(SEX_FILTER);
            category=article.getString(CATEGORY);
            seriousness=article.getString(SERIOUSNESS);

            Symptom symptom=new Symptom(id,name,commonName,sexFilter,category,seriousness);
            symptoms.add(symptom);
        }

        return symptoms;
    }


    public static ContentValues[] getContentValues(Context context, String inputJsonStr) throws JSONException
    {
        final String ID="id";
        final String NAME="name";
        final String COMMON_NAME="common_name";
        final String SEX_FILTER="sex_filter";
        final String CATEGORY="category";
        final String SERIOUSNESS="seriousness";

        JSONArray data=new JSONArray(inputJsonStr);

        ContentValues[] contentNewsValues=new ContentValues[data.length()];


        for (int i=0;i<data.length();i++)
        {
            String id,name,commonName,sexFilter,category,seriousness;
            JSONObject article = data.getJSONObject(i);
            id=article.getString(ID);
            name=article.getString(NAME);
            commonName=article.getString(COMMON_NAME);
            sexFilter=article.getString(SEX_FILTER);
            category=article.getString(CATEGORY);
            seriousness=article.getString(SERIOUSNESS);
            ContentValues contentValues = new ContentValues();

            Log.i("tg","writting");
            contentValues.put(SymptomsContract.SymptomsEntry.COLUMN_ID,id);
            contentValues.put(SymptomsContract.SymptomsEntry.COLUMN_NAME,name);
            contentValues.put(SymptomsContract.SymptomsEntry.COLUMN_COMMON_NAME,commonName);
            contentValues.put(SymptomsContract.SymptomsEntry.COLUMN_SEX_FILTER,sexFilter);
            contentValues.put(SymptomsContract.SymptomsEntry.COLUMN_CATEGORY,category);
            contentValues.put(SymptomsContract.SymptomsEntry.COLUMN_SERIOUSNESS,seriousness);

            contentNewsValues[i]=contentValues;

        }
        return contentNewsValues;

    }


    public static String parseQuestionText(Context context,String  inputString) throws JSONException
    {
        JSONObject json = new JSONObject(inputString);
        JSONObject question=json.getJSONObject("question");
        String text=question.getString("text");
        Log.i("text",text);
        return text;
    }

    public static String parseQuestionName(Context context,String  inputString) throws JSONException
    {
        JSONObject json = new JSONObject(inputString);
        JSONObject question=json.getJSONObject("question");
        JSONArray items=question.getJSONArray("items");
        JSONObject item=items.getJSONObject(0);
        String name=item.getString("name");
        return name;
    }




    public static HashMap<String,String> parseSymptom(Context context,String  inputString) throws JSONException
    {
        JSONObject json = new JSONObject(inputString);
        JSONObject question=json.getJSONObject("question");
        JSONArray items=question.getJSONArray("items");
        HashMap<String,String> newSymptoms=new HashMap<>();
        for (int i=0;i<items.length();i++)
        {
            JSONObject sym=items.getJSONObject(i);
            String id=sym.getString("id");
            String name=sym.getString("name");
            newSymptoms.put(id,name);

        }
        return newSymptoms;
    }


    public static ArrayList<Symptom> parseNewSymptom(Context context, String  inputString) throws JSONException
    {
        JSONObject json = new JSONObject(inputString);
        JSONObject question=json.getJSONObject("question");
        JSONArray items=question.getJSONArray("items");
        ArrayList<Symptom> newSymptoms=new ArrayList<>();
        for (int i=0;i<items.length();i++)
        {
            JSONObject sym=items.getJSONObject(i);
            String id=sym.getString("id");
            String name=sym.getString("name");
            newSymptoms.add(new Symptom(id,name));

        }
        return newSymptoms;
    }


    public static Condition[] parseJsonForConditions(Context context, String  inputString) throws JSONException
    {
        final String ID="id";
        final String NAME="name";
        final String COMMON_NAME="common_name";
        final String PROBABILITY="probability";


        JSONObject json = new JSONObject(inputString);
        JSONArray data=json.getJSONArray("conditions");
        Condition[] conditions=new Condition[data.length()];

        for (int i=0;i<data.length();i++)
        {
            String id,name,commonName,probability;
            JSONObject article = data.getJSONObject(i);
            id=article.getString(ID);
            name=article.getString(NAME);
            commonName=article.getString(COMMON_NAME);
            probability=article.getString(PROBABILITY);


            Condition condition=new Condition(id,name,commonName,probability);
            conditions[i]=condition;
        }

        return conditions;
    }




    public static ArrayList<Symptom> parseForSymptoms(Context context,String  inputString) throws JSONException
    {
        final String ID="id";
        final String NAME="name";
        final String COMMON_NAME="common_name";
        final String CHOICE_ID="choice_id";

        JSONObject json = new JSONObject(inputString);
        JSONArray data=json.getJSONArray("mentions");
        ArrayList<Symptom> symptoms=new ArrayList<>();

        for (int i=0;i<data.length();i++)
        {
            String id,name,commonName,choiceId;
            JSONObject article = data.getJSONObject(i);
            id=article.getString(ID);
            name=article.getString(NAME);
            commonName=article.getString(COMMON_NAME);
            choiceId=article.getString(CHOICE_ID);


            Symptom symptom=new Symptom(id,name,commonName,choiceId);
            symptoms.add(symptom);
        }

        return symptoms;
    }


































    /////////////////////////////////////////////////////
    //////create strings for pos diagnosis

    public static String createJsonForDiagnosis(String sex, String age, String symptomps)
    {

        String json="{\n  \"sex\": \""+sex+ "\" ," +
                "\n  \"age\":\""+age+ "\" ,"  +
                "\n  \"evidence\": [" +symptomps+ "]\n}";
        Log.i("json",json);
        return json;
    }

    public static String getSymptompsFromSymptom(ArrayList<Symptom> symptoms)
    {
        String symp=new String();
        for (Symptom s:symptoms)
        {
            symp+="\n    {\n\"id\": \""+s.getId()+"\",\n      \"choice_id\": \""+s.getChoiceID()+"\"\n    },";
        }
        Log.i("opa",symp.substring(0, symp.length() - 1));
        return symp.substring(0, symp.length() - 1);
    }




}
