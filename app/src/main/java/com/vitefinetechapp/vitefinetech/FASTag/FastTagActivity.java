package com.vitefinetechapp.vitefinetech.FASTag;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.MantraSDk.RDServiceInfo;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.utils.AppStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FastTagActivity extends AppCompatActivity {


    String fastTag_bank_string []={"Select Bank", "Axis Bank",
            "Bank of Baroda",
            "Bhagyanagar Gas Limited",
            "HDFC Bank","IDFC First Bank",
            "Indian Highways Management Company",
            "IndusInd Bank",
            "Kotak Mahindra Bank"
    };

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;


    ArrayList<String> list;
    Spinner spinner_fast_tag;
    ImageButton backbutton;
    LinearLayout linear_confirm_ft;
    LinearLayout linearLayoutPay_ft;
    Button btn_fasttag_confirm;
    EditText editTextVehicalNof;
    String  URL;
    TextView textView_local;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_tag);

        spinner_fast_tag = findViewById(R.id.fasttag_spinner);



        backbutton   = (ImageButton) findViewById(R.id.backbuttonf);
        spinner_fast_tag = findViewById(R.id.fasttag_spinner);

        linear_confirm_ft = findViewById(R.id.linear_confirmft);
        linearLayoutPay_ft = findViewById(R.id.linearLayoutPayft);
        btn_fasttag_confirm = findViewById(R.id.btn_fasttag_confirm);
        editTextVehicalNof = findViewById(R.id.editTextVehicalNof);
        textView_local = findViewById(R.id.txtQty1);

        //  progressBar_confirm.setVisibility(View.GONE);
        linear_confirm_ft.setVisibility(View.GONE);
        linearLayoutPay_ft.setVisibility(View.GONE);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getbiometricdevice();

        btn_fasttag_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  consumer_no = editTextVehicalNof.getText().toString();
                Log.e("FastTag Activity ","consumer_no=="+consumer_no);

                String bank_name_sp =   spinner_fast_tag.getSelectedItem().toString();
                Log.e("FastTag Activity ","operator_name_sp=="+bank_name_sp);


                if(editTextVehicalNof.toString().isEmpty()){
                    editTextVehicalNof.setError("account no not entered");
                    editTextVehicalNof.requestFocus();
                } else if(bank_name_sp.toString().isEmpty()){

                }else {
                    if (spinner_fast_tag.getSelectedItem().toString().equals("Axis Bank")) {
                        String values = "AXIF";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("Bank of Baroda")) {
                        String values = "BOBF";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("Bhagyanagar Gas Limited")) {
                        String values = "DDAW";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("HDFC Bank")) {
                        String values = "DLBW";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("IDFC First Bank")) {
                        String values = "IDFF";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("Indian Highways Management Company")) {
                        String values = "IHMF";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("IndusInd Bank")) {
                        String values = "IIBF";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    } else if (spinner_fast_tag.getSelectedItem().toString().equals("Kotak Mahindra Bank")) {
                        String values = "KMBF";
                        URL = "https://vitefintech.com/viteapi/recharge/fetch_bill?account=" + editTextVehicalNof.getText().toString() + "&operator=" + values;
                        textView_local.setText(URL);
                    }


                    retrivedata();

                }
            }
        });

    }

    private void retrivedata() {

        progressBar = new ProgressDialog(FastTagActivity.this);
        progressBar.setCancelable(true);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        Log.d("local",textView_local.getText().toString());
        StringRequest eventoReq = new StringRequest(Request.Method.GET, textView_local.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REsponce", response.toString());
                        try {
                            // progressBar_confirm.setVisibility(View.GONE);

                            progressBar.dismiss();
                            JSONObject obj = new JSONObject(response);
                            String operator = obj.getString("operator");
                            Log.e("FastTagActivity","operator=="+operator);
                            String dueAmount = obj.getString("dueAmount");
                            Log.e("FastTagActivity","operator=="+operator);

                            String dueDate = obj.getString("dueDate");
                            Log.e("FastTagActivity","operator=="+operator);

                            String customerName = obj.getString("customerName");
                            Log.e("FastTagActivity","operator=="+operator);

                            String billNumber = obj.getString("billNumber");
                            Log.e("FastTagActivity","operator=="+operator);

                            String billDate = obj.getString("billDate");
                            Log.e("FastTagActivity","operator=="+operator);

                            String billPeriod = obj.getString("billPeriod");
                            Log.e("FastTagActivity","operator=="+operator);

                            String refId = obj.getString("refId");
                            Log.e("FastTagActivity","operator=="+operator);

                            String status = obj.getString("status");
                            Log.e("FastTagActivity","operator=="+operator);

                            String message = obj.getString("message");
                            Log.e("FastTagActivity","operator=="+operator);


                            Log.d("operator", operator);

                            if(status.equals("SUCCESS")){
                                // progressBar.dismiss();
                                btn_fasttag_confirm.setVisibility(View.GONE);
                                linearLayoutPay_ft.setVisibility(View.VISIBLE);
                                textView_local.setText(operator+System.lineSeparator()+"dueAmount:"+dueAmount+System.lineSeparator()
                                        +"dueDate: "+dueDate+System.lineSeparator()+"customerName:" +customerName+System.lineSeparator()+
                                        "billNumber:" +billNumber+System.lineSeparator()+ "billDate:" +billDate+System.lineSeparator()+"billPeriod:"+billPeriod+
                                        System.lineSeparator()+"refId:"+refId);

                            }else{
                                // progressBar.dismiss();

                                Toast.makeText(FastTagActivity.this,"Please check your Vehical No." , Toast.LENGTH_LONG).show();

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:" + error.toString());
            }
        });
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(eventoReq);
        }

    }

    private void getbiometricdevice() {

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(FastTagActivity.this, android.R.layout.simple_spinner_dropdown_item, fastTag_bank_string);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_fast_tag.setAdapter(dataAdapter);

        spinner_fast_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String seleted_device = parent.getItemAtPosition(position).toString();

                Log.d(seleted_device,seleted_device);

                RDServiceInfo rdServiceInfo = new RDServiceInfo();
                String status =  rdServiceInfo.status = "";
                Log.d("status",status);

                if(parent.getItemAtPosition(position).toString().equals("Select Bank"))
                {

                    Log.e("if",""+seleted_device.toString());
                }else {

                    Log.e("else",""+seleted_device.toString());

                    parent.getItemAtPosition(position);

                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {


                        progressBar = new ProgressDialog(view.getContext());
                        progressBar.setCancelable(true);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show();
                        progressBarStatus = 0;

                        fileSize = 0;
                        new Thread(new Runnable() {
                            public void run() {
                                while (progressBarStatus < 100) {
                                    progressBarStatus = downloadFile();

                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    progressBarbHandler.post(new Runnable() {
                                        public void run() {
                                            progressBar.setProgress(progressBarStatus);
                                        }
                                    });
                                }

                                if (progressBarStatus >= 100) {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    progressBar.dismiss();
                                    linear_confirm_ft.getHandler().post(new Runnable() {
                                        public void run() {
                                            linear_confirm_ft.setVisibility(View.VISIBLE);
                                            // linearLayoutPay.setVisibility(View.VISIBLE);

                                        }
                                    });

                                }
                            }
                        }).start();
                    }


                    else {

                        Toast.makeText(FastTagActivity.this,"Inetrnet is Unavailable!!!!",Toast.LENGTH_LONG).show();
                        Log.v("Home", "############################You are not online!!!!");

                    }



                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private int downloadFile() {

        while (fileSize <= 1000000) {
            fileSize++;

            if (fileSize == 100000) {
                return 10;
            }else if (fileSize == 200000) {
                return 20;
            }else if (fileSize == 300000) {
                return 30;
            }else if (fileSize == 400000) {
                return 40;
            }else if (fileSize == 500000) {
                return 50;
            }else if (fileSize == 700000) {
                return 70;
            }else if (fileSize == 800000) {
                return 80;
            }
        }
        return 100;

    }

}
