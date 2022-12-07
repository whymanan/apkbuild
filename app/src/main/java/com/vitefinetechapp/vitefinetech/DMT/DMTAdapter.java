package com.vitefinetechapp.vitefinetech.DMT;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.vitefinetechapp.vitefinetech.Electricity.ElecticityActivity;
import com.vitefinetechapp.vitefinetech.R;
import com.vitefinetechapp.vitefinetech.Recharge.TopUPActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DMTAdapter extends RecyclerView.Adapter<DMTAdapter.Viewholder> {

    private Context context;
    private ArrayList<DMTModel> dmtModelArrayList;
    ArrayList<String> list;
    String amount,order_id,benificiary_account,benificiary_ifsc,transfer_mode;
    public static final String URL_transaction = "https://vitefintech.com/viteapi/dmt/dmtTransection";
    int randomNum;
    Random rand = new Random();
    String uniqueId = null,cust_mob;
    Dialog dialog;
    private ProgressDialog progressDialog;
    Boolean suc=false;

    Button dialogButton;
    EditText edt_amount;
    TextInputLayout input_layout_amount;


    // Constructor
    public DMTAdapter(String cust_mob,Context context, ArrayList<DMTModel> dmtModelArrayList) {
        this.context = context;
        this.dmtModelArrayList = dmtModelArrayList;
        this.cust_mob=cust_mob;
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dmt_card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {

        DMTModel model = dmtModelArrayList.get(position);
        holder.txt_beneficiary_id.setText("Beneficiary ID: "+model.getBa_primary());
        Log.e("dmt adapter", "ba_primary ===" + model.getBa_primary());

        holder.txt_acct_name.setText("Account Name: "+model.getAccount_name());
        holder.txt_acct_no.setText( "Account Numbers: "+ model.getAcct_no());
        holder.txt_ifsc_code.setText("IFSC Code: "+model.getIfsc_code());
        holder.txt_status.setText("Status: "+model.getStatus());

        holder.btn_make_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.btn_make_pay.setEnabled(false);
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.custom);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                edt_amount =  dialog.findViewById(R.id.edt_amount);


                input_layout_amount=dialog.findViewById(R.id.input_layout_amount);



                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(false);

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(edt_amount.getText())){
                            Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final String SHARED_PREFS = "shared_prefs";
                            SharedPreferences sharedPreferences;
                            sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

                            String member_id = sharedPreferences.getString("member_id", null);
                            String user_id = sharedPreferences.getString("user_id", null);

                            String bName=dmtModelArrayList.get(position).getAccount_name();
                            String bIfsc=dmtModelArrayList.get(position).getIfsc_code();
                            String bMob=dmtModelArrayList.get(position).getPhone_no();
//                            String bUid=dmtModelArrayList.get(position).getUser_detail_id();
                            String bUid=user_id;

                            String mId=member_id;
                            String amt=edt_amount.getText().toString();
                            String bAcc=dmtModelArrayList.get(position).getAcct_no();
                            String bId=dmtModelArrayList.get(position).getBa_primary();

//                            dialog.setCancelable(false);
                            dialog.dismiss();
                            progressDialog.show();
                            dialogButton.setVisibility(View.GONE);
                            edt_amount.setVisibility(View.GONE);
                            input_layout_amount.setVisibility(View.GONE);
//                            makeTransaction2(bName,bIfsc,bMob,bUid,mId,amt,bAcc,bId);
                            Intent i=new Intent(context,DMTreceiptActivity.class);
                            i.putExtra("bName",bName);
                            i.putExtra("bIfsc",bIfsc);
                            i.putExtra("bMob",bMob);
                            i.putExtra("bUid",bUid);
                            i.putExtra("mId",mId);
                            i.putExtra("amt",amt);
                            i.putExtra("bAcc",bAcc);
                            i.putExtra("bId",bId);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
//                            Toast.makeText(context, amt, Toast.LENGTH_SHORT).show();


                        }

                    }
                });
                dialog.show();


            }
        });

        holder.btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(false);


                final String SHARED_PREFS = "shared_prefs";
                SharedPreferences sharedPreferences;
                sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

                String member_id = sharedPreferences.getString("member_id", null);
                String bId=dmtModelArrayList.get(position).getBa_primary();
                progressDialog.show();

                String url="https://nextmoney.co.in/nextmoney/dmt2/varify";
                StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                        response ->{

                            progressDialog.dismiss();
                            Log.d("dmtVBenres", ":"+response);
                            try {
                                JSONObject obj=new JSONObject(response);
                                String msg=obj.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("dmtVBenerr1", ":"+e.getMessage());
                            }

                        },error -> {
                    progressDialog.dismiss();
                    Log.d("dmtVBenerr2", ":"+error.getMessage());
                    Toast.makeText(context, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                } ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("member_id", member_id);
                        params.put("beneficiary_id", bId);
                        params.put("api_key", "NMA001");
                        Log.d("dmtVBenpar", ":"+params);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bId=dmtModelArrayList.get(position).getBa_primary();
                String dUrl="https://vitefintech.com/viteapi/dmt/beneficiarydelete?api_key=NMA001&beneficiary_id="+bId;
                StringRequest stringRequest=new StringRequest(Request.Method.GET,dUrl,
                        response ->{
//                            progressDialog.dismiss();
                            Log.d("DdeleteRes", response);
                            try {
                                JSONObject obj=new JSONObject(response);
                                Boolean st=obj.getBoolean("status");
                                if(st==true){
                                    dmtModelArrayList.remove(position);
                                    notifyItemRemoved(position);
                                }
                                String msg=obj.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        },error -> {
//                    progressDialog.dismiss();
                    Log.d("DdeleteErr","error:"+error.getMessage());
                    Toast.makeText(context, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            }
        });

    }

//    private void makeTransaction2(String bName, String bIfsc, String bMob, String bUid, String mId, String amt, String bAcc, String bId) {
//
//
//        String url = "https://nextmoney.co.in/nextmoney/dmt/submitTransection";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    progressDialog.dismiss();
////            dialog.setCancelable(true);
//                    try {
//                        Log.d("dmtAdapTra", response.toString());
//                        JSONObject obj=new JSONObject(response);
////                        Boolean st=obj.getBoolean("status");
////                        String msg=obj.getString("msg");
//                        String st=obj.getString("status");
//                        String msg=obj.getString("message");
//
//
//                        Intent i=new Intent(context,DMTreceiptActivity.class);
//                        i.putExtra("status",st);
//                        i.putExtra("cust_mob",cust_mob);
//                        i.putExtra("amt",amt);
//                        i.putExtra("bName",bName);
//                        i.putExtra("bIfsc",bIfsc);
//                        i.putExtra("msg",msg);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(i);
//
//                    } catch (JSONException e) {
////                        Intent i=new Intent(context,DMTreceiptActivity.class);
////                        i.putExtra("status",false);
////                        i.putExtra("cust_mob",cust_mob);
////                        i.putExtra("amt",amt);
////                        i.putExtra("bName",bName);
////                        i.putExtra("bIfsc",bIfsc);
////                        i.putExtra("msg",e.getMessage());
////                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        context.startActivity(i);
//                        Toast.makeText(context, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        e.printStackTrace();
//                    }
//                },
//                error -> {
//                    progressDialog.dismiss();
////                    Intent i=new Intent(context,DMTreceiptActivity.class);
////                    i.putExtra("status",false);
////                    i.putExtra("cust_mob",cust_mob);
////                    i.putExtra("amt",amt);
////                    i.putExtra("bName",bName);
////                    i.putExtra("bIfsc",bIfsc);
////                    i.putExtra("msg",error.getMessage());
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(i);
//                    Toast.makeText(context, "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }) {
//            //Add parameters
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("beneficiary_name", bName);
//                params.put("beneficiary_ifsc", bIfsc);
//                params.put("mobile", bMob);
//                params.put("user_id", bUid);
//                params.put("member_id", mId);
////                params.put("member_id", "--");
//                params.put("amount", amt);
//                params.put("beneficiary_account_number", bAcc);
//                params.put("beneficiaryid", bId);
//                params.put("api_key", "NMA001");
//
//                Log.d("dmtAdapPa", params.toString());
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("Client-Secret", "31BNFRBWA001Yak");
//                params.put("Secret-Key", "RySxFWDsm6SSX5QcG62f8a61cacs9s");
//                params.put("Token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0aW1lc3RhbXAiOiIxMjM0NTY3ODkwIiwicGFydG5lcklkIjoiR0lUQTAwMSIsInJlcWlkIjoxNTE2MjM5MDIyfQ.Am_Qjdmr5B2HFcJLuiROB6N73CXpzIVg8zbqIo7ovv8");
//                return params;
//            }
//        };
//
////        Log.d("dmtATr", String.valueOf(stringRequest));
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
//    }




    @Override
    public int getItemCount() {
        return dmtModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView txt_beneficiary_id, txt_acct_no,txt_acct_name,txt_ifsc_code,txt_status;
        private Button btn_make_pay,btn_verify,btn_delete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            btn_verify=itemView.findViewById(R.id.btn_verify);
            txt_beneficiary_id = itemView.findViewById(R.id.txt_beneficiary_id);
            txt_acct_no = itemView.findViewById(R.id.txt_acct_no);
            txt_acct_name = itemView.findViewById(R.id.txt_acct_name);
            txt_ifsc_code = itemView.findViewById(R.id.txt_ifsc_code);
            txt_status = itemView.findViewById(R.id.txt_status);
            btn_make_pay = itemView.findViewById(R.id.btn_make_payment);
            btn_delete=itemView.findViewById(R.id.btn_delete);



        }
    }
}