package com.vitefinetechapp.vitefinetech.Aeps2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.paysprint.onboardinglib.activities.HostActivity;
import com.vitefinetechapp.vitefinetech.Aeps.BankActivity;
import com.vitefinetechapp.vitefinetech.Aeps.BankActivity2;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.MainAeps2Activity;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.History.HistoryAdapter;
import com.vitefinetechapp.vitefinetech.History.HistoryModel;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class TransactionReceiptAeps2 extends AppCompatActivity {
    String adharcard_numberget, mobilenoget, amountget, transaction_typeget,bank_codeget, access_tokenget,
            latitude, longitude, data, piddata, ipaddress;

//    public static final String API_BALANCE_ENQUIRY    =  "https://vitefintech.com/viteapi/pay/aepsbalanceenquiry";
//    public static final String API_WITHDRWAL          =  "https://vitefintech.com/viteapi/pay/apescashwithdraw";
//    public static final String API_MINISTATEMENT      =  "https://vitefintech.com/viteapi/pay/aepsmini";

//    public static final String NEW_URL    =  "https://emopay.co.in/rbwish/pay/submitTransection";

    public static final String NEW_URL    =  "https://nextmoney.co.in/nextmoney/pay/submitTransection";
    String stan_no;
    String from;
    private ProgressDialog progressDialog;

    TextView txn_type,date,time,amount,adhrano,txnid,rrn_no,status,retail_name,transaction_details,balance_amt,amttxt;
    Dialog dialog;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String submerchantid;
    LinearLayout lineartrabill;
    Button done_button,cashBtn;

    private RecyclerView recycler_view;
    private ArrayList<Ministatement> ministatementMODELArraylist;
    TransactionReceiptAdapter transactionReceiptAdapter;


    private long backPressedTime;
    String pid,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_receipt_aeps2);

        txn_type                = (TextView) findViewById(R.id.txn_type);
        date                    = (TextView) findViewById(R.id.date);
        time                    = (TextView) findViewById(R.id.time);
        amount                  = (TextView) findViewById(R.id.amount);
        adhrano                 = (TextView) findViewById(R.id.adhrano);
        txnid                   = (TextView) findViewById(R.id.txnid);
        rrn_no                  = (TextView) findViewById(R.id.rrn_no);
        status                  = (TextView) findViewById(R.id.status);
        balance_amt             = (TextView) findViewById(R.id.balanc_amt);
        amttxt                  = (TextView) findViewById(R.id.amttxt);
        transaction_details     = (TextView) findViewById(R.id.transaction_details);
        retail_name             = (TextView) findViewById(R.id.retail_name);
//        locas                   = (EditText) findViewById(R.id.locas);
        lineartrabill           = (LinearLayout) findViewById(R.id.lineartrabill);
        done_button             = (Button) findViewById(R.id.done_button);
        cashBtn=findViewById(R.id.cashBtn);
        cashBtn.setVisibility(View.GONE);
        from=getIntent().getStringExtra("from");

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        submerchantid = sharedPreferences.getString("member_id", null);
        user_id = sharedPreferences.getString("user_id", null);


        progressDialog = new ProgressDialog(TransactionReceiptAeps2.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        lineartrabill.setVisibility(View.GONE);
        done_button.setVisibility(View.GONE);

        transaction_typeget = getIntent().getStringExtra("transaction_type");
        adharcard_numberget = getIntent().getStringExtra("adhar_no");
        mobilenoget = getIntent().getStringExtra("mobile_no");
        amountget = getIntent().getStringExtra("amount");
        bank_codeget = getIntent().getStringExtra("bank_code");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        piddata = getIntent().getStringExtra("pidData");
        Log.d("piddata",piddata);

        stan();

        recycler_view = findViewById(R.id.recycler_view);
        ministatementMODELArraylist = new ArrayList<>();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));


        //TODO: complete cashBTN
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selected_positionBank=getIntent().getIntExtra("sBankPos",0);
                int selected_positionType=getIntent().getIntExtra("sTypePos",0);

