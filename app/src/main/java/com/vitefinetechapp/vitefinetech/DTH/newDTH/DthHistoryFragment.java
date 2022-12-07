package com.vitefinetechapp.vitefinetech.DTH.newDTH;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.AepsHistory;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.AepsHistoryAdapter;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DthHistoryFragment extends Fragment {

    private ProgressDialog progressDialog;

    RecyclerView aepsHisRecy;
    private ArrayList<AepsHistory> historyList;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;

    String member_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dth_history, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        member_id = sharedPreferences.getString("member_id", null);

        aepsHisRecy=view.findViewById(R.id.aepsHisRecy);
        historyList=new ArrayList<>();

        fetchHistory();
        return view;
    }

    private void fetchHistory() {


        String url = "https://nextmoney.co.in/nextmoney/Rechargebillpayment/transition";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Log.d("dmt-Hist", response.toString());

                    JSONObject obj = null;
                    try {

                        obj = new JSONObject(response);
                        if (obj.getInt("status") == 1) {
                            JSONArray data = obj.getJSONArray("all_transition");
                            for(int i=0;i< data.length();i++){
                                JSONObject data1 = data.getJSONObject(i);
                                String st=data1.getString("transection_msg");
                                if(st.equals("PENDING")){
                                    st="SUCCESS";
                                }
                                historyList.add(new AepsHistory(
                                        data1.getString("transection_id"),
                                        data1.getString("transection_type"),
                                        st,
                                        data1.getString("transection_mobile"),
                                        data1.getString("transection_amount"),
                                        data1.getString("transection_bank_code"),
                                        data1.getString("transection_bank_ifsc"),
                                        data1.getString("created")));
                            }
                            AepsHistoryAdapter adapter=new AepsHistoryAdapter(historyList);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
                            aepsHisRecy.setLayoutManager(layoutManager);
                            aepsHisRecy.setItemAnimator(new DefaultItemAnimator());
                            aepsHisRecy.setAdapter(adapter);

                        }else{
                            Toast.makeText(getContext(), obj.getString("all_transition"), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
//                        Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
//                    Toast.makeText(this, "error2:"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.d("ElecMakeRechargeError", "error:" + error.getMessage());

                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                final String SHARED_PREFS = "shared_prefs";
                SharedPreferences sharedPreferences;
                sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                String member_id = sharedPreferences.getString("member_id", null);
                String service_id = sharedPreferences.getString("user_id",null);

                params.put("member_id", member_id);
                params.put("service_id", 9+"");
                params.put("api_requist", "dth");
                params.put("api_key", "NMA001");
//                Log.d("dmt-par", params.toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}