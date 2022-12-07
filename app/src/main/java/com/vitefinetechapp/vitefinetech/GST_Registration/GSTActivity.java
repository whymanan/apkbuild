package com.vitefinetechapp.vitefinetech.GST_Registration;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import com.vitefinetechapp.vitefinetech.KYCDetails.ApiInterface;
import com.vitefinetechapp.vitefinetech.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GSTActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText input_edt_dob, input_edt_email, input_edt_mobile, input_edt_name_of_person,
            input_edt_f_h_name, input_edt_designation, input_edt_pan_no, input_edt_adhar_no, input_edt_address,
            input_edt_din_no;
    private int mYear;
    private int mMonth;
    private int mDay;
    ArrayList<String> list;
    Spinner sp_sel_comp_type;
    RelativeLayout relative_main, relative_below;
    LinearLayout liear;
    Button btn_save_g;
    ImageButton backbutton;
    CardView card_view_certificate, card_view_moa, card_view_aoa;
    Button btn_submit;
    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;

    String member_id;
    private static final String ROOT_URL = "https://vitefintech.com/viteapi/";
    File sourceFile;
    TextInputEditText input_edt_firm, input_edt_nature, input_edt_natureof_business, input_edit_state, input_edt_district,
            input_edt_buiseness_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst);

        input_edt_dob = findViewById(R.id.input_edt_dob);
        relative_main = findViewById(R.id.relative_main);
        relative_below = findViewById(R.id.relative_below);
        sp_sel_comp_type = findViewById(R.id.sel_comp_type_spinner);
        backbutton = findViewById(R.id.backbutton);
        liear = findViewById(R.id.linear_button);
        card_view_certificate = findViewById(R.id.card_view_certificate);
        card_view_moa = findViewById(R.id.card_view_moa);
        card_view_aoa = findViewById(R.id.card_view_aoa);
        btn_submit = findViewById(R.id.submit);

        input_edt_email = findViewById(R.id.input_edt_email);
        input_edt_mobile = findViewById(R.id.input_edt_mobile_no);
        input_edt_name_of_person = findViewById(R.id.input_edt_person_name);
        input_edt_f_h_name = findViewById(R.id.input_edt_f_h_name);
        input_edt_designation = findViewById(R.id.input_edt_designation);
        input_edt_pan_no = findViewById(R.id.input_edt_pan_no_g);
        input_edt_adhar_no = findViewById(R.id.input_edt_adhar_no_g);
        input_edt_address = findViewById(R.id.input_edt_addr);
        input_edt_din_no = findViewById(R.id.input_edt_din_no);


        input_edt_firm = findViewById(R.id.input_edt_firm);
        input_edt_nature = findViewById(R.id.input_edt_nature);
        input_edt_natureof_business = findViewById(R.id.input_edt_natureof_business);
        input_edit_state = findViewById(R.id.input_edit_state);
        input_edt_district = findViewById(R.id.input_edt_district);
        input_edt_buiseness_add = findViewById(R.id.input_edt_buiseness_add);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString("member_id", null);
        Log.e("KYCManagementActivity", "member_id" + member_id);


        btn_submit.setOnClickListener(this);
        relative_main.setVisibility(View.GONE);
        relative_below.setVisibility(View.GONE);
        liear.setVisibility(View.GONE);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        input_edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        selectCompanyType();
    }

    private void selectCompanyType() {
        list = new ArrayList<>();

        list.add(0, "Select Company Types");
        list.add(1, "Private Limited");
        list.add(2, "Partnership");
        list.add(3, "Proprietorship");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GSTActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sel_comp_type.setAdapter(dataAdapter);
        sp_sel_comp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String companytype = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString().equals("Select Company Types")) {
                    relative_main.setVisibility(View.GONE);
                    liear.setVisibility(View.GONE);
                    relative_below.setVisibility(View.GONE);

                } else if (parent.getItemAtPosition(position).toString().equals("Private Limited")) {
                    relative_main.setVisibility(View.VISIBLE);
                    liear.setVisibility(View.VISIBLE);
                    relative_below.setVisibility(View.VISIBLE);
                    card_view_moa.setVisibility(View.VISIBLE);
                    card_view_certificate.setVisibility(View.VISIBLE);
                    card_view_aoa.setVisibility(View.VISIBLE);

                } else if (parent.getItemAtPosition(position).toString().equals("Partnership")) {
                    relative_main.setVisibility(View.VISIBLE);
                    liear.setVisibility(View.VISIBLE);
                    relative_below.setVisibility(View.VISIBLE);
                    card_view_aoa.setVisibility(View.GONE);
                    card_view_certificate.setVisibility(View.GONE);
                    card_view_moa.setVisibility(View.GONE);

                } else if (parent.getItemAtPosition(position).toString().equals("Proprietorship")) {
                    relative_main.setVisibility(View.VISIBLE);
                    liear.setVisibility(View.VISIBLE);
                    relative_below.setVisibility(View.VISIBLE);
                    card_view_aoa.setVisibility(View.GONE);
                    card_view_certificate.setVisibility(View.GONE);
                    card_view_moa.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(GSTActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        input_edt_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.submit:
                // submitInfo();
                submitFormInfo();
                break;

        }

    }

    private void submitFormInfo() {



        String firm_name = input_edt_firm.getText().toString();
        String nature_of_prot = input_edt_nature.getText().toString();
        String nature_of_bussi = input_edt_natureof_business.getText().toString();
        String state = input_edit_state.getText().toString();
        String district = input_edt_district.getText().toString();
        String bussi_add = input_edt_buiseness_add.getText().toString();
        String comp_type = sp_sel_comp_type.getSelectedItem().toString();

        if (firm_name.toString().isEmpty()) {
            input_edt_firm.setError("Firm Name is Empty");
            input_edt_firm.requestFocus();

        } else {
            if (nature_of_prot.toString().isEmpty()) {
                input_edt_nature.setError("Nature of property is Empty");
                input_edt_nature.requestFocus();
            } else {

                if (nature_of_bussi.toString().isEmpty()) {
                    input_edt_natureof_business.setError("name is Empty");
                    input_edt_natureof_business.requestFocus();
                } else {
                    if (state.toString().isEmpty()) {
                        input_edit_state.setError("Father Or Husband name is Empty");
                        input_edit_state.requestFocus();
                    } else {
                        if (district.toString().isEmpty()) {
                            input_edt_district.setError("Date of Birth is Empty");
                            input_edt_district.requestFocus();
                        } else {
                            if (bussi_add.toString().isEmpty()) {
                                input_edt_buiseness_add.setError(" Designation is Empty");
                                input_edt_buiseness_add.requestFocus();
                            } else {
                                if (sp_sel_comp_type.getSelectedItem().toString().equals("Select Company Types")) {
                                    Toast.makeText(this, "Select Your company type", Toast.LENGTH_SHORT).show();
                                } else {

                                    saveGstToServer(firm_name, nature_of_prot, nature_of_bussi, state, district, bussi_add, comp_type);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void saveGstToServer(String firm_name, String nature_of_prot, String nature_of_bussi, String state, String district, String bussi_add, String comp_type) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);

        // File file = FileUtils.getFile(this, fileUri);

        RequestBody requeststate = RequestBody.create(MediaType.parse("multipart/form-data"), state);
        RequestBody requestdistrict = RequestBody.create(MediaType.parse("multipart/form-data"), district);
        RequestBody requestbusiness_adress = RequestBody.create(MediaType.parse("multipart/form-data"), bussi_add);
        RequestBody requestnob = RequestBody.create(MediaType.parse("multipart/form-data"), nature_of_bussi);
        RequestBody requestcreated_by = RequestBody.create(MediaType.parse("multipart/form-data"), member_id);
        RequestBody requestfirm_name_ = RequestBody.create(MediaType.parse("multipart/form-data"), firm_name);
        RequestBody requestnop = RequestBody.create(MediaType.parse("multipart/form-data"), nature_of_prot);
        RequestBody requestcom_type = RequestBody.create(MediaType.parse("multipart/form-data"), comp_type);


        MultipartBody.Part requestauth_sign_file = null;
        MultipartBody.Part request_moa_file = null;
        MultipartBody.Part request_aoa_file = null;
        MultipartBody.Part request_electricity_bill = null;
        MultipartBody.Part request_rent_agreement = null;


        if (sourceFile == null) {
            sourceFile = new File("download.jpg");
        }

        RequestBody reuestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "download.jpg");

        requestauth_sign_file = MultipartBody.Part.createFormData("auth_sign_file", "download.jpg", reuestFile);
        request_moa_file = MultipartBody.Part.createFormData("moa_file", "download.jpg", reuestFile);
        request_aoa_file = MultipartBody.Part.createFormData("aoa_file", "download.jpg", reuestFile);
        request_electricity_bill = MultipartBody.Part.createFormData("electricity_bill", "download.jpg", reuestFile);
        request_rent_agreement = MultipartBody.Part.createFormData("rent_agreement", "download.jpg", reuestFile);

        Call<GSTModel> callUploadImg = retrofitAPI.uploadImageData(
                requestauth_sign_file, request_moa_file, request_aoa_file, request_electricity_bill, request_rent_agreement,
                requeststate, requestdistrict, requestbusiness_adress
                , requestnob, requestcreated_by, requestfirm_name_,
                requestnop, requestcom_type);
        callUploadImg.enqueue(new Callback<GSTModel>() {
            @Override
            public void onResponse(Call<GSTModel> call, Response<GSTModel> response) {
                Log.e("FileUploadActivity", "Image " + response.toString());
                Log.e("FileUploadActivity", "????? " + response.message());
                // Log.e("FileUploadActivity", "????? " + response.body().toString());


                if (response.isSuccessful()) {
                    Log.e("FileUploadActivity", "" + response.toString());

                }
            }

            @Override
            public void onFailure(Call<GSTModel> call, Throwable t) {

                Log.e("FileUploadActivity", "image not upload " + t.toString());

            }
        });

    }


    private void submitInfo() {

        String email = input_edt_email.getText().toString();
        String mobile_no = input_edt_mobile.getText().toString();
        String name_of_person = input_edt_name_of_person.getText().toString();
        String f_h_name = input_edt_f_h_name.getText().toString();
        String dob = input_edt_dob.getText().toString();
        String designation = input_edt_designation.getText().toString();
        String pan_no = input_edt_pan_no.getText().toString();
        String adhar_no = input_edt_adhar_no.getText().toString();
        String address = input_edt_address.getText().toString();
        String din_no = input_edt_din_no.getText().toString();


        if(email.toString().isEmpty()) {
            input_edt_email.setError("Email is Empty");
            input_edt_email.requestFocus();

        }else {
            if(mobile_no.toString().isEmpty()) {
                input_edt_mobile.setError("Mobile no is Empty");
                input_edt_mobile.requestFocus();
            }else {

                if(name_of_person.toString().isEmpty()) {
                    input_edt_name_of_person.setError("name is Empty");
                    input_edt_name_of_person.requestFocus();
                }else {
                    if(f_h_name.toString().isEmpty()) {
                        input_edt_f_h_name.setError("Father Or Husband name is Empty");
                        input_edt_f_h_name.requestFocus();
                    } else {
                        if(dob.toString().isEmpty()) {
                            input_edt_dob.setError("Date of Birth is Empty");
                            input_edt_dob.requestFocus();
                        }else {
                            if(designation.toString().isEmpty()) {
                                input_edt_designation.setError(" Designation is Empty");
                                input_edt_designation.requestFocus();
                            }else {
                                if(pan_no.toString().isEmpty()) {
                                    input_edt_pan_no.setError("Pan number is Empty");
                                    input_edt_pan_no.requestFocus();
                                }else {
                                    if(adhar_no.toString().isEmpty()) {
                                        input_edt_adhar_no.setError("Aadhar number is Empty");
                                        input_edt_adhar_no.requestFocus();
                                    }else {
                                        if(address.toString().isEmpty()) {
                                            input_edt_address.setError("Address is Empty");
                                            input_edt_address.requestFocus();
                                        }else {
                                            if(din_no.toString().isEmpty()) {
                                                input_edt_din_no.setError("Din number is Empty");
                                                input_edt_din_no.requestFocus();
                                            }else {

                                                Toast.makeText(this, "Save Information", Toast.LENGTH_SHORT).show();

                                                saveInfoToServer(email,mobile_no,name_of_person,f_h_name,dob,designation,pan_no,adhar_no,address,din_no);

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

    private void saveInfoToServer(String email, String mobile_no, String name_of_person, String f_h_name, String dob, String designation, String pan_no, String adhar_no, String address, String din_no) {


    }


}

