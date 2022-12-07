package com.vitefinetechapp.vitefinetech.Aeps2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.vitefinetechapp.vitefinetech.Aeps.BankActivity2;
import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddNewAdharPayment2 extends AppCompatActivity {
    Spinner s1;
    EditText amount,adharno,mobno;
    TextView txtamttxt;
    ArrayList<String> list;
    Button submit,scan;
    ImageButton backbutton;
//    private IntentIntegrator qrScan;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String transactionType,bank_code;
    LinearLayout scan_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_adhar_payment);

        s1         =    (Spinner)findViewById(R.id.tras_spinner);
        amount     =    (EditText) findViewById(R.id.amount);
        adharno    =    (EditText) findViewById(R.id.adharno);
        txtamttxt  =    (TextView) findViewById(R.id.txtamt);
        submit     =    (Button) findViewById(R.id.submit);
        backbutton =   (ImageButton) findViewById(R.id.backbutton);
        scan_linear = (LinearLayout) findViewById(R.id.scan_linear);
        mobno      =   (EditText) findViewById(R.id.mobno);

        list = new ArrayList<String>();
        selectTransactionType();
//        typeadharno();

        bank_code    = getIntent().getStringExtra("bank_code");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactiontype_     =   s1.getSelectedItem().toString();
                String adhrno_              =   adharno.getText().toString();
                String mobno_               =   mobno.getText().toString();
                String amount_              =   amount.getText().toString();

                if(transactiontype_.equals("Select Transaction Types")){
                    Toast.makeText(AddNewAdharPayment2.this, "Please select transaction type", Toast.LENGTH_SHORT).show();
                }else if(adhrno_.toString().isEmpty()) {
                    adharno.setError("Please enter adhar number");
                    adharno.requestFocus();
                }else if (adhrno_.trim().length() < 12) {
                    adharno.setError("Enter a twelve digit adhar number");
                    adharno.requestFocus();
                }else if (adhrno_.trim().length() > 12) {
                    adharno.setError("Enter a twelve digit adhar number");
                    adharno.requestFocus();
                }else if(mobno_.toString().isEmpty()) {
                    mobno.setError("Please enter mobile number");
                    mobno.requestFocus();
                }else if(mobno_.trim().length() < 10){
                    mobno.setError("Please enter 10 digit mobile number");
                    mobno.requestFocus();
                } else if (mobno_.trim().length() > 10) {
                    mobno.setError("Please enter 10 digit mobile number");
                    mobno.requestFocus();
                }else {
                    Intent i = new Intent(AddNewAdharPayment2.this, BioMetricActivity2.class);
                    i.putExtra("transaction_type", transactionType.toString());
                    i.putExtra("adhar_no", adharno.getText().toString());
                    i.putExtra("mobile_no", mobno.getText().toString());
                    i.putExtra("amount", amount.getText().toString());
                    i.putExtra("bank_code", bank_code.toString());
                    startActivity(i);
                }
            }
        });



        scan_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNewAdharPayment2.this, ScanActivity2.class);
                startActivityForResult(i,1);
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 ) {
            if (resultCode == RESULT_OK) {
                String useruid = data.getStringExtra("editvalue");
                String username = data.getStringExtra("editvalue2");
                adharno.setText(useruid);
//                name.setText(username);

            }
        }
    }


    public void selectTransactionType(){
        list.add(0, "Select Transaction Types");
        list.add(1, "Cash Withdrawal");
        list.add(2, "Balance Enquiry");
        list.add(3, "Mini Statement");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewAdharPayment2.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String transactiontype = parent.getItemAtPosition(position).toString();
                if(transactiontype.equals("Balance Enquiry")){
                    amount.setVisibility(View.GONE);
                    txtamttxt.setVisibility(View.GONE);

                    transactionType  = "BE";
                }else if(transactiontype.equals("Cash Withdrawal")){
                    amount.setVisibility(View.VISIBLE);
                    txtamttxt.setVisibility(View.VISIBLE);

                    transactionType  = "CW";
                }else if(transactiontype.equals("Mini Statement")){
                    amount.setVisibility(View.GONE);
                    txtamttxt.setVisibility(View.GONE);

                    transactionType  = "MS";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void typeadharno(){
        adharno.addTextChangedListener(new TextWatcher() {
            private final String space = " "; // you can change this to whatever you want
            private final Pattern pattern = Pattern.compile("^(\\d{4}"+space+"{1}){0,3}\\d{1,4}$"); // check whether we need to modify or not
            @Override
            public void onTextChanged(CharSequence s, int st, int be, int count) {
                String currentText = adharno.getText().toString();
                if (currentText.isEmpty() || pattern.matcher(currentText).matches())
                    return; // no need to modify
                String numbersOnly = currentText.trim().replaceAll("[^\\d.]", "");; // remove everything but numbers
                String formatted = "";
                for(int i = 0; i < numbersOnly.length(); i += 4)
                    if (i + 4 < numbersOnly.length())
                        formatted += numbersOnly.substring(i,i+4)+space;
                    else
                        formatted += numbersOnly.substring(i);
                adharno.setText(formatted);
                adharno.setSelection(adharno.getText().toString().length());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable e) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}