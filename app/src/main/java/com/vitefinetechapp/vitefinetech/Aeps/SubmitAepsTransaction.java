package com.vitefinetechapp.vitefinetech.Aeps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.vitefinetechapp.vitefinetech.R;



public class SubmitAepsTransaction extends AppCompatActivity {

    TextView name,amount,mobileno,adharno,amttxt;
    Button previos,submit;
    String adharcard_numberget, mobilenoget, amountget, transaction_typeget, nameget, bank_codeget,access_tokenget,
            latitude,longitude,data,piddata;

    TextView local;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_aeps_transaction);


        amount             = (TextView)findViewById(R.id.amount);
        mobileno           = (TextView)findViewById(R.id.mobileno);
        adharno            = (TextView)findViewById(R.id.adharno);
        amttxt             = (TextView)findViewById(R.id.amttxt);


        previos            = (Button) findViewById(R.id.previous);
        submit             = (Button) findViewById(R.id.submit);


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


        mobileno.setText(mobilenoget);
        adharno.setText(adharcard_numberget);

        if (transaction_typeget.equals("BE")) {
            amount.setVisibility(View.GONE);
            amttxt.setVisibility(View.GONE);
        } else if(transaction_typeget.equals("MS")){
            amount.setVisibility(View.GONE);
            amttxt.setVisibility(View.GONE);
        }else {
            amount.setVisibility(View.VISIBLE);
            amttxt.setVisibility(View.VISIBLE);
            amount.setText(amountget);
        }


        previos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (transaction_typeget) {
                            case "CW": {
                                Intent i = new Intent(SubmitAepsTransaction.this,SubmitAepsTransaction.class);
                                i.putExtra("transaction_type", transaction_typeget);
                                i.putExtra("adhar_no", adharcard_numberget);
                                i.putExtra("mobile_no", mobilenoget);
                                i.putExtra("amount", amountget);
                                i.putExtra("bank_code", bank_codeget);
                                i.putExtra("access_token", access_tokenget);
                                i.putExtra("latitude", latitude);
                                i.putExtra("longitude", longitude);
                                i.putExtra("data", data); //response.tostring
                                i.putExtra("pidData", piddata);  //piddata
                                startActivity(i);
                                break;
                            }
                            case "BE": {
                                Intent i = new Intent(SubmitAepsTransaction.this,SubmitAepsTransaction.class);
                                i.putExtra("transaction_type", transaction_typeget);
                                i.putExtra("adhar_no", adharcard_numberget);
                                i.putExtra("mobile_no", mobilenoget);
                                i.putExtra("amount", "0");
                                i.putExtra("bank_code", bank_codeget);
                                i.putExtra("access_token", access_tokenget);
                                i.putExtra("latitude", latitude);
                                i.putExtra("longitude", longitude);
                                i.putExtra("data", data); //response.tostring
                                i.putExtra("pidData", piddata);  //piddata
                                startActivity(i);
                                break;
                            }
                            case "MS": {
                                Intent i = new Intent(SubmitAepsTransaction.this,SubmitAepsTransaction.class);
                                i.putExtra("transaction_type", transaction_typeget);
                                i.putExtra("adhar_no", adharcard_numberget);
                                i.putExtra("mobile_no", mobilenoget);
                                i.putExtra("amount", "0");
                                i.putExtra("bank_code", bank_codeget);
                                i.putExtra("access_token", access_tokenget);
                                i.putExtra("latitude", latitude);
                                i.putExtra("longitude", longitude);
                                i.putExtra("data", data); //response.tostring
                                i.putExtra("pidData", piddata);  //piddata
                                startActivity(i);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }
}