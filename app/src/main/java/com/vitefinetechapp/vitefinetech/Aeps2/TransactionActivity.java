package com.vitefinetechapp.vitefinetech.Aeps2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vitefinetechapp.vitefinetech.R;

public class TransactionActivity extends AppCompatActivity {

    Button transaction,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transaction = (Button)findViewById(R.id.transaction);
        history = (Button)findViewById(R.id.history);

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransactionActivity.this, SelectBankActivity2.class);
                startActivity(i);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransactionActivity.this, TransactionHistoryActivity.class);
                startActivity(i);
            }
        });
    }
}