package com.gesanidas.housemd;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        editor.putString("sex",sex);
        editor.putString("age",age);
        editor.commit();

    }
}
