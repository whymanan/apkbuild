package com.vitefinetechapp.vitefinetech.Pan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddVLEActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_add_vle;
    TextInputEditText input_edt_add_vle_id, input_edt_vle_name, input_edt_vle_mobile, input_edt_vle_email,
            input_edt_vle_shop, input_edt_loc, input_edt_vle_state, input_edt_vle_pin, input_edt_vle_pan_no, input_edt_vle_uid;
    public static final String ADD_VLE_URL = "https://panmitra.com/api/add_vle.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vleactivity);

        input_edt_add_vle_id = findViewById(R.id.input_edt_add_vle_id);
        input_edt_vle_name = findViewById(R.id.input_edt_vle_name);
        input_edt_vle_mobile = findViewById(R.id.input_edt_vle_mobile);
        input_edt_vle_email = findViewById(R.id.input_edt_vle_email);
        input_edt_vle_shop = findViewById(R.id.input_edt_vle_shop);
        input_edt_loc = findViewById(R.id.input_edt_loc);
        input_edt_vle_state = findViewById(R.id.input_edt_vle_state);
        input_edt_vle_pin = findViewById(R.id.input_edt_vle_pin);
        input_edt_vle_pan_no = findViewById(R.id.input_edt_vle_pan_no);
        input_edt_vle_uid = findViewById(R.id.input_edt_vle_uid);

        btn_add_vle = findViewById(R.id.btn_add_vle);

        btn_add_vle.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add_vle:
                submit();
                break;
        }

    }

    private void submit() {

        String vle_id = input_edt_add_vle_id.getText().toString();
        String vle_name = input_edt_vle_name.getText().toString();
        String vle_mobile = input_edt_vle_mobile.getText().toString();
        String vle_email = input_edt_vle_email.getText().toString();
        String shop = input_edt_vle_shop.getText().toString();
        String loc = input_edt_loc.getText().toString();
        String state = input_edt_vle_state.getText().toString();
        String pin = input_edt_vle_pin.getText().toString();
        String pan_no = input_edt_vle_pan_no.getText().toString();
        String uid = input_edt_vle_uid.getText().toString();


        if (vle_id.toString().isEmpty()) {
            input_edt_add_vle_id.setError("id is Empty");
            input_edt_add_vle_id.requestFocus();

        } else {

            if (vle_name.toString().isEmpty()) {
                input_edt_vle_name.setError("Name is Empty");
                input_edt_vle_name.requestFocus();

            } else {

                if (vle_mobile.toString().isEmpty()) {
                    input_edt_vle_mobile.setError("Mobile is Empty");
                    input_edt_vle_mobile.requestFocus();

                } else {

                    if (vle_email.toString().isEmpty()) {
                        input_edt_vle_email.setError("Email is Empty");
                        input_edt_vle_email.requestFocus();

                    } else {


                        if (shop.toString().isEmpty()) {
                            input_edt_vle_shop.setError("Shop is Empty");
                            input_edt_vle_shop.requestFocus();

                        } else {

                            if (loc.toString().isEmpty()) {
                                input_edt_loc.setError("Location is Empty");
                                input_edt_loc.requestFocus();

                            } else {


                                if (state.toString().isEmpty()) {
                                    input_edt_vle_state.setError("State is Empty");
                                    input_edt_vle_state.requestFocus();

                                } else {

                                    if (pin.toString().isEmpty()) {
                                        input_edt_vle_pin.setError("Pin is Empty");
                                        input_edt_vle_pin.requestFocus();

                                    } else {

                                        if (uid.toString().isEmpty()) {
                                            input_edt_vle_uid.setError("User Id is Empty");
                                            input_edt_vle_uid.requestFocus();

                                        } else {

                                            if (pan_no.toString().isEmpty()) {
                                            input_edt_vle_pan_no.setError("Pan Number is Empty");
                                            input_edt_vle_pan_no.requestFocus();

                                        } else {


                                                saveInformationToServer(vle_id,vle_name,vle_mobile,vle_email,shop,loc,state,pin,uid,pan_no);


                                            }
                                            }
                                        }

                                        }

                                    }
                            }

                        }
                    }

                }
            }

        }

    private void saveInformationToServer(String vle_id, String vle_name, String vle_mobile, String vle_email, String shop, String loc, String state, String pin, String uid, String pan_no) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, ADD_VLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        VolleyLog.d("ResponseLogin:" + response.toString());
                        Log.e("response ===", "" + response.toString());


                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("obj==", "" + obj.toString());
                            String status = obj.getString("status");
                            Log.e("status==", "" + status.toString());

                            String message = obj.getString("message");
                            Log.e("message==", "" + message.toString());

                            if(status.equals("FAILED"))
                            {
                                Toast.makeText(AddVLEActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(AddVLEActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error ===", "" + error.toString());

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params  = new HashMap<>();
                params.put("api_key", "657bc3-43c8b1-f22638-02a376-f46a82");
                params.put("vle_id", vle_id);
                params.put("vle_name", vle_name);
                params.put("vle_mob", vle_mobile);
                params.put("vle_email", vle_email);
                params.put("vle_shop", shop);
                params.put("vle_loc", loc);
                params.put("vle_state", state);
                params.put("vle_pin", pin);
                params.put("vle_uid", uid);
                params.put("vle_pan", pan_no);


                return params ;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}

