package com.vitefinetechapp.vitefinetech.GST_Registration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class GSTReturnActivity extends AppCompatActivity {

    ArrayList<String> list;
    Spinner sp_sel_return_type;
    LinearLayout linear_gr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstreturn);

        sp_sel_return_type = findViewById(R.id.sel_return_type_sp);
        linear_gr = findViewById(R.id.linear_gr);

        selectReturnType();


    }

    private void selectReturnType() {

        list = new ArrayList<>();

        list.add(0, "Select Retrun Types");
        list.add(1, "Purchase Return");
        list.add(2, "Sales Return");


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GSTReturnActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sel_return_type.setAdapter(dataAdapter);
        sp_sel_return_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String companytype = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString().equals("Select Retrun Types")) {

                   // Toast.makeText(GSTReturnActivity.this, "Select Your Return Type", Toast.LENGTH_SHORT).show();

                } else if (parent.getItemAtPosition(position).toString().equals("Purchase Return")) {

                    linear_gr.removeView(view);

                    LayoutInflater inflater = (LayoutInflater) GSTReturnActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view_ = inflater.inflate(R.layout.add_purchase_return_layout, null);
                    linear_gr.addView(view_);

                } else if (parent.getItemAtPosition(position).toString().equals("Sales Return")) {

                    linear_gr.removeView(view);

                    LayoutInflater inflater = (LayoutInflater) GSTReturnActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view_ = inflater.inflate(R.layout.add_sales_return_layout, null);
                    linear_gr.addView(view_);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}