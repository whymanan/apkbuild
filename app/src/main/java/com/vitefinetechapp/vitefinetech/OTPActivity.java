package com.vitefinetechapp.vitefinetech;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPActivity extends AppCompatActivity {

    public int counter;
    TextView txt_sec;
    Button btn_resent_otp;
    ImageView img_arrow;
    Dialog dialog;
    String user_id,member_id;
    PinView firstPinView;
    public static final String VERIFY_OTP_URL = "https://vitefintech.com/viteapi/auth/verify_otp";
    public static final String CHECK_USER_URL = "https://vitefintech.com/viteapi/auth/getdata";
    public static final String RESET_PASS_URL = "https://vitefintech.com/viteapi/auth/reset_pass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        txt_sec = findViewById(R.id.txt_sec);
        btn_resent_otp = findViewById(R.id.btn_resent_otp);
        img_arrow = findViewById(R.id.img_arrow);
        firstPinView = findViewById(R.id.firstPinView);

        dialog = new Dialog(this);


        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            user_id = (String) b.get("user_id");
            member_id = (String) b.get("member_id");

        }

        btn_resent_otp.setBackgroundColor(getResources().getColor(R.color.redlight));

        btn_resent_otp.setEnabled(false);

        btn_resent_otp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              //  Toast.makeText(OTPActivity.this, "click", Toast.LENGTH_SHORT).show();

                Log.e("OTPActivity","member_id>>>>"+member_id);
                firstPinView.setText("");
               resendemail(member_id);
               startCounter();
            }
        });

        startCounter();

        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String otp = firstPinView.getText().toString();


                RequestQueue requestQueue = Volley.newRequestQueue(OTPActivity.this);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("user_id", user_id);
                    Log.e("user_id--", "user_id >>>>>" + user_id);
                    jsonObject.put("code", otp);
                    Log.e("otp---", "otp >>>>>" + otp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, VERIFY_OTP_URL, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    //TODO: Handle your response here

                                    Log.e("fff", "response????" + response);

                                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                    Log.e("response", "jsonObject===" + jsonObject.toString());

                                    String status = jsonObject.getString("status");
                                    Log.e("wallet fragment", "status ===" + status);

                                    String msg = jsonObject.getString("msg");
                                    Log.e("wallet fragment", "msg ===" + msg);


                                   // if (status.equals("1")) {
                                        dialog.setContentView(R.layout.password_reset_layout);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                                        Button btn_del_p = dialog.findViewById(R.id.btn_del_p);
                                        TextInputEditText input_edt_new_pass = findViewById(R.id.input_edt_new_pass);
                                        TextInputEditText input_edt_confirm_pass = findViewById(R.id.input_edt_confirm_pass);

                                        String new_pass = input_edt_new_pass.getText().toString();
                                        String conf_pass = input_edt_confirm_pass.getText().toString();


                                        if((new_pass.isEmpty())&& (conf_pass.isEmpty()))
                                        {
                                            Toast.makeText(OTPActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                                        }else if(new_pass.equals(conf_pass)){

                                            resetPassword(new_pass,conf_pass);

                                        }else {
                                            Toast.makeText(OTPActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

                                        }
                                        btn_del_p.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });


                                        dialog.show();
//                                    } else {
//                                        Toast.makeText(OTPActivity.this, "OTP is not match", Toast.LENGTH_SHORT).show();
//                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.print(response);

                            }
                        }, new com.android.volley.Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                error.printStackTrace();
                                Log.e("fff", "error????" + error);

                                Toast.makeText(OTPActivity.this, "OTP is not match", Toast.LENGTH_SHORT).show();
                            }


                        });

                requestQueue.add(jsonObjectRequest);


                //dialog.show();

            }
        });
    }

    private void startCounter()
    {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {

                txt_sec.setText(String.valueOf(counter) + " Sec");
                counter++;
            }

            public void onFinish() {

                txt_sec.setText("0 Sec.");
                counter = 0;
                btn_resent_otp.setEnabled(true);


                btn_resent_otp.setBackgroundColor(getResources().getColor(R.color.red));

            }
        }.start();

    }

    private void resetPassword(String new_pass, String conf_pass) {


        RequestQueue requestQueue = Volley.newRequestQueue(OTPActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("password", new_pass);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,RESET_PASS_URL, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //TODO: Handle your response here

                            Log.e("fff","response????"+response);

                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            Log.e("response", "jsonObject===" + jsonObject.toString());

                            String status = jsonObject.getString("status");
                            Log.e("wallet fragment", "status ===" + status);

                            String msg = jsonObject.getString("msg");
                            Log.e("wallet fragment", "msg ===" + msg);

                            Toast.makeText(OTPActivity.this, "Password is change "+msg, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                        Log.e("fff","error????"+error);

                        Toast.makeText(OTPActivity.this, "Incorrect User", Toast.LENGTH_SHORT).show();
                    }


                });

        requestQueue.add(jsonObjectRequest);

    }

    private void resendemail(String member_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(OTPActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("member_id", member_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,CHECK_USER_URL, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //TODO: Handle your response here

                            Log.e("fff","response????"+response);

                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            Log.e("response", "jsonObject===" + jsonObject.toString());

                            String status = jsonObject.getString("status");
                            Log.e("wallet fragment", "status ===" + status);

                            String msg = jsonObject.getString("msg");
                            Log.e("wallet fragment", "msg ===" + msg);

                            JSONObject ja_data = jsonObject.getJSONObject("data");
                            Log.e("response==","jsonArray???==="+ja_data.toString());


                            String user_id = ja_data.getString("user_id");
                            Log.e("forgetPasswordActivity", "user_id ===" + user_id);

                            String phone = ja_data.getString("phone");
                            Log.e("forgetPasswordActivity", "phone ===" + phone);

                            String email = ja_data.getString("email");
                            Log.e("forgetPasswordActivity", "email ===" + email);


//                            Intent intent = new Intent(OTPActivity.this,OTPActivity.class);
//                            intent.putExtra("user_id",user_id);
//                            startActivity(intent);

                            Toast.makeText(OTPActivity.this, "OTP ", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                        Log.e("fff","error????"+error);

                        Toast.makeText(OTPActivity.this, "Incorrect User", Toast.LENGTH_SHORT).show();
                    }


                });

        requestQueue.add(jsonObjectRequest);

    }



    private void callMethod() {

        Toast.makeText(OTPActivity.this, "Click me", Toast.LENGTH_SHORT).show();
    }


}