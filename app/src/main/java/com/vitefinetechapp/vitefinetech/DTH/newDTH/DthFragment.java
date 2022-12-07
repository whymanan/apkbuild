package com.vitefinetechapp.vitefinetech.DTH.newDTH;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.DTH.DTH_MakeRechargeActivity;
import com.vitefinetechapp.vitefinetech.DTH.DthRechargActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DthFragment extends Fragment {

    Button Save_button;
    ImageButton backbutton;
    Spinner operator, circle;
    ArrayList<String> listoprator, operatorid;
    ArrayList<String> listcircle;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    EditText Dth_amount, Dth_canumber;
    CardView cInfo, cNoInfo;

    String sOperator, selected_operator;
    private ProgressDialog progressDialog;

    Button btn_fetchInfo;
    Boolean infofetched=false;
    TextView tv_cusInfo,tv_amount;
    TextView erMsg;
    TextView Name, Balance, Plan, date, monthly;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dth, container, false);

        backbutton = (ImageButton) view.findViewById(R.id.backbutton);
        Save_button = (Button) view.findViewById(R.id.Save_DTHbutton);
//        Offer_DTHbutton  =     (Button)findViewById( R.id.Offer_DTHbutton );
        operator = (Spinner) view.findViewById(R.id.DTH_Provider_spinner);
