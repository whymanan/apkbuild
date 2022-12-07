package com.vitefinetechapp.vitefinetech.DTH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.Recharge.MakeRechargeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DTH_MakeRechargeActivity extends AppCompatActivity {

    String operator, circle, amount, mobileno;
    private ProgressDialog progressDialog;
    TextView sucessful_tv, tv_Psuccess, tv_pDate, tv_detail, tv_mobNum, tvRefNO, tvAmt, tvRetail, tvOperator;
    ImageView imageButtonAe;
    CardView crd_id;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String latitude, longitude, member_id, admin_id, rType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dth_make_recharge);

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

        progressDialog = new ProgressDialog(DTH_MakeRechargeActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        operator = getIntent().getStringExtra("operator");
        amount = getIntent().getStringExtra("amount");
        mobileno = getIntent().getStringExtra("mobileno");
        rType = getIntent().getStringExtra("rType");

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        latitude = sharedPreferences.getString("latitude", null);
        longitude = sharedPreferences.getString("longitude", null);
        member_id = sharedPreferences.getString("member_id", null);

        retrivedata2();
    }

    private void retrivedata2() {

        String url = "https://nextmoney.co.in/nextmoney/Rechargebillpayment/recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Log.d("DTHMakeRechargeResponse", response);
                    crd_id.setVisibility(View.VISIBLE);

//                    JSONObject obj = null;
//                    String result = "";
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
                            crd_id.setVisibility(View.GONE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error-:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        crd_id.setVisibility(View.GONE);
                        imageButtonAe.setImageResource(R.drawable.ic_fail);
                        sucessful_tv.setText("Recharge Failed");
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Log.d("DTHMakeRechargeError", "error:" + error.getMessage());
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    crd_id.setVisibility(View.GONE);
                    imageButtonAe.setImageResource(R.drawable.ic_fail);
                    sucessful_tv.setText("Recharge Failed");
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("member_id", member_id);
//                params.put("member_id", "RBWRT1002");
                params.put("mobile", mobileno);
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("amount", amount);
                params.put("operator", operator);
                params.put("type", rType);
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