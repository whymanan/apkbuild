package com.vitefinetechapp.vitefinetech.DMT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DMTScreenActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText mobileno;
    Button search;
    ProgressBar progressBar;
    ImageButton backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_m_t_screen);

        search         =    (Button)findViewById(R.id.search);
        mobileno       =    (TextInputEditText)findViewById(R.id.input_edt_mobileno);
        progressBar    =    (ProgressBar) findViewById(R.id.loading);
        backbutton     =    (ImageButton) findViewById(R.id.backbutton);

        progressBar.setVisibility(View.GONE);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobilenumber = mobileno.getText().toString();
                Log.e("DMTScreenActivity","mobilenumber=="+mobilenumber);

                if (mobilenumber.trim().isEmpty()) {
                    mobileno.setError("Mobile number not entered");
                    mobileno.requestFocus();
                }else{

                    fetchMobileNo(mobilenumber);

                }
            }
        });
    }

    private void fetchMobileNo(String mobileno) {

        progressBar.setVisibility(View.VISIBLE);

        final String MOBILE_FETCH_API = "https://nextmoney.co.in/nextmoney/dmt/customers?mobile="+mobileno;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, MOBILE_FETCH_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject obj1 = new JSONObject(response);
                    Log.e("dmtresponse",""+response);
                    // JSONArray array=jsonObject.getJSONArray(response);
                    JSONObject jsonObject=obj1.getJSONObject("customer");

                    Boolean Status = obj1.getBoolean("status");
                    Log.e("dmtScreen","Status ==="+Status);


                    if(Status==true){


                        String first_name = jsonObject.getString("first_name");
                        Log.e("dmtScreenf","first_name ==="+first_name);

                        String last_name = jsonObject.getString("last_name");
                        Log.e("dmtScreenl","last_name ==="+last_name);

                        String status2;
                        try{
                            status2=obj1.getJSONObject("beneficiaryDeatils").getString("Status");
                        }
                        catch (Exception e){
                            Log.d("dmtSs", Status.toString());
                            status2=Status.toString();
                        }


                        fetchBenificiaryList(mobileno,status2,first_name,last_name);

                    }else
                    {

                        Intent i = new Intent(DMTScreenActivity.this, AddNewDmtActivity.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error==","error"+error);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void fetchBenificiaryList(String mobile_no,String Status,String first_name,String last_name) {

        final String BENIFICIARY_LIST_API = "https://nextmoney.co.in/nextmoney/dmt/customers?mobile="+mobile_no;


        StringRequest stringRequest=new StringRequest(Request.Method.GET, BENIFICIARY_LIST_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject obj1=new JSONObject(response);
                    try {
                        JSONArray jsonArray= obj1.getJSONArray("beneficiaryDeatils");
                        Log.d("dmtTrue", jsonArray.toString());
                        Intent search_intent = new Intent(DMTScreenActivity.this, BenefiaciaryListActivity.class);
                        // search_intent.putExtra("Response", response);
                        search_intent.putExtra("jsonArray", jsonArray.toString());
                        search_intent.putExtra("customer_mobile", mobile_no);
                        search_intent.putExtra("first_name", first_name);
                        search_intent.putExtra("last_name", last_name);
                        search_intent.putExtra("Status", Status);

                        startActivity(search_intent);
                    }catch (Exception e){
                        JSONObject bObj=obj1.getJSONObject("beneficiaryDeatils");
                        Intent search_intent = new Intent(DMTScreenActivity.this, BenefiaciaryListActivity.class);
                        // search_intent.putExtra("Response", response);
//                        search_intent.putExtra("jsonArray", jsonArray.toString());
                        search_intent.putExtra("customer_mobile", mobile_no);
                        search_intent.putExtra("first_name", first_name);
                        search_intent.putExtra("last_name", last_name);
                        search_intent.putExtra("Status", Status);

                        startActivity(search_intent);
                    }


//                    for (int i=0; i<jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);


//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error==","error"+error);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}