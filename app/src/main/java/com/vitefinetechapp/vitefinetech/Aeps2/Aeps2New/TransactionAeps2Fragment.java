package com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Aeps2.BioMetricActivity2;
import com.vitefinetechapp.vitefinetech.Aeps2.DatabaseHelper;
import com.vitefinetechapp.vitefinetech.Aeps2.ScanActivity2;
import com.vitefinetechapp.vitefinetech.Aeps2.SelectBankActivity2;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionAeps2Fragment extends Fragment {

//        public static final String BANK_Api = "https://vitefintech.com/viteapi/pay/banklist?api_key=VITA001";
    public static final String BANK_Api = "https://vitefintech.com/viteapi/pay/banklist?api_key=NMA001";
    String ACCESS_TOKEN,bank_code,bank_name,stan_no;
    Spinner bank;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    ArrayList<String> list,tTypeList;
//    DatabaseHelper db;
    private ProgressDialog progressDialog;

    Button submit,scan;
    String transactionType,from;
    LinearLayout scan_linear;
    EditText amount,adharno1,adharno2,adharno3,mobno;
    Spinner tType;
    TextView txtamttxt,errTv;
    int selected_positionBank,selected_positionType;
    AutoCompleteTextView autoComp;
    ArrayList<String> bankList;
    ImageView iv_aepsLogo;
    TextView tv_transactionType;
    HashMap<String,String> bankListCode;


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(savedInstanceState!=null){
//            bank.setSelection(savedInstanceState.getInt("selected_positionBank"));
//            tType.setSelection(savedInstanceState.getInt("selected_positionType"));
//            mobno.setText(savedInstanceState.getString("mobile"));
//            adharno1.setText(savedInstanceState.getString("aad1"));
//            adharno2.setText(savedInstanceState.getString("aad2"));
//            adharno3.setText(savedInstanceState.getString("aad3"));
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transaction_aeps2, container, false);

        bank          =     (Spinner)view.findViewById(R.id.bank_spinner);

        submit     =    (Button) view.findViewById(R.id.submit);
        tType         =    (Spinner)view.findViewById(R.id.tras_spinner);
        txtamttxt  =    (TextView) view.findViewById(R.id.txtamt);
        amount     =    (EditText) view.findViewById(R.id.amount);
        adharno1    =    (EditText) view.findViewById(R.id.adharno1);
        adharno2    =    (EditText) view.findViewById(R.id.adharno2);
        adharno3    =    (EditText) view.findViewById(R.id.adharno3);
        scan_linear = (LinearLayout) view.findViewById(R.id.scan_linear);
        mobno      =   (EditText) view.findViewById(R.id.mobno);
        iv_aepsLogo=view.findViewById(R.id.iv_aepsLogo);
        tv_transactionType=view.findViewById(R.id.tv_transactionType);
        bankListCode=new HashMap<>();
        errTv=view.findViewById(R.id.errorTv);
        errTv.setVisibility(View.GONE);
        autoComp=view.findViewById(R.id.tras_auto);
        bankList = new ArrayList<>();
        from=getActivity().getIntent().getStringExtra("from");
        String callFrom=getActivity().getIntent().getExtras().getString("callFrom");
        if(from.equals("aadhar")){
            iv_aepsLogo.setImageDrawable(getResources().getDrawable(R.drawable.adharpaynew));
            tv_transactionType.setVisibility(View.GONE);
            tType.setVisibility(View.GONE);
            transactionType="M";
        }

        list = new ArrayList<String>();
        tTypeList=new ArrayList<String>();


        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ACCESS_TOKEN = sharedPreferences.getString("tokenname", null);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        selectTransactionType();
        getBanklist();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mobno.getText().length()>10 || mobno.getText().length()<10)
                {
                    errTv.setVisibility(View.VISIBLE);
                }
                else{
                    errTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mobno.addTextChangedListener(textWatcher);
        TextWatcher textWatcherAd1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adharno1.getText().length()==4)
                    adharno2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        TextWatcher textWatcherAd2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adharno2.getText().length()==4)
                    adharno3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        adharno1.addTextChangedListener(textWatcherAd1);
        adharno2.addTextChangedListener(textWatcherAd2);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactiontype_     =   tType.getSelectedItem().toString();
                String adhrno_              =   adharno1.getText().toString()+adharno2.getText().toString()+adharno3.getText().toString();
                String mobno_               =   mobno.getText().toString();
                String amount_              =   amount.getText().toString();
                TextView ad=view.findViewById(R.id.tvAadhar);

                if(transactiontype_.equals("Select Transaction Types") && !from.equals("aadhar")){
                    Toast.makeText(getContext(), "Please select transaction type", Toast.LENGTH_SHORT).show();
                }else if(adhrno_.toString().isEmpty()) {
                    ad.setError("Please enter adhar number");
                    adharno1.requestFocus();
                }else if (adhrno_.trim().length() < 12) {
                    ad.setError("Enter a twelve digit adhar number");
                }else if (adhrno_.trim().length() > 12) {
                    ad.setError("Enter a twelve digit adhar number");
                }else if(mobno_.toString().isEmpty()) {
                    mobno.setError("Please enter mobile number");
                    mobno.requestFocus();
                }else if(mobno_.trim().length() < 10){
                    mobno.setError("Please enter 10 digit mobile number");
                    mobno.requestFocus();
                } else if (mobno_.trim().length() > 10) {
                    mobno.setError("Please enter 10 digit mobile number");
                    mobno.requestFocus();
                }else {
                    Intent i = new Intent(getContext(), BioMetricActivity2.class);
                    i.putExtra("from",from);
                    i.putExtra("transaction_type", transactionType);
                    i.putExtra("adhar_no", adhrno_);
                    i.putExtra("mobile_no", mobno.getText().toString());
                    i.putExtra("amount", amount.getText().toString());
                    i.putExtra("bank_code", bank_code);
                    i.putExtra("sBankPos",selected_positionBank);
                    i.putExtra("sTypePos",selected_positionType);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });

        scan_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ScanActivity2.class);
                startActivityForResult(i,1);
            }
        });

        return view;
    }
    private void selectTransactionType() {
        tTypeList.add(0, "Select Transaction Types");
        tTypeList.add(1, "Cash Withdrawal");
        tTypeList.add(2, "Balance Enquiry");
        tTypeList.add(3, "Mini Statement");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tType.setAdapter(dataAdapter);

        tType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("bankListAe","here12" );
                selected_positionType=position;
                Log.d("bankListAe1",tTypeList.get(selected_positionType));
                String transactiontype = parent.getItemAtPosition(position).toString();
                if(transactiontype.equals("Balance Enquiry")){
                    amount.setVisibility(View.GONE);
                    txtamttxt.setVisibility(View.GONE);

                    transactionType  = "BE";
                }else if(transactiontype.equals("Cash Withdrawal")){
                    amount.setVisibility(View.VISIBLE);
                    txtamttxt.setVisibility(View.VISIBLE);

                    transactionType  = "CW";
                }else if(transactiontype.equals("Mini Statement")){
                    amount.setVisibility(View.GONE);
                    txtamttxt.setVisibility(View.GONE);

                    transactionType  = "MS";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 ) {
            if (resultCode == getActivity().RESULT_OK) {
                String useruid = data.getStringExtra("editvalue");
                String username = data.getStringExtra("editvalue2");
//                adharno.setText(useruid);
//                name.setText(username);

            }
        }
    }



    private void getBanklist(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BANK_Api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d("ResponseBank:" + response.toString());
                progressDialog.dismiss();
                try{

                    JSONObject jsonObject = response.getJSONObject("banklist");

//                    Toast.makeText(getContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray= jsonObject.getJSONArray("data");
//                    Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
                    for(int i= 0; i< jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        String bank_name2    =   data.getString("bankName");
                        String bank_code2    =   data.getString("iinno");

//                        Log.d("bank_name",bank_name);
//                        Log.d("bank_code",bank_code);

                        list.add(bank_code2);
                        bankList.add(bank_name2);
                        bankListCode.put(bank_name2,bank_code2);
//                        db.insertBanklist(bank_name2,bank_code2);

                    }
                    list.add(0, "Select Your Transaction Bank");

                    final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bank.setAdapter(dataAdapter);

                    ArrayAdapter<String> arAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,bankList);
                    autoComp.setThreshold(1);
                    autoComp.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            autoComp.showDropDown();
                            return false;
                        }
                    });

                    autoComp.setAdapter(arAdapter);
                    autoComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Log.d("bankListAe","here1" );
                            selected_positionBank=bankList.indexOf(autoComp.getText().toString());
                            String seleted_bank = parent.getItemAtPosition(position).toString();

                            bank_code=bankListCode.get(seleted_bank);
                        }
                    });

                    bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selected_positionBank=position;
                            String seleted_bank = parent.getItemAtPosition(position).toString();

