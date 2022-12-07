package com.vitefinetechapp.vitefinetech.Payout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PayoutAccountActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerView recyclerView;
    private ArrayList<payoutData> payoutList;
    private String amt,charge,bal;
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout_account);

        btnAdd=findViewById(R.id.add_acc);
        recyclerView=findViewById(R.id.recyclerview_payout);
        payoutList=new ArrayList<>();
        Intent i=getIntent();
        amt=i.getStringExtra("pAmount");
        charge=i.getStringExtra("pCharge");
        bal=i.getStringExtra("balance");

        backbutton=findViewById(R.id.backbuttonB);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setList();

    }

    private void setList() {

        final String SHARED_PREFS = "shared_prefs";
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id", null);

        String Url="https://nextmoney.co.in/nextmoney/wallet/get_bank_list?user_id="+user_id+"&api_key=NMA001";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                response -> {
                    Log.d("payoutAcc", response.toString());
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                        Boolean st=obj.getBoolean("status");
                        if(st==true){

                            JSONArray data= obj.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {

                                JSONObject ObjI= data.getJSONObject(i);
                                payoutData pData=new payoutData(ObjI.get("account_no")+"",
                                        ObjI.getString("bank_name"),ObjI.getString("ifsc_code"),
                                        ObjI.get("phone_no")+"");
//                                Log.d("payoutest", ObjI.get("account_no")+"--"+pData.getAccount_no());
                                payoutList.add(pData);
                            }
                            PayoutRecyclerAdap adapter=new PayoutRecyclerAdap(bal,amt,charge,getApplicationContext(),payoutList);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Log.d("PayoutChError", "pCharError:" + error.getMessage());

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}