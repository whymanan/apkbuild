package com.vitefinetechapp.vitefinetech.M_ATM;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MatmReceiptActivity extends AppCompatActivity {
    String from, status, response, message, dataResponse, dataTransAmount, dataBalAmount, dataBalRrn, dataTxnId, dataTransType, dataType, dataCardNumber,
            dataCardType, dataTerminalId, dataBankName, mobile_num,res;
    TextView txn_type, date, time, amount, cardNo, txnid, rrn_no, tv_status, retail_name, transaction_details, balance_amt, amttxt;
    Button done_button;
    String member_id;
    private ProgressDialog progressDialog;
    String url;
    LinearLayout details;
    SharedPreferences sharedpreferences;
    String user_id;
    String latitude;
    String longitude;
    TextView txt_des,txt_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matm_receipt);

        Toast.makeText(this, "ReceiptHere", Toast.LENGTH_SHORT).show();
        txn_type = (TextView) findViewById(R.id.txn_type);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        amount = (TextView) findViewById(R.id.amount);
        cardNo = (TextView) findViewById(R.id.cardNo);
        txnid = (TextView) findViewById(R.id.txnid);
        rrn_no = (TextView) findViewById(R.id.rrn_no);
        tv_status = (TextView) findViewById(R.id.status);
        balance_amt = (TextView) findViewById(R.id.balanc_amt);
        amttxt = (TextView) findViewById(R.id.amttxt);
        transaction_details = (TextView) findViewById(R.id.transaction_details);
        retail_name = (TextView) findViewById(R.id.retail_name);
        done_button = (Button) findViewById(R.id.done_button);
        details=findViewById(R.id.details);
        txt_des=findViewById(R.id.m_atm_des);
        txt_status=findViewById(R.id.m_atm_status);
        progressDialog = new ProgressDialog(MatmReceiptActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            Intent i = getIntent();
            from = i.getStringExtra("from");
            status = i.getStringExtra("status");
            response = i.getStringExtra("response");
            message = i.getStringExtra("message");
            dataResponse = i.getStringExtra("dataResponse");
            dataTransAmount = i.getStringExtra("dataTransAmount");
            dataBalAmount = i.getStringExtra("dataBalAmount");
            dataBalRrn = i.getStringExtra("dataBalRrn");
            dataTxnId = i.getStringExtra("dataTxnId");
            dataTransType = i.getStringExtra("dataTransType");
            dataType = i.getStringExtra("dataType");
            dataCardNumber = i.getStringExtra("dataCardNumber");
            dataCardType = i.getStringExtra("dataCardType");
            dataTerminalId = i.getStringExtra("dataTerminalId");
            dataBankName = i.getStringExtra("dataBankName");
            mobile_num = i.getStringExtra("mobile_num");
            res=i.getStringExtra("res");
        }
        catch (Exception e){
            Toast.makeText(this, "RecExc:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        final String SHARED_PREFS = "shared_prefs";
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        member_id = sharedpreferences.getString("member_id", null);

        user_id = sharedpreferences.getString("user_id", null);
        latitude = sharedpreferences.getString("latitude", null);
        longitude = sharedpreferences.getString("longitude", null);


        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MatmReceiptActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });

        if (from.equals("CW")) {
            url = "https://nextmoney.co.in/nextmoney/matm/transactionCW";
        } else {
            url = "https://nextmoney.co.in/nextmoney/matm/transactionBE";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date today = new Date();
                    String currentdate = simpleDateFormat.format(today);
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa z", Locale.US);
//                    int hour = now.get(Calendar.HOUR_OF_DAY);
//                    int minute = now.get(Calendar.MINUTE);
                    date.setText(currentdate);
                    time.setText(sdf.format(today));

                    txn_type.setText(dataTransType);
                    amount.setText(dataTransAmount);
                    balance_amt.setText(dataBalAmount);
                    cardNo.setText(dataCardNumber);
                    txnid.setText(dataTxnId);
                    rrn_no.setText(dataBalRrn);
                    tv_status.setText(status);
                    retail_name.setText(member_id);
                },
                error -> {
                    progressDialog.dismiss();
                    details.setVisibility(View.GONE);
//                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    txt_status.setText("MATM Transaction failed");
                    txt_des.setVisibility(View.VISIBLE);
                    txt_des.setText("Transaction failed,404 Not found error.");

                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("mobileNumber", mobile_num);
                params.put("bankRrn", dataBalRrn);
                params.put("transAmount", dataTransAmount);
                params.put("txnid", dataTxnId);
                params.put("status", status);
                params.put("message", message);
                params.put("response_code", response + "");
                params.put("response", res);
                params.put("cardType", dataCardType);
                params.put("transactionType", dataTransType);
                params.put("submerchantid", member_id);
                params.put("api_key", "NMA001");
                params.put("user_id", user_id);
//                params.put("dataBalAmount",dataBalAmount);
//                params.put("dataType",dataType);
//                params.put("dataCardNumber",dataCardNumber);
//                params.put("dataTerminalId",dataTerminalId);
//                params.put("dataBankName",dataBankName);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}