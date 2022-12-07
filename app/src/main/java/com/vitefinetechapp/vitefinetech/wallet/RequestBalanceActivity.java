package com.vitefinetechapp.vitefinetech.wallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputEditText;
import com.open.open_web_sdk.OpenPayment;
import com.open.open_web_sdk.listener.PaymentStatusListener;
import com.open.open_web_sdk.model.TransactionDetails;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestBalanceActivity extends AppCompatActivity implements View.OnClickListener{

    Spinner spinner_paymt_mode,spinner_stock_type,spinner_our_bank;
    ArrayList<String> our_bank_list,paymt_mode_list,stock_type_list;
    Button reset_btn,submit_req_bal_btn;
    TextInputEditText input_edt_req_bal_remark,input_edt_req_amt;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    ImageButton backbuttonReqBal;
    String user_id,url,member_id;
    Button btn_open;
    EditText et_mob,et_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_balance);

        spinner_paymt_mode        =   findViewById(R.id.spinner_paymt_mode);
        spinner_stock_type        =   findViewById(R.id.spinner_stock_type);
        spinner_our_bank          =   findViewById(R.id.spinner_our_bank);
        reset_btn                 =   findViewById(R.id.reset_btn);
        submit_req_bal_btn        =   findViewById(R.id.submit_req_bal_btn);
        input_edt_req_bal_remark  =   findViewById(R.id.input_edt_req_bal_remark);
        input_edt_req_amt         =   findViewById(R.id.input_edt_req_amt);
        backbuttonReqBal=findViewById(R.id.backbuttonReqBal);
        btn_open=findViewById(R.id.open_payment);
        et_mob=findViewById(R.id.et_mob);
        et_mail=findViewById(R.id.et_mail);

        backbuttonReqBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        user_id = sharedpreferences.getString("user_id",null);
        Log.e("KYCManagementActivity","user_id"+user_id);

        member_id = sharedpreferences.getString("member_id",null);
        Log.e("KYCManagementActivity","member_id"+member_id);

        reset_btn.setOnClickListener(this);
        submit_req_bal_btn.setOnClickListener(this);

//        selectOurBank();
        selectPaymtMode();
