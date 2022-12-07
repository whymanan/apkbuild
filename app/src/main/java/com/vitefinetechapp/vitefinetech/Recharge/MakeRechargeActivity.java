package com.vitefinetechapp.vitefinetech.Recharge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class
MakeRechargeActivity extends AppCompatActivity {
    String operator, circle, amount, mobileno;
    private ProgressDialog progressDialog;
    TextView sucessful_tv, tv_Psuccess, tv_pDate, tv_detail, tv_mobNum, tvRefNO, tvAmt, tvRetail, tvOperator;
    ImageView imageButtonAe;
    CardView crd_id;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String latitude, longitude, member_id, admin_id,rType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_recharge);

        sucessful_tv = (TextView) findViewById(R.id.tv_success);
        imageButtonAe = findViewById(R.id.imageView2);
        crd_id = (CardView) findViewById(R.id.crd_id);
        tv_Psuccess = findViewById(R.id.tv_Psuccess);
        tv_pDate = findViewById(R.id.tv_pDate);
        tv_detail = findViewById(R.id.tv_detail);
        tv_mobNum = findViewById(R.id.tv_uMobNum);
        tvRefNO = findViewById(R.id.tv_reference);
        tvAmt = findViewById(R.id.tv_amt);
        tvRetail = findViewById(R.id.tvRetailer);
        tvOperator = findViewById(R.id.tvOperator);


        crd_id.setVisibility(View.GONE);
        tv_detail.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(MakeRechargeActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        operator = getIntent().getStringExtra("oprator");
        circle = getIntent().getStringExtra("circle");
        amount = getIntent().getStringExtra("amount");
        mobileno = getIntent().getStringExtra("mobileno");
        rType=getIntent().getStringExtra("rType");


        Log.d("Rechargeoprator", operator);
        Log.d("Rechargecircle", circle);
        Log.d("Rechargeamount", amount);
        Log.d("Rechargemobileno", mobileno);


//        url = "https://vitefintech.com/viteapi/recharge/mobile_submit";
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        latitude = sharedPreferences.getString("latitude", null);
        longitude = sharedPreferences.getString("longitude", null);
        member_id = sharedPreferences.getString("member_id", null);
        admin_id = "VITA001";

        Log.d("MakeRechargeValues:", latitude + "|" + longitude + "|" + member_id+"|"+rType);

// seting date and time
        Calendar calendar    =    Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();
        String currentdate = simpleDateFormat.format(today);

        Calendar now = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
//        int hour = now.get(Calendar.HOUR_OF_DAY);
//        int minute = now.get(Calendar.MINUTE);
//        tv_pDate.setText(currentdate+" "+hour+":"+minute);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa z", Locale.US);
        tv_pDate.setText(currentdate+" "+sdf.format(today));

//        retrivedata();
        retrivedata2();
    }

    private void retrivedata2() {
        String url = "https://nextmoney.co.in/nextmoney/Rechargebillpayment/recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> { progressDialog.dismiss();
                    Log.d("make-recharge-res", response.toString());
                    try {
                        String myStatus="";
                        JSONObject obj=new JSONObject(response);
                        try{
                            Boolean st=obj.getBoolean("status");
                            if(st){
                                myStatus="bool";
                            }
                        }catch (JSONException e){
                            Log.d("make-recharge-e", "here"+e.getMessage());
                            int st=obj.getInt("status");
                            if(st==56){
                                myStatus="int";
                            }
                        }
                        Log.d("make-recharge-e2", "hereee");
                        if(myStatus.equals("bool")){
                            imageButtonAe.setImageResource(R.drawable.ic_check);
                            sucessful_tv.setText("Payment Successful");
                            tv_Psuccess.setText(obj.getString("message"));
                            tv_mobNum.setText(mobileno);
                            tvRefNO.setText(""+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                            tvAmt.setText(amount);
                            tvRetail.setText(member_id);
                            tvOperator.setText(operator);
                            tv_detail.setVisibility(View.VISIBLE);
                            crd_id.setVisibility(View.VISIBLE);
                        }
                        else if(myStatus.equals("int")){
                            JSONObject data=obj.getJSONObject("data");
                            int st2=data.getInt("status");
                            if(st2==1){
                                imageButtonAe.setImageResource(R.drawable.ic_fail);
                                sucessful_tv.setText("Payment Pending");
                                tv_Psuccess.setText(data.getString("msg"));
                                tv_mobNum.setText(mobileno);
                                tvRefNO.setText(""+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                                tvAmt.setText(amount);
                                tvRetail.setText(member_id);
                                tvOperator.setText(operator);
                                tv_detail.setVisibility(View.VISIBLE);
                                crd_id.setVisibility(View.VISIBLE);
                            }else if(st2==2){
                                imageButtonAe.setImageResource(R.drawable.ic_check);
                                sucessful_tv.setText("Payment Successful");
                                tv_Psuccess.setText(data.getString("msg"));
                                tv_mobNum.setText(mobileno);
                                tvRefNO.setText(""+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                                tvAmt.setText(amount);
                                tvRetail.setText(member_id);
                                tvOperator.setText(operator);
                                tv_detail.setVisibility(View.VISIBLE);
                                crd_id.setVisibility(View.VISIBLE);
                            }
                            else if(st2==3){
                                imageButtonAe.setImageResource(R.drawable.ic_fail);
                                sucessful_tv.setText("Payment Failed");
                                tv_Psuccess.setText(data.getString("msg"));
                            }

                        }
                        else{
                            imageButtonAe.setImageResource(R.drawable.ic_fail);
                            sucessful_tv.setText("Payment Failed");
                            tv_Psuccess.setText(obj.getString("message"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("make-recharge-err", e.getMessage());
                        imageButtonAe.setImageResource(R.drawable.ic_fail);
                        sucessful_tv.setText("Payment Failed");
                        tv_Psuccess.setText("Your transaction has been failed with an JSONerror message");

                    }


                },
                error -> {
                    progressDialog.dismiss();
                    Log.d("MakeRechargeError", "error:" + error.getMessage());
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    imageButtonAe.setImageResource(R.drawable.ic_fail);
                    sucessful_tv.setText("Payment Failed");
                    tv_Psuccess.setText("Your transaction has been failed");
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String operator_id="0";
                if (operator.equals("Vodafone")) {
                    operator_id = "22";
                } else if (operator.equals("Jio")) {
                    operator_id = "18";
                } else if (operator.equals("BSNL")) {
                    operator_id = "13";
                } else if (operator.equals("Airtel")) {
                    operator_id = "11";
                }
                params.put("member_id", member_id);
                params.put("mobile", mobileno);
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("amount", amount);
                params.put("operator", operator_id);
                params.put("type", rType);
                Log.d("makeRecPar", params.toString());
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


//    public void retrivedata(){
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("circle",circle);
//            obj.put("operator",operator);
//            obj.put("type","1");
//            obj.put("mobile",mobileno);
//            obj.put("amount",amount);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,obj,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        progressDialog.dismiss();
//                        Log.d("REsponce", response.toString());
//                        try {
//                            String operator = response.getString("status");
//                            if(operator.equals("FAILED")){
//                                crd_id.setVisibility(View.VISIBLE);
//                                imageButtonAe.setImageResource(R.drawable.ic_fail);
//                                sucessful_tv.setText("Recharge Failed");
//                            }else{
//                                crd_id.setVisibility(View.VISIBLE);
//                                imageButtonAe.setImageResource(R.drawable.ic_check);
//                                sucessful_tv.setText("Recharge Successful");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Error:" + error.toString());
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonObjectRequest);
//    }
}