package com.vitefinetechapp.vitefinetech.DMT;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.Recharge.TopUPActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewDmtActivity extends AppCompatActivity {

    Spinner title_spinner;
    ArrayList<String> list;
    ImageButton backbutton;
    String title;
    Button btnSave,btnNext;
    EditText fName,lName,mNum,address;
    TextView errTv;
    LinearLayout msgLlayout;

    private ProgressDialog progressDialog;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_dmt);

        title_spinner = (Spinner)findViewById(R.id.title_spinner);
        backbutton  =     (ImageButton) findViewById(R.id.backbutton);
        btnSave=findViewById(R.id.btnSave);
        fName=findViewById(R.id.input_edt_firstname);
        lName=findViewById(R.id.input_edt_lastname);
        mNum=findViewById(R.id.input_edt_mobileno);
        address=findViewById(R.id.input_edt_customer_full_add);
        btnNext=findViewById(R.id.btnNext);
        btnNext.setVisibility(View.GONE);
        msgLlayout=findViewById(R.id.msgLayout);
        msgLlayout.setVisibility(View.GONE);
       errTv=findViewById(R.id.errorTv);
       errTv.setVisibility(View.GONE);

       progressDialog = new ProgressDialog(this);
       progressDialog.setMessage("loading...");
       progressDialog.setCancelable(false);

       backbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              onBackPressed();
//               Intent i = new Intent(AddNewDmtActivity.this, DMTScreenActivity.class);
//               startActivity(i);
               finish();
           }
       });


       list = new ArrayList<String>();

        list.add(0, "Mr.");
        list.add(1, "Ms.");
        list.add(2, "Miss");
        list.add(3, "Mis.");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewDmtActivity.this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        title_spinner.setAdapter(dataAdapter);

       title_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               title = parent.getItemAtPosition(position).toString();

           }
           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       TextWatcher textWatcher = new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(mNum.getText().length()>10 || mNum.getText().length()<10)
               {
                   errTv.setVisibility(View.VISIBLE);
               }
               else{
                   errTv.setVisibility(View.GONE);
               }
           }

           @Override
           public void afterTextChanged(Editable s) {
           }
       };
       mNum.addTextChangedListener(textWatcher);

       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(TextUtils.isEmpty(fName.getText())){
                   Toast.makeText(AddNewDmtActivity.this, "Enter a valid First Name", Toast.LENGTH_SHORT).show();
               }
               else if(TextUtils.isEmpty(lName.getText())){
                   Toast.makeText(AddNewDmtActivity.this, "Enter a valid Last Name", Toast.LENGTH_SHORT).show();
               }
               else if(TextUtils.isEmpty(mNum.getText())){
                   Toast.makeText(AddNewDmtActivity.this, "Enter a valid mobile", Toast.LENGTH_SHORT).show();
               }
               else if(TextUtils.isEmpty(address.getText())){
                   Toast.makeText(AddNewDmtActivity.this, "Enter a valid address", Toast.LENGTH_SHORT).show();
               }
               else{
                   progressDialog.show();
                   addCustomer();
               }
           }
       });

       btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(AddNewDmtActivity.this,AddnewBeneficiaryActivity.class);
               i.putExtra("cust_mob",mNum.getText());
               startActivity(i);
           }
       });

    }

    private void addCustomer() {
        String url = "https://nextmoney.co.in/nextmoney/dmt/addCustomer";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
//                    Intent i = new Intent(this, TopUPActivity.class);
//                    i.putExtra("response", response);
//                    startActivity(i);
                    try {
                        JSONObject obj=new JSONObject(response);
                        TextView msg=findViewById(R.id.msg);

                        if(obj.getBoolean("status")==true){
                            msgLlayout.setVisibility(View.VISIBLE);
                            msg.setText(obj.getString("msg"));
                            btnSave.setVisibility(View.GONE);
                            btnNext.setVisibility(View.VISIBLE);
                        }
                        else{
                            msgLlayout.setVisibility(View.VISIBLE);
                            msg.setText(obj.getString("msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            //Add parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("first_name", fName.getText().toString());
                params.put("last_name", lName.getText().toString());
                params.put("phone_no", mNum.getText().toString());
                params.put("customer_address", address.getText().toString());
                params.put("title", title);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}