//        circle           =     (Spinner) findViewById(R.id.Dth_circle);
        Dth_amount = (EditText) view.findViewById(R.id.Dth_accountid);
        Dth_canumber = (EditText) view.findViewById(R.id.GAS_accountid);
        cInfo = view.findViewById(R.id.card_info);
        cNoInfo = view.findViewById(R.id.card_noInfo);
        cInfo.setVisibility(View.GONE);
        erMsg=view.findViewById(R.id.errorMsg);
        Name = view.findViewById(R.id.tv_cName);
        Balance = view.findViewById(R.id.tv_cBalance);
        Plan = view.findViewById(R.id.tv_cPlan);
        date = view.findViewById(R.id.tv_cRechargeDate);
        monthly = view.findViewById(R.id.tv_cMonthly);

        tv_amount=view.findViewById(R.id.tv_amount);
        tv_cusInfo=view.findViewById(R.id.tv_cusInfo);
        tv_cusInfo.setVisibility(View.GONE);
        cNoInfo.setVisibility(View.GONE);
        btn_fetchInfo=view.findViewById(R.id.fetchinfo);
        Save_button.setVisibility(View.GONE);
        Dth_amount.setVisibility(View.GONE);
        tv_amount.setVisibility(View.GONE);

        listoprator = new ArrayList<>();
        operatorid = new ArrayList<>();
        listcircle = new ArrayList<String>();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        setoperator();

        operator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(infofetched){
                    tv_cusInfo.setVisibility(View.GONE);
                    cInfo.setVisibility(View.GONE);
                    cNoInfo.setVisibility(View.GONE);
                    btn_fetchInfo.setVisibility(View.VISIBLE);
                    Save_button.setVisibility(View.GONE);
                    Dth_amount.setVisibility(View.GONE);
                    tv_amount.setVisibility(View.GONE);
                    infofetched=false;
                }
                selected_operator = parent.getItemAtPosition(position).toString();
                sOperator = operatorid.get(position);

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
                    Save_button.setVisibility(View.GONE);
                    Dth_amount.setVisibility(View.GONE);
                    tv_amount.setVisibility(View.GONE);
                    infofetched=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        Dth_canumber.addTextChangedListener(textWatcher);

        btn_fetchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Dth_canumber.getWindowToken(), 0);
                if(TextUtils.isEmpty(Dth_canumber.getText())){
                    Toast.makeText(getContext(), "Enter a valid Account id", Toast.LENGTH_SHORT).show();
                }
                else if(operator.getSelectedItem()==null || selected_operator.equals("-select service provider")){
                    Toast.makeText(getContext(), "Select a Service provider", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    setcInfo();
                }

            }
        });

        //proceed button
        Save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator.getSelectedItem() == null)
                    Toast.makeText(getContext(), "Select service provider", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(Dth_canumber.getText().toString()))
                    Toast.makeText(getContext(), "Enter a valid Account number", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(Dth_amount.getText().toString()))
                    Toast.makeText(getContext(), "Enter a amount", Toast.LENGTH_SHORT).show();
                else {
                    String type = "dth";
                    Intent i = new Intent(getContext(), DTH_MakeRechargeActivity.class);
                    i.putExtra("operator", sOperator);
                    Log.d("dthonClick: ", sOperator + "");
                    i.putExtra("amount", Dth_amount.getText().toString());
                    i.putExtra("mobileno", Dth_canumber.getText().toString());
                    i.putExtra("rType", type);
                    startActivity(i);
                }

            }
        });

    return view;
    }

    private void setcInfo() {

        infofetched=true;
        String url = "https://vitefintech.com/viteapi/home/getdthinfo";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONObject obj = null;
                    try {

                        Log.d("dthSetInfo: ", response);
                        obj = new JSONObject(response);
                        if(obj.getString("status").equals("false") || obj.getBoolean("status")==false){

                            erMsg.setText("*"+obj.getString("message"));
                            cNoInfo.setVisibility(View.VISIBLE);
                            tv_cusInfo.setVisibility(View.VISIBLE);
                        }
                        else {

                            Name.setText(obj.getJSONArray("info").getJSONObject(0).getString("customerName"));
                            Balance.setText(obj.getJSONArray("info").getJSONObject(0).getInt("Balance") + "");
                            Plan.setText(obj.getJSONArray("info").getJSONObject(0).getString("planname"));
                            date.setText(obj.getJSONArray("info").getJSONObject(0).getString("NextRechargeDate"));
                            monthly.setText(obj.getJSONArray("info").getJSONObject(0).getString("MonthlyRecharge"));

                            cInfo.setVisibility(View.VISIBLE);
                            cNoInfo.setVisibility(View.GONE);
                            tv_cusInfo.setVisibility(View.VISIBLE);
                            btn_fetchInfo.setVisibility(View.GONE);
                            Save_button.setVisibility(View.VISIBLE);
                            Dth_amount.setVisibility(View.VISIBLE);
                            tv_amount.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        erMsg.setText("Customer not found");
                        cNoInfo.setVisibility(View.VISIBLE);
                        tv_cusInfo.setVisibility(View.VISIBLE);
                        Log.d("dthSetInfoerror: ", e.getMessage());
                    }
                    progressDialog.dismiss();
                },
                error -> {
                    progressDialog.dismiss();
                    tv_cusInfo.setVisibility(View.VISIBLE);
                    erMsg.setText("*Enter a valid Service provider and account number");
                    cNoInfo.setVisibility(View.VISIBLE);
                    Log.d("dthSetInfoerror2: ", error.getMessage()+"");
                    Toast.makeText(getContext(), "Error+:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("canumber", Dth_canumber.getText().toString());
                if (selected_operator.equals("Airtel Digital TV"))
                    params.put("operator", "Airteldth");
                else if (selected_operator.equals("Dish TV"))
                    params.put("operator", "Dishtv");
                else if (selected_operator.equals("Sun Direct"))
                    params.put("operator", "Sundirect");
                else if (selected_operator.equals("Tata Sky"))
                    params.put("operator", "TataSky");
                else if (selected_operator.equals("Videocon D2H"))
                    params.put("operator", "Videocon");
                params.put("api_key","NMA001");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void setoperator() {


        listoprator.add("-select-");
        operatorid.add("-select-");
        String url = "https://vitefintech.com/viteapi/home";
        Log.d("dthSetop", "here-= ");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("DTH_setoperator", response);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        Log.d("DTh_responseArray", jsonArray.toString());
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("dth_list", "heree09");
                                Log.d("DTh_responseloop", jsonArray.getJSONObject(i).getString("category"));
                                if (jsonArray.getJSONObject(i).getString("category").equals("DTH")) {
                                    listoprator.add(jsonArray.getJSONObject(i).getString("name"));
                                    operatorid.add(jsonArray.getJSONObject(i).getString("id"));
                                    Log.d("dth_list", listoprator + "");
                                }

                            }
                        }
                        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listoprator);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        operator.setAdapter(dataAdapter);
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    Log.d("dth_setOperatorError", error.getMessage()+"");
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}