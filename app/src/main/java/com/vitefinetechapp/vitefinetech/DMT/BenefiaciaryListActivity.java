package com.vitefinetechapp.vitefinetech.DMT;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BenefiaciaryListActivity extends AppCompatActivity implements View.OnClickListener {

    DMTModel dmtModel;
    private ArrayList<DMTModel> dmtModelArrayList;
    RecyclerView dmt_recyclerview;
    DMTAdapter dmtAdapter;
    TextView txt_status, customer_name, customer_mobile;
    Button add_benef;
    ImageButton imgBtn;
    String cust_mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_benificiary_list);

        dmt_recyclerview = findViewById(R.id.recyclerview_dmt);
        customer_name = findViewById(R.id.customer_name);
        customer_mobile = findViewById(R.id.customer_mobile);
        add_benef = findViewById(R.id.add_benef);
        imgBtn = findViewById(R.id.backbuttonB);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Intent intent = getIntent();
        cust_mobile = intent.getStringExtra("customer_mobile");
        String first_name1 = intent.getStringExtra("first_name");
        String last_name2 = intent.getStringExtra("last_name");
        String Status = intent.getStringExtra("Status");
        String cUid = intent.getStringExtra("userId");

        add_benef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BenefiaciaryListActivity.this, AddnewBeneficiaryActivity.class);
                i.putExtra("cust_mobile",cust_mobile);
                startActivity(i);
            }
        });

        Log.d("dmtbstat", Status);
        if (Status.equals("Faild")) {
            Toast.makeText(this, "No Benificiary", Toast.LENGTH_SHORT).show();
        } else {
            String jsonArray = intent.getStringExtra("jsonArray");
            dmt_recyclerview.setLayoutManager(new LinearLayoutManager(BenefiaciaryListActivity.this));

            // we are initializing our adapter class and passing our arraylist to it.
//            dmtAdapter = new DMTAdapter(getApplicationContext(), dmtModelArrayList);
//            dmtModel = new DMTModel();
            dmtModelArrayList = new ArrayList<>();

            try {
                JSONArray array = new JSONArray(jsonArray);
//                System.out.println(array.toString(2));
                Log.d("DMTBENlis", ":"+jsonArray);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);

                    String ba_primary = jsonObject.getString("ba_primary");
//                    Log.e("BenificiaryListActivity", "ba_primary ??===" + ba_primary);
//
//                    String title = jsonObject.getString("title");
//                    Log.e("BenificiaryListActivity", "title ===" + title);
//
//                    String first_name = jsonObject.getString("first_name");
//                    Log.e("BenificiaryListActivity", "first_name ===" + first_name);
//
//                    String last_name = jsonObject.getString("last_name");
//                    Log.e("BenificiaryListActivity", "last_name ===" + last_name);
//
                    String beneficiary_name = jsonObject.getString("beneficiary_name");
//                    Log.e("BenificiaryListActivity", "beneficiary_name ===" + beneficiary_name);
//
                    String beneficiaryId = jsonObject.getString("beneficiaryId");
//                    Log.e("BenificiaryListActivity", "beneficiaryId ===" + beneficiaryId);
//
//                    String customer_mobile = jsonObject.getString("customer_mobile");
//                    Log.e("BenificiaryListActivity", "customer_mobile ===" + customer_mobile);
//
                    String beneficiary_mobile = jsonObject.getString("beneficiary_mobile");
//                    Log.e("BenificiaryListActivity", "beneficiary_mobile ===" + beneficiary_mobile);
//
                    String beneficiary_account_number = jsonObject.getString("beneficiary_account_number");
//                    Log.e("BenificiaryListActivity", "beneficiary_account_number ===" + beneficiary_account_number);
//
                    String beneficiary_ifsc = jsonObject.getString("beneficiary_ifsc");
//                    Log.e("BenificiaryListActivity", "beneficiary_ifsc ===" + beneficiary_ifsc);
//
//                    String beneficiary_bank_name = jsonObject.getString("beneficiary_bank_name");
//                    Log.e("BenificiaryListActivity", "beneficiary_bank_name ===" + beneficiary_bank_name);
//
//                    String created = jsonObject.getString("created");
//                    Log.e("BenificiaryListActivity", "created ===" + created);
//
//                    String updated = jsonObject.getString("updated");
//                    Log.e("BenificiaryListActivity", "updated ===" + updated);
//
//                    String mobile_verify = jsonObject.getString("mobile_verify");
//                    Log.e("BenificiaryListActivity", "mobile_verify ===" + mobile_verify);

                    dmtModel = new DMTModel();

                    dmtModel.setAccount_name(beneficiary_name);
                    dmtModel.setIfsc_code(beneficiary_ifsc);
                    dmtModel.setPhone_no(beneficiary_mobile);
                    dmtModel.setUser_detail_id(cUid);
                    dmtModel.setAcct_no(beneficiary_account_number);
                    dmtModel.setBeneficiary_id(beneficiaryId);
                    dmtModel.setBa_primary(ba_primary);

                    dmtModel.setStatus(Status);


                    dmtModelArrayList.add(dmtModel);
                    dmtAdapter = new DMTAdapter(cust_mobile,getApplicationContext(), dmtModelArrayList);
                    dmt_recyclerview.setAdapter(dmtAdapter);
                    dmtAdapter.notifyDataSetChanged();


                }

            } catch (JSONException e) {
                Log.d("DMTBENlisterr", ":"+e.getMessage());
                e.printStackTrace();
            }
        }


        customer_name.setText("Customer Name : " + first_name1 + " " + last_name2);
        customer_mobile.setText("Customer Mobile : " + cust_mobile);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_benef:
                dmtTransaction();
                break;

            case R.id.backbuttonB:
                onBackPressed();
                break;

        }
    }

    private void dmtTransaction() {
    }
}