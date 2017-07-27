package com.gesanidas.housemd.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by gesanidas on 7/27/2017.
 */

public class SymptomsContentProvider extends ContentProvider
{
    private SymptomsDbHelper symptomsDbHelper;
    public static final int SYMPTOMS=100;
    public static final int SYMPTOMS_WITH_ID=101;
    private static final UriMatcher sUriMatcher=buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(SymptomsContract.AUTHORITY,SymptomsContract.PATH_SYMPTOMS,SYMPTOMS);
        uriMatcher.addURI(SymptomsContract.AUTHORITY, SymptomsContract.PATH_SYMPTOMS + "/#", SYMPTOMS_WITH_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        symptomsDbHelper = new SymptomsDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        final SQLiteDatabase db = symptomsDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match)
        {
            case SYMPTOMS:
                retCursor =  db.query(SymptomsContract.SymptomsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case SYMPTOMS_WITH_ID:
                retCursor =  db.query(SymptomsContract.SymptomsEntry.TABLE_NAME,
                        projection,
                        SymptomsContract.SymptomsEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        final SQLiteDatabase db = symptomsDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match)
        {
            case SYMPTOMS:

                long id = db.insert(SymptomsContract.SymptomsEntry.TABLE_NAME, null, values);
                if ( id > 0 )
                {
                    returnUri = ContentUris.withAppendedId(SymptomsContract.SymptomsEntry.CONTENT_URI, id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }




    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values)
    {
        final SQLiteDatabase db = symptomsDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri))
        {

            case SYMPTOMS:
                db.beginTransaction();
                int rowsInserted = 0;
                try
                {
                    for (ContentValues value : values)
                    {
                        long _id = db.insert(SymptomsContract.SymptomsEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                        {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }
                finally
                {
                    db.endTransaction();
                }

                if (rowsInserted > 0)
                {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }








    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        final SQLiteDatabase db = symptomsDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int moviesDeleted;

        switch (match)
        {
            case SYMPTOMS_WITH_ID:

                String id = uri.getPathSegments().get(1);
                moviesDeleted = db.delete(SymptomsContract.SymptomsEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (moviesDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return moviesDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
