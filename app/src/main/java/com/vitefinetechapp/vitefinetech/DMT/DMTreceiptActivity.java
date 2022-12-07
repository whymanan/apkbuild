package com.vitefinetechapp.vitefinetech.DMT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DMTreceiptActivity extends AppCompatActivity {

    TextView tStat;
    LinearLayout tRec;
    Button doneButton;
    TextView trMob,trAmt,trBank,trIfsc,trDate,trTime,cusCharge,serName,trAcc,transaction_msg;
    String bName,bIfsc, cust_Mob, bUid,  mId, amt,  bAcc, bId,bMob,msg;
    private ProgressDialog progressDialog;
    String token="";
    Boolean generated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmtreceipt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tStat=findViewById(R.id.transaction_stat);
        tRec=findViewById(R.id.tRec);
        tRec.setVisibility(View.GONE);
        doneButton=findViewById(R.id.dialogButtonDone);
        trDate=findViewById(R.id.trDate);
        trTime=findViewById(R.id.trTime);
        trMob=findViewById(R.id.trMob);
        trAmt=findViewById(R.id.trAmount);
        trBank=findViewById(R.id.trBankCode);
        trIfsc=findViewById(R.id.trBankIfsc);
        cusCharge=findViewById(R.id.cusCharge);
        serName=findViewById(R.id.serName);
        trAcc=findViewById(R.id.trAcc);
        transaction_msg=findViewById(R.id.transaction_msg);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        String currentdate = simpleDateFormat.format(today);

        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        trDate.setText(currentdate);
        trTime.setText(hour+ ":" +minute);


        Intent i=getIntent();
        bName=i.getStringExtra("bName");
        bIfsc=i.getStringExtra("bIfsc");
        bMob=i.getStringExtra("bMob");
        bUid=i.getStringExtra("bUid");
        mId=i.getStringExtra("mId");
        amt=i.getStringExtra("amt");
        bAcc=i.getStringExtra("bAcc");
        bId=i.getStringExtra("bId");
        getToken();
//        makeTransaction2(bName,bIfsc,bMob,bUid,mId,amt,bAcc,bId);
//        String status=i.getStringExtra("status");
//        cust_Mob=i.getStringExtra("cust_mob");
//        amt=i.getStringExtra("amt");
//        bName=i.getStringExtra("bName");
//        bIfsc=i.getStringExtra("bIfsc");
//        msg=i.getStringExtra("msg");
//
//        tStat.setText("Transaction " + status);
//        transaction_msg.setText(msg);
//        if(!status.equals("false")){
//            fetchHistory();
//        }


//        if(status.equals()){
//            progressDialog.show();
//            tStat.setText("Transaction Sucessful");
//            fetchHistory();
//        }
//        else{
//            tStat.setText("Transaction Failed");
//            Toast.makeText(this,"msg:"+msg, Toast.LENGTH_SHORT).show();
//        }


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DMTreceiptActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(DMTreceiptActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
//maketransaction(var)-> gettoken-token global
    //gettoken()-maketransa-var global
    private void makeTransaction2() {


        Log.d("dmttokgen", ":"+generated+token);
//        if(generated==true){
        String url = "https://nextmoney.co.in/nextmoney/dmt/submitTransection";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
//            dialog.setCancelable(true);
                    Log.d("dmtTraRec", response.toString());
                    try {

                        JSONObject obj=new JSONObject(response);
                        try{
                            String st=obj.getString("status");
                            String msg=obj.getString("message");
                            tStat.setText("Transaction " + st);
                            transaction_msg.setText(msg);
                            fetchHistory();

                        }catch (Exception e){
                            Boolean st=obj.getBoolean("status");
                            if(st==true){
                                String msg=obj.getString("msg");
                                tStat.setText(msg);
                                transaction_msg.setText(msg);
                                fetchHistory();
                            }else{
                                String msg=obj.getString("msg");
//                                JSONObject error=obj.getJSONObject("error");
                                tStat.setText("Transaction Failed");
//                                String msg2=error.getString("message");
//                                String cname=error.getString("classname");
                                transaction_msg.setText(msg);
                            }

//                            JSONObject error=obj.getJSONObject("error");
//                            tStat.setText("Transaction Failed");
//                            String msg=error.getString("message");
//                            String cname=error.getString("classname");
//                            transaction_msg.setText(cname+", "+msg);
                        }

//                        String msg=obj.getString("message");GITRT1017 6383600216



                    } catch (JSONException e) {

                        Toast.makeText(this, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("dmtTraErr", ":"+error.getMessage());
                    progressDialog.dismiss();
                    tStat.setText("Transaction Failed");
                    transaction_msg.setText("Internal Server Error");
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("beneficiary_name", bName);
                params.put("beneficiary_ifsc", bIfsc);
                params.put("mobile", bMob);
                params.put("user_id", bUid);
                params.put("member_id", mId);
//                params.put("member_id", "--");
                params.put("amount", amt);
                params.put("beneficiary_account_number", bAcc);
                params.put("beneficiaryid", bId);
                params.put("api_key", "NMA001");

                Log.d("dmtTraPar", params.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Client-Secret", "3BY1VNMA001fwpD");
                params.put("Secret-Key", "nxuuMLYGMb92VWI3R62ff9e7f3916a");
//                params.put("Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0aW1lc3RhbXAiOiIxMjM0NTY3ODkwIiwicGFydG5lcklkIjoiR0lUQTAwMSIsInJlcWlkIjoxNTE2MjM5MDIyfQ.Am_Qjdmr5B2HFcJLuiROB6N73CXpzIVg8zbqIo7ovv8");
                params.put("Token",token);
                Log.d("dmtTraHead", params.toString());
                return params;
            }
        };

//        Log.d("dmtATr", String.valueOf(stringRequest));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getToken(){
        //token
        String url = "https://nextmoney.co.in/nextmoney/dmt/auth";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
//                    progressDialog.dismiss();
//            dialog.setCancelable(true);
                    Log.d("dmtTokRec", response.toString());
                    try {

                        JSONObject obj=new JSONObject(response);
                        Boolean st=obj.getBoolean("status");
                        if(st==true)
                        {
                            token=obj.getString("token");
//                            generated=true;
                            makeTransaction2();
                        }
                        else{
                            progressDialog.dismiss();
                            tStat.setText("Trnsaction Failed");
                            transaction_msg.setText("Token generation failed");
                        }
//                        String msg=obj.getString("message");GITRT1017 6383600216



                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        tStat.setText("Trnsaction Failed");
                        transaction_msg.setText("Token generation failed");
                        Log.d("dmtTokErr1", ":"+e.getMessage());
                        Toast.makeText(this, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    tStat.setText("Trnsaction Failed");
                    transaction_msg.setText("Token generation failed");
                    Log.d("dmtTokErr", ":"+error.getMessage());
//                    progressDialog.dismiss();
//                    tStat.setText("Transaction Failed");
//                    transaction_msg.setText("Internal Server Error");
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("partnerId", "NMA001");
                params.put("Client-Secret", "nxuuMLYGMb92VWI3R62ff9e7f3916a");


                Log.d("dmtTokPar", params.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Client-Secret", "NMA001");
                params.put("Secret-Key", "nxuuMLYGMb92VWI3R62ff9e7f3916a");
//                params.put("Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0aW1lc3RhbXAiOiIxMjM0NTY3ODkwIiwicGFydG5lcklkIjoiR0lUQTAwMSIsInJlcWlkIjoxNTE2MjM5MDIyfQ.Am_Qjdmr5B2HFcJLuiROB6N73CXpzIVg8zbqIo7ovv8");

                Log.d("dmtTokHead", params.toString());
                return params;
            }
        };

//        Log.d("dmtATr", String.valueOf(stringRequest));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void fetchHistory() {
//        Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();

        String url = "https://nextmoney.co.in/nextmoney/Rechargebillpayment/transition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Log.d("dmt-Hist", response.toString());

                    JSONObject obj = null;
                    try {

                        obj = new JSONObject(response);
                        if (obj.getInt("status") == 1) {
                            JSONArray data = obj.getJSONArray("all_transition");
                            JSONObject data1 = data.getJSONObject(0);
                            String st = data1.getString("transection_msg");
                            if (st.equals("PENDING")) {
                                st = "SUCCESS";
                            }
                            tRec.setVisibility(View.VISIBLE);
                            TextView trId=findViewById(R.id.trId);
                            trId.setText(data1.getString("transection_id"));
//                            cusCharge.setText(data1.getString("customer_charge"));
                            serName.setText(data1.getString("transection_type"));
                            trAcc.setText(data1.getString("transaction_bank_account_no"));
                            trMob.setText(cust_Mob);
                            trAmt.setText(amt+"");
                            trBank.setText(bName);
                            trIfsc.setText(bIfsc);


//                                historyList.add(new AepsHistory(
//                                        data1.getString("transection_id"),
//                                        data1.getString("transection_type"),
//                                        st,
//                                        data1.getString("transection_mobile"),
//                                        data1.getString("transection_amount"),
//                                        data1.getString("transection_bank_code"),
//                                        data1.getString("transection_bank_ifsc"),
//                                        data1.getString("created")));

                        }

                    } catch (JSONException e) {
                        Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "error2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ElecMakeRechargeError", "error:" + error.getMessage());

                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                final String SHARED_PREFS = "shared_prefs";
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                String member_id = sharedPreferences.getString("member_id", null);
                String service_id = sharedPreferences.getString("user_id",null);

                params.put("member_id", member_id);
                params.put("service_id", 7+"");
                params.put("api_requist", "dmtv2");
                params.put("api_key", "NMA001");
                Log.d("dmt-par", params.toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}