package com.vitefinetechapp.vitefinetech.DMT;

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


public class dmtHistoryFragment extends Fragment {

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
        View view=inflater.inflate(R.layout.fragment_dmt_history, container, false);
//        Toast.makeText(getContext(), "here1", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();

        String url="https://nextmoney.co.in/nextmoney/dmt/transectionlist?member_id="+member_id+"&api_key=NMA001&length=10&start=0";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("AepsHis", response.toString());

                    JSONObject obj = null;
                    try {
                        progressDialog.dismiss();

                        obj = new JSONObject(response);
                        if(obj.getBoolean("status")==true){
                            JSONArray data= obj.getJSONArray("data");
                            for(int i=0;i< data.length();i++){
                                JSONObject data1=data.getJSONObject(i);
                                Log.d("DMTHisFetch", data1.toString());
//                                String s=dataEach.getString("transection_response");
//                                s=s.replace("\\n","");
//                                s=s.replace("\\","");
//                                JSONObject obj1=new JSONObject(s);

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

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Log.d("ElecMakeRechargeError", "error:" + error.getMessage());

                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}