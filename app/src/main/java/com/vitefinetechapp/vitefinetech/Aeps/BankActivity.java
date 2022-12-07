package com.vitefinetechapp.vitefinetech.Aeps;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Aeps2.DatabaseHelper;
import com.vitefinetechapp.vitefinetech.Aeps2.SelectBankActivity2;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class BankActivity extends AppCompatActivity {

    public static final String BANK_Api = "https://vitefintech.com/public/api/aepsbanks";
    String ACCESS_TOKEN;
    Button confirm;
    Spinner bank;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    ArrayList<String> list;
    ImageButton backbutton;
    String bank_name,code;
    String  bank_code;
    private ProgressDialog progressDialog;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        bank          =     (Spinner)findViewById(R.id.bank_spinner);
        confirm       =     (Button)findViewById(R.id.confirm);
        backbutton    =     (ImageButton) findViewById(R.id.backbutton);

        list = new ArrayList<String>();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ACCESS_TOKEN = sharedPreferences.getString("tokenname", null);
        Log.d("ACCESS_TOKEN",ACCESS_TOKEN);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(BankActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        db.deletebanklistaeps1Record();

        getBanklist();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bank.getSelectedItem().toString().equals("Select Your Transaction Bank")){
                    Toast.makeText(BankActivity.this, "Please select your bank", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(BankActivity.this, BankActivity2.class);
                    i.putExtra("bank_code",bank_code);
                    i.putExtra("access_token",ACCESS_TOKEN);
                    startActivity(i);
                }
            }
        });

    }

    private void getBanklist(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BANK_Api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("ResponseBank:" + response.toString());
                progressDialog.dismiss();
                try{
                    JSONArray jsonArray= response.getJSONArray("codeValues");
                    for(int i= 0; i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        bank_name    =   data.getString("value");
                        code         =   data.getString("code");

                        list.add(bank_name);
                        db.insertaeps1Banklist(bank_name,code);
                    }
                    list.add(0, "Select Your Transaction Bank");

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BankActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bank.setAdapter(dataAdapter);

                    bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String seleted_bank = parent.getItemAtPosition(position).toString();
                            Log.d("bank",bank.getSelectedItem().toString());

                            SQLiteDatabase dataBase = db.getWritableDatabase();
                            Cursor mCursor2 = dataBase.rawQuery("SELECT * FROM "
                                    + DatabaseHelper.TABLE_AEPS1, null);
                            if (mCursor2.moveToFirst()) {
                                do {
                                    if(bank.getSelectedItem().toString().equals(mCursor2.getString(mCursor2.getColumnIndex("bank_name")))) {
                                        String bankcode = mCursor2.getString(mCursor2.getColumnIndex("code"));
                                        Log.d("bankkkkkkkkkkkk",bankcode);
                                        bank_code = bankcode;
                                    }
                                } while (mCursor2.moveToNext());
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
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