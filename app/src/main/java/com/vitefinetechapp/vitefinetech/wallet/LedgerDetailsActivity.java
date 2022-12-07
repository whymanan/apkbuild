package com.vitefinetechapp.vitefinetech.wallet;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LedgerDetailsActivity extends AppCompatActivity {

    String wallet_trans_id;
    JSONObject jsonObj;
    TextView tv_member_id,tv_amount,tv_status,tv_mode,tv_ref,tv_stock_type,tv_open_bal,tv_close_bal,tv_commision,tv_bank,
            tv_narration,tv_type,tv_date;
    String member1,refrence,bank,mode,stock_type,type,trans_type,updated,date,narration,status_,member2;
    int   wallet_transaction_id,wallet_id,service_id,amount,balance,closebalance,member_to,member_from,
          surcharge,commission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_details);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            try {
            wallet_trans_id = extras.getString("wallet_trans_id");
            Log.e("tag","wallet_trans_id ?=="+wallet_trans_id);

                 jsonObj = new JSONObject(getIntent().getStringExtra("jsonobject"));
                Log.e("tag","jsonObj ?=="+jsonObj);
                 wallet_transaction_id = jsonObj.getInt("wallet_transaction_id");
                //Log.d("mylog", "i =" + " and wallet_transaction_id =" + wallet_transaction_id);

                 wallet_id = jsonObj.getInt("wallet_id");
                //Log.d("mylog", "i =" + " and wallet_id =" + wallet_id);

                 member_to = jsonObj.getInt("member_to");
                //Log.d("mylog", "i =" + " and wallet_transaction_id =" + member_to);

                 member_from = jsonObj.getInt("member_from");
                //Log.d("mylog", "i =" + " and member_from =" + member_from);

                 service_id = jsonObj.getInt("service_id");
                //Log.d("mylog", "i =" + " and service_id =" + service_id);

                 surcharge = jsonObj.getInt("surcharge");
                //Log.d("mylog", "i =" + " and surcharge =" + surcharge);

                 refrence = jsonObj.getString("refrence");
                //Log.d("mylog", "i =" +" and refrence =" + refrence);

                 mode = jsonObj.getString("mode");
                //Log.d("mylog", "i =" + " and mode =" + mode);

                 bank = jsonObj.getString("bank");
                //Log.d("mylog", "i =" + " and bank =" + bank);

                 stock_type = jsonObj.getString("stock_type");
                //Log.d("mylog", "i =" + " and stock_type =" + stock_type);

                 amount = jsonObj.getInt("amount");
                //Log.d("mylog", "i =" + " and amount =" + amount);

                 balance = jsonObj.getInt("balance");
                //Log.d("mylog", "i =" + " and balance =" + balance);

                 closebalance = jsonObj.getInt("closebalance");
                //Log.d("mylog", "i =" + " and closebalance =" + closebalance);

                 commission = jsonObj.getInt("commission");
                //Log.d("mylog", "i =" + " and commission =" + commission);

                 type = jsonObj.getString("type");
                //Log.d("mylog", "i =" +  " and type =" + type);

                 trans_type = jsonObj.getString("trans_type");
                //Log.d("mylog", "i =" + " and trans_type =" + trans_type);

                 updated = jsonObj.getString("updated");
                //Log.d("mylog", "i =" +  " and updated =" + updated);

                 date = jsonObj.getString("date");
                //Log.d("mylog", "i =" +  " and date =" + date);

                 narration = jsonObj.getString("narration");
                 //Log.d("mylog", "i =" +  " and narration =" + narration);

                 status_ = jsonObj.getString("status");
                //Log.d("mylog", "i =" +  " and status =" + status_);

                 member1 = jsonObj.getString("member1");
                //Log.d("mylog", "i =" + " and member1 =" + member1);

                 member2 = jsonObj.getString("member2");
                //Log.d("mylog", "i =" + " and member2 =" + member2);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        tv_member_id = findViewById(R.id.tv_member_id);
        tv_amount = findViewById(R.id.tv_amount);
        tv_status = findViewById(R.id.tv_status);
        tv_mode = findViewById(R.id.tv_mode);
        tv_ref = findViewById(R.id.tv_ref);
        tv_stock_type = findViewById(R.id.tv_stock_type);
        tv_open_bal = findViewById(R.id.tv_open_bal);
        tv_close_bal = findViewById(R.id.tv_close_bal);
        tv_commision = findViewById(R.id.tv_commision);
        tv_bank = findViewById(R.id.tv_bank);
        tv_narration = findViewById(R.id.tv_narration);
        tv_type = findViewById(R.id.tv_type);
        tv_date = findViewById(R.id.tv_date);

        tv_member_id.setText("MEMBER ID:-  "+member1);
        tv_amount.setText("AMOUNT:-  "+amount);
        tv_status.setText("STATUS:-  "+status_);
        tv_mode.setText("MODE:-  "+mode);
        tv_ref.setText("REFERENCE :-  "+refrence);
        tv_stock_type.setText("STOCK TYPE:-  "+stock_type);
        tv_open_bal.setText("OPEN BALANCE:-  "+balance);
        tv_close_bal.setText("CLOSE BALANCE:-  "+closebalance);
        tv_commision.setText("COMMISSION:-  "+commission);
        tv_bank.setText("BANK:-  "+bank);
        tv_narration.setText("NARRATION:-  "+narration);
        tv_type.setText("TYPE:-  "+type);
        tv_date.setText("DATE:-  "+date);



    }


}