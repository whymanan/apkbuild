package com.vitefinetechapp.vitefinetech.Aeps3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vitefinetechapp.vitefinetech.Aeps2.ScanActivity2;
import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class AddNewAdharPayment3 extends AppCompatActivity {

    Spinner s1;
    EditText amount,adharno,name;
    TextView txtamttxt;
    ArrayList<String> list;
    Button submit,scan;
    ImageButton backbutton;
//    private IntentIntegrator qrScan;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String value,names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_adhar_payment3);

        s1         =    (Spinner)findViewById(R.id.tras_spinner);
        amount     =    (EditText) findViewById(R.id.amount);
        adharno     =    (EditText) findViewById(R.id.adharno);
        name        =    (EditText) findViewById(R.id.name);
        txtamttxt  =    (TextView) findViewById(R.id.txtamt);
        submit     =    (Button) findViewById(R.id.submit);
        scan       =    (Button) findViewById(R.id.scan);
        backbutton  =   (ImageButton) findViewById(R.id.backbutton);

        list = new ArrayList<String>();
        selectTransactionType();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNewAdharPayment3.this, BioMetricActivity3.class);
                i.putExtra("adharcard_number", adharno.getText().toString());
                startActivity(i);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddNewAdharPayment3.this, ScanActivity2.class);
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
                name.setText(username);

            }
        }
    }


    public void selectTransactionType(){
        list.add(0, "Select Transaction Types");
        list.add(1, "Cash Withdrawal");
        list.add(2, "Balance Enquiry");
        list.add(3, "Mini Statement");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewAdharPayment3.this, android.R.layout.simple_spinner_dropdown_item, list);
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
                }else{
                    amount.setVisibility(View.VISIBLE);
                    txtamttxt.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
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