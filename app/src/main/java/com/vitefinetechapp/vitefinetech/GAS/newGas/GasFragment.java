package com.vitefinetechapp.vitefinetech.GAS.newGas;

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
import com.vitefinetechapp.vitefinetech.Electricity.Electricity_makePaymentActivity;
import com.vitefinetechapp.vitefinetech.GAS.GasBillPayActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GasFragment extends Fragment {

    Spinner spinner;
    //    String string1 []={"Avantika Gas Ltd","Assam Gas company Ltd","Bhagya Nagar gas Ltd","Central U.P gasLtd"};
    ArrayList<String> operatorList,operatorIdList;
    ArrayAdapter arrayAdapter;
    ImageButton backbutton;
    String URL = "https://dashboard.goterpay.com/api/mobileinfo?mid=G52435328&mkey=FJKdsfsdh&mobile=9005464564";
    private ProgressDialog progressDialog;
    String selected_operator,sOperator;
    EditText accountId;
    CardView cInfo, cNoInfo;
    Button submit;
    String amtBal,duedate,uname;

    Button btn_fetchInfo;
    Boolean infofetched=false;
    TextView tv_cusInfo;
    TextView info;
    TextView Name, Cell, amt, netAmt, dued;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gas, container, false);

        operatorList=new ArrayList<>();
        operatorIdList=new ArrayList<>();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Name = view.findViewById(R.id.tv_cName);
        Cell = view.findViewById(R.id.tv_cCell);
        amt = view.findViewById(R.id.tv_cBillAmt);
        netAmt = view.findViewById(R.id.tv_cNetBillAmt);
        dued = view.findViewById(R.id.tv_cDue);
        backbutton  =     (ImageButton) view.findViewById(R.id.backbutton);
        spinner    =      (Spinner)view.findViewById( R.id.GAS_spinner );
        accountId=view.findViewById(R.id.GAS_accountid);
        submit=view.findViewById(R.id.submit);
        cInfo = view.findViewById(R.id.card_info);
        cNoInfo = view.findViewById(R.id.card_noInfo);
        cInfo.setVisibility(View.GONE);
        info=view.findViewById(R.id.infoErrorMsg);
        tv_cusInfo=view.findViewById(R.id.tv_cusInfo);
        tv_cusInfo.setVisibility(View.GONE);
        cNoInfo.setVisibility(View.GONE);
        btn_fetchInfo=view.findViewById(R.id.fetchinfo);
        submit.setVisibility(View.GONE);

        fetchOperator();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(infofetched){
                    tv_cusInfo.setVisibility(View.GONE);
                    cInfo.setVisibility(View.GONE);
                    cNoInfo.setVisibility(View.GONE);
                    btn_fetchInfo.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
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
                    submit.setVisibility(View.GONE);
                    infofetched=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        accountId.addTextChangedListener(textWatcher);

        btn_fetchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(accountId.getWindowToken(), 0);
                if(TextUtils.isEmpty(accountId.getText())){
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem() == null)
                    Toast.makeText(getContext(), "Select service provider", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(accountId.getText().toString()))
                    Toast.makeText(getContext(), "Enter a valid Account number", Toast.LENGTH_SHORT).show();
                else {
                    String type = "Gas";
                    String service="22";
                    Log.d("GasAmount2", amtBal + "");
                    Intent i = new Intent(getContext(), Electricity_makePaymentActivity.class);
                    i.putExtra("operator", sOperator);
                    Log.d("GasonClick: ", sOperator + "");
                    i.putExtra("amount", amtBal);
                    i.putExtra("mobileno", accountId.getText().toString());
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
        String url = "https://vitefintech.com/viteapi/home/billfetch";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONObject obj = null;
                    try {
                        progressDialog.dismiss();

                        Log.d("GasSetInfo: ", response);
                        obj = new JSONObject(response);
                        if(obj.getBoolean("status")==false){


                            info.setText(obj.getString("message"));
                            cNoInfo.setVisibility(View.VISIBLE);
                            tv_cusInfo.setVisibility(View.VISIBLE);
                        }
                        else {



                            duedate = obj.getString("duedate");
                            amtBal = obj.getJSONObject("bill_fetch").getString("billnetamount");
                            uname = obj.getJSONObject("bill_fetch").getString("userName");

                            dued.setText(duedate);
                            Name.setText(uname);
                            Cell.setText(obj.getJSONObject("bill_fetch").getString("cellNumber"));
                            amt.setText(obj.getJSONObject("bill_fetch").getString("billAmount"));
                            netAmt.setText(amtBal);
                            Log.d("GasAmount", amtBal + "");

                            tv_cusInfo.setVisibility(View.VISIBLE);
                            cInfo.setVisibility(View.VISIBLE);
                            cNoInfo.setVisibility(View.GONE);
                            btn_fetchInfo.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);

                        }



                    } catch (JSONException e) {
                        Log.d("GasSetInfoerror: ", e.getMessage());
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    tv_cusInfo.setVisibility(View.VISIBLE);
                    info.setText("*Enter a valid Service provider and account number");
                    cNoInfo.setVisibility(View.VISIBLE);
                    Log.d("GasSetInfoerror2: ", error.getMessage()+"--");
                    Toast.makeText(getContext(), "Error+:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("canumber", accountId.getText().toString());
                params.put("operator",sOperator);
                params.put("mode","online");
                params.put("api_key","NMA001");
                Log.d("Gasparams", accountId.getText().toString()+"|"+sOperator);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void fetchOperator() {
        operatorList.add("-select service provider");
        operatorIdList.add("select");
        String url = "https://vitefintech.com/viteapi/home/billoperator";
        Log.d("GasSetop", "here-= ");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("Gas_setoperator", response);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        Log.d("Gas_responseArray", jsonArray.toString());
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getString("category").equals("Gas") || jsonArray.getJSONObject(i).getString("category").equals("LPG") ) {
                                    operatorList.add(jsonArray.getJSONObject(i).getString("name"));
                                    operatorIdList.add(jsonArray.getJSONObject(i).getString("id"));
                                    Log.d("Gas_list", operatorList + "");
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
                    Log.d("Gas_setOperatorError","error"+error.getMessage());
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}