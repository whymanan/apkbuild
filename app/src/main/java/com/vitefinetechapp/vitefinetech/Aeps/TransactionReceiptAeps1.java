package com.vitefinetechapp.vitefinetech.Aeps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class TransactionReceiptAeps1 extends AppCompatActivity {

    public static final String API_TRANSACTION = "https://vitefintech.com/public/api/aepstransection";
    String stan_no;
    String adharcard_numberget, mobilenoget, amountget, transaction_typeget, nameget, bank_codeget,access_tokenget,
            latitude,longitude,data,piddata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_receipt_aeps1);

        transaction_typeget    = getIntent().getStringExtra("transaction_type");
        adharcard_numberget    = getIntent().getStringExtra("adhar_no");
        mobilenoget            = getIntent().getStringExtra("mobile_no");
        amountget              = getIntent().getStringExtra("amount");
        bank_codeget           = getIntent().getStringExtra("bank_code");
        access_tokenget        = getIntent().getStringExtra("access_token");
        latitude               = getIntent().getStringExtra("latitude");
        longitude              = getIntent().getStringExtra("longitude");
        data                   = getIntent().getStringExtra("data");
        piddata                = getIntent().getStringExtra("pidData");

        stan();
        SendBiometricData();
    }

    public void stan(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int day_of_year = calendar.get(Calendar.DAY_OF_YEAR);
        int daycount =  day_of_year;

        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        String today = hour+""+minute+"";

        Random r = new Random();
        int random_no = r.nextInt(999999 - 100000) + 100000;

        stan_no = year+""+daycount+""+today+""+random_no+"";


        Log.d("year", String.valueOf(year));
        Log.d("daycount", String.valueOf(daycount));
        Log.d("today", String.valueOf(today));
        Log.d("random_no", String.valueOf(random_no));

        Log.d("stan_no", String.valueOf(stan_no));

    }



    public void SendBiometricData() {
        String location = (latitude+"|"+longitude);

        JSONObject obj = new JSONObject();
        try {
            obj.put("bankCode",bank_codeget);
            obj.put("ifscCode",bank_codeget);
            obj.put("location",location);
            obj.put("txType",transaction_typeget);
            obj.put("aadharNo",adharcard_numberget);
            obj.put("amount",amountget);
            obj.put("stan",stan_no);
            obj.put("data",data);
            obj.put("pidData",piddata);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_TRANSACTION,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ResponseBioDevice", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error:" + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Authorization","bearer " +access_tokenget);

                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}