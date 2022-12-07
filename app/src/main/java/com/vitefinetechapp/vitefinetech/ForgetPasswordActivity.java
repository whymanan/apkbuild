package com.vitefinetechapp.vitefinetech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button btn_s_otp;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    EditText edtEmail;
    String member_id ;
    public static final String CHECK_USER_URL = "https://vitefintech.com/viteapi/auth/getdata";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btn_s_otp = findViewById(R.id.btn_s_otp);
        edtEmail = findViewById(R.id.edtEmail);

        btn_s_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ForgetPasswordActivity.this,OTPActivity.class);
//                startActivity(intent);
                member_id = edtEmail.getText().toString();

                if(!(member_id.isEmpty()))
                {
                    sendemail(member_id);

                }else {

                    edtEmail.setError("Email is empty");
                }


            }
        });


    }

    private void sendemail(String member_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(ForgetPasswordActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("member_id", member_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,CHECK_USER_URL, jsonObject, new Response.Listener<JSONObject>() {

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


                            Intent intent = new Intent(ForgetPasswordActivity.this,OTPActivity.class);
                            intent.putExtra("user_id",user_id);
                            intent.putExtra("member_id",member_id);
                            startActivity(intent);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                        Log.e("fff","error????"+error);

                        Toast.makeText(ForgetPasswordActivity.this, "Incorrect User", Toast.LENGTH_SHORT).show();
                        }


                });

        requestQueue.add(jsonObjectRequest);
    }
}