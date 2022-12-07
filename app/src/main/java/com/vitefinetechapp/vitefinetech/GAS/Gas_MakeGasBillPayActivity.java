package com.vitefinetechapp.vitefinetech.GAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.DTH.DTH_MakeRechargeActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Gas_MakeGasBillPayActivity extends AppCompatActivity {

    String operator, circle, amount, mobileno;
    private ProgressDialog progressDialog;
    TextView sucessful_tv;
    ImageButton imageButtonAe;
    CardView crd_id;
    TextView tv_acc,tv_amt,tv_op,tvRname,tvRid,tv_detail;
    LinearLayout ll_detail;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String latitude, longitude, member_id, admin_id, rType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_make_gas_bill_pay);

        sucessful_tv = (TextView) findViewById(R.id.sucessful_tv);
        imageButtonAe = (ImageButton) findViewById(R.id.imageButtonAe);
        crd_id = (CardView) findViewById(R.id.crd_id);
//        tvUname=findViewById(R.id.tvUname);
        tv_acc=findViewById(R.id.tv_acc);
        tv_amt=findViewById(R.id.tv_amt);
        tv_op=findViewById(R.id.tv_op);
        tvRname=findViewById(R.id.tvRname);
        tvRid=findViewById(R.id.tvRid);
        tv_detail=findViewById(R.id.tv_detail);
        ll_detail=findViewById(R.id.ll_detail);

        crd_id.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(Gas_MakeGasBillPayActivity.this);
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
                    Log.d("GasMakeRechargeResponse", response);
                    crd_id.setVisibility(View.VISIBLE);

                    JSONObject obj = null;
                    String result = "";
                    try {
                        obj = new JSONObject(response);
                        int res = obj.getInt("response");
                        int stat = obj.getInt("status");

                        if (res == 1) {
                            imageButtonAe.setImageResource(R.drawable.ic_check);
                            sucessful_tv.setText("Recharge Successful");
                            ll_detail.setVisibility(View.VISIBLE);
                            tv_detail.setVisibility(View.VISIBLE);
//                            tvUname.setText(uname);
                            tv_acc.setText(mobileno);
                            tv_amt.setText(amount);
                            tv_op.setText(operator);
                            tvRid.setText(member_id);
                        } else if (stat == 4 && res == 4) {
                            imageButtonAe.setImageResource(R.drawable.ic_fail);
                            sucessful_tv.setText("Access denied");
                        } else if (stat == 2) {
                            imageButtonAe.setImageResource(R.drawable.ic_fail);
                            sucessful_tv.setText("Transaction Failed kindly connect admin");
                        } else if (stat == 6 && res == 6) {
                            imageButtonAe.setImageResource(R.drawable.ic_fail);
                            sucessful_tv.setText("Insufficient balance");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Log.d("GasMakeRechargeError", "error:" + error.getMessage());
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    crd_id.setVisibility(View.VISIBLE);
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
        requestQueue.add(stringRequest);
    }
}