//                                        Toast.makeText(TransactionReceiptAeps2.this, "Recipt: sBankPos:"+selected_positionBank +
//                                                "tType:"+selected_positionType+
//                                                "add1:"+adharcard_numberget.substring(0,4)+
//                                                "add2:"+adharcard_numberget.substring(4,8)+
//                                                "add3:"+adharcard_numberget.substring(8,12)+
//                                                "mob:"+mobilenoget, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(TransactionReceiptAeps2.this, MainAeps2Activity.class);
                i.putExtra("from",from);
                i.putExtra("callFrom","reciptCW");
                i.putExtra("mobno",mobilenoget);
                i.putExtra("adharno1",adharcard_numberget.substring(0,4));
                i.putExtra("adharno2",adharcard_numberget.substring(4,8));
                i.putExtra("adharno3",adharcard_numberget.substring(8,12));
                i.putExtra("sBankPos",selected_positionBank);
                i.putExtra("sTypePos",selected_positionType);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        if(transaction_typeget.toString().equals("BE")){
            SendBiometricBalncEnquiry();
            recycler_view.setVisibility(View.GONE);
            transaction_details.setVisibility(View.GONE);
        }else if(transaction_typeget.toString().equals("CW") || transaction_typeget.toString().equals("M")){
            SendBiometriCashWithdrwal();
            recycler_view.setVisibility(View.GONE);
            transaction_details.setVisibility(View.GONE);
        }else if(transaction_typeget.toString().equals("MS")){
            SendBiometricMiniStatement();
            recycler_view.setVisibility(View.VISIBLE);
            transaction_details.setVisibility(View.VISIBLE);
        }

        String adharno = adharcard_numberget;
        String last = adharno.substring(adharno.length() - 4);
        Log.d("last", String.valueOf(last));

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TransactionReceiptAeps2.this, DashboardActivity.class);
                startActivity(i);
            }
        });

    }

    public void stan() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int day_of_year = calendar.get(Calendar.DAY_OF_YEAR);
        int daycount = day_of_year;

        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        String today = hour + "" + minute + ""+second;

        Random r = new Random();
        int random_no = r.nextInt(999999 - 100000) + 100000;

        stan_no = year + "" + daycount + "" + today;
        Log.d("stan_no", String.valueOf(stan_no));
    }


    public void SendBiometricBalncEnquiry() {
        StringRequest jsonObjectRequest1 = new StringRequest(Request.Method.POST, NEW_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("ResponseBioDevice1", response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);
                            String message       =    obj.getString("msg");
                            Boolean sts=obj.getBoolean("status");

                            if(sts==false){
                                Toast.makeText(TransactionReceiptAeps2.this, message, Toast.LENGTH_SHORT).show();
                                lineartrabill.setVisibility(View.GONE);
                                dialog = new Dialog(TransactionReceiptAeps2.this);
                                dialog.setContentView(R.layout.aeps2transactionbill);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCanceledOnTouchOutside(false);

                                TextView transaction_status = dialog.findViewById(R.id.transactionfail);
                                TextView transaction_msg    = dialog.findViewById(R.id.error_discription);
                                Button ok                   = dialog.findViewById(R.id.ok_button);

                                transaction_status.setText("Unable to fetch bank balance");
                                transaction_msg.setText(message);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        int selected_positionBank=getIntent().getIntExtra("sBankPos",0);
                                        int selected_positionType=getIntent().getIntExtra("sTypePos",0);

                                                Intent i = new Intent(TransactionReceiptAeps2.this, MainAeps2Activity.class);
                                        i.putExtra("from",from);
                                                i.putExtra("callFrom","recipt");
                                                i.putExtra("mobno",mobilenoget);
                                                i.putExtra("adharno1",adharcard_numberget.substring(0,4));
                                                i.putExtra("adharno2",adharcard_numberget.substring(4,8));
                                                i.putExtra("adharno3",adharcard_numberget.substring(8,12));
                                                i.putExtra("sBankPos",selected_positionBank);
                                                i.putExtra("sTypePos",selected_positionType);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);



                                    }
                                });
                                dialog.show();
                            }
                            else{

                                String s= obj.getString("data");
                                s=s.replace("\\n","");
                                s=s.replace("\\","");
                                JSONObject obj1=new JSONObject(s);
                                    lineartrabill.setVisibility(View.VISIBLE);

                                    done_button.setVisibility(View.VISIBLE);
                                    String statuss          =    obj1.getString("status");
                                    String clientrefno      =    obj1.getString("clientrefno");
                                    String balanceamount    =    obj1.getString("balanceamount");
                                    String bankrrn          =    obj1.getString("bankrrn");
                                    String name             =    obj1.getString("name");
                                    String last_aadhar      =    obj1.getString("last_aadhar");

                                    Calendar calendar    =    Calendar.getInstance();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    Date today = new Date();
                                    String currentdate = simpleDateFormat.format(today);

                                    Calendar now = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                                    int hour = now.get(Calendar.HOUR_OF_DAY);
                                    int minute = now.get(Calendar.MINUTE);

                                    amount.setVisibility(View.GONE);
                                    amttxt.setVisibility(View.GONE);
                                    cashBtn.setVisibility(View.VISIBLE);

                                    txn_type.setText("BE");
                                    date.setText(currentdate);
                                    time.setText(hour+ ":" +minute);
                                    balance_amt.setText(getResources().getString(R.string.us)+" "+balanceamount);
                                    adhrano.setText(last_aadhar);
                                    txnid.setText(clientrefno);
                                    rrn_no.setText(bankrrn);
                                    status.setText(statuss);
                                    retail_name.setText(name);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(TransactionReceiptAeps2.this, "error:"+error.toString(), Toast.LENGTH_SHORT).show();
                        VolleyLog.d("Error:" + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                int number2=rnd.nextInt(9999999);
                String ref=number+""+number2;

                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                params.put("mobilenumber",mobilenoget);
                params.put("referenceno",ref);
                params.put("adhaarnumber",adharcard_numberget);
                params.put("accessmodetype","SITE");
                params.put("ipaddress","148.66.132.29");
                params.put("bankCode",bank_codeget);
                params.put("pidData",piddata);
                params.put("member_id",submerchantid);
                params.put("api_key","NMA001");
                params.put("transcationtype","BE");
                params.put("amount","0");
                params.put("user_id",user_id);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest1.setShouldCache(false);
        jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest1);


    }

    public void SendBiometriCashWithdrwal() {
        StringRequest jsonObjectRequest2 = new StringRequest(Request.Method.POST, NEW_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("ResponseBioDevice2", response.toString());

                        try {
                            JSONObject obj = new JSONObject(response);
                            String message       =    obj.getString("msg");
                            Boolean sts=obj.getBoolean("status");

                            if(sts==false) {
                                lineartrabill.setVisibility(View.GONE);
                                dialog = new Dialog(TransactionReceiptAeps2.this);
                                dialog.setContentView(R.layout.aeps2transactionbill);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCanceledOnTouchOutside(false);

                                TextView transaction_status = dialog.findViewById(R.id.transactionfail);
                                TextView transaction_msg    = dialog.findViewById(R.id.error_discription);
                                Button ok                   = dialog.findViewById(R.id.ok_button);

                                transaction_status.setText("Transaction failed");
                                transaction_msg.setText(message);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(TransactionReceiptAeps2.this, MainAeps2Activity.class);
                                        int selected_positionBank=getIntent().getIntExtra("sBankPos",0);
                                        int selected_positionType=getIntent().getIntExtra("sTypePos",0);
                                        i.putExtra("from",from);
                                        i.putExtra("callFrom","recipt");
                                        i.putExtra("mobno",mobilenoget);
                                        i.putExtra("adharno1",adharcard_numberget.substring(0,4));
                                        i.putExtra("adharno2",adharcard_numberget.substring(4,8));
                                        i.putExtra("adharno3",adharcard_numberget.substring(8,12));
                                        i.putExtra("sBankPos",selected_positionBank);
                                        i.putExtra("sTypePos",selected_positionType);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                });
                                dialog.show();
                            }else{
                                String s= obj.getString("data");
//                                Toast.makeText(TransactionReceiptAeps2.this, "s:"+s, Toast.LENGTH_SHORT).show();
                                s=s.replace("\\n","");
                                s=s.replace("\\","");
                                JSONObject obj1=new JSONObject(s);

                                lineartrabill.setVisibility(View.VISIBLE);
                                done_button.setVisibility(View.VISIBLE);
                                String statuss          =  obj1.getString("status");
                                String clientrefno      =  obj1.getString("clientrefno");
                                String balanceamount    =  obj1.getString("balanceamount");
                                String bankrrn          =  obj1.getString("bankrrn");
                                String name             =  obj1.getString("name");
                                String last_aadhar      =  obj1.getString("last_aadhar");
                                String amounts          =  obj1.getString("amount");

                                Calendar calendar    =    Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date today = new Date();
                                String currentdate = simpleDateFormat.format(today);

                                Calendar now = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                                int hour = now.get(Calendar.HOUR_OF_DAY);
                                int minute = now.get(Calendar.MINUTE);

                                amount.setVisibility(View.VISIBLE);
                                amttxt.setVisibility(View.VISIBLE);


                                txn_type.setText("CW");
                                date.setText(currentdate);
                                time.setText(hour+ ":" +minute);
                                amount.setText(getResources().getString(R.string.us)+" "+amounts);
                                balance_amt.setText(getResources().getString(R.string.us)+" "+balanceamount);
                                adhrano.setText(last_aadhar);
                                txnid.setText(clientrefno);
                                rrn_no.setText(bankrrn);
                                status.setText(statuss);
                                retail_name.setText(name);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(TransactionReceiptAeps2.this, "error:"+error.toString(), Toast.LENGTH_SHORT).show();
                        VolleyLog.d("Error:" + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                int number2=rnd.nextInt(9999999);
                String ref=number+""+number2;

                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                params.put("mobilenumber",mobilenoget);
                params.put("referenceno", ref);
                params.put("adhaarnumber",adharcard_numberget);
                params.put("accessmodetype","SITE");
                params.put("ipaddress","148.66.132.29");
                params.put("bankCode",bank_codeget);
                params.put("pidData",piddata);
                params.put("member_id",submerchantid);
                params.put("api_key","NMA001");
                params.put("transcationtype",transaction_typeget);
                params.put("amount",amountget);
                params.put("user_id",user_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest2.setShouldCache(false);
        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest2);


    }


    public void SendBiometricMiniStatement() {
        StringRequest jsonObjectRequest3 = new StringRequest(Request.Method.POST, NEW_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("ResponseBioDevice3", response.toString());
                        Toast.makeText(TransactionReceiptAeps2.this, "res:"+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            String message       =    obj.getString("msg");
                            Boolean sts=obj.getBoolean("status");

                            if(sts==false) {
                                lineartrabill.setVisibility(View.GONE);
                                dialog = new Dialog(TransactionReceiptAeps2.this);
                                dialog.setContentView(R.layout.aeps2transactionbill);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCanceledOnTouchOutside(false);

                                TextView transaction_status = dialog.findViewById(R.id.transactionfail);
                                TextView transaction_msg    = dialog.findViewById(R.id.error_discription);
                                Button ok                   = dialog.findViewById(R.id.ok_button);

                                transaction_status.setText("Failed");
                                transaction_msg.setText(message);
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(TransactionReceiptAeps2.this, MainAeps2Activity.class);
                                        int selected_positionBank=getIntent().getIntExtra("sBankPos",0);
                                        int selected_positionType=getIntent().getIntExtra("sTypePos",0);
                                        i.putExtra("from",from);
                                        i.putExtra("callFrom","recipt");
                                        i.putExtra("mobno",mobilenoget);
                                        i.putExtra("adharno1",adharcard_numberget.substring(0,4));
                                        i.putExtra("adharno2",adharcard_numberget.substring(4,8));
                                        i.putExtra("adharno3",adharcard_numberget.substring(8,12));
                                        i.putExtra("sBankPos",selected_positionBank);
                                        i.putExtra("sTypePos",selected_positionType);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                });
                                dialog.show();
                            }else {

                                String s= obj.getString("data");
//                                Toast.makeText(TransactionReceiptAeps2.this, "s:"+s, Toast.LENGTH_SHORT).show();
                                s=s.replace("\\n","");
                                s=s.replace("\\","");
                                JSONObject obj1=new JSONObject(s);

                                lineartrabill.setVisibility(View.VISIBLE);
                                done_button.setVisibility(View.VISIBLE);

                                String statuss       =    obj1.getString("status");
                                String clientrefno   =    obj1.getString("clientrefno");
                                String balanceamount =    obj1.getString("balanceamount");
                                String bankrrn       =    obj1.getString("bankrrn");
                                String name          =    obj1.getString("name");
                                String last_aadhar   =    obj1.getString("last_aadhar");

                                Calendar calendar    =    Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date today = new Date();
                                String currentdate = simpleDateFormat.format(today);

                                Calendar now = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                                int hour = now.get(Calendar.HOUR_OF_DAY);
                                int minute = now.get(Calendar.MINUTE);

                                amount.setVisibility(View.GONE);
                                amttxt.setVisibility(View.GONE);
                                cashBtn.setVisibility(View.VISIBLE);

                                txn_type.setText("MS");
                                date.setText(currentdate);
                                time.setText(hour+ ":" +minute);
                                balance_amt.setText(getResources().getString(R.string.us)+" "+ balanceamount);
                                adhrano.setText(last_aadhar);
                                txnid.setText(clientrefno);
                                rrn_no.setText(bankrrn);
                                status.setText(statuss);
                                retail_name.setText(name);

                                JSONArray jsonArray= obj.getJSONArray("ministatement");
                                for(int i= 0; i< jsonArray.length();i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    String date          =   data.getString("date");
                                    String txnType       =   data.getString("txnType");
                                    String narration     =   data.getString("narration");
                                    String amount        =   data.getString("amount");

                                    Ministatement ministatement = new Ministatement();
                                    ministatement.setDate(date);
                                    ministatement.setNarriation(narration);
                                    ministatement.setAmount(getResources().getString(R.string.us)+" "+amount);
                                    ministatement.setTxntype(txnType);

                                    ministatementMODELArraylist.add(ministatement);
                                }
                                transactionReceiptAdapter = new TransactionReceiptAdapter(TransactionReceiptAeps2.this, ministatementMODELArraylist);
                                recycler_view.setAdapter(transactionReceiptAdapter);
                                transactionReceiptAdapter.notifyDataSetChanged();

                                done_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(TransactionReceiptAeps2.this, DashboardActivity.class);
                                        startActivity(i);
                                    }
                                });
                            }
