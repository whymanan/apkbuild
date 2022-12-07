package com.vitefinetechapp.vitefinetech.Recharge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RechargeActivity extends AppCompatActivity {
    Spinner spinner1, spinner2;
    String string1[] = {"Select Operator", "Vodafone", "Jio", "BSNL", "Airtel"};
    String string2[] = {"Select circle", "Andhra", "Assam", "Bihar", "Chennai", "Delhi", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu &amp; Kashmir",
            "Karnataka", "Kerala", "Kolkata", "Maharashtra", "Madhya Pradesh &amp;", "Chhattisgarh", "Mumbai",
            " North East", "Orissa", "Punjab", "Rajasthan", "Tamil Nadu", "UP West", "UP East", "West Bengal", "Goa", "Manipur"};
    EditText editText;

    ImageButton backbutton;

    String circle, mobno;
    EditText Dth_rechaeg_accountid, Amount_Dth_amtid;
    String state, operator, operatorsss;
    ArrayList<String> list;
    ArrayList<String> list2;
    Button Offer_DTHbutton, Save_rechgbutton;
    private ProgressDialog progressDialog;
    TextView states;

    RadioGroup typeRadioGroup;
    RadioButton typeRadioBtn;
//    AlertDialog alert;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        Log.d("mm", "mm");


        Dth_rechaeg_accountid = (EditText) findViewById(R.id.Dth_rechaeg_accountid);
        Offer_DTHbutton = (Button) findViewById(R.id.Offer_DTHbutton);
        Save_rechgbutton = (Button) findViewById(R.id.Save_rechgbutton);
        spinner1 = (Spinner) findViewById(R.id.Recharge_Provider_spinner);
        backbutton = (ImageButton) findViewById(R.id.backbutton);
        spinner2 = (Spinner) findViewById(R.id.Recharge_Area_spinner);
        Amount_Dth_amtid = (EditText) findViewById(R.id.Amount_Dth_amtid);
        states = (TextView) findViewById(R.id.state);
        typeRadioGroup=findViewById(R.id.radioGroup1);

        mobno = Dth_rechaeg_accountid.getText().toString();

        Amount_Dth_amtid.setText("");

        list = new ArrayList<String>();
        list2 = new ArrayList<String>();

        progressDialog = new ProgressDialog(RechargeActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("ResponseData").apply();


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RechargeActivity.this, android.R.layout.simple_spinner_dropdown_item, string1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleted_device = parent.getItemAtPosition(position).toString();
                operator = seleted_device;

//                if (operator.equals("Vodafone")) {
//                    operator = "VI";
//                } else if (operator.equals("Jio")) {
//                    operator = "JO";
//                } else if (operator.equals("BSNL")) {
//                    operator = "BS";
//                } else if (operator.equals("Airtel")) {
//                    operator = "AT";
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        final ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(RechargeActivity.this, android.R.layout.simple_spinner_dropdown_item, string2);
        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter0);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleted_device2 = parent.getItemAtPosition(position).toString();
                circle = seleted_device2;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //This function is called when the user enters his number.- progressDialog is on when the 10 digit is entered.
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Dth_rechaeg_accountid.getText().toString().equals("") && Dth_rechaeg_accountid.getText().toString().length() == 10) {
                    progressDialog.show();
//                    retrivedata();
                    retrivedata2();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        Dth_rechaeg_accountid.addTextChangedListener(textWatcher);

        Offer_DTHbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobno = Dth_rechaeg_accountid.getText().toString();

                if (mobno.toString().isEmpty()) {
                    Dth_rechaeg_accountid.setError("mob no not entered");
                    Dth_rechaeg_accountid.requestFocus();
                } else {
//                    openDiologue();
                    progressDialog.show();
                    rechargePlan();
                }
            }
        });

        Save_rechgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobno = Dth_rechaeg_accountid.getText().toString();
                String amount = Amount_Dth_amtid.getText().toString();

                if (mobno.toString().isEmpty()) {
                    Dth_rechaeg_accountid.setError("mob no not entered");
                    Dth_rechaeg_accountid.requestFocus();
                } else if (amount.isEmpty()) {
                    Amount_Dth_amtid.setError("Amount not entered");
                    Amount_Dth_amtid.requestFocus();
                }
                else {
                    int SelectedId=typeRadioGroup.getCheckedRadioButtonId();
                    typeRadioBtn=findViewById(SelectedId);
                    String Selected,type="";
                    Selected=typeRadioBtn.getText().toString();
                    if(Selected!=null) {
                        if (Selected == "Prepaid")
                            type = "prepaid";
                        else if (Selected == "Postpaid") {
                            type = "postpaid";
                        }
                        Intent i = new Intent(RechargeActivity.this, MakeRechargeActivity.class);
                        i.putExtra("oprator", operator);
                        i.putExtra("circle", circle);
                        Log.d("onClick: ",operator+circle );
                        i.putExtra("amount", amount);
                        i.putExtra("mobileno", mobno);
                        i.putExtra("rType", type);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(RechargeActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }



    //This method is showed when the user pressed "offer" btn
    private void rechargePlan() {
        String url = "https://vitefintech.com/viteapi/home/plan";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
//                    i.putExtra("response", (Parcelable) obj);
                    i.putExtra("response", response);
                    startActivity(i);
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("circle", spinner2.getSelectedItem().toString());
                params.put("operator", spinner1.getSelectedItem().toString());
                params.put("api_key","NMA001");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //This function is called when the users typed 10 digit number- trying post request
    // {"status":true,"response_code":1,"info":{"operator":"Airtel","circle":"Chennai"},"message":"Successful"}
    public void retrivedata2() {

        String url = "https://vitefintech.com/viteapi/home/getmobile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    JSONObject obj = null;
                    try {
                        Log.d("retrivedataPost: ", response);
                        obj = new JSONObject(response);
                        operator = obj.getJSONObject("info").getString("operator");
                        circle = obj.getJSONObject("info").getString("circle");

                        Log.d("operator", operator);
                        Log.d("circle", circle);


                        //setting spinner1(Service provider) TODO: connect api here instead of manual list adding.
                        list.add(0, "Select Operator");
                        list.add(1, "Vodafone");
                        list.add(2, "Jio");
                        list.add(3, "BSNL");
                        list.add(4, "Airtel");
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RechargeActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(dataAdapter);
                        spinner1.setSelection(list.indexOf(operator));
                        Log.d("index of operator", list.indexOf(operator) + "");


                        //setting spinner2(Circle) TODO: connect api here instead of manual list adding.
                        list2.add(0, "Select circle");
                        list2.add(1, "Andhra");
                        list2.add(2, "Assam");
                        list2.add(3, "Bihar");
                        list2.add(4, "Chennai");
                        list2.add(5, "Delhi");
                        list2.add(6, "Gujarat");
                        list2.add(7, "Haryana");
                        list2.add(8, "Himachal");
                        list2.add(9, "Karnataka");
                        list2.add(10, "Kerala");
                        list2.add(11, "Kolkata");
                        list2.add(12, "Maharashtra");
                        list2.add(13, "Madhya Pradesh &amp;");
                        list2.add(14, "Chhattisgarh");
                        list2.add(15, "Mumbai");
                        list2.add(16, "North East");
                        list2.add(17, "Orissa");
                        list2.add(18, "Punjab");
                        list2.add(19, "Rajasthan");
                        list2.add(20, "Tamil Nadu");
                        list2.add(21, "UP East");
                        list2.add(22, "UP West");
                        list2.add(23, "West Bengal");
                        list2.add(24, "Goa");
                        list2.add(25, "Manipur");


                        final ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(RechargeActivity.this, android.R.layout.simple_spinner_dropdown_item, list2);
                        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter0);
                        spinner2.setSelection(list2.indexOf(circle));

                    } catch (JSONException e) {
                        Log.d("retrivedata2: ", e.getMessage());
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Error+:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", Dth_rechaeg_accountid.getText().toString());


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onRestart() {
        Log.d("backback", "bbb1");
//        alert.dismiss();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("ResponseData", null);

        if (data != null) {
            Log.d("backback", data);
            try {
                JSONObject data2 = new JSONObject(data);
                Amount_Dth_amtid.setText(data2.getString("rs"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onRestart();

    }

    @Override
    protected void onResume() {
        Log.d("bBb", spinner1.getSelectedItem().toString());

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("ResponseData", null);

        if (data != null) {
            Log.d("backback", data);
            try {
                JSONObject data2 = new JSONObject(data);
                Amount_Dth_amtid.setText(data2.getString("rs"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
//                Log.d("llll", "llll");
//                String ammount = data.getStringExtra("amount");
//                Log.d("ammount", ammount);
//
//                Amount_Dth_amtid.setText(ammount);
                String data3 = sharedPreferences.getString("ResponseData", null);
                Log.d("backback-", data3);
                if (data != null) {
                    try {
                        JSONObject data2 = new JSONObject(data3);
                        Amount_Dth_amtid.setText(data2.getString("rs"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private void openDiologue() {
        LayoutInflater inflater = LayoutInflater.from(RechargeActivity.this);
        View view = inflater.inflate(R.layout.activity_offer, null);

        RelativeLayout topupbtn = (RelativeLayout) view.findViewById(R.id.r1);
        RelativeLayout fulltalktym = (RelativeLayout) view.findViewById(R.id.r2);
        RelativeLayout datarechg = (RelativeLayout) view.findViewById(R.id.r3);
        RelativeLayout smspack = (RelativeLayout) view.findViewById(R.id.r4);
        RelativeLayout loaclcall = (RelativeLayout) view.findViewById(R.id.r5);
        RelativeLayout natinal = (RelativeLayout) view.findViewById(R.id.r6);
        RelativeLayout other_rechg = (RelativeLayout) view.findViewById(R.id.r7);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);


//        alert = builder.create();

        Log.d("fff", "fff");


        topupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "TUP");
                startActivity(i);
            }
        });

        fulltalktym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "FTT");
                startActivity(i);
            }
        });

        datarechg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "3G");
                startActivity(i);

            }
        });

        smspack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "SMS");
                startActivity(i);
            }
        });

        loaclcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "LSC");
                startActivity(i);
            }
        });

        natinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "RMG");
                startActivity(i);
            }
        });

        other_rechg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RechargeActivity.this, TopUPActivity.class);
                i.putExtra("oprator", operator);
                i.putExtra("circle", circle);
                i.putExtra("type", "OTR");
                startActivity(i);
            }
        });


