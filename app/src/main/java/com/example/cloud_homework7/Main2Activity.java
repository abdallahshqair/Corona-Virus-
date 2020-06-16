package com.example.cloud_homework7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("النسب العالمية");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView viewtext1=findViewById(R.id.viewtext1);
        TextView viewtext2=findViewById(R.id.viewtext2);
        TextView viewtext3=findViewById(R.id.viewtext3);
        TextView viewtext4=findViewById(R.id.viewtext4);
        TextView viewtext5=findViewById(R.id.viewtext5);
        TextView viewtext6=findViewById(R.id.viewtext6);



        requestQueue= Volley.newRequestQueue(getApplicationContext());

         String url="https://api.covid19api.com/summary";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject global=response.getJSONObject("Global");
                    String newconfirmed=global.getString("NewConfirmed");
                    String totalConfirmed=global.getString("TotalConfirmed");
                    String newDeaths=global.getString("NewDeaths");
                    String totalDeaths=global.getString("TotalDeaths");
                    String newRecovered=global.getString("NewRecovered");
                    String totalRecovered=global.getString("TotalRecovered");

                    viewtext1.setText(newconfirmed);
                    viewtext2.setText(totalConfirmed);
                    viewtext3.setText(newRecovered);
                    viewtext4.setText(totalRecovered);
                    viewtext5.setText(newDeaths);
                    viewtext6.setText(totalDeaths);
//                    Log.d("t",newconfirmed+"\n"+totalConfirmed);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(request);

    }


}
