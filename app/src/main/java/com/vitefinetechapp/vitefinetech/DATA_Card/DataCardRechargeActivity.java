package com.vitefinetechapp.vitefinetech.DATA_Card;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.vitefinetechapp.vitefinetech.R;

public class DataCardRechargeActivity extends AppCompatActivity {
    Spinner spinner;
    String string1 []={"Airltel","Idea","Vodaphone","BSNL","MTNL"};
    ArrayAdapter arrayAdapter;

    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_datacard_recharge );

        backbutton  =      (ImageButton) findViewById(R.id.backbutton);
        spinner     =      (Spinner)findViewById( R.id.Data_Card_spinner );

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arrayAdapter=new ArrayAdapter( DataCardRechargeActivity.this, android.R.layout.simple_list_item_1,string1 );
        spinner.setAdapter( arrayAdapter );


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