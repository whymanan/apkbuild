package com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paysprint.onboardinglib.activities.HostActivity;
import com.vitefinetechapp.vitefinetech.MainActivity;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;


public class BoardingAeps2Fragment extends Fragment {


    EditText ed_memberId, ed_emailId, ed_phone;
    Button btn_submit;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String latitude, longitude, member_id;
    String jwt="";

    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boarding_aeps2, container, false);

        ed_memberId = view.findViewById(R.id.account_id);
        ed_emailId = view.findViewById(R.id.email_id);
        ed_phone = view.findViewById(R.id.phno);
        btn_submit = view.findViewById(R.id.submit);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        latitude = sharedPreferences.getString("latitude", null);
        longitude = sharedPreferences.getString("longitude", null);
        member_id = sharedPreferences.getString("member_id", null);

        ed_memberId.setText(member_id);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                progressDialog = new ProgressDialog(getContext());
//                progressDialog.setMessage("loading...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
////                        intent.putExtra("pApiKey", "UFMwMDE0MDhlYWViNTZlZmYzMTJhMDkxZDI5YjhmMmE5ODAzNDkw");//JWT API Key provided in credential
//                setJWT();

                Intent intent = new Intent(getActivity(), HostActivity.class);
                intent.putExtra("pId", "PS00140");//partner Id provided in credential
                intent.putExtra("pApiKey", "UFMwMDE0MDhlYWViNTZlZmYzMTJhMDkxZDI5YjhmMmE5ODAzNDkw");//JWT API Key provided in credential
                intent.putExtra("mCode", member_id);//Merchant Code
                intent.putExtra("mobile", ed_phone.getText().toString());// merchant mobile number
                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longitude);
                intent.putExtra("firm", "Vite Fintect Private limited");
                intent.putExtra("email", ed_emailId.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 999);

            }
        });


        return view;
    }

    private void setJWT() {
        String url = "https://vitefintech.com/viteapi/pay/token";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("boardJWT", response.toString());

                    JSONObject obj = null;
                    try {
                        progressDialog.dismiss();

                        obj = new JSONObject(response);
                        jwt=obj.getString("token:");
                        Log.d("boardReJWT", jwt);
                        Intent intent = new Intent(getActivity(), HostActivity.class);
                        intent.putExtra("pId", "PS00140");//partner Id provided in credential
                        intent.putExtra("pApiKey", jwt);
                        intent.putExtra("mCode", member_id);//Merchant Code
                        intent.putExtra("mobile", ed_phone.getText().toString());// merchant mobile number
                        intent.putExtra("lat", latitude);
                        intent.putExtra("lng", longitude);
                        intent.putExtra("firm", "Vite Fintect Private limited");
                        intent.putExtra("email", ed_emailId.getText().toString());
                        Log.d("boardRPut", intent.toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent, 999);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Log.d("ElecMakeRechargeError", "error:" + error.getMessage());

                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api_key", member_id);
                Log.d("boardRm", params.toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == getActivity().RESULT_OK) {
                Boolean status = data.getBooleanExtra("status", false);
                int response = data.getIntExtra("response", 0);
                String message = data.getStringExtra("message");
                String detailedResponse = "Status: $status,  " +
                        "Response: $response, " +
                        "Message: $message ";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Info");
                builder.setMessage(message);
                builder.setCancelable(false);
//                builder.setPositiveButton("Ok"){
//                    DialogInterface dialogInterface, which->dialogInterface.dismiss();
//                }

                builder.show();
            }
        }
    }
}