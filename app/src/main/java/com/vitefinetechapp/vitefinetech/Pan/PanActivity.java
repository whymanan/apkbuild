package com.vitefinetechapp.vitefinetech.Pan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.vitefinetechapp.vitefinetech.R;


public class PanActivity extends AppCompatActivity implements View.OnClickListener{

    CardView card_r_uti_pan,card_ch_tokn_status,card_status_pan,card_pan_add_token,card_uti_login;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan);

        card_r_uti_pan = findViewById(R.id.card_r_uti_pan);
        card_ch_tokn_status = findViewById(R.id.card_ch_tokn_status);
        card_status_pan = findViewById(R.id.card_status_pan);
        card_pan_add_token = findViewById(R.id.card_pan_add_token);
        card_uti_login = findViewById(R.id.card_uti_login);

        dialog = new Dialog(this);
        card_r_uti_pan.setOnClickListener(this);
        card_ch_tokn_status.setOnClickListener(this);
        card_status_pan.setOnClickListener(this);
        card_pan_add_token.setOnClickListener(this);
        card_uti_login.setOnClickListener(this);


    }

    private void checkTokenStatus() {

        dialog.setContentView(R.layout.check_token_status);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }

    private void reuestUTIPanDialog() {

        dialog.setContentView(R.layout.reuest_uti_pan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }

    private void checkPanRegistrStatus() {

        dialog.setContentView(R.layout.check_pan_register_status);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.card_r_uti_pan:
                Intent intent_pan = new Intent(PanActivity.this,AddVLEActivity.class);
                startActivity(intent_pan);
                break;

            case R.id.card_status_pan:
                checkPanRegistrStatus();
                break;

            case R.id.card_pan_add_token:
                reuestUTIPanDialog();
                break;

            case R.id.card_ch_tokn_status:
                checkTokenStatus();
                break;

            case R.id.card_uti_login:
                Toast.makeText(PanActivity.this, "UTI Login", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}