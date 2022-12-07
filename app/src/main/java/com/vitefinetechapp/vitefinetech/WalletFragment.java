package com.vitefinetechapp.vitefinetech;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.KYCDetails.KYCManagementActivity;
import com.vitefinetechapp.vitefinetech.Payout.PayOutActivity;
import com.vitefinetechapp.vitefinetech.wallet.LedgerAdapter;
import com.vitefinetechapp.vitefinetech.wallet.LedgerModel;
import com.vitefinetechapp.vitefinetech.wallet.RequestBalanceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WalletFragment extends Fragment {
    private DashboardActivity appCompatActivity;

    EditText fromDate, toDate;
    int year,month,day;
    String bal;

    TextView txt_withdrawal,txtAvailBalance,txt_add_bal,txt_req_bal,txtBalance;
    Button btn_go;
    RecyclerView recycler_walllet_ledger;
    // Arraylist for storing data
    private ArrayList<LedgerModel> ledgerModelArrayList;
    LinearLayoutManager linearLayoutManager;
    LedgerModel ledgerModel;
    LedgerAdapter ledgerAdapter;
    ProgressBar loadingL;

    private int mYear;
    private int mMonth;
    private int mDay;
    Calendar c;
    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;
    String role_id,user_id,memberid,fromD,toD ;


    public WalletFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appCompatActivity = (DashboardActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
//        getWalletBalance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        fromDate=  view.findViewById( R.id.edt_from_date_id );
        toDate=     view.findViewById( R.id.edt_to_date_id );
        btn_go = view.findViewById(R.id.btn_go);

        txt_withdrawal = view.findViewById(R.id.txt_withdrawal);
        txtAvailBalance = view.findViewById(R.id.txt_avail_bal);
        txt_add_bal = view.findViewById(R.id.txt_add_req_bal);
        txt_req_bal = view.findViewById(R.id.txt_req_bal);
        txtBalance=view.findViewById(R.id.txt_avail_bal);

        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        role_id = sharedpreferences.getString("rollid",null);
        Log.e("Wallet fragment","role_id"+role_id);

        user_id = sharedpreferences.getString("user_id",null);
        Log.e("Wallet fragment","user_id"+user_id);
        memberid = sharedpreferences.getString("member_id", null);
        setBalance();


        recycler_walllet_ledger = view.findViewById(R.id.recycler_walllet_ledger);
        loadingL = view.findViewById(R.id.loadingL);
        //   dt = view.findViewById(R.id.dt);
        ledgerModelArrayList = new ArrayList<>();

        String avail_bal = txtAvailBalance.getText().toString();
        Log.e("HomeFragment","avail_bal"+avail_bal);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar= Calendar.getInstance() ;
        format.format(calendar.getTime());
        Log.e("","");



        txt_add_bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), RequestBalanceActivity.class);
                startActivity(i);

            }
        });

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(getActivity().getApplicationContext(), KYCManagementActivity.class);
//                startActivity(i);
                if(TextUtils.isEmpty(fromDate.getText()) || TextUtils.isEmpty(toDate.getText())){
                    Toast.makeText(appCompatActivity, "Select from date & to date", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("fromD:toD", fromD+":"+toD);
                    ArrayList<LedgerModel> newList= new ArrayList<>();
                    for(int i=0;i<ledgerModelArrayList.size();i++){
                        String s=ledgerModelArrayList.get(i).getLedger_date();
                        if(s.compareTo(fromD)>0 && s.compareTo(toD)<0){
                            newList.add(ledgerModelArrayList.get(i));
                        }
                    }
                    Collections.reverse(newList);
                    ledgerAdapter= new LedgerAdapter(getContext(), newList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recycler_walllet_ledger.setLayoutManager(linearLayoutManager);
                    recycler_walllet_ledger.setAdapter(ledgerAdapter);
                    ledgerAdapter.notifyDataSetChanged();
                }

            }
        });

        fromDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                fromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                if((monthOfYear+1)/10==0){
                                    if(dayOfMonth/10==0){
                                        fromD=year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                    }
                                    else{
                                        fromD=year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    }
                                }
                                else{
                                    if(dayOfMonth/10==0){
                                        fromD=year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                    }
                                    else{
                                        fromD=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    }
                                }



