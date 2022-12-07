package com.vitefinetechapp.vitefinetech.DTH;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DTH_PlanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DTH_Adapter adapter;

    List<DTH_ModelClass> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String dth_api;
    ImageButton backbutton;
    SharedPreferences sharedpreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_t_h__plan);

        recyclerView   =     (RecyclerView)findViewById(R.id.recycler_view);
        backbutton     =     (ImageButton)findViewById(R.id.backbutton);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(DTH_PlanActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String value_operator = sharedPreferences.getString("value_operator", null);

        dth_api = "https://vitefintech.com/viteapi/recharge/dth_plan?operator="+value_operator;

        retriveData();
    }

    private void retriveData() {
        StringRequest eventoReq = new StringRequest(Request.Method.GET, dth_api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REsponce", response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONArray k = new JSONArray(response);
                            for (int i = 0; i < k.length(); i++) {
                                try {
                                    JSONObject obj = k.getJSONObject(i);

                                    String Amount      =    obj.getString("Amount");
                                    String Operator    =    obj.getString( "Operator" );
                                    String PlanName    =    obj.getString( "PlanName" );
                                    String Description =    obj.getString( "Description" );
                                    String Validity    =    obj.getString( "Validity" );

                                    DTH_ModelClass dth_modelClass = new DTH_ModelClass();

                                    dth_modelClass.setOperator(Operator);
                                    dth_modelClass.setDescription(Description);
                                    dth_modelClass.setPlanName(PlanName);
                                    dth_modelClass.setAmount(Amount);
                                    dth_modelClass.setValidity(Validity);


                                    list.add(dth_modelClass);
                                    adapter = new DTH_Adapter(DTH_PlanActivity.this, list);
                                    recyclerView.setAdapter(adapter);

                                    adapter.setOnItemClickListener2(new ItemClickListener2() {
                                        @Override
                                        public void OnItemClick(Integer position,String amount) {
                                            Log.d("amt",amount);
                                            Log.d("posi",position.toString());



                                            Intent intent = new Intent();
                                            intent.putExtra("amounts", amount);
                                            setResult(RESULT_OK,intent);
                                            finish();

                                            onBackPressed();

                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:" + error.toString());
            }
        });
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(eventoReq);
        }
    }

}