//                            SQLiteDatabase dataBase = db.getWritableDatabase();
//                            Cursor mCursor2 = dataBase.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BANK, null);
//                            if (mCursor2.moveToFirst()) {
//                                do {
//                                    if(bank.getSelectedItem().toString().equals(mCursor2.getString(mCursor2.getColumnIndex("bank_name")))) {
//                                        String bankcode = mCursor2.getString(mCursor2.getColumnIndex("bank_code"));
//                                        Log.d("bankkkkkkkkkkkk",bankcode);
//                                        bank_code = bankcode;
//                                    }
//                                } while (mCursor2.moveToNext());
//                            }
                            bank_code=bankListCode.get(seleted_bank);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    String callFrom=getActivity().getIntent().getStringExtra("callFrom");
                    String from=getActivity().getIntent().getStringExtra("from");
                    if(callFrom!=null){
                        if(callFrom.equals("recipt")){

//                            bank.setSelection(getActivity().getIntent().getIntExtra("sBankPos",0));
                            selected_positionBank=getActivity().getIntent().getIntExtra("sBankPos",0);
//                            autoComp.setText(bankList.get(getActivity().getIntent().getIntExtra("sBankPos",0)));
                            autoComp.setText(bankList.get(selected_positionBank));
                            bank_code= list.get(selected_positionBank+1);
                            if(!from.equals("aadhar"))
                            tType.setSelection(getActivity().getIntent().getIntExtra("sTypePos",0));
                            mobno.setText(getActivity().getIntent().getStringExtra("mobno"));
                            adharno1.setText(getActivity().getIntent().getStringExtra("adharno1"));
                            adharno2.setText(getActivity().getIntent().getStringExtra("adharno2"));
                            adharno3.setText(getActivity().getIntent().getStringExtra("adharno3"));

                        }
                        else if(callFrom.equals("reciptCW")){

//                            bank.setSelection(getActivity().getIntent().getIntExtra("sBankPos",0));
//                tType.setSelection(getActivity().getIntent().getIntExtra("sTypePos",0));
                            selected_positionBank=getActivity().getIntent().getIntExtra("sBankPos",0);
//                            autoComp.setText(bankList.get(getActivity().getIntent().getIntExtra("sBankPos",0)));
                            autoComp.setText(bankList.get(selected_positionBank));
                            bank_code= list.get(selected_positionBank+1);
                            tType.setSelection(1);
                            mobno.setText(getActivity().getIntent().getStringExtra("mobno"));
                            adharno1.setText(getActivity().getIntent().getStringExtra("adharno1"));
                            adharno2.setText(getActivity().getIntent().getStringExtra("adharno2"));
                            adharno3.setText(getActivity().getIntent().getStringExtra("adharno3"));

                        }
                    }



                }catch ( JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:" +error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJQQVlTUFJJTlQiLCJ0aW1lc3RhbXAiOjE2MTAwOTY0MjIsInBhcnRuZXJJZCI6IlBTMDAxIiwicHJvZHVjdCI6IldBTExFVCIsInJlcWlkIjoxNjEwMDk2NDIyfQ.CYe9uRoQCM75IUhyxoQQj08TDNz-uZ-kPyw33nyy0Iw");
                headers.put("Authorisedkey" , "MzNkYzllOGJmZGVhNWRkZTc1YTgzM2Y5ZDFlY2EyZTQ=");

                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString("aad1",adharno1.getText().toString());
//        outState.putString("aad2",adharno2.getText().toString());
//        outState.putString("aad3",adharno3.getText().toString());
//        outState.putString("mobile",mobno.getText().toString());
//        outState.putInt("selected_positionBank",selected_positionBank);
//        outState.putInt("selected_positionType",selected_positionType);
    }


}