package com.vitefinetechapp.vitefinetech.Coupon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponPurchaseActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<String> pay_mode_list, coupon_type_list;
    Spinner sp_coupon_type, sp_pay_mode;
    Button btn_purchase;
    TextInputEditText input_edt_vle_id, input_edt_coupon_qty, input_edt_total_amt;
    public static final String PURCHASE_COUPON_URL = "https://panmitra.com/api/coupon_req.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_purchase);

        sp_coupon_type = findViewById(R.id.sp_coupon_type);
        sp_pay_mode = findViewById(R.id.sp_pay_mode);
        input_edt_vle_id = findViewById(R.id.input_edt_vle_id);
        input_edt_coupon_qty = findViewById(R.id.input_edt_coupon_qty);
        input_edt_total_amt = findViewById(R.id.input_edt_total_amt);

        btn_purchase = findViewById(R.id.btn_purchase);
        btn_purchase.setOnClickListener(this);

        selectCouponType();
        selectPayMode();
    }

    private void selectCouponType() {

        coupon_type_list = new ArrayList<>();

        coupon_type_list.add(0, "Select Coupon Type");
        coupon_type_list.add(1, "Physical Coupon");
        coupon_type_list.add(2, "Electronic Coupon");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CouponPurchaseActivity.this, android.R.layout.simple_spinner_dropdown_item, coupon_type_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_coupon_type.setAdapter(dataAdapter);
        sp_coupon_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String companytype = parent.getItemAtPosition(position).toString();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void selectPayMode() {

        pay_mode_list = new ArrayList<>();

        pay_mode_list.add(0, "Payment Mode");
        pay_mode_list.add(1, "Wallet Payment");
        pay_mode_list.add(2, "Online Payment");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CouponPurchaseActivity.this, android.R.layout.simple_spinner_dropdown_item, pay_mode_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_pay_mode.setAdapter(dataAdapter);
        sp_pay_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pay_mode = parent.getItemAtPosition(position).toString();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_purchase:
                purchaseCouponNow();
                break;
        }
    }

    private void purchaseCouponNow() {

        String vle_id = input_edt_vle_id.getText().toString();
        String quantity = input_edt_coupon_qty.getText().toString();
        String total_amt = input_edt_total_amt.getText().toString();
        String coupon_type = sp_coupon_type.getSelectedItem().toString();
        String pay_mode = sp_pay_mode.getSelectedItem().toString();

        if (vle_id.toString().isEmpty()) {
            input_edt_vle_id.setError("Id is Empty");
            input_edt_vle_id.requestFocus();

        } else {

            if (sp_coupon_type.getSelectedItem().toString().equals("Select Coupon Type")) {
                Toast.makeText(this, "Please Select Coupon Type", Toast.LENGTH_SHORT).show();
            } else {


                if (quantity.toString().isEmpty()) {
                    input_edt_coupon_qty.setError("Quantity is Empty");
                    input_edt_coupon_qty.requestFocus();

                } else {

                    if (total_amt.toString().isEmpty()) {
                        input_edt_total_amt.setError("Quantity is Empty");
                        input_edt_total_amt.requestFocus();

                    } else {

                        if (sp_pay_mode.getSelectedItem().toString().equals("Payment Mode")) {
                            Toast.makeText(this, "Please Select Payment Mode", Toast.LENGTH_SHORT).show();
                        } else {

                            saveData(vle_id, coupon_type, quantity, total_amt, pay_mode);

                        }

                    }

                }
            }


        }
    }

    private void saveData(String vle_id, String coupon_type, String quantity, String total_amt, String pay_mode) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, PURCHASE_COUPON_URL,
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
                                Toast.makeText(CouponPurchaseActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(CouponPurchaseActivity.this, ""+message, Toast.LENGTH_SHORT).show();

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
                params.put("type", coupon_type);
                params.put("qty", quantity);


                return params ;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}