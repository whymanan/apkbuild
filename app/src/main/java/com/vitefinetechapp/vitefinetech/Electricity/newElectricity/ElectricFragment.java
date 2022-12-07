package com.vitefinetechapp.vitefinetech.Electricity.newElectricity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Electricity.ElecticityActivity;
import com.vitefinetechapp.vitefinetech.Electricity.Electricity_makePaymentActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ElectricFragment extends Fragment {
    Spinner spinner;

    ArrayList<String> operatorList,operatorIdList;

    ArrayAdapter arrayAdapter;
    ImageButton backbutton;
    Button proceed;
    String  URL;
    TextView tv,local;
    CardView crdstory_id;
    EditText account_id;
    CardView cInfo, cNoInfo;
    private ProgressDialog progressDialog;
    String selected_operator,sOperator,amtBal,duedate,uname;


    Button btn_fetchInfo;
    Boolean infofetched=false;
    TextView tv_cusInfo;
    TextView erMsg;
    TextView Name, Cell, amt, netAmt, due;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_electric, container, false);
        spinner        =     (Spinner)view.findViewById( R.id.Elecrtcity_spinner );
        backbutton     =     (ImageButton) view.findViewById(R.id.backbutton);
        proceed      =     (Button) view.findViewById(R.id.fetchbill);
        account_id     =     (EditText) view.findViewById(R.id.account_id);
        cInfo = view.findViewById(R.id.card_info);
        cNoInfo = view.findViewById(R.id.card_noInfo);
        cInfo.setVisibility(View.GONE);
        tv_cusInfo=view.findViewById(R.id.tv_cusInfo);
        tv_cusInfo.setVisibility(View.GONE);
        cNoInfo.setVisibility(View.GONE);
        btn_fetchInfo=view.findViewById(R.id.fetchinfo);
        proceed.setVisibility(View.GONE);
        operatorList=new ArrayList<>();
        operatorIdList=new ArrayList<>();
        erMsg=view.findViewById(R.id.errorMsg);
        Name = view.findViewById(R.id.tv_cName);
        Cell =  view.findViewById(R.id.tv_cCell);
        amt =  view.findViewById(R.id.tv_cBillAmt);
        netAmt =  view.findViewById(R.id.tv_cNetBillAmt);
        due =  view.findViewById(R.id.tv_cDue);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        fetchOperator();

        arrayAdapter=new ArrayAdapter( getContext(), android.R.layout.simple_list_item_1,operatorList);
        spinner.setAdapter( arrayAdapter );




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
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(account_id.getWindowToken(), 0);
                if(TextUtils.isEmpty(account_id.getText())){
                    Toast.makeText(getContext(), "Enter a valid Account id", Toast.LENGTH_SHORT).show();
                }
                else if(spinner.getSelectedItem()==null || selected_operator.equals("-select service provider")){
                    Toast.makeText(getContext(), "Select a Service provider", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Select service provider", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(account_id.getText().toString()))
                    Toast.makeText(getContext(), "Enter a valid Account number", Toast.LENGTH_SHORT).show();
                else {
                    String type = "electrict";
                    String service="18";
                    Log.d("ElecAmount2", amtBal + "");
                    Intent i = new Intent(getContext(), Electricity_makePaymentActivity.class);
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

        return view;
    }

    private void setcInfo() {

        infofetched=true;
        if(spinner.getSelectedItem() == null || selected_operator.equals("-select service provider")){
            Toast.makeText(getContext(), "Select a Service provider", Toast.LENGTH_SHORT).show();
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
                                erMsg.setText("*"+obj.getString("message"));
                                cNoInfo.setVisibility(View.VISIBLE);
                                tv_cusInfo.setVisibility(View.VISIBLE);
                            }
                            else {


                                duedate=obj.getString("duedate");
                                amtBal = obj.getJSONObject("bill_fetch").getString("billnetamount");
                                uname=obj.getJSONObject("bill_fetch").getString("userName");
                                Name.setText(uname);
                                Cell.setText(obj.getJSONObject("bill_fetch").getString("cellNumber"));
                                amt.setText(obj.getJSONObject("bill_fetch").getString("billAmount"));
                                netAmt.setText(amtBal);
                                due.setText(duedate);


                                Log.d("ElecAmount", amtBal + "");

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
                        erMsg.setText("*Enter a valid Service provider and account number");
                        cNoInfo.setVisibility(View.VISIBLE);
                        Log.d("ElectSetInfoerror2: ", error.getMessage() + "--");
                        Toast.makeText(getContext(), "Error+:" + error.getMessage(), Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }

    private void fetchOperator() {

        operatorList.add("-select service provider");
        operatorIdList.add("select");
        String url = "https://vitefintech.com/viteapi/home/billoperator";
        Log.d("EleSetop", "here-= ");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("Ele_setoperator", response);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        Log.d("Ele_responseArray", jsonArray.toString());
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("Ele_list", "heree09");
                                Log.d("Ele_responseloop", jsonArray.getJSONObject(i).getString("category"));
                                if (jsonArray.getJSONObject(i).getString("category").equals("Electricity") ) {
                                    operatorList.add(jsonArray.getJSONObject(i).getString("name"));
                                    operatorIdList.add(jsonArray.getJSONObject(i).getString("id"));
                                    Log.d("Ele_list", operatorList + "");
                                }

                            }
                        }
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, operatorList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("Ele_setOperatorError","error"+error.getMessage());
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}