//        selectStockType();
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                var openPayment: OpenPayment =
//                        OpenPayment.Builder()
//                                .with(this@MainActivity)
//        .setPaymentToken(body?.id!!) // Add your payment token here
//        .setEnvironment(OpenPayment.Environment.SANDBOX)
//                        .setAccessKey(accessKey) // Add your Access Key here
//                        .setColor(colorHexCode)   // Add your Color Hex Code here
//                        .setErrorColor(errorColorHexCode).  // Add your Error Color Hex Code here
//         .setLogoUrl(logoURL)  // Add your Logo URL here
//                        .build()
                if(TextUtils.isEmpty(input_edt_req_amt.getText())){
                    Toast.makeText(RequestBalanceActivity.this, "Enter a Amount", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(et_mob.getText())){
                    Toast.makeText(RequestBalanceActivity.this, "Enter a mobile number", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(et_mail.getText())){
                    Toast.makeText(RequestBalanceActivity.this, "Enter a Email id", Toast.LENGTH_SHORT).show();
                }
                else{
                    gettoken(input_edt_req_amt.getText().toString(),et_mob.getText().toString(),et_mail.getText().toString());

                }

            }
        });

    }

    private void gettoken(String amount, String mobile, String email){
        String url = "https://sandbox-icp-api.bankopen.co/api/payment_token";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("OReqBalRes", ":"+response.toString());
                    try {

                        JSONObject obj=new JSONObject(response);
                        String token=obj.getString("id");
                        makePayment(token);

                    } catch (JSONException e) {

                        Toast.makeText(this, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("OReqBalErr", ":"+error.getMessage());
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String pattern = "yyyyMMddHHmmss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                String referenceNo = date;
                String refernceNumber = "INC" + referenceNo;

                params.put("amount", amount);
                params.put("contact_number", mobile);
                params.put("email_id", email);
                params.put("currency", "INR");
                params.put("mtx", refernceNumber);

                Log.d("OReqBalPar","="+params);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Authorization", "Bearer 8e514240-72d9-11ec-8fe5-7b96da4e9eec:64355219fd629e3630bc9bbce8b98609e97df78f");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void makePayment(String token){
        OpenPayment openPayment=new OpenPayment.Builder().with(RequestBalanceActivity.this)
                .setPaymentToken(token)
                .setEnvironment(OpenPayment.Environment.SANDBOX)
                .setAccessKey("8e514240-72d9-11ec-8fe5-7b96da4e9eec")
                .setColorPrimary("#DA251C")
                .setErrorColor("#cf2827").build();
        openPayment.startPayment();

        PaymentStatusListener mListerner=new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
                Toast.makeText(RequestBalanceActivity.this, transactionDetails.getPaymentId()+"||"+transactionDetails.getStatus(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull String s) {

            }
        };
        openPayment.setPaymentStatusListener(mListerner);
    }

    private void selectStockType() {

        stock_type_list = new ArrayList<>();

        stock_type_list.add(0, "Select Stock Type");
        stock_type_list.add(1, "Main Bal");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RequestBalanceActivity.this, android.R.layout.simple_spinner_dropdown_item, stock_type_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_stock_type.setAdapter(dataAdapter);

        spinner_stock_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String stock_type = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void selectPaymtMode() {

        paymt_mode_list = new ArrayList<>();

        paymt_mode_list.add(0, "Select Payment Mode");
        paymt_mode_list.add(1, "SAME BANK FUND TRANSFER");
        paymt_mode_list.add(1, "OTHER BANK FUND TRANSFER");
//        paymt_mode_list.add(1, "ATM FUND TRANSFER");
        paymt_mode_list.add(1, "IMPS/UPI");
//        paymt_mode_list.add(1, "OTHER");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RequestBalanceActivity.this, android.R.layout.simple_spinner_dropdown_item, paymt_mode_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_paymt_mode.setAdapter(dataAdapter);

        spinner_paymt_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String paymt_mode_type = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void selectOurBank() {

        our_bank_list = new ArrayList<>();

        our_bank_list.add(0, "Select Your Bank");
        our_bank_list.add(1, "icici 231246578");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RequestBalanceActivity.this, android.R.layout.simple_spinner_dropdown_item, our_bank_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_our_bank.setAdapter(dataAdapter);

        spinner_our_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bank_type = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.submit_req_bal_btn:
                submitBalRequest();
                break;

            case R.id.reset_btn:
                restData();
                break;
        }
    }

    private void restData() {


        input_edt_req_bal_remark.setText("");
        input_edt_req_amt.setText("");
    }

    private void submitBalRequest() {

        String amount = input_edt_req_amt.getText().toString();
        String remark = input_edt_req_bal_remark.getText().toString();
        String bank_type = "icici 231246578";
        String stock_type = "Main Bal";
        String paymt_mode = spinner_paymt_mode.getSelectedItem().toString();

        if(amount.isEmpty())
        {
            input_edt_req_amt.setError("Amount is Empty");
            input_edt_req_amt.requestFocus();
        }else if (spinner_paymt_mode.getSelectedItem().toString().equals("Select Payment Mode")) {
            Toast.makeText(this, "Please Select Payment Mode", Toast.LENGTH_SHORT).show();
        }else {

            submitDataToServer(amount,bank_type,stock_type,paymt_mode,remark);
        }

    }

    private void submitDataToServer(String amount, String bank_type, String stock_type, String paymt_mode, String remark) {

        url = "https://nextmoney.co.in/nextmoney/Wallet/addbalance";
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id",user_id);
            Log.e("user_id", user_id);

            obj.put("amount",amount);
            Log.e("amount", amount);

            obj.put("remark",remark);
            Log.e("remark", remark);

            obj.put("mode",paymt_mode);
            Log.e("mode", paymt_mode);

            obj.put("stock_type",stock_type);
            Log.e("stock_type", stock_type);

            obj.put("bank",bank_type);
            Log.e("bank", bank_type);

            Log.d("addBlnObj", obj.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //  progressDialog.dismiss();
                        Log.e("addBlnRes", response.toString());
                        try {
                            Boolean status = response.getBoolean("status");
                            String msg = response.getString("msg");

                            Toast.makeText(RequestBalanceActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if(status==true){
                                input_edt_req_bal_remark.setText("");
                                input_edt_req_amt.setText("");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:" + error.toString());
                Log.e("Error",""+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        VolleyLog.d("ResponseLogin:" + response.toString());
//                        try {
//                            JSONObject obj = new JSONObject(response);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RequestBalanceActivity.this, "Login Failed. You have entered the wrong credentials.", Toast.LENGTH_LONG).show();
//                    }
//                })
//        {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params  = new HashMap<>();
//
//
//                params.put("user_id", user_id);
//                params.put("amount", amount);
//                params.put("remark", remark);
//                params.put("mode", paymt_mode);
//                params.put("bank", bank_type);
//                params.put("stock_type", stock_type);
//
//
//
//
//                return params ;
//            }
//        };
//        RequestQueue requestQueue1= Volley.newRequestQueue(this);
//        requestQueue1.add(stringRequest);
    }

}
