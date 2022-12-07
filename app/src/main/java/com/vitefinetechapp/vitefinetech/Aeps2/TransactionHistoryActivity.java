package com.vitefinetechapp.vitefinetech.Aeps2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private ArrayList<History> HistoryMODELArraylist;
    TransactionHistoryAdapter transactionHistoryAdapter;
    ImageButton backbutton;
    private ProgressDialog progressDialog;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;

    String merchantid,rollid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        recycler_view = findViewById(R.id.recycler_view_history);
        backbutton    =     (ImageButton) findViewById(R.id.backbutton);
        HistoryMODELArraylist = new ArrayList<>();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(TransactionHistoryActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        merchantid = sharedPreferences.getString("member_id", null);
        rollid = sharedPreferences.getString("rollid", null);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fetchhistory();
    }

   public void  fetchhistory(){
        StringRequest jsonObjectRequest3 = new StringRequest(Request.Method.GET, "https://vitefintech.com/viteapi/pay/transaction?member_id="+merchantid+"&role_id="+rollid,
               new Response.Listener<String>() {
                   @SuppressLint("SetTextI18n")
                   @Override
                   public void onResponse(String response) {
                       Log.d("Response", response.toString());
                       progressDialog.dismiss();
                       try {
                           JSONObject obj = new JSONObject(response);
                           String status       =    obj.getString("status");

                           JSONArray jsonArray= obj.getJSONArray("data");
                               for(int i= 0; i< jsonArray.length();i++) {
                                   JSONObject data = jsonArray.getJSONObject(i);

                                   String date          =   data.getString("created");
                                   String transection_response     =   data.getString("transection_response");

                                   JSONObject obj2 = new JSONObject(transection_response);
                                   String amount        =   obj2.getString("billAmount");
                                   String txnstatus       =   obj2.getString("status");

                                   Log.d("date",date);
                                   Log.d("txnstatus",txnstatus);
                                   Log.d("transection_response",transection_response);
                                   Log.d("amount",amount);

                                   History history = new History();
                                   history.setAepstype("Cash Withdrwal");
                                   history.setDate(date);
                                   history.setAmount(getResources().getString(R.string.us)+" "+amount);
                                   history.setTrastatus(txnstatus);

                                   HistoryMODELArraylist.add(history);
                               }
                               transactionHistoryAdapter = new TransactionHistoryAdapter(TransactionHistoryActivity.this, HistoryMODELArraylist);
                               recycler_view.setAdapter(transactionHistoryAdapter);
                               transactionHistoryAdapter.notifyDataSetChanged();

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       VolleyLog.d("Error:" + error.toString());
                   }
               });

       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(jsonObjectRequest3);
    }
}