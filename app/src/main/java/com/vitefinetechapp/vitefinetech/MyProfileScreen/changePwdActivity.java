package com.vitefinetechapp.vitefinetech.MyProfileScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.ForgetPasswordActivity;
import com.vitefinetechapp.vitefinetech.MainActivity;
import com.vitefinetechapp.vitefinetech.OTPActivity;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

public class changePwdActivity extends AppCompatActivity {

    EditText oldpwd,newpwd;
    Button btn_chpwd;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String member_id,user_id,old_pwd,new_pwd;
    public static final String KEY_USERNAMES="member_id";
    public static final String KEY_PASSWORD="password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        btn_chpwd=findViewById(R.id.btn_chpwd);
        oldpwd=findViewById(R.id.oldpwd);
        newpwd=findViewById(R.id.newpwd);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        member_id = sharedPreferences.getString("member_id", null);
        user_id=sharedPreferences.getString("user_id", null);

        btn_chpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(oldpwd.getText())){
                    Toast.makeText(changePwdActivity.this, "Enter the Old Password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(newpwd.getText())){
                    Toast.makeText(changePwdActivity.this, "Enter the New Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    old_pwd=oldpwd.getText().toString();
                    new_pwd=newpwd.getText().toString();
                    checkOldPwd();
                }

            }
        });
    }

    private void checkOldPwd() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        String Url="https://nextmoney.co.in/nextmoney/users/checkPassword";

        try {
            jsonObject.put("api_key", "NMA001");
            jsonObject.put("user_id", user_id);
            jsonObject.put("password", old_pwd);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,Url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Boolean st=response.getBoolean("status");
                            if(st==true){
                                updatepwd();
                            }
                            else {
                                String msg=response.getString("msg");
                                Toast.makeText(changePwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
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

                        Toast.makeText(changePwdActivity.this, "Incorrect User", Toast.LENGTH_SHORT).show();
                    }


                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updatepwd() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        String Url="https://nextmoney.co.in/nextmoney/users/checkPassword";

        try {
            jsonObject.put("api_key", "NMA001");
            jsonObject.put("user_id", user_id);
            jsonObject.put("password", new_pwd);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,Url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Boolean st=response.getBoolean("status");
                            String msg=response.getString("msg");
                            Toast.makeText(changePwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if(st==true){
                                logoutuser();
                            }
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

                        Toast.makeText(changePwdActivity.this, "Incorrect User", Toast.LENGTH_SHORT).show();
                    }


                });

        requestQueue.add(jsonObjectRequest);
    }

    private void logoutuser() {
        Intent i = new Intent(changePwdActivity.this, MainActivity.class);

        sharedPreferences.edit().remove(KEY_USERNAMES).apply();
        sharedPreferences.edit().remove(KEY_PASSWORD).apply();
        sharedPreferences.edit().remove("tokenname").apply();

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void openConfirmationlogoutbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit...");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(changePwdActivity.this, MainActivity.class);

                sharedPreferences.edit().remove(KEY_USERNAMES).apply();
                sharedPreferences.edit().remove(KEY_PASSWORD).apply();
                sharedPreferences.edit().remove("tokenname").apply();

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
