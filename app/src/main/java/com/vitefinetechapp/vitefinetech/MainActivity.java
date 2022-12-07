package com.vitefinetechapp.vitefinetech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText emailtxt,passwordtxt;
    Button login;
    public static final String KEY_USERNAMES="member_id";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_Logintime="logintime";

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    String e, p,t;


public static final String LOGIN_URL = "https://vitefintech.com/nextpay-public/api/login";
//    public static final String LOGIN_URL = "https://vitefintech.com/demo/public/api/login";


    FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTextView,longitTextView;
    int PERMISSION_ID = 44;

    private ProgressDialog progressDialog;
    Date d,time;
    CardView card_login;
    TextView txt_forget_pass;
    Dialog Enabledialog;
    Dialog Accessdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailtxt             =   (EditText) findViewById(R.id.email);
        passwordtxt          =   (EditText) findViewById(R.id.password);
        login                =   findViewById(R.id.login);
        latitudeTextView     =   (TextView)findViewById(R.id.latitude);
        longitTextView       =   (TextView)findViewById(R.id.longitude);
        txt_forget_pass      =   (TextView)findViewById(R.id.forget_pass);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);

        Accessdialog = new Dialog(this);
        Enabledialog = new Dialog(this);


        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        e =         sharedpreferences.getString(KEY_USERNAMES,null);
        p =         sharedpreferences.getString(KEY_PASSWORD,null);
        t =         sharedpreferences.getString(KEY_Logintime,null);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
        try {
            if(t != null){
                time = sdf.parse(t);
                Log.d("nnnnnnnnnnnn", String.valueOf(time));
            }
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email       =        emailtxt.getText().toString().trim();
                String password    =        passwordtxt.getText().toString().trim();

                if (!email.isEmpty() || !password.isEmpty()) {
                    Login(email, password);
                    progressDialog.show();

                } else {
                    emailtxt.setError("Please insert Id");
                    emailtxt.requestFocus();
                    passwordtxt.setError("Please insert password");
                    passwordtxt.requestFocus();
                }
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void Login(final String email, final String password) {
        login.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        VolleyLog.d("ResponseLogin:" + response.toString());
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            sharedpreferences =    getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                            String token      =    obj.getString("token").trim();
                            String user_id    =    obj.getString("user_id").trim();
                            String member_id  =    obj.getString("member_id").trim();
                            String phone      =    obj.getString("phone").trim();
                            String role_id    =    obj.getString("role_id").trim();
                            String kyc_status =    obj.getString("kyc_status").trim();
                            String user_status=    obj.getString("user_status").trim();
                            String user_type  =    obj.getString("user_type").trim();
                            String latitude   =    obj.getString("latitude").trim();
                            String longitude  =    obj.getString("longitude").trim();
                            String token_type =    obj.getString("token_type").trim();
                            String expires_in =    obj.getString("expires_in").trim();

                            Date today = new Date();

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(today);
                            cal.add(Calendar.MINUTE,15);
                            d = cal.getTime();
                            Log.d("fifteenminutesaftertime", String.valueOf(d));

                            SharedPreferences.Editor editor1 = sharedpreferences.edit();

                            editor1.putString(KEY_USERNAMES, String.valueOf(emailtxt.getText())).toString().trim();
                            editor1.putString(KEY_PASSWORD, String.valueOf(passwordtxt.getText())).toString().trim();
                            editor1.putString(KEY_Logintime, String.valueOf(d).toString().trim());

                            editor1.apply();

                            Log.d(role_id,role_id);


                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("tokenname", token);
                            editor.putString("member_id", member_id);
                            editor.putString("user_id", user_id);
                            editor.putString("rollid", role_id);
                            editor.putString("latitude", latitudeTextView.getText().toString());
                            editor.putString("longitude", longitTextView.getText().toString());

                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(latitudeTextView.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                        }

                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        finish();
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();

                       }
                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login Failed. You have entered the wrong credentials.", Toast.LENGTH_LONG).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params  = new HashMap<>();
                params.put(KEY_USERNAMES, email);
                params.put(KEY_PASSWORD, password);
                params.put("latitude", latitudeTextView.getText().toString());
                params.put("longitude", longitTextView.getText().toString());

                return params ;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

        @Override
    protected void onStart(){
        super.onStart();
        Date d = new Date();
        if(e!= null && p!= null && time.getTime() > d.getTime()){
            Intent i = new Intent(MainActivity.this, DashboardActivity.class);

            startActivity(i);
            finish();
        }
    }

    private void getLastLocation() {
        if (checkPermissions()) {
            Accessdialog.hide();
            if (isLocationEnabled()) {
                Enabledialog.hide();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
                buildAlertMessageNoGps();
            }
        } else {
//            requestPermissions();
            Accessdialog.setContentView(R.layout.custom_location_access);
            Window window = Accessdialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            TextView custom_location_text=Accessdialog.findViewById(R.id.custom_location_text);
            custom_location_text.setText(getResources().getString(R.string.app_name)+" needs to access your device's location at the time of login, to enable you to use its services and prevent fraud" );

            Button enableLocation=Accessdialog.findViewById(R.id.LocationOk);
            enableLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestPermissions();
                }
            });
            Accessdialog.show();

        }
    }
    private void buildAlertMessageNoGps() {


        Enabledialog.setContentView(R.layout.custom_location_enable);
        Window window = Enabledialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button enableLocation = Enabledialog.findViewById(R.id.enableLocation);
        enableLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        });
        Enabledialog.show();
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText(mLastLocation.getLatitude()+"");
            longitTextView.setText(mLastLocation.getLongitude() + "");
        }
    };


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        getLastLocation();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}