//                            else {
//                                lineartrabill.setVisibility(View.GONE);
//                                dialog = new Dialog(TransactionReceiptAeps2.this);
//                                dialog.setContentView(R.layout.aeps2transactionbill);
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                                TextView transaction_status  = dialog.findViewById(R.id.transactionfail);
//                                TextView transaction_msg     = dialog.findViewById(R.id.error_discription);
//                                Button ok                    = dialog.findViewById(R.id.ok_button);
//
//                                transaction_status.setText("Failed");
//                                transaction_msg.setText(message);
//                                ok.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent i = new Intent(TransactionReceiptAeps2.this, DashboardActivity.class);
//                                        startActivity(i);
//                                    }
//                                });
//                                dialog.show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(TransactionReceiptAeps2.this, "error:"+error.toString(), Toast.LENGTH_SHORT).show();
                        VolleyLog.d("Error:" + error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                int number2=rnd.nextInt(9999999);
                String ref=number+""+number2;

                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                params.put("mobilenumber",mobilenoget);
                params.put("referenceno", ref);
                params.put("adhaarnumber",adharcard_numberget);
                params.put("accessmodetype","SITE");
                params.put("ipaddress","148.66.132.29");
                params.put("bankCode",bank_codeget);
                params.put("pidData",piddata);
                params.put("member_id",submerchantid);
                params.put("api_key","NMA001");
                params.put("transcationtype","MS");
                params.put("amount","0");
                params.put("user_id",user_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest3.setShouldCache(false);
        jsonObjectRequest3.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest3);



    }



    public void OpenOnboardingPopup() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.onboarding_popup);
        dialog.setTitle("Merchant Onboarding");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText name      =    dialog.findViewById(R.id.input_edt_name);
        TextInputEditText email     =    dialog.findViewById(R.id.input_edt_email);
        TextInputEditText merchant  =    dialog.findViewById(R.id.input_edt_marchant);
        TextInputEditText mobileno  =    dialog.findViewById(R.id.input_edt_mobile);
        Button submit               =    dialog.findViewById(R.id.submit);
        TextView msg                =    dialog.findViewById(R.id.msg);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String message = sharedPreferences.getString("message", null);
        msg.setText(message);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() && email.getText().toString().isEmpty() &&
                        merchant.getText().toString().isEmpty() && mobileno.getText().toString().isEmpty()) {

                } else {
                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, "https://vitefintech.com/viteapi/pay/onbording",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("onboardingResponse:", response.toString());
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        int response_code   = obj.getInt("response_code");
                                        Boolean status      = obj.getBoolean("status");
                                        Log.d("statusssss", String.valueOf(status));
                                        if (status.equals(true)) {
                                            String redirecturl = obj.getString("redirecturl");
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(redirecturl));
                                            startActivity(i);
                                        } else if (status.equals(false)) {
                                            String message = obj.getString("message");
                                            Log.d("message", message);
                                            Toast.makeText(TransactionReceiptAeps2.this, message, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d("Error:" + error.toString());
                                }
                            }) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name.getText().toString());
                            params.put("email", email.getText().toString());
                            params.put("mobile", mobileno.getText().toString());
                            params.put("merchant", merchant.getText().toString());

                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(TransactionReceiptAeps2.this);
                    jsonObjectRequest.setShouldCache(false);
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(jsonObjectRequest);


                }
            }
        });
        dialog.show();
    }



    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
        }
        backPressedTime = System.currentTimeMillis();
    }
}