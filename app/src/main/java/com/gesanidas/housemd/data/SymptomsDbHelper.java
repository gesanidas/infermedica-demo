package com.gesanidas.housemd.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gesanidas.housemd.models.Symptom;

/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomsDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="favorites.db";
    private static final int DATABASE_VERSION=1;



    public SymptomsDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_FAVORITES_TABLE="CREATE TABLE "+ SymptomsContract.SymptomsEntry.TABLE_NAME+"("+
                SymptomsContract.SymptomsEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
                SymptomsContract.SymptomsEntry.COLUMN_ID+" TEXT NOT NULL,"+
                SymptomsContract.SymptomsEntry.COLUMN_NAME+" TEXT NOT NULL ,"+
                SymptomsContract.SymptomsEntry.COLUMN_COMMON_NAME+" TEXT NOT NULL ,"+
                SymptomsContract.SymptomsEntry.COLUMN_SEX_FILTER+" TEXT NOT NULL ,"+
                SymptomsContract.SymptomsEntry.COLUMN_CATEGORY+" TEXT NOT NULL ,"+
                SymptomsContract.SymptomsEntry.COLUMN_SERIOUSNESS+" TEXT NOT NULL "+");";
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+  SymptomsContract.SymptomsEntry.TABLE_NAME);
        onCreate(db);
    }

}