//        alert.show();
    }

    public void retrivedata() {
        StringRequest eventoReq = new StringRequest(Request.Method.GET, "https://vitefintech.com/viteapi/recharge/mobile?mobile=" + Dth_rechaeg_accountid.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REsponce", response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);
//                            progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();


                            operator = obj.getString("operator");
                            circle = obj.getString("circle");
                            String status = obj.getString("status");
                            String message = obj.getString("message");


                            Log.d("operator", operator);
                            Log.d("circle", circle);


                            if (circle.equals("1")) {
                                state = "Andhra";
                                states.setText(state);
                            } else if (circle.equals("2")) {
                                state = "Assam";
                                states.setText(state);
                            } else if (circle.equals("3")) {
                                state = "Bihar";
                                states.setText(state);
                            } else if (circle.equals("4")) {
                                state = "Chennai";
                                states.setText(state);
                            } else if (circle.equals("5")) {
                                state = "Delhi";
                                states.setText(state);
                            } else if (circle.equals("6")) {
                                state = "Gujarat";
                                states.setText(state);
                            } else if (circle.equals("7")) {
                                state = "Haryana";
                                states.setText(state);
                            } else if (circle.equals("8")) {
                                state = "Himachal Pradesh";
                                states.setText(state);
                            } else if (circle.equals("9")) {
                                state = "Jammu &amp; Kashmir";
                                states.setText(state);
                            } else if (circle.equals("10")) {
                                state = "Karnataka";
                                states.setText(state);
                            } else if (circle.equals("11")) {
                                state = "Kerala";
                                states.setText(state);
                            } else if (circle.equals("12")) {
                                state = "Kolkata";
                                states.setText(state);
                            } else if (circle.equals("13")) {
                                state = "Maharashtra";
                                states.setText(state);
                            } else if (circle.equals("14")) {
                                state = "Madhya Pradesh &amp; Chhattisgarh";
                                states.setText(state);
                            } else if (circle.equals("15")) {
                                state = "Mumbai";
                                states.setText(state);
                            } else if (circle.equals("16")) {
                                state = "North East";
                                states.setText(state);
                            } else if (circle.equals("17")) {
                                state = "Orissa";
                                states.setText(state);
                            } else if (circle.equals("18")) {
                                state = "Punjab";
                                states.setText(state);
                            } else if (circle.equals("19")) {
                                state = "Rajasthan";
                                states.setText(state);
                            } else if (circle.equals("20")) {
                                state = "Tamil Nadu";
                                states.setText(state);
                            } else if (circle.equals("21")) {
                                state = "Uttar Pradesh (E)";
                                states.setText(state);
                            } else if (circle.equals("22")) {
                                state = "Uttar Pradesh (W)";
                                states.setText(state);
                            } else if (circle.equals("23")) {
                                state = "West Bengal";
                                states.setText(state);
                            } else if (circle.equals("24")) {
                                state = "";
                            } else if (circle.equals("25")) {
                                state = "";
                            } else if (circle.equals("26")) {
                                state = "Goa";
                                states.setText(state);
                            } else if (circle.equals("27")) {
                                state = "Manipur";
                                states.setText(state);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (operator.equals("VI")) {
                            operatorsss = "Vodafone";
                        } else if (operator.equals("JO")) {
                            operatorsss = "Jio";
                        } else if (operator.equals("BS")) {
                            operatorsss = "BSNL";
                        } else if (operator.equals("AT")) {
                            operatorsss = "Airtel";
                        }
                        Toast.makeText(RechargeActivity.this, operator + "-hey", Toast.LENGTH_SHORT).show();


                        list.add(0, "Select Oprator");
                        list.add(1, "Vodafone");
                        list.add(2, "Jio");
                        list.add(3, "BSNL");
                        list.add(4, "Airtel");
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RechargeActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(dataAdapter);
                        spinner1.setSelection(list.indexOf(operatorsss));


                        list2.add(0, "Select circle");
                        list2.add(1, "Andhra");
                        list2.add(2, "Assam");
                        list2.add(3, "Bihar");
                        list2.add(4, "Chennai");
                        list2.add(5, "Delhi");
                        list2.add(6, "Gujarat");
                        list2.add(7, "Haryana");
                        list2.add(8, "Himachal");
                        list2.add(9, "Karnataka");
                        list2.add(10, "Kerala");
                        list2.add(11, "Kolkata");
                        list2.add(12, "Maharashtra");
                        list2.add(13, "Madhya Pradesh &amp;");
                        list2.add(14, "Chhattisgarh");
                        list2.add(15, "Mumbai");
                        list2.add(16, "North East");
                        list2.add(17, "Orissa");
                        list2.add(18, "Punjab");
                        list2.add(19, "Rajasthan");
                        list2.add(20, "Tamil Nadu");
                        list2.add(21, "Uttar Pradesh (E)");
                        list2.add(22, "Uttar Pradesh (W)");
                        list2.add(23, "West Bengal");
                        list2.add(24, "Goa");
                        list2.add(25, "Manipur");


                        final ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(RechargeActivity.this, android.R.layout.simple_spinner_dropdown_item, list2);
                        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter0);
                        spinner2.setSelection(list2.indexOf(states.getText().toString()));
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
}