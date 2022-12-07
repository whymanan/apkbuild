package com.vitefinetechapp.vitefinetech.KYCDetails;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KYCManagementActivity  extends AppCompatActivity implements View.OnClickListener {

    private static int IMG_PROFILE_RESULT = 1;
    private static int IMG_PAN_RESULT = 2;
    private static int IMG_ADHAR_F_RESULT = 3;
    private static int IMG_ADHAR_B_RESULT = 4;
    private static int IMG_PASSBK_RESULT = 5;


    String ImageDecode,member_id,user_id;
    ImageView img_profile, img_adhar_f, img_adhar_b, img_pan, img_passbk_photo;
    Button btn_pf_chose, btn_adhar_f_chose, btn_adhar_b_chose, btn_pan_chose, btn_passbk_photo_chose, btn_save_info;
    TextView txt_passbk_no_file,txt_pan_no_file,txt_adhar_f_no_file,txt_adhar_b_no_file,txt_pf_no_file;
    Intent intent;
    String[] FILE;
    TextInputEditText textInputEditText_adhar_no,textInputEditText_pan_no,textInputEditText_shop_name,
         textInputEditText_gst_no,textInputEditText_full_shop_address,textInputEditText_postal_code,textInputEditText_account_name,
         textInputEditText_acct_no,textInputEditText_ifsc_code,textInputEditText_phone_no;

    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;
    public static final String URL_KYC = "https://vitefintech.com/viteapi/kyc";

    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c_management);

        backbutton   = (ImageButton) findViewById(R.id.backbuttonKyc);

        btn_pf_chose = findViewById(R.id.btn_pf_chose);
        btn_adhar_f_chose = findViewById(R.id.btn_adhar_f_chose);
        btn_adhar_b_chose = findViewById(R.id.btn_adhar_b_chose);
        btn_pan_chose = findViewById(R.id.btn_pan_chose);
        btn_passbk_photo_chose = findViewById(R.id.btn_passbk_photo_chose);
        btn_save_info = findViewById(R.id.btn_save_info);

        img_profile = findViewById(R.id.img_profile);
        img_adhar_f = findViewById(R.id.img_adhar_f);
        img_adhar_b = findViewById(R.id.img_adhar_b);
        img_pan = findViewById(R.id.img_pan);
        img_passbk_photo = findViewById(R.id.img_passbk_photo);

        txt_pf_no_file = findViewById(R.id.txt_pf);
        txt_adhar_f_no_file = findViewById(R.id.txt_adhar_f_no_file);
        txt_adhar_b_no_file = findViewById(R.id.txt_adhar_b_no_file);
        txt_pan_no_file = findViewById(R.id.txt_pan_no_file);
        txt_passbk_no_file = findViewById(R.id.txt_passbk_no_file);

        textInputEditText_adhar_no = findViewById(R.id.input_edt_adhar_no);
        textInputEditText_pan_no = findViewById(R.id.input_edt_pan_no);
        textInputEditText_shop_name = findViewById(R.id.input_edt_shop_n);
        textInputEditText_gst_no = findViewById(R.id.input_edt_gst_no);
        textInputEditText_full_shop_address = findViewById(R.id.input_edt_full_shop_add);
        textInputEditText_postal_code = findViewById(R.id.input_edt_postal_code);
        textInputEditText_account_name = findViewById(R.id.input_edt_acct_holder_name);
        textInputEditText_acct_no = findViewById(R.id.input_edt_account_no);
        textInputEditText_ifsc_code = findViewById(R.id.input_edt_bank_ifsc_code);
        textInputEditText_phone_no = findViewById(R.id.input_edt_phone_no);


        btn_pf_chose.setOnClickListener(this);
        btn_adhar_f_chose.setOnClickListener(this);
        btn_adhar_b_chose.setOnClickListener(this);
        btn_pan_chose.setOnClickListener(this);
        btn_passbk_photo_chose.setOnClickListener(this);
        btn_save_info.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString("member_id",null);
        Log.e("KYCManagementActivity","member_id"+member_id);

        user_id = sharedpreferences.getString("user_id",null);
        Log.e("KYCManagementActivity","user_id"+user_id);

        if (ContextCompat.checkSelfPermission(KYCManagementActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(KYCManagementActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(KYCManagementActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_pf_chose:
                loadProfileImage();

                break;

            case R.id.btn_adhar_f_chose:
                loadAdharFontImage();
                break;

            case R.id.btn_adhar_b_chose:
                loadAdharBackImage();
                break;

            case R.id.btn_pan_chose:
                loadPanImage();
                break;

            case R.id.btn_passbk_photo_chose:
                loadPassbkFImage();
                break;


            case R.id.btn_save_info:
                saveInfo();
                break;

        }
    }

    private void saveInfo() {

        String adhar_no = textInputEditText_adhar_no.getText().toString();
        String pan_no = textInputEditText_pan_no.getText().toString();
        String shop_name = textInputEditText_shop_name.getText().toString();
        String gst_no = textInputEditText_gst_no.getText().toString();
        String full_shop_addr = textInputEditText_full_shop_address.getText().toString();
        String postal_code = textInputEditText_postal_code.getText().toString();
        String acc_holder_n = textInputEditText_account_name.getText().toString();
        String acc_no = textInputEditText_acct_no.getText().toString();
        String bank_ifsc_code = textInputEditText_pan_no.getText().toString();
        String phone_no = textInputEditText_phone_no.getText().toString();


        if(adhar_no.toString().isEmpty()) {
            textInputEditText_adhar_no.setError("Adhar no is Empty");
            textInputEditText_adhar_no.requestFocus();

        }else {
            if(pan_no.toString().isEmpty()) {
                textInputEditText_pan_no.setError("Pan no is Empty");
                textInputEditText_pan_no.requestFocus();
            }else {

                if(shop_name.toString().isEmpty()) {
                    textInputEditText_shop_name.setError("Shop name is Empty");
                    textInputEditText_shop_name.requestFocus();
                }else {
                    if(full_shop_addr.toString().isEmpty()) {
                        textInputEditText_full_shop_address.setError("Shop Address is Empty");
                        textInputEditText_full_shop_address.requestFocus();
                    } else {
                        if(postal_code.toString().isEmpty()) {
                            textInputEditText_postal_code.setError("Postal code is Empty");
                            textInputEditText_postal_code.requestFocus();
                        }else {
                            if(acc_holder_n.toString().isEmpty()) {
                                textInputEditText_account_name.setError(" Account holder name is Empty");
                                textInputEditText_account_name.requestFocus();
                            }else {
                                if(acc_no.toString().isEmpty()) {
                                    textInputEditText_acct_no.setError("Account number is Empty");
                                    textInputEditText_acct_no.requestFocus();
                                }else {
                                    if(bank_ifsc_code.toString().isEmpty()) {
                                        textInputEditText_ifsc_code.setError("Bank IFSC code is Empty");
                                        textInputEditText_ifsc_code.requestFocus();
                                    }else {
                                        if(phone_no.toString().isEmpty()) {
                                            textInputEditText_phone_no.setError("Phone number is Empty");
                                            textInputEditText_phone_no.requestFocus();
                                        }else {
                                            if(phone_no.length() <= 0 && phone_no.length() >= 10) {
                                                textInputEditText_phone_no.setError("Please Enter valid phone Phone Number");
                                                textInputEditText_phone_no.requestFocus();
                                            }else {

                                                Toast.makeText(this, "Save Information", Toast.LENGTH_SHORT).show();
                                            
                                            saveInfoToServer(adhar_no,pan_no,shop_name,gst_no,full_shop_addr,postal_code,acc_holder_n,acc_no,bank_ifsc_code,phone_no);

                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        }

    private void saveInfoToServer(String adhar_no, String pan_no, String shop_name, String gst_no, String full_shop_addr, String postal_code, String acc_holder_n, String acc_no, String bank_ifsc_code, String phone_no) {


        JSONObject obj = new JSONObject();
        try {
            obj.put("name", acc_holder_n);
            obj.put("user_id", "1234");
            obj.put("account_no", acc_no);
            obj.put("bank_name", "sbi");
            obj.put("phone", phone_no);
            obj.put("ifsc", bank_ifsc_code);
            obj.put("adharcard", adhar_no);
            obj.put("pancard", pan_no);
            obj.put("organization_name", shop_name);
            obj.put("gst_no", gst_no);
            obj.put("address", full_shop_addr);
            obj.put("states", "maharastra");
            obj.put("city", "Wardha");
            obj.put("area", "seloo");
            obj.put("pincode", postal_code);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_KYC,
                obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

//                    final String token = String.valueOf(response);
//                    Log.e("KYCManagementActivity","token"+token);
                    Log.d("Response", response.toString());

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                  params.put("Content-Type","application/json");
                  return params;

            }
        };

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }


    private void loadPanImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMG_PAN_RESULT);

    }

    private void loadAdharBackImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMG_ADHAR_B_RESULT);

    }

    private void loadPassbkFImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMG_PASSBK_RESULT);

    }

    private void loadAdharFontImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMG_ADHAR_F_RESULT);

    }

    private void loadProfileImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMG_PROFILE_RESULT);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_PROFILE_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement","URI =="+URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String  ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement","ImageDecode =="+ImageDecode);

                cursor.close();


                txt_pf_no_file.setText(ImageDecode);
                img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));



            }
            if (requestCode == IMG_ADHAR_F_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement","URI =="+URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String  ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement","ImageDecode =="+ImageDecode);

                cursor.close();


                txt_adhar_f_no_file.setText(ImageDecode);
                img_adhar_f.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));



            }
            if (requestCode == IMG_ADHAR_B_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement","URI =="+URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String  ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement","ImageDecode =="+ImageDecode);

                cursor.close();


                txt_adhar_b_no_file.setText(ImageDecode);
                img_adhar_b.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));



            }

            if (requestCode == IMG_PAN_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement","URI =="+URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String  ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement","ImageDecode =="+ImageDecode);

                cursor.close();


                txt_pan_no_file.setText(ImageDecode);
                img_pan.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));



            }

            if (requestCode == IMG_PASSBK_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement","URI =="+URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String  ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement","ImageDecode =="+ImageDecode);

                cursor.close();


                txt_passbk_no_file.setText(ImageDecode);
                img_passbk_photo.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));



            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
