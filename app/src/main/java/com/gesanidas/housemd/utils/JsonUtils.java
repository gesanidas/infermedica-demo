package com.gesanidas.housemd.utils;

import android.content.Context;
import android.util.Log;

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
    public static Symptom[] parseJsonForSymptoms(Context context,String  inputString) throws JSONException
    {
        final String ID="id";
        final String NAME="name";
        final String COMMON_NAME="common_name";
        final String SEX_FILTER="sex_filter";
        final String CATEGORY="category";
        final String SERIOUSNESS="seriousness";

        JSONArray data=new JSONArray(inputString);
        Symptom[] symptoms=new Symptom[data.length()];

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
            symptoms[i]=symptom;
        }

        return symptoms;
    }


    public static String parseQuestionText(Context context,String  inputString) throws JSONException
    {
        JSONObject json = new JSONObject(inputString);
        JSONObject question=json.getJSONObject("question");
        String text=question.getString("text");
        Log.i("text",text);
        return text;
    }

    public ArrayList<String> parseSymptom(Context context,String  inputString) throws JSONException
    {
        JSONObject json = new JSONObject(inputString);
        JSONObject question=json.getJSONObject("question");
        JSONArray items=question.getJSONArray("items");
        for (int i=0;i<items.length();i++)
        {
            
        }

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

    public static String getSymptompsFromSymptom(HashMap<String,String> symptoms)
    {
        String symp=new String();
        for (String s:symptoms.keySet())
        {
            symp+="\n    {\n\"id\": \""+s+"\",\n      \"choice_id\": \""+symptoms.get(s)+"\"\n    },";
        }
        Log.i("opa",symp.substring(0, symp.length() - 1));
        return symp.substring(0, symp.length() - 1);
    }




}
