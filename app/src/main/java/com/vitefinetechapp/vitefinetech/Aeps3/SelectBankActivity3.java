package com.vitefinetechapp.vitefinetech.Aeps3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectBankActivity3 extends AppCompatActivity {

    public static final String BANK_Api = "https://vitefintech.com/public/api/aepsbanks";
    String ACCESS_TOKEN;
    Button confirm;
    Spinner bank;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    ArrayList<String> list;
    ImageButton backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank3);

        bank          =     (Spinner)findViewById(R.id.bank_spinner);
        confirm       =     (Button)findViewById(R.id.confirm);
        backbutton    =     (ImageButton) findViewById(R.id.backbutton);

        list = new ArrayList<String>();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ACCESS_TOKEN = sharedPreferences.getString("tokenname", null);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectBankActivity3.this, AddNewAdharPayment3.class);
                startActivity(i);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getBanklist();

    }

    private void getBanklist(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BANK_Api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("ResponseBank:" + response.toString());
                try{
                    JSONArray jsonArray= response.getJSONArray("codeValues");
                    for(int i= 0; i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        String bank_name    =   data.getString("value");
                        Log.d("bank_name",bank_name);

                        list.add(bank_name);
                    }
                    list.add(0, "Select Your Transaction Bank");

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectBankActivity3.this, android.R.layout.simple_spinner_dropdown_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bank.setAdapter(dataAdapter);

                }catch ( JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:" +error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Authorization","bearer " +ACCESS_TOKEN);

                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}