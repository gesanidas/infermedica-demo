package com.gesanidas.housemd;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity
{
    EditText editText,editText1;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editText=(EditText)findViewById(R.id.editText);
        editText1=(EditText)findViewById(R.id.editText2);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        editText.setText(sharedPreferences.getString("sex","male"));
        editText1.setText(sharedPreferences.getString("age","30"));
    }



    public void save(View view)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String sex=editText.getText().toString();
        String age=editText1.getText().toString();

        if (sex.toLowerCase().equals("male")||sex.toLowerCase().equals("female") && Integer.valueOf(age)>0 &&Integer.valueOf(age)<150&&Integer.valueOf(age)!=null)
        {
            editor.putString("sex",sex);
            editor.putString("age",age);
            editor.commit();
        }
        else
        {
            Toast.makeText(SettingsActivity.this,"Please enter valid values",Toast.LENGTH_LONG).show();
        }

    }
}
