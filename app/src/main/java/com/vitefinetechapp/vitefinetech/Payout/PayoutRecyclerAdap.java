package com.vitefinetechapp.vitefinetech.Payout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PayoutRecyclerAdap extends RecyclerView.Adapter<PayoutRecyclerAdap.MyViewHolder> {

    private Context context;
    private String amt,charge,bal;
    private ArrayList<payoutData> payoutList;
    private ProgressDialog progressDialog;

    public PayoutRecyclerAdap(String bal,String amt,String charge,Context context,ArrayList<payoutData> payoutList) {
        this.bal=bal;
        this.amt=amt;
        this.charge=charge;
        this.context = context;
        this.payoutList = payoutList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.payout_card_view,parent,false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.phone.setText(payoutList.get(position).getPhone_no());
        holder.accNo.setText(payoutList.get(position).getAccount_no());
        holder.bank.setText(payoutList.get(position).getBank_name());
        holder.ifsc.setText(payoutList.get(position).getIfsc_code());

        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(false);

                progressDialog.show();

                makePayment();
            }
        });
        holder.btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(false);

                progressDialog.show();

                final String SHARED_PREFS = "shared_prefs";
                SharedPreferences sharedPreferences;
                sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                String member_id = sharedPreferences.getString("member_id", null);
//                String bId=dmtModelArrayList.get(position).getBa_primary();

            }
        });

    }

    private void makePayment() {
        final String SHARED_PREFS = "shared_prefs";
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        String user_id = sharedPreferences.getString("user_id", null);

        HashMap<String,String> params = new HashMap<>();
        params.put("api_key","NMA001");
        params.put("user_id",user_id);
        params.put("amount",amt);
        params.put("charge",charge);
        params.put("wallet_balance",bal);

        JSONObject jsonParams=new JSONObject(params);
        Log.d("payAdap", jsonParams.toString());

        String URL="https://nextmoney.co.in/nextmoney/wallet/widthdraw";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            Log.d("payAdaRe", response.toString());
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(context, response.getString("msg"), Toast.LENGTH_LONG).show();
                            Intent i=new Intent(context, PayOutActivity.class);
                            context.startActivity(i);
                            ((Activity)context).finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("payAdError: ", "error-"+error.getMessage());
            }
        });

// add the request object to the queue to be executed
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);
    }

    @Override
    public int getItemCount() {
        return payoutList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView accNo,
                bank,
                ifsc,
        phone;
        private Button btnPay,btn_verify;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_verify=itemView.findViewById(R.id.btn_verify);
            accNo=itemView.findViewById(R.id.txt_accNo);
            bank=itemView.findViewById(R.id.txt_bank);
            ifsc=itemView.findViewById(R.id.txt_ifsc);
            phone=itemView.findViewById(R.id.txt_phone);
            btnPay=itemView.findViewById(R.id.btn_make_payment);
        }
    }
}
