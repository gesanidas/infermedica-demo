package com.gesanidas.housemd.utils;

import com.gesanidas.housemd.models.Symptom;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class JsonUtils
{



























    /////////////////////////////////////////////////////
    //////create strings for pos diagnosis

    public static String createJsonForDiagnosis(String sex, String age, String symptomps)
    {

        String json="{\n  \"sex\": \""+sex+"\",\n  \""+age+ "\": 30," +
                "\n  \"evidence\": [" +symptomps+ "\n}";
        return json;
    }

    public static String getSymptompsFromSymptom(Symptom[] symptoms)
    {
        String symp=null;
        for (Symptom s:symptoms)
        {
            symp+="\n    {\n      \"id\": \""+s.getId()+"\",\n      \"choice_id\": \"present\"\n    },";
        }

        return symp.substring(0, symp.length() - 1);
    }




}
