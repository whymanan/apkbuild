package com.vitefinetechapp.vitefinetech.EMI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.MainAeps2Activity;
import com.vitefinetechapp.vitefinetech.Aeps2.TransactionReceiptAeps2;
import com.vitefinetechapp.vitefinetech.Electricity.ElecticityActivity;
import com.vitefinetechapp.vitefinetech.Electricity.Electricity_makePaymentActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EmiActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> operatorList,operatorIdList;
    ArrayAdapter arrayAdapter;
    ImageButton backbutton;
    Button proceed;
    CardView crdstory_id;
    EditText account_id;
    CardView cInfo, cNoInfo;
    private ProgressDialog progressDialog;
    String selected_operator,sOperator,amtBal,duedate,uname;

    Button btn_fetchInfo;
    Boolean infofetched=false;
    TextView tv_cusInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);

        spinner        =     (Spinner)findViewById( R.id.Elecrtcity_spinner );
        backbutton     =     (ImageButton) findViewById(R.id.backbutton);
        proceed      =     (Button) findViewById(R.id.fetchbill);
        account_id     =     (EditText) findViewById(R.id.account_id);
        cInfo = findViewById(R.id.card_info);
        cNoInfo = findViewById(R.id.card_noInfo);
        cInfo.setVisibility(View.GONE);

        tv_cusInfo=findViewById(R.id.tv_cusInfo);
        tv_cusInfo.setVisibility(View.GONE);
        cNoInfo.setVisibility(View.GONE);
        btn_fetchInfo=findViewById(R.id.fetchinfo);
        proceed.setVisibility(View.GONE);

        operatorList=new ArrayList<>();
        operatorIdList=new ArrayList<>();

        progressDialog = new ProgressDialog(EmiActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        fetchOperator();

        arrayAdapter=new ArrayAdapter( EmiActivity.this, android.R.layout.simple_list_item_1,operatorList);
        spinner.setAdapter( arrayAdapter );


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(infofetched){
                    tv_cusInfo.setVisibility(View.GONE);
                    cInfo.setVisibility(View.GONE);
                    cNoInfo.setVisibility(View.GONE);
                    btn_fetchInfo.setVisibility(View.VISIBLE);
                    proceed.setVisibility(View.GONE);
                    infofetched=false;
                }
                selected_operator = parent.getItemAtPosition(position).toString();
                sOperator = operatorIdList.get(position);

                Log.d("GasOperator", sOperator);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(infofetched)
                {
                    tv_cusInfo.setVisibility(View.GONE);
                    cInfo.setVisibility(View.GONE);
                    cNoInfo.setVisibility(View.GONE);
                    btn_fetchInfo.setVisibility(View.VISIBLE);
                    proceed.setVisibility(View.GONE);
                    infofetched=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        account_id.addTextChangedListener(textWatcher);

        btn_fetchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(account_id.getWindowToken(), 0);
                if(TextUtils.isEmpty(account_id.getText())){
                    Toast.makeText(EmiActivity.this, "Enter a valid Account id", Toast.LENGTH_SHORT).show();
                }
                else if(spinner.getSelectedItem()==null || selected_operator.equals("-select service provider")){
                    Toast.makeText(EmiActivity.this, "Select a Service provider", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    setcInfo();
                }

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem() == null)
                    Toast.makeText(EmiActivity.this, "Select service provider", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(account_id.getText().toString()))
                    Toast.makeText(EmiActivity.this, "Enter a valid Account number", Toast.LENGTH_SHORT).show();
                else {
                    String type = "EMI";
                    String service="41";
                    Log.d("ElecAmount2", amtBal + "");
                    Intent i = new Intent(EmiActivity.this, Electricity_makePaymentActivity.class);
                    i.putExtra("operator", sOperator);
                    Log.d("EleconClick: ", sOperator + "");
                    i.putExtra("amount", amtBal);
                    i.putExtra("mobileno", account_id.getText().toString());
                    i.putExtra("rType", type);
                    i.putExtra("due", duedate);
                    i.putExtra("uname", uname);
                    i.putExtra("ser", service);
                    startActivity(i);
                }
            }
        });


    }

    private void setcInfo() {
        infofetched=true;
        if(spinner.getSelectedItem() == null || selected_operator.equals("-select service provider")){
            Toast.makeText(EmiActivity.this, "Select a Service provider", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = "https://vitefintech.com/viteapi/home/billfetch";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        JSONObject obj = null;
                        try {
                            Log.d("ElecSetInfo: ", response);
                            obj = new JSONObject(response);
                            if(obj.getString("status").equals("false") || obj.getBoolean("status")==false){
                                TextView erMsg=findViewById(R.id.errorMsg);
                                erMsg.setText("*"+obj.getString("message"));
                                cNoInfo.setVisibility(View.VISIBLE);
                                tv_cusInfo.setVisibility(View.VISIBLE);
                            }
                            else {
                                TextView Name, Cell, amt, netAmt, due;
                                Name = findViewById(R.id.tv_cName);
                                Cell = findViewById(R.id.tv_cCell);
                                amt = findViewById(R.id.tv_cBillAmt);
                                netAmt = findViewById(R.id.tv_cNetBillAmt);
                                due = findViewById(R.id.tv_cDue);

                                duedate=obj.getString("duedate");

                                amtBal = obj.getJSONObject("bill_fetch").getString("billnetamount");
                                uname=obj.getJSONObject("bill_fetch").getString("userName");
                                Name.setText(uname);
                                Cell.setText(obj.getJSONObject("bill_fetch").getString("cellNumber"));
                                amt.setText(obj.getJSONObject("bill_fetch").getString("billAmount"));
                                netAmt.setText(amtBal);
                                due.setText(duedate);


                                Log.d("EMI"+"5", amtBal + "");

                                cInfo.setVisibility(View.VISIBLE);
                                cNoInfo.setVisibility(View.GONE);
                                tv_cusInfo.setVisibility(View.VISIBLE);
                                btn_fetchInfo.setVisibility(View.GONE);
                                proceed.setVisibility(View.VISIBLE);
                            }
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            Log.d("ElectSetInfoerror: ", e.getMessage());
                            progressDialog.dismiss();
                        }
                    },
                    error -> {
                        progressDialog.dismiss();
                        tv_cusInfo.setVisibility(View.VISIBLE);
                        TextView info=findViewById(R.id.errorMsg);
                        info.setText("*Enter a valid Service provider and account number");
                        cNoInfo.setVisibility(View.VISIBLE);
                        Log.d("ElectSetInfoerror2: ", error.getMessage() + "--");
                        Toast.makeText(this, "Error+:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }) {
                //Add parameters
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("canumber", account_id.getText().toString());
                    params.put("operator", sOperator);
                    params.put("mode", "online");
                    params.put("api_key","NMA001");
                    Log.d("Electparams", account_id.getText().toString() + "|" + sOperator);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void fetchOperator() {
        operatorList.add("-select service provider");
        operatorIdList.add("select");
        String url = "https://vitefintech.com/viteapi/home/billoperator";
        Log.d("GasSetop", "here-= ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("Gas_setoperator", response);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        Log.d("Gas_responseArray", jsonArray.toString());
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("Gas_list", "heree09");
                                Log.d("Gas_responseloop", jsonArray.getJSONObject(i).getString("category"));
                                if (jsonArray.getJSONObject(i).getString("category").equals("EMI") ) {
                                    operatorList.add(jsonArray.getJSONObject(i).getString("name"));
                                    operatorIdList.add(jsonArray.getJSONObject(i).getString("id"));
                                    Log.d("Gas_list", operatorList + "");
                                }

                            }
                        }
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(EmiActivity.this, android.R.layout.simple_spinner_dropdown_item, operatorList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("Gas_setOperatorError", error.getMessage());
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

