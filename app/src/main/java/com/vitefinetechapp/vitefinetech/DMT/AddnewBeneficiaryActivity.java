package com.vitefinetechapp.vitefinetech.DMT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddnewBeneficiaryActivity extends AppCompatActivity {

    TextInputEditText bName,bAcc,bIfsc,bMob,bBank;
    ImageButton backButton;
    Button btnSave,btnNext;
    private ProgressDialog progressDialog;
    LinearLayout msgLlayout;
    String cust_mob="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_beneficiary);

        bName=findViewById(R.id.input_edt_beneficiaryname);
        bMob=findViewById(R.id.input_edt_beneficiarymobile);
        bAcc=findViewById(R.id.input_edt_beneficiaryaccountno);
        bIfsc=findViewById(R.id.input_edt_beneficiaryifsc);
        bBank=findViewById(R.id.input_edt_bankname);
        backButton=findViewById(R.id.backbutton);
        btnSave=findViewById(R.id.btnSave);
        btnNext=findViewById(R.id.btnNext);
        btnNext.setVisibility(View.GONE);
        msgLlayout=findViewById(R.id.msgLayout);
        msgLlayout.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddnewBeneficiaryActivity.this,BenefiaciaryListActivity.class);
                startActivity(i);
                finish();
            }
        });

//        cust_mob=
        Intent intent = getIntent();
        cust_mob = intent.getStringExtra("cust_mobile");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(bName.getText())){
                    Toast.makeText(AddnewBeneficiaryActivity.this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(bMob.getText())){
                    Toast.makeText(AddnewBeneficiaryActivity.this, "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(bAcc.getText())){
                    Toast.makeText(AddnewBeneficiaryActivity.this, "Enter a valid account number", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(bIfsc.getText())){
                    Toast.makeText(AddnewBeneficiaryActivity.this, "Enter a valid Ifsc number", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(bBank.getText())){
                    Toast.makeText(AddnewBeneficiaryActivity.this, "Enter a valid Bank name", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    addBeneficiaryActivity();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddnewBeneficiaryActivity.this,DMTScreenActivity2.class);
                startActivity(i);
            }
        });


    }

    private void addBeneficiaryActivity() {

        String url = "https://nextmoney.co.in/nextmoney/dmt/addBeneficiary";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Log.d("dmtABENres", ":"+response);
//                    Intent i = new Intent(this, TopUPActivity.class);
//                    i.putExtra("response", response);
//                    startActivity(i);
                    try {
                        JSONObject obj=new JSONObject(response);
                        TextView msg=findViewById(R.id.msg);

                        if(obj.getBoolean("status")==true){
                            msgLlayout.setVisibility(View.VISIBLE);
                            msg.setText(obj.getString("msg"));
                            btnSave.setVisibility(View.GONE);
                            btnNext.setVisibility(View.VISIBLE);
                        }
                        else{
                            msgLlayout.setVisibility(View.VISIBLE);
                            msg.setText(obj.getString("msg"));
                        }
                    } catch (JSONException e) {
                        Log.d("dmtABENerr1", ":"+e.getMessage());
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Log.d("dmtABENerr2", ":"+error.getMessage());
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("mobile", cust_mob);
                params.put("beneficiary_name", bName.getText().toString());
                params.put("beneficiary_account", bAcc.getText().toString());
                params.put("beneficiary_mobile", bMob.getText().toString());
                params.put("beneficiary_ifsc_code", bIfsc.getText().toString());
                params.put("beneficiary_bank", bBank.getText().toString());

                Log.d("dmtABENpar", ":"+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}