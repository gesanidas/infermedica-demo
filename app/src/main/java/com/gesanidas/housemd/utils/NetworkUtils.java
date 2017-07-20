package com.gesanidas.housemd.utils;

import com.gesanidas.housemd.models.Symptom;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class NetworkUtils
{

    private static final String BASE_URL="https://api.infermedica.com/v2/";
    private static final String APP_ID="da4c0ca3";
    private static final String APP_KEY="af0c88514164f73746d5e4bc7d6a9edc";



    public static final String CONDITIONS="conditions";
    public static final String LAB_TESTS="lab_tests";
    public static final String RISK_FACTORS ="risk_factors";
    public static final String SYMPTOMPS="symptoms";









    //////////////////////////////////////////////////
    //////////////get requests

    public static String getAllItems(String item)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL+item)
                .get()
                .addHeader("app-id", APP_ID)
                .addHeader("app-key", APP_KEY)
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "6f16ca44-c312-681e-a7fc-1b5d7d491f71")
                .build();

        try
        {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public static String getItemById(String item,String id)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL+item+"/"+id)
                .get()
                .addHeader("app-id", APP_ID)
                .addHeader("app-key", APP_KEY)
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "6f16ca44-c312-681e-a7fc-1b5d7d491f71")
                .build();

        try
        {
            Response response = client.newCall(request).execute();
            return response.body().string();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }















    //////////////////////////////////////////////////////
    /////////////postrequests


    public static String getDiagnosis(String sex, String age, Symptom[] symptoms)
    {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String req=JsonUtils.createJsonForDiagnosis(sex,age,JsonUtils.getSymptompsFromSymptom(symptoms));
        RequestBody body = RequestBody.create(mediaType,req);
        Request request = new Request.Builder()
                .url("https://api.infermedica.com/v2/diagnosis")
                .post(body)
                .addHeader("app-id", APP_ID)
                .addHeader("app-key", APP_KEY)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "4b795926-fb02-a2d6-f792-1d591674d246")
                .build();
        try
        {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }


}
