package com.vitefinetechapp.vitefinetech.Recharge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RechargePlanFragment extends Fragment {


    String response;
    RecyclerView recyclerView;
    List<Recharge> list = new ArrayList<>();
    TopUpAdapter adapter;

    private TopUpAdapter.RecyclerViewClickListener listener;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;

    public RechargePlanFragment(String text) {
        response = text;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recharge_plan, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        try {
            JSONArray jsonArray = new JSONArray(response);
            Log.d("fragment-rech", jsonArray.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = jsonArray.getJSONObject(i);
                String amount = data.getString("rs");
                String detail = data.getString("desc");
                String Validity = data.getString("validity");
//                String talktime = data.getString("last");


                Recharge recharge = new Recharge();

                recharge.setTv_amount(amount);
                recharge.setTv_details(detail);

                recharge.setTv_validity(Validity);
                recharge.setTv_talktym("talktime");

                list.add(recharge);
            }
//            Log.d("fragment-rech", list.size()+"");

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            setOnClicklistener();
            adapter = new TopUpAdapter(getContext(), list, listener);
            recyclerView.setAdapter(adapter);

//                adapter.setOnItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void OnItemClick(Integer position, String amount) {
//                        Log.d("amt", amount);
//                        Log.d("posi", position.toString());
//
//                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString("amount", amount);
//                        editor.apply();
//
//                        onBackPressed();
//                    }
//                });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setOnClicklistener() {
        listener = new TopUpAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
//                Toast.makeText(getContext(), "pressed", Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    JSONObject data = jsonArray.getJSONObject(position);
//                    JSONObject data2=new JSONObject(data.toString());
//                    Toast.makeText(getContext(), "pressed:"+data2, Toast.LENGTH_SHORT).show();
                    sharedpreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("ResponseData", data.toString());
                    editor.apply();

                    getActivity().onBackPressed();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }



}