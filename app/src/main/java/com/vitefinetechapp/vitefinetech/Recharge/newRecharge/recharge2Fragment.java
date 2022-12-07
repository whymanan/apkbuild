package com.vitefinetechapp.vitefinetech.Recharge.newRecharge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.Recharge.MakeRechargeActivity;
import com.vitefinetechapp.vitefinetech.Recharge.RechargeActivity;
import com.vitefinetechapp.vitefinetech.Recharge.TopUPActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class recharge2Fragment extends Fragment {
    Spinner spinner1, spinner2;
    String string1[] = {"Select Operator", "Vodafone", "Jio", "BSNL", "Airtel"};
    String string2[] = {"Select circle", "Andhra", "Assam", "Bihar", "Chennai", "Delhi", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu &amp; Kashmir",
            "Karnataka", "Kerala", "Kolkata", "Maharashtra", "Madhya Pradesh &amp;", "Chhattisgarh", "Mumbai",
            " North East", "Orissa", "Punjab", "Rajasthan", "Tamil Nadu", "UP West", "UP East", "West Bengal", "Goa", "Manipur"};
    EditText editText;


    String circle, mobno;
    EditText Dth_rechaeg_accountid, Amount_Dth_amtid;
    String state, operator, operatorsss;
    ArrayList<String> list;
    ArrayList<String> list2;
    Button Offer_DTHbutton, Save_rechgbutton;
    private ProgressDialog progressDialog;
    TextView states;
    int SelectedId;
    String Selected, type = "";
    RadioGroup typeRadioGroup;
    RadioButton typeRadioBtn;
    //    AlertDialog alert;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recharge2, container, false);

        Dth_rechaeg_accountid = (EditText) view.findViewById(R.id.Dth_rechaeg_accountid);
        Offer_DTHbutton = (Button) view.findViewById(R.id.Offer_DTHbutton);
        Save_rechgbutton = (Button) view.findViewById(R.id.Save_rechgbutton);
        spinner1 = (Spinner) view.findViewById(R.id.Recharge_Provider_spinner);

        spinner2 = (Spinner) view.findViewById(R.id.Recharge_Area_spinner);
        Amount_Dth_amtid = (EditText) view.findViewById(R.id.Amount_Dth_amtid);
        states = (TextView) view.findViewById(R.id.state);
        typeRadioGroup = view.findViewById(R.id.radioGroup1);


        mobno = Dth_rechaeg_accountid.getText().toString();

        Amount_Dth_amtid.setText("");

        list = new ArrayList<String>();
        list2 = new ArrayList<String>();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);


        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("ResponseData").apply();


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, string1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleted_device = parent.getItemAtPosition(position).toString();
                operator = seleted_device;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        final ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, string2);
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

        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SelectedId = typeRadioGroup.getCheckedRadioButtonId();
                typeRadioBtn = view.findViewById(SelectedId);

                Selected = typeRadioBtn.getText().toString();

                    if (Selected.equals("Prepaid"))
                        type = "prepaid";
                    else if (Selected.equals("Postpaid")) {
                        type = "postpaid";
//                        Toast.makeText(getContext(), "sel:"+type, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                } else if(type==""){
                    Toast.makeText(getContext(), "Select Prepaid or Postpaid", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getContext(), "sel:"+type, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getContext(), MakeRechargeActivity.class);
                        i.putExtra("oprator", operator);
                        i.putExtra("circle", circle);
                        Log.d("onClick: ", operator + circle);
                        i.putExtra("amount", amount);
                        i.putExtra("mobileno", mobno);
                        i.putExtra("rType", type);
                        startActivity(i);


                }
            }
        });

        return view;
    }

    //This method is showed when the user pressed "offer" btn
    private void rechargePlan() {
        String url = "https://vitefintech.com/viteapi/home/plan";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Intent i = new Intent(getContext(), TopUPActivity.class);
//                    i.putExtra("response", (Parcelable) obj);
                    i.putExtra("response", response);
                    startActivity(i);
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("circle", spinner2.getSelectedItem().toString());
                params.put("operator", spinner1.getSelectedItem().toString());
                params.put("api_key", "NMA001");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
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


                        final ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list2);
                        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter0);
                        spinner2.setSelection(list2.indexOf(circle));

                    } catch (JSONException e) {
                        Log.d("retrivedata2: ", e.getMessage());
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Error+:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", Dth_rechaeg_accountid.getText().toString());
                params.put("api_key", "NMA001");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

//    @Override
//    protected void onRestart() {
//        Log.d("backback", "bbb1");
////        alert.dismiss();
//        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("ResponseData", null);
//
//        if (data != null) {
//            Log.d("backback", data);
//            try {
//                JSONObject data2 = new JSONObject(data);
//                Amount_Dth_amtid.setText(data2.getString("rs"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        super.onRestart();
//
//    }

//    @Override
//    protected void onResume() {
//        Log.d("bBb", spinner1.getSelectedItem().toString());
//
//        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("ResponseData", null);
//
//        if (data != null) {
//            Log.d("backback", data);
//            try {
//                JSONObject data2 = new JSONObject(data);
//                Amount_Dth_amtid.setText(data2.getString("rs"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        super.onResume();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
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

}