//                                Log.d("fromDateSel", fromD);
//                                if(fromD.compareTo("2022-03-31 21:41:40")>0){
//                                    Log.d("fromDate", "greater ");
//                                }
//                                else{
//                                    Log.d("fromDate", "Lesser");
//                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        } );


        toDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                toDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                if((monthOfYear+1)/10==0){
                                    if(dayOfMonth/10==0){
                                        toD=year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                    }
                                    else{
                                        toD=year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    }
                                }
                                else{
                                    if(dayOfMonth/10==0){
                                        toD=year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                    }
                                    else{
                                        toD=year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    }
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        } );

        txt_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_payout = new Intent(getActivity().getApplicationContext(), PayOutActivity.class);
                intent_payout.putExtra("avail_bal",bal);
                startActivity(intent_payout);
            }
        });

        getLdegerHistoryData();

        return view;
    }

    private void setBalance() {
        String url = "https://nextmoney.co.in/nextmoney/Rechargebillpayment/wallet";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("setBalance", response);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        Log.d("setBlanceWal:", obj.getString("response"));
                        int res=obj.getInt("response");
                        int st=obj.getInt("status");
                        bal="";
                        if(st==1 && res==1){
                            bal=obj.getString("wallet_balance");
                        }
                        else if(st==0 && res==1){
                            bal="----.--";
                        }
                        else{
                            bal="----.-";
                        }
                        txtBalance.setText(bal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("setBalanceerror", "error:" + error.getMessage());
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("member_id", memberid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

//    private void getWalletBalance() {
//
//        String url_bal = "https://vitefintech.com/viteapi/wallet/wallet_balance";
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        JSONObject object = new JSONObject();
//
//        //input your API parameters
//        try {
//            object.put("user_id",user_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//
//        }
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_bal, object,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Log.e("response","response==="+response.toString());
//
//                        try {
//                            JSONObject jsonObject= new JSONObject(String.valueOf(response));
//                            Log.e("response","jsonObject==="+jsonObject.toString());
////                            Toast.makeText(appCompatActivity, response.toString(), Toast.LENGTH_SHORT).show();
//
//                            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
//                            Log.e("response==","jsonArray???==="+jsonArray.toString());
//
//                            for (int j = 0; j < jsonArray.length(); j++) {
//
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
//
//                                String balance = jsonObject1.getString("balance");
//                                Log.e("wallet fragment", "balance ===" + balance);
//
//                                String total = jsonObject1.getString("total");
//                                Log.e("wallet fragment", "total ===" + total);
//
//                                txtAvailBalance.setText("Rs. "+balance);
//                                txt_req_bal.setText("Rs."+total);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("walletFragment","error=="+error.toString());
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }



    private void getLdegerHistoryData()
    {
//
        Log.d("ldger1", user_id+"::"+role_id);
        final String LADGER_API = "https://nextmoney.co.in/nextmoney/wallet/get_ledger?user_id="+user_id+"&api_key=NMA001&role_id="+role_id;

        JsonObjectRequest stringRequest=new JsonObjectRequest(Request.Method.GET, LADGER_API,null,
                jsonObject -> {

                try {
//                    Log.e("ldgerres",""+response);

                    loadingL.setVisibility(View.GONE);
//                    JSONObject jsonObject= new JSONObject(response);
                    Boolean status = jsonObject.getBoolean("status");
                    Log.e("wallet fragment","status ==="+status);

                    String msg = jsonObject.getString("msg");
                    Log.e("wallet fragment","msg ==="+msg);

                    JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                    Log.e("wallet fragment","jsonArrayData ==="+jsonArrayData);

                    List<String> purchasesList = new ArrayList<>();
                    for (int j = 0; j < jsonArrayData.length(); j++)
                    {


                        JSONObject imageListItem = jsonArrayData.getJSONObject(j);
                        int wallet_transaction_id = imageListItem.getInt("wallet_transaction_id");
//                        Log.d("mylog", "i =" + j + " and wallet_transaction_id =" + wallet_transaction_id);

                        int wallet_id = imageListItem.getInt("wallet_id");
//                        Log.d("mylog", "i =" + j + " and wallet_id =" + wallet_id);

                        int member_to = imageListItem.getInt("member_to");
//                        Log.d("mylog", "i =" + j + " and wallet_transaction_id =" + member_to);

                        int member_from = imageListItem.getInt("member_from");
//                        Log.d("mylog", "i =" + j + " and member_from =" + member_from);

                        int service_id = imageListItem.getInt("service_id");
//                        Log.d("mylog", "i =" + j + " and service_id =" + service_id);

                        int surcharge = imageListItem.getInt("surcharge");
//                        Log.d("mylog", "i =" + j + " and surcharge =" + surcharge);

                        String refrence = imageListItem.getString("refrence");
//                        Log.d("mylog", "i =" + j + " and refrence =" + refrence);

                        String mode = imageListItem.getString("mode");
//                        Log.d("mylog", "i =" + j + " and mode =" + mode);

                        String bank = imageListItem.getString("bank");
//                        Log.d("mylog", "i =" + j + " and bank =" + bank);

                        String stock_type = imageListItem.getString("stock_type");
//                        Log.d("mylog", "i =" + j + " and stock_type =" + stock_type);

                        int amount = imageListItem.getInt("amount");
//                        Log.d("mylog", "i =" + j + " and amount =" + amount);

                        int balance = imageListItem.getInt("balance");
//                        Log.d("mylog", "i =" + j + " and balance =" + balance);

                        int closebalance = imageListItem.getInt("closebalance");
//                        Log.d("mylog", "i =" + j + " and closebalance =" + closebalance);

                        int commission = imageListItem.getInt("commission");
//                        Log.d("mylog", "i =" + j + " and commission =" + commission);

                        String type = imageListItem.getString("type");
//                        Log.d("mylog", "i =" + j + " and type =" + type);

                        String trans_type = imageListItem.getString("trans_type");
//                        Log.d("mylog", "i =" + j + " and trans_type =" + trans_type);

                        String updated = imageListItem.getString("updated");
//                        Log.d("mylog", "i =" + j + " and updated =" + updated);

                        String date = imageListItem.getString("date");
//                        Log.d("mylog", "i =" + j + " and date =" + date);

                        String narration = imageListItem.getString("narration");
//                        Log.d("mylog", "i =" + j + " and narration =" + narration);

                        String status_ = imageListItem.getString("status");
//                        Log.d("mylog", "i =" + j + " and status =" + status_);

                        String member1 = imageListItem.getString("member1");
//                        Log.d("mylog", "i =" + j + " and member1 =" + member1);

                        String member2 = imageListItem.getString("member2");
//                        Log.d("mylog", "i =" + j + " and member2 =" + member2);


                        ledgerModel = new LedgerModel();

                        ledgerModel.setLedger_wallet_transaction_id(String.valueOf(wallet_transaction_id));
                        ledgerModel.setLedger_wallet_id(String.valueOf(wallet_id));
                        ledgerModel.setLedger_member_to(String.valueOf(member_to));
                        ledgerModel.setLedger_member_from(String.valueOf(member_from));
                        ledgerModel.setLedger_service_id(String.valueOf(service_id));
                        ledgerModel.setLedger_surcharge(String.valueOf(surcharge));
                        ledgerModel.setLedger_ref(String.valueOf(refrence));
                        ledgerModel.setLedger_mode(String.valueOf(mode));
                        ledgerModel.setLedger_bank(String.valueOf(bank));
                        ledgerModel.setLedger_stock_type(String.valueOf(stock_type));
                        ledgerModel.setLedger_amt(String.valueOf(amount));
                        ledgerModel.setLedger_balance(String.valueOf(balance));
                        ledgerModel.setLedger_closebalance(String.valueOf(closebalance));
                        ledgerModel.setLedger_commision(String.valueOf(commission));
                        ledgerModel.setLedger_type(String.valueOf(type));
                        ledgerModel.setLedger_trans_type(String.valueOf(trans_type));
                        ledgerModel.setLedger_updated(String.valueOf(updated));
                        ledgerModel.setLedger_date(String.valueOf(date));
                        ledgerModel.setLedger_narration(String.valueOf(narration));
                        ledgerModel.setLedger_status(String.valueOf(status_));
                        ledgerModel.setLedger_member1(String.valueOf(member1));
                        ledgerModel.setLedger_member2(String.valueOf(member2));
                        ledgerModel.setJsonObject(String.valueOf(imageListItem));


                        ledgerModelArrayList.add(ledgerModel);
                    }
                    Log.d("ldgerres", "here");

                    ledgerAdapter= new LedgerAdapter(getContext(), ledgerModelArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                    recycler_walllet_ledger.setLayoutManager(linearLayoutManager);
                    recycler_walllet_ledger.setItemAnimator(new DefaultItemAnimator());
                    recycler_walllet_ledger.setAdapter(ledgerAdapter);
//                    ledgerAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    loadingL.setVisibility(View.GONE);
                    Toast.makeText(appCompatActivity, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error==","error"+error);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}





