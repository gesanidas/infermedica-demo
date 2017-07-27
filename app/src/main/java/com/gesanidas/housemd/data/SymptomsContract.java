package com.gesanidas.housemd.data;

/**
 * Created by gesanidas on 7/27/2017.
 */

import android.net.Uri;
import android.provider.BaseColumns;



public class SymptomsContract
{


    public static final String AUTHORITY="com.gesanidas.housemd";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);
    public static final String PATH_SYMPTOMS="symptoms";

    public static final class SymptomsEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SYMPTOMS).build();
        public static final String TABLE_NAME = "symptoms";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COMMON_NAME = "commonName";
        public static final String COLUMN_SEX_FILTER = "sexFilter";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_SERIOUSNESS = "seriousness";
        public static final String COLUMN_CHOICE_ID = "choiceId";



        public static Uri buildSymptomsUri(int id)
        {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        }
    }
}