package com.vitefinetechapp.vitefinetech.DMT;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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


public class dmtMainFragment extends Fragment {

    EditText mobileno;
    Button search;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_dmt_main, container, false);

        search         =    (Button)view.findViewById(R.id.search);
        mobileno       =    view.findViewById(R.id.input_edt_mobileno);
        progressBar    =    (ProgressBar) view.findViewById(R.id.loading);

        progressBar.setVisibility(View.GONE);


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

         return view;
    }

    private void fetchMobileNo(String mobilenumber) {
        progressBar.setVisibility(View.VISIBLE);

        final String MOBILE_FETCH_API = "https://nextmoney.co.in/nextmoney/dmt/customers?mobile="+mobilenumber;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, MOBILE_FETCH_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject obj1 = new JSONObject(response);
                    Log.e("dmtresponse",mobilenumber+"--"+response);
                    // JSONArray array=jsonObject.getJSONArray(response);
                    JSONObject jsonObject=obj1.getJSONObject("customer");

                    Boolean Status = obj1.getBoolean("status");
                    Log.e("dmtScreen","Status ==="+Status);


                    if(Status==true){


                        String first_name = jsonObject.getString("first_name");
                        Log.e("dmtScreenf","first_name ==="+first_name);

                        String last_name = jsonObject.getString("last_name");
                        Log.e("dmtScreenl","last_name ==="+last_name);

                        String userId=jsonObject.getString("user_detail_id");

                        String status2;
                        try{
                            status2=obj1.getJSONObject("beneficiaryDeatils").getString("Status");
                        }
                        catch (Exception e){
                            Log.d("dmtSs", Status.toString());
                            status2=Status.toString();
                        }


                        fetchBenificiaryList(userId,mobilenumber,status2,first_name,last_name);

                    }else
                    {

                        Intent i = new Intent(getContext(), AddNewDmtActivity.class);
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
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void fetchBenificiaryList(String userId,String mobile_no, String Status, String first_name, String last_name) {


        final String BENIFICIARY_LIST_API = "https://nextmoney.co.in/nextmoney/dmt/customers?mobile="+mobile_no;


        StringRequest stringRequest=new StringRequest(Request.Method.GET, BENIFICIARY_LIST_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                Log.d("DMTBENres", ":"+response);
                try {
                    JSONObject obj1=new JSONObject(response);
                    try {
                        JSONArray jsonArray= obj1.getJSONArray("beneficiaryDeatils");
                        Log.d("dmtTrue", jsonArray.toString());
                        Intent search_intent = new Intent(getContext(), BenefiaciaryListActivity.class);
                        // search_intent.putExtra("Response", response);
                        search_intent.putExtra("jsonArray", jsonArray.toString());
                        search_intent.putExtra("customer_mobile", mobile_no);
                        search_intent.putExtra("first_name", first_name);
                        search_intent.putExtra("last_name", last_name);
                        search_intent.putExtra("Status", Status);
                        search_intent.putExtra("userId", userId);
                        startActivity(search_intent);
                    }catch (Exception e){
                        JSONObject bObj=obj1.getJSONObject("beneficiaryDeatils");
                        Intent search_intent = new Intent(getContext(), BenefiaciaryListActivity.class);
                        // search_intent.putExtra("Response", response);
//                        search_intent.putExtra("jsonArray", jsonArray.toString());
                        search_intent.putExtra("customer_mobile", mobile_no);
                        search_intent.putExtra("first_name", first_name);
                        search_intent.putExtra("last_name", last_name);
                        search_intent.putExtra("Status", Status);
                        search_intent.putExtra("userId", userId);

                        startActivity(search_intent);
                    }


//                    for (int i=0; i<jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);


//                    }
                } catch (JSONException e) {
                    Log.d("DMTBENerr",":"+e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error==","error"+error);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}