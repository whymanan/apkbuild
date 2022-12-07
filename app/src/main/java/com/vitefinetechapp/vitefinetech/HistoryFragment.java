package com.vitefinetechapp.vitefinetech;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vitefinetechapp.vitefinetech.History.HistoryAdapter;
import com.vitefinetechapp.vitefinetech.History.HistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    private DashboardActivity appCompatActivity;
    BottomNavigationView bottomNavigationView;

    private RecyclerView history_recycler_view;

    // Arraylist for storing data
    private ArrayList<HistoryModel> historyModelArrayList;
    HistoryAdapter historyAdapter;
    HistoryModel historyModel;
    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;
    String member_id ;

    public HistoryFragment() {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        history_recycler_view = view.findViewById(R.id.recycler_view_history);
        // here we have created new array list and added data to it.
        historyModelArrayList = new ArrayList<>();

        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString("member_id",null);
        Log.e("PayoutActivity","member_id"+member_id);

        history_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));


        historyAdapter = new HistoryAdapter(getContext(), historyModelArrayList);
        historyModel = new HistoryModel();

        getHistoryData();
        return view;
    }



    private void getHistoryData() {
        final String HISTORY_API = "https://vitefintech.com/viteapi/history?member_id="+member_id;
//        final String HISTORY_API = "https://nextmoney.co.in/nextmoney/history?member_id="+member_id;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HISTORY_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray= new JSONArray(response);
                    Log.e("response",""+response);
                    // JSONArray array=jsonObject.getJSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject ob=jsonArray.getJSONObject(i);
                        String transection_amount = ob.getString("transection_amount");
                        Log.e("History fragment","transection_amoun ==="+transection_amount);

                        String transection_bank_code = ob.getString("transection_bank_code");
                        Log.e("History fragment","transection_bank_code ==="+transection_bank_code);

                        String transection_bank_ifsc = ob.getString("transection_bank_ifsc");
                        Log.e("History fragment","transection_bank_ifsc ==="+transection_bank_ifsc);

                        String transection_id = ob.getString("transection_id");
                        Log.e("History fragment","transection_id ==="+transection_id);

                        historyModel = new HistoryModel();

                        historyModel.setCourse_name(transection_bank_code);
                        historyModel.setAmount(transection_amount);
                        historyModel.setPerson_name(transection_id);
//                        historyModel.setTv_validity(Validity);
//                        historyModel.setTv_talktym(talktime);


                        historyModelArrayList.add(historyModel);
                        historyAdapter = new HistoryAdapter(getContext(), historyModelArrayList);
                        history_recycler_view.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
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

