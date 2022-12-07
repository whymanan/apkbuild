package com.vitefinetechapp.vitefinetech.Payout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.AepsHistory;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.AepsHistoryAdapter;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PayOutActivity extends AppCompatActivity {

    Button btnNext, btnSubmit;
    EditText edt_amount;
    String user_name, amount, remark, member_id;
    String bal;
    TextView txtId,cBal,nBal,pChar,pAmt,error_msg;
    CardView card_info,card_error;
    int pChargeAmt;
    float nBalAmt;
    float pAmtAmt;
    ImageButton backbutton;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_out);

        edt_amount = findViewById(R.id.editTextAmount);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext=findViewById(R.id.btnNext);
        txtId = findViewById(R.id.txtId);
        card_info=findViewById(R.id.card_info);
        card_error=findViewById(R.id.card_error);
        cBal=findViewById(R.id.tv_cBal);
        nBal=findViewById(R.id.tv_nBal);
        pChar=findViewById(R.id.tv_pChar);
        pAmt=findViewById(R.id.tv_pAmt);
        error_msg=findViewById(R.id.error_msg);

        btnSubmit.setVisibility(View.GONE);
        card_info.setVisibility(View.GONE);
        card_error.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString("member_id", null);
        txtId.setText(member_id);

        setBalance();
//        Intent i=getIntent();
//        bal=i.getStringExtra("avail_bal");

//        cBal.setText(bal);

        backbutton=findViewById(R.id.backbuttonI);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edt_amount.getText()) || Float.parseFloat(edt_amount.getText().toString())==0){
                    Toast.makeText(PayOutActivity.this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                }else if(Float.parseFloat(edt_amount.getText().toString())<100.0){
                    Toast.makeText(PayOutActivity.this, "Enter a amount more than 100", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(bal)< Float.parseFloat(edt_amount.getText().toString())){
                    card_error.setVisibility(View.VISIBLE);
                    error_msg.setText("Your current balance is less than the payout amount");
                }
                else{
                    fetchInfo(Float.parseFloat(edt_amount.getText().toString()));
//                    Intent i=new Intent(PayOutActivity.this,PayoutAccountActivity.class);
//                    i.putExtra("pAmount",pAmtAmt+"");
//                    i.putExtra("pCharge",pChargeAmt+"");
//                    i.putExtra("balance",bal);
//                    startActivity(i);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PayOutActivity.this,PayoutAccountActivity.class);
                i.putExtra("pAmount",pAmtAmt+"");
                i.putExtra("pCharge",pChargeAmt+"");
                i.putExtra("balance",bal);
                startActivity(i);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSubmit.setVisibility(View.GONE);
                card_info.setVisibility(View.GONE);
                card_error.setVisibility(View.GONE);

                btnNext.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        edt_amount.addTextChangedListener(textWatcher);
    }

    private void fetchInfo(float eAmt) {
        Log.d("PAYchfet", eAmt+"here");


        String chUrl="https://nextmoney.co.in/nextmoney/wallet/get_move_charge?amount="+eAmt+"&api_key=NMA001";
//        chUrl="https://nextmoney.co.in/nextmoney/wallet/get_move_charge?amount=10.0&api_key=NMA001";
        Log.d("payChURL", chUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, chUrl,
                response -> {
                    Log.d("payCh", response.toString());
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        Boolean stat=obj.getBoolean("status");
                        if(stat==true){
                            pChargeAmt=obj.getInt("Payout_charge");
                            pChar.setText(pChargeAmt+"");

                            nBalAmt=Float.parseFloat(bal)-eAmt;
                            pAmtAmt=eAmt-pChargeAmt;

                            nBal.setText(nBalAmt+"");
                            pAmt.setText(pAmtAmt+"");

                            card_info.setVisibility(View.VISIBLE);
                            btnNext.setVisibility(View.GONE);

                            if(pAmtAmt<0){
                                card_error.setVisibility(View.VISIBLE);
                                error_msg.setText("Final payout amount can't be negative, change the payout amount");
                            }
                            else{
                                btnSubmit.setVisibility(View.VISIBLE);
                            }


                        }
                        else{
                            Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                },
                error -> {
                    Log.d("PayChError", "pCharError:" + error.getMessage());

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(PayOutActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void setBalance() {
        String url = "https://nextmoney.co.in/nextmoney/Rechargebillpayment/wallet";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("setBalance", response);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        Log.d("setBlanceres:", obj.getString("response"));
                        int res = obj.getInt("response");
                        int st = obj.getInt("status");
                        bal = "";
                        if (st == 1 && res == 1) {
                            bal = obj.getString("wallet_balance");
                        } else if (st == 0 && res == 1) {
                            bal = "----.--";
                        } else {
                            bal = "----.-";
                        }
                        cBal.setText(bal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("setBalanceerror", "error:" + error.getMessage());
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("member_id", member_id);
                Log.d("balpayout", params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}