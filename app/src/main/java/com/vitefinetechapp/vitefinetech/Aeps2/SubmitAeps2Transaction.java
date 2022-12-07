package com.vitefinetechapp.vitefinetech.Aeps2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.MainActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class SubmitAeps2Transaction extends AppCompatActivity {

    TextView amount, mobileno, adharno, amttxt;
    Button previos, submit;
    String adharcard_numberget, mobilenoget, amountget, transaction_typeget, nameget, bank_codeget, access_tokenget,
            latitude, longitude, data, piddata, ipaddress,from;

    TextView local;
    Boolean pressed=false;

    @Override
    protected void onResume() {
        super.onResume();

        submit = (Button) findViewById(R.id.submit);
        submit.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_aeps2_transaction);

        amount = (TextView) findViewById(R.id.amount);
        mobileno = (TextView) findViewById(R.id.mobileno);
        adharno = (TextView) findViewById(R.id.adharno);
        amttxt = (TextView) findViewById(R.id.amttxt);
        local = (TextView) findViewById(R.id.local3);

        previos = (Button) findViewById(R.id.previous);
        submit = (Button) findViewById(R.id.submit);


        from=getIntent().getStringExtra("from");
        transaction_typeget = getIntent().getStringExtra("transaction_type");
        adharcard_numberget = getIntent().getStringExtra("adhar_no");
        mobilenoget = getIntent().getStringExtra("mobile_no");
        amountget = getIntent().getStringExtra("amount");
        bank_codeget = getIntent().getStringExtra("bank_code");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        piddata = getIntent().getStringExtra("pidData");



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
                    submit.setEnabled(false);
                int selected_positionBank = getIntent().getIntExtra("sBankPos", 0);
                int selected_positionType = getIntent().getIntExtra("sTypePos", 0);

                switch (transaction_typeget) {
                    case "CW": {

                        Intent i = new Intent(SubmitAeps2Transaction.this, TransactionReceiptAeps2.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.putExtra("transaction_type", transaction_typeget);
                        i.putExtra("from",from);
                        i.putExtra("adhar_no", adharcard_numberget);
                        i.putExtra("mobile_no", mobilenoget);
                        i.putExtra("amount", amountget);
                        i.putExtra("bank_code", bank_codeget);
                        i.putExtra("latitude", latitude);
                        i.putExtra("longitude", longitude);
                        i.putExtra("pidData", piddata);
                        i.putExtra("sBankPos", selected_positionBank);
                        i.putExtra("sTypePos", selected_positionType);
                        startActivity(i);
                        break;
                    }
                    case "BE": {

                        Intent i = new Intent(SubmitAeps2Transaction.this, TransactionReceiptAeps2.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.putExtra("from",from);
                        i.putExtra("transaction_type", transaction_typeget);
                        i.putExtra("adhar_no", adharcard_numberget);
                        i.putExtra("mobile_no", mobilenoget);
                        i.putExtra("bank_code", bank_codeget);
                        i.putExtra("latitude", latitude);
                        i.putExtra("amount", "0");
                        i.putExtra("longitude", longitude);
                        i.putExtra("pidData", piddata);
                        i.putExtra("sBankPos", selected_positionBank);
                        i.putExtra("sTypePos", selected_positionType);
                        startActivity(i);
                        break;
                    }
                    case "MS": {

                        Intent i = new Intent(SubmitAeps2Transaction.this, TransactionReceiptAeps2.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.putExtra("from",from);
                        i.putExtra("transaction_type", transaction_typeget);
                        i.putExtra("adhar_no", adharcard_numberget);
                        i.putExtra("mobile_no", mobilenoget);
                        i.putExtra("bank_code", bank_codeget);
                        i.putExtra("latitude", latitude);
                        i.putExtra("amount", "0");
                        i.putExtra("longitude", longitude);
                        i.putExtra("pidData", piddata);
                        i.putExtra("sBankPos", selected_positionBank);
                        i.putExtra("sTypePos", selected_positionType);
                        startActivity(i);
                        break;
                    }
                    case "M": {

                        Intent i = new Intent(SubmitAeps2Transaction.this, TransactionReceiptAeps2.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i.putExtra("transaction_type", transaction_typeget);
                        i.putExtra("from",from);
                        i.putExtra("adhar_no", adharcard_numberget);
                        i.putExtra("mobile_no", mobilenoget);
                        i.putExtra("amount", amountget);
                        i.putExtra("bank_code", bank_codeget);
                        i.putExtra("latitude", latitude);
                        i.putExtra("longitude", longitude);
                        i.putExtra("pidData", piddata);
                        i.putExtra("sBankPos", selected_positionBank);
                        i.putExtra("sTypePos", selected_positionType);
                        startActivity(i);
                        break;
                    }
                }

            }
        });
    }
}








