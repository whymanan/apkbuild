package com.vitefinetechapp.vitefinetech.M_ATM;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.paysprint.microatmlib.activities.HostActivity;
import com.vitefinetechapp.vitefinetech.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MatmWithdrawFragment extends Fragment {

    Button btn_cash;
    EditText et_amt,et_mob,et_response;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String latitude,longitude,member_id;
    int _resultCode=999;
    Spinner smanu;
    int manId;
    String man,mobile_num;
    String partnerId,apiKey;
    String refernceNumber;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_matm_withdraw, container, false);

        btn_cash=view.findViewById(R.id.btn_withdraw);
        et_amt=view.findViewById(R.id.et_amount);
        et_mob=view.findViewById(R.id.et_mob);
        smanu=view.findViewById(R.id.smanu);
        et_response=view.findViewById(R.id.et_response);
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        latitude          = sharedPreferences.getString("latitude", null);
        longitude         = sharedPreferences.getString("longitude", null);
        member_id = sharedPreferences.getString("member_id",null);

        ArrayList<String> manufacturerList=new ArrayList<>();
        manufacturerList.add("-Select Device Manufacturer-");
        manufacturerList.add("AF60s");
        manufacturerList.add("MP63");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, manufacturerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smanu.setAdapter(dataAdapter);

        btn_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_mob.getText())){
                    Toast.makeText(getContext(), "Enter Mobile", Toast.LENGTH_SHORT).show();
                }
                else if(smanu.getSelectedItem().toString().equals("-Select Device Manufacturer-")){
                    Toast.makeText(getContext(), "Select a Device Manufacturer", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(et_amt.getText())){
                    Toast.makeText(getContext(), "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else{
                    String pattern = "yyyyMMddHHmmss";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String date = simpleDateFormat.format(new Date());
                    String referenceNo = date;
                    refernceNumber = "INC"+referenceNo;
                    partnerId="PS00140";
                    apiKey="UFMwMDE0MDhlYWViNTZlZmYzMTJhMDkxZDI5YjhmMmE5ODAzNDkw";

                    mobile_num=et_mob.getText().toString();
                    Intent intent = new Intent(getContext(), HostActivity.class);
                    intent.putExtra("partnerId", "PS00140");											//PARTNER ID PROVIDED IN CREDENTIAL
                    intent.putExtra("apiKey", "UFMwMDE0MDhlYWViNTZlZmYzMTJhMDkxZDI5YjhmMmE5ODAzNDkw");												   //JWT KEY PROVIDED IN CREDENTIAL
                    intent.putExtra("merchantCode", member_id);											// API PARTNER END UNQIUE MERCHANT CODE used during onboarding
                    intent.putExtra("transactionType", "CW"); 										// BE for Balance Enquiry and CW for Cash Withdrawal
                    intent.putExtra("amount", et_amt.getText().toString()); 							// Amount for Cash Withdrawal (Minimum 100 and maximum 10000)
                    intent.putExtra("remarks", "Test Transaction"); 									// Transaction remarks
                    intent.putExtra("mobileNumber", et_mob.getText().toString()); 									// Customer Mobile Number
                    intent.putExtra("referenceNumber", refernceNumber); 													//unqiue txn Reference Number of api partner end
                    intent.putExtra("latitude", latitude); 										// Latitude
                    intent.putExtra("longitude", longitude); 										// Longitude
                    intent.putExtra("subMerchantId", member_id); 									// API PARTNER END UNQIUE MERCHANT CODE used during onboarding
                    intent.putExtra("deviceManufacturerId", manId);									// value should be 1 for AF60s and 2 for MP63
//                    intent.putExtra("deviceManufacturerId", 2);
                    startActivityForResult(intent, _resultCode);
                }
            }
        });

        smanu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                man = parent.getItemAtPosition(position).toString();
                if(man.equals("AF60s")){
                    manId=1;
                }else if(man.equals("MP63")){
                    manId=2;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == _resultCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Boolean status = data.getBooleanExtra("status", false);
                    int response = data.getIntExtra("response", 0);
                    String message = data.getStringExtra("message");
                    String dataResponse = data.getStringExtra("data:response");
                    String dataTransAmount = data.getStringExtra("data:transAmount");
                    String dataBalAmount = data.getStringExtra("data:balAmount");
                    String dataBalRrn = data.getStringExtra("data:bankRrn");
                    String dataTxnId = data.getStringExtra("data:txnid");
                    String dataTransType = data.getStringExtra("data:transType");
                    String dataType = data.getStringExtra("data:type");
                    String dataCardNumber = data.getStringExtra("data:cardNumber");
                    String dataCardType = data.getStringExtra("data:cardType");
                    String dataTerminalId = data.getStringExtra("data:terminalId");
                    String dataBankName = data.getStringExtra("data:bankName");
                    et_response.setText(data.getExtras().toString());
                    String res="data:cardNumber="+dataCardNumber+"," +
                            " partnerId="+partnerId+"," +
                            " data:response="+dataResponse+"," +
                            " latitude="+latitude+"," +
                            " amount=0," +
                            "apiKey="+apiKey+"," +
                            "data:cardType="+dataCardType+"," +
                            "data:balAmount="+dataBalAmount+"," +
                            " data:bankRrn="+dataBalRrn+"," +
                            " status="+status+"," +
                            " data:type="+dataType+"," +
                            "data:transAmount="+dataTransAmount+"," +
                            "response="+response+"," +
                            "longitude="+longitude+"," +
                            " subMerchantId="+member_id+"," +
                            "data:transType="+dataTransType+"," +
                            " data:terminalId="+dataTerminalId+"," +
                            "deviceManufacturerId="+manId+"," +
                            "data:txnid="+dataTxnId+"," +
                            " merchantCode="+member_id+"," +
                            " referenceNumber="+refernceNumber+"," +
                            " message="+message+"," +
                            " remarks=Test Transaction, " +
                            "data:bankName="+dataBankName+"," +
                            " transactionType=BE," +
                            " mobileNumber="+mobile_num;

//                    Toast.makeText(getContext(), message+"Balance:"+dataBalAmount, Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(),MatmReceiptActivity.class);
                    i.putExtra("from","CW");
                    i.putExtra("status",status+"");
                    i.putExtra("response",response+"");
                    i.putExtra("message",message+"" );
                    i.putExtra("dataResponse",dataResponse+"");
                    i.putExtra("dataTransAmount",dataTransAmount+"");
                    i.putExtra("dataBalAmount",dataBalAmount+"");
                    i.putExtra("dataBalRrn",dataBalRrn+"");
                    i.putExtra("dataTxnId",dataTxnId+"");
                    i.putExtra("dataTransType",dataTransType+"");
                    i.putExtra("dataType",dataType+"");
                    i.putExtra("dataCardNumber",dataCardNumber+"");
                    i.putExtra("dataCardType",dataCardType+"");
                    i.putExtra("dataTerminalId",dataTerminalId+"");
                    i.putExtra("dataBankName",dataBankName+"");
                    i.putExtra("mobile_num",mobile_num+"");
                    i.putExtra("res",res);
                    getActivity().startActivity(i);
                    getActivity().finish();
                }
            }
        }
    }
}
