package com.gesanidas.housemd;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gesanidas.housemd.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    FetchEmployeesTask fetchEmployeesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView2);
        fetchEmployeesTask=new FetchEmployeesTask();
    }


    public void get(View view)
    {
        fetchEmployeesTask.execute();
    }


    public class FetchEmployeesTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {

            String response=null;
            try
            {
                response = NetworkUtils.getDiagnosis();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }




            @Override
            protected void onPostExecute (String response)
            {
                super.onPostExecute(response);
                textView.setText(response);

            }

    }
}
