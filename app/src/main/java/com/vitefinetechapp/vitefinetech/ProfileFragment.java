package com.vitefinetechapp.vitefinetech;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vitefinetechapp.vitefinetech.KYCDetails.KYCManagementActivity;
import com.vitefinetechapp.vitefinetech.MyProfileScreen.MyProfileActivity;
import com.vitefinetechapp.vitefinetech.MyProfileScreen.changePwdActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ProfileFragment extends Fragment {
    private DashboardActivity appCompatActivity;
    ImageButton myprofilescreen,kycdetails;
    ImageView img_authorImage;
    JSONObject data;
    Button logout,btn_chpwd;
    TextView tv_mail,tv_mob,tv_kycst,tv_memId;

    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;
    public static final String KEY_USERNAMES="member_id";
    public static final String KEY_PASSWORD="password";


    public ProfileFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appCompatActivity = (DashboardActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        myprofilescreen     = (ImageButton)view.findViewById(R.id.backbutton);
        img_authorImage=view.findViewById(R.id.img_authorImage);
        logout =view.findViewById(R.id.btn_logout);
        btn_chpwd=view.findViewById(R.id.btn_chpwd);
        tv_mail=view.findViewById(R.id.tv_mail);
        tv_mob=view.findViewById(R.id.tv_mob);
        tv_kycst=view.findViewById(R.id.tv_kycst);
        tv_memId=view.findViewById(R.id.tv_memId);

        sharedpreferences =   getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        getProfileDetail();

        btn_chpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), changePwdActivity.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmationlogoutbox();
            }
        });

//        linear_myprof.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity().getApplicationContext(), MyProfileActivity.class);
//                startActivity(i);
//            }
//        });

//        linear_kysdet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity().getApplicationContext(), KYCManagementActivity.class);
//                startActivity(i);
//            }
//        });

//        linear_log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openConfirmationlogoutbox();
//            }
//        });

        return view;
    }

    private void getProfileDetail() {
        String member_id = sharedpreferences.getString("member_id",null);
        String user_id = sharedpreferences.getString("user_id", null);


        String Url="https://nextmoney.co.in/nextmoney/users/getUserDetails?api_key=NMA001&member_id="+member_id+"&user_id="+user_id;
        Log.d("profileURL", Url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,Url,
                response ->{
//                            progressDialog.dismiss();
                    Log.d("profileRes", response);
                    try {
                        JSONObject obj=new JSONObject(response);
                        Boolean st=obj.getBoolean("status");
                        if(st==true){
                            String img=obj.getString("profileImage");
                            URL imgUrl=new URL(img);
                            Glide.with(getContext()).load(imgUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(img_authorImage);
                            Log.d("profileimg", img);

                            data=obj.getJSONObject("data");
                            tv_mail.setText(data.getString("email"));
                            tv_mob.setText(data.getString("phone"));
                            tv_kycst.setText(data.getString("kyc_status"));
                            tv_memId.setText(data.getString("member_id"));
//                            Bitmap imgBit= BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
//                            img_authorImage.setImageBitmap(imgBit);
                        }
                    } catch (JSONException | MalformedURLException e) {
                        e.printStackTrace();
                    }

                },error -> {
//                    progressDialog.dismiss();
            Log.d("profileErr","error:"+error.getMessage());
            Toast.makeText(getContext(), "Error-:" + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void openConfirmationlogoutbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to exit...");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);

                sharedpreferences.edit().remove(KEY_USERNAMES).apply();
                sharedpreferences.edit().remove(KEY_PASSWORD).apply();
                sharedpreferences.edit().remove("tokenname").apply();

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                appCompatActivity.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
