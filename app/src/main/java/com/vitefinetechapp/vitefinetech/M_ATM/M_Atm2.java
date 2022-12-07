package com.vitefinetechapp.vitefinetech.M_ATM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.vitefinetechapp.vitefinetech.R;

public class M_Atm2 extends AppCompatActivity {
  Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m__atm2);

        proceed = (Button) findViewById(R.id.proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(M_Atm2.this, M_Atm3.class);
                startActivity(i);
            }
        });
    }
}