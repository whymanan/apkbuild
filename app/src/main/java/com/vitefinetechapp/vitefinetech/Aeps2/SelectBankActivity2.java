package com.vitefinetechapp.vitefinetech.Aeps2;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.Recharge.RechargeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

public class SelectBankActivity2 extends AppCompatActivity {

    public static final String BANK_Api = "https://vitefintech.com/viteapi/pay/banklist?api_key=VITA001";
//    public static final String BANK_Api = "https://nextmoney.co.in/nextmoney/pay/banklist?api_key=VITA001";
    String ACCESS_TOKEN,bank_code,bank_name,stan_no;
    Spinner bank;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    ArrayList<String> list,tTypeList;
    ImageButton backbutton;
    DatabaseHelper db;
    private ProgressDialog progressDialog;

    Button submit,scan;
    String transactionType;
    LinearLayout scan_linear;
    EditText amount,adharno,mobno;
    Spinner tType;
    TextView txtamttxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank2);

        bank          =     (Spinner)findViewById(R.id.bank_spinner);
        backbutton    =     (ImageButton) findViewById(R.id.backbutton);

        submit     =    (Button) findViewById(R.id.submit);
        tType         =    (Spinner)findViewById(R.id.tras_spinner);
        txtamttxt  =    (TextView) findViewById(R.id.txtamt);
        amount     =    (EditText) findViewById(R.id.amount);
        adharno    =    (EditText) findViewById(R.id.adharno);
        scan_linear = (LinearLayout) findViewById(R.id.scan_linear);
        mobno      =   (EditText) findViewById(R.id.mobno);


        list = new ArrayList<String>();
        tTypeList=new ArrayList<String>();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ACCESS_TOKEN = sharedPreferences.getString("tokenname", null);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        db.deletebanklistRecord();

        progressDialog = new ProgressDialog(SelectBankActivity2.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getBanklist();

        selectTransactionType();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactiontype_     =   tType.getSelectedItem().toString();
                String adhrno_              =   adharno.getText().toString();
                String mobno_               =   mobno.getText().toString();
                String amount_              =   amount.getText().toString();

                if(transactiontype_.equals("Select Transaction Types")){
                    Toast.makeText(SelectBankActivity2.this, "Please select transaction type", Toast.LENGTH_SHORT).show();
                }else if(adhrno_.toString().isEmpty()) {
                    adharno.setError("Please enter adhar number");
                    adharno.requestFocus();
                }else if (adhrno_.trim().length() < 12) {
                    adharno.setError("Enter a twelve digit adhar number");
                    adharno.requestFocus();
                }else if (adhrno_.trim().length() > 12) {
                    adharno.setError("Enter a twelve digit adhar number");
                    adharno.requestFocus();
                }else if(mobno_.toString().isEmpty()) {
                    mobno.setError("Please enter mobile number");
                    mobno.requestFocus();
                }else if(mobno_.trim().length() < 10){
                    mobno.setError("Please enter 10 digit mobile number");
                    mobno.requestFocus();
                } else if (mobno_.trim().length() > 10) {
                    mobno.setError("Please enter 10 digit mobile number");
                    mobno.requestFocus();
                }else {
                    Intent i = new Intent(SelectBankActivity2.this, BioMetricActivity2.class);
                    i.putExtra("transaction_type", transactionType);
                    i.putExtra("adhar_no", adharno.getText().toString());
                    i.putExtra("mobile_no", mobno.getText().toString());
                    i.putExtra("amount", amount.getText().toString());
                    i.putExtra("bank_code", bank_code);
                    startActivity(i);
                }
            }
        });

        scan_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectBankActivity2.this, ScanActivity2.class);
                startActivityForResult(i,1);
            }
        });
    }

    private void selectTransactionType() {
        tTypeList.add(0, "Select Transaction Types");
        tTypeList.add(1, "Cash Withdrawal");
        tTypeList.add(2, "Balance Enquiry");
        tTypeList.add(3, "Mini Statement");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectBankActivity2.this, android.R.layout.simple_spinner_dropdown_item, tTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tType.setAdapter(dataAdapter);

        tType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String transactiontype = parent.getItemAtPosition(position).toString();
                if(transactiontype.equals("Balance Enquiry")){
                    amount.setVisibility(View.GONE);
                    txtamttxt.setVisibility(View.GONE);

                    transactionType  = "BE";
                }else if(transactiontype.equals("Cash Withdrawal")){
                    amount.setVisibility(View.VISIBLE);
                    txtamttxt.setVisibility(View.VISIBLE);

                    transactionType  = "CW";
                }else if(transactiontype.equals("Mini Statement")){
                    amount.setVisibility(View.GONE);
                    txtamttxt.setVisibility(View.GONE);

                    transactionType  = "MS";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 ) {
            if (resultCode == RESULT_OK) {
                String useruid = data.getStringExtra("editvalue");
                String username = data.getStringExtra("editvalue2");
                adharno.setText(useruid);
//                name.setText(username);

            }
        }
    }



    private void getBanklist(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BANK_Api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("ResponseBank:" + response.toString());
                progressDialog.dismiss();
                try{
                    JSONObject jsonObject = response.getJSONObject("banklist");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");
                    for(int i= 0; i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        bank_name    =   data.getString("bankName");
                        bank_code    =   data.getString("iinno");

                        Log.d("bank_name",bank_name);
                        Log.d("bank_code",bank_code);

                        list.add(bank_name);
                        db.insertBanklist(bank_name,bank_code);

                    }
                    list.add(0, "Select Your Transaction Bank");

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SelectBankActivity2.this, android.R.layout.simple_spinner_dropdown_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bank.setAdapter(dataAdapter);

                    bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String seleted_bank = parent.getItemAtPosition(position).toString();

                            SQLiteDatabase dataBase = db.getWritableDatabase();
                            Cursor mCursor2 = dataBase.rawQuery("SELECT * FROM "
                                    + DatabaseHelper.TABLE_BANK, null);
                            if (mCursor2.moveToFirst()) {
                                do {
                                    if(bank.getSelectedItem().toString().equals(mCursor2.getString(mCursor2.getColumnIndex("bank_name")))) {
                                        String bankcode = mCursor2.getString(mCursor2.getColumnIndex("bank_code"));
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

                headers.put("Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQQVlTUFJJTlQiLCJ0aW1lc3RhbXAiOjE2MTAwOTY0MjIsInBhcnRuZXJJZCI6IlBTMDAxIiwicHJvZHVjdCI6IldBTExFVCIsInJlcWlkIjoxNjEwMDk2NDIyfQ.CYe9uRoQCM75IUhyxoQQj08TDNz-uZ-kPyw33nyy0Iw");
                headers.put("Authorisedkey" , "MzNkYzllOGJmZGVhNWRkZTc1YTgzM2Y5ZDFlY2EyZTQ=");

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