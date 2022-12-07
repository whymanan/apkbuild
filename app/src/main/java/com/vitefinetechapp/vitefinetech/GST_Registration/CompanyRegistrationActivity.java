package com.vitefinetechapp.vitefinetech.GST_Registration;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
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

public class CompanyRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static int IMG_ELECTRICITY_RESULT = 1;
    private static int IMG_RENT_AGRREMENT_RESULT = 2;
    private static int IMG_PHOTO_RESULT = 3;
    private static int IMG_ADHAR_F_RESULT = 4;
    private static int IMG_ADHAR_B_RESULT = 5;
    private static int IMG_PAN_RESULT = 6;
    private static int IMG_BANK_STATEMENT_RESULT = 7;
    private static int IMG_USERFILE_RESULT = 8;

    private static int IMG_PHOTO_CR_VIEW_RESULT = 11;
    private static int IMG_PAN_CR_VIEW_RESULT = 12;
    private static int IMG_ADHAR_F_CR_VIEW_RESULT = 13;
    private static int IMG_ADHAR_B_CR_VIEW_RESULT = 14;
    private static int IMG_BANK_STATEMT_CR_VIEW_RESULT = 15;

    private static final String COMP_REG_ROOT_URL = "https://vitefintech.com/viteapi/legal_api/";

    Spinner nature_of_proprt_sp_pvt;
    ArrayList<String> list, n_of_propt_list;
    TextInputEditText input_edt_buiseness_add;
    Button btn_add_new_cpm_field, delete_buttonCRView, login_bt_reg_cmp, btn_electrcity_choseCR, btn_rent_agree_choseCR, btn_photo_choseCR, btn_adhar_f_g_choseCR,
            btn_adhar_b_g_choseCR, btn_pan_g_choseCR, btn_bank_stmt_choseDCR;
    TextInputLayout input_layout_din_no, input_layout_din_no_add;
    TextInputEditText input_edt_name_CRView, input_edt_emailCRView, input_edt_mobile_noCRView, input_edt_f_h_nameCRView, input_edt_dobCRView, input_edt_pan_no_CRView,
            input_edt_adhar_no_CRView, input_edt_addrCRView, input_edt_designationCRView,
            input_edt_firmCR, input_edit_stateCR, input_edt_districtCR,
            input_edt_buiseness_addCR, input_edt_nameCR, input_edt_emailDCR, input_edt_mobile_noDCR, input_edt_f_h_nameCR,
            input_edt_dobCR, input_edt_pan_no_CR, input_edt_adhar_no_CR, input_edt_addrDCR, input_edt_din_noCR;
    LinearLayout layout_list_comp_reg;
    TextView txt_electricitybill_CR,txt_rent_agreement_CR,txt_photoCR,txt_adhar_f_CR,txt_adhar_b_CR,txt_pan_CR,txt_bank_stmtDCR;
    private int mYear;
    private int mMonth;
    private int mDay;
    ArrayList<String> name_of_person_Array;
    ArrayList<String> email_Array;
    ArrayList<String> mobile_Array;
    ArrayList<String> f_h_name_Array;
    ArrayList<String> dob_Array;
    ArrayList<String> pan_no_Array;
    ArrayList<String> adhar_no_Array;
    ArrayList<String> address_Array;
    ArrayList<String> designation_Array;

    ArrayList<String> photo_Array;
    ArrayList<String> adhar_front_Array;
    ArrayList<String> adhar_back_Array;
    ArrayList<String> pan_file_Array;
    ArrayList<String> statement_Array;
    String member_id,emailPattern;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    Intent intent;
    File file_e,file_rent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);

        nature_of_proprt_sp_pvt = findViewById(R.id.nature_of_proprt_spCR);
        btn_add_new_cpm_field = findViewById(R.id.btn_add_new_cpm_field);
        layout_list_comp_reg = findViewById(R.id.layout_list_comp_reg);
        input_edt_firmCR = findViewById(R.id.input_edt_firmCR);
        input_edit_stateCR = findViewById(R.id.input_edit_stateCR);
        input_edt_districtCR = findViewById(R.id.input_edt_districtCR);
        input_edt_buiseness_addCR = findViewById(R.id.input_edt_buiseness_addCR);
        input_edt_nameCR = findViewById(R.id.input_edt_nameCR);
        input_edt_emailDCR = findViewById(R.id.input_edt_emailDCR);
        input_edt_mobile_noDCR = findViewById(R.id.input_edt_mobile_noDCR);
        input_edt_f_h_nameCR = findViewById(R.id.input_edt_f_h_nameCR);
        input_edt_dobCR = findViewById(R.id.input_edt_dobCR);
        input_edt_pan_no_CR = findViewById(R.id.input_edt_pan_no_CR);
        input_edt_adhar_no_CR = findViewById(R.id.input_edt_adhar_no_CR);
        input_edt_addrDCR = findViewById(R.id.input_edt_addrDCR);
        input_edt_din_noCR = findViewById(R.id.input_edt_din_noCR);
        login_bt_reg_cmp = findViewById(R.id.login_bt_reg_cmp);
        btn_electrcity_choseCR = findViewById(R.id.btn_electrcity_choseCR);
        btn_rent_agree_choseCR = findViewById(R.id.btn_rent_agree_choseCR);
        btn_photo_choseCR = findViewById(R.id.btn_photo_choseCR);
        btn_adhar_f_g_choseCR = findViewById(R.id.btn_adhar_f_g_choseCR);
        btn_adhar_b_g_choseCR = findViewById(R.id.btn_adhar_b_g_choseCR);
        btn_pan_g_choseCR = findViewById(R.id.btn_pan_g_choseCR);
        btn_bank_stmt_choseDCR = findViewById(R.id.btn_bank_stmt_choseDCR);
        txt_electricitybill_CR = findViewById(R.id.txt_electricitybill_CR);
        txt_rent_agreement_CR = findViewById(R.id.txt_rent_agreement_CR);
        txt_photoCR = findViewById(R.id.txt_photoCR);
        txt_adhar_f_CR= findViewById(R.id.txt_adhar_f_CR);
        txt_adhar_b_CR= findViewById(R.id.txt_adhar_b_CR);
        txt_pan_CR = findViewById(R.id.txt_pan_CR);
        txt_bank_stmtDCR = findViewById(R.id.txt_bank_stmtDCR);


        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        btn_add_new_cpm_field.setOnClickListener(this);
        login_bt_reg_cmp.setOnClickListener(this);
        input_edt_dobCR.setOnClickListener(this);
        btn_electrcity_choseCR.setOnClickListener(this);
        btn_rent_agree_choseCR.setOnClickListener(this);
        btn_photo_choseCR.setOnClickListener(this);
        btn_adhar_f_g_choseCR.setOnClickListener(this);
        btn_adhar_b_g_choseCR.setOnClickListener(this);
        btn_pan_g_choseCR.setOnClickListener(this);
        btn_bank_stmt_choseDCR.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        member_id = sharedpreferences.getString("member_id", null);
        Log.e("KYCManagementActivity", "member_id" + member_id);

        selectNatureOfProperty();
        input_edt_dobCR.setOnClickListener(this);


    }


    private void selectNatureOfProperty() {
        n_of_propt_list = new ArrayList<>();

        n_of_propt_list.add(0, "Nature Of Property");
        n_of_propt_list.add(1, "Rented");
        n_of_propt_list.add(2, "Owned");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CompanyRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, n_of_propt_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nature_of_proprt_sp_pvt.setAdapter(dataAdapter);
        nature_of_proprt_sp_pvt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String noptype = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString().equals("Nature Of Property")) {


                } else if (parent.getItemAtPosition(position).toString().equals("Rented")) {


                } else if (parent.getItemAtPosition(position).toString().equals("Owned")) {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_add_new_cpm_field:
                InflateAddNewFieldLayout();
                break;

            case R.id.login_bt_reg_cmp:
                submitCompRegistration();
                break;

            case R.id.input_edt_dobCR:
                hideSoftKeyboard();
                showDateDialog();
                break;

            case R.id.btn_electrcity_choseCR:
                getElectricityImage();
                break;

            case R.id.btn_rent_agree_choseCR:
                getRentAgreeImage();
                break;

            case R.id.btn_photo_choseCR:
                getUserFileImage();
                break;

            case R.id.btn_adhar_f_g_choseCR:
                getAdharFImage();
                break;

            case R.id.btn_adhar_b_g_choseCR:
                getAdharBackImage();
                break;

            case R.id.btn_pan_g_choseCR:
                getPanImage();
                break;

            case R.id.btn_bank_stmt_choseDCR:
                getBankStatementImage();
                break;


        }
    }

    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(CompanyRegistrationActivity.this.getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void getRentAgreeImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_RENT_AGRREMENT_RESULT);

    }

    private void getUserFileImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_USERFILE_RESULT);

    }

    private void getAdharFImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_ADHAR_F_RESULT);

    }

    private void getAdharBackImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_ADHAR_B_RESULT);

    }

    private void getPanImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_PAN_RESULT);

    }

    private void getBankStatementImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_BANK_STATEMENT_RESULT);

    }

    private void getElectricityImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_ELECTRICITY_RESULT);

    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CompanyRegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        input_edt_dobCR.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void submitCompRegistration() {

        name_of_person_Array = new ArrayList<>();
        email_Array = new ArrayList<>();
        mobile_Array = new ArrayList<>();
        f_h_name_Array = new ArrayList<>();
        dob_Array = new ArrayList<>();
        pan_no_Array = new ArrayList<>();
        adhar_no_Array = new ArrayList<>();
        address_Array = new ArrayList<>();
        designation_Array = new ArrayList<>();

        photo_Array = new ArrayList<>();
        adhar_front_Array = new ArrayList<>();
        adhar_back_Array = new ArrayList<>();
        pan_file_Array = new ArrayList<>();
        statement_Array = new ArrayList<>();

        String firm_name = input_edt_firmCR.getText().toString();
        String state = input_edit_stateCR.getText().toString();
        String district = input_edt_districtCR.getText().toString();
        String buss_addr = input_edt_buiseness_addCR.getText().toString();


        String p_name = input_edt_nameCR.getText().toString();
        String email = input_edt_emailDCR.getText().toString();
        String mobile = input_edt_mobile_noDCR.getText().toString();
        String f_h_name = input_edt_f_h_nameCR.getText().toString();
        String dob = input_edt_dobCR.getText().toString();
        String pan_no = input_edt_pan_no_CR.getText().toString();
        String adhar_no = input_edt_adhar_no_CR.getText().toString();
        String addr = input_edt_addrDCR.getText().toString();
        String din_no = input_edt_din_noCR.getText().toString();

        name_of_person_Array.add(p_name);
        email_Array.add(email);
        mobile_Array.add(mobile);
        f_h_name_Array.add(f_h_name);
        dob_Array.add(dob);
        pan_no_Array.add(pan_no);
        adhar_no_Array.add(adhar_no);
        address_Array.add(addr);
        designation_Array.add(din_no);


        String photo = txt_photoCR.getText().toString();
        String adhar_ff = txt_adhar_f_CR.getText().toString();
        String adhar_bb = txt_adhar_b_CR.getText().toString();
        String pan_photo = txt_pan_CR.getText().toString();
        String stamt_photo = txt_bank_stmtDCR.getText().toString();

        photo_Array.add(photo);
        adhar_front_Array.add(adhar_ff);
        adhar_back_Array.add(adhar_bb);
        pan_file_Array.add(pan_photo);
        statement_Array.add(stamt_photo);

        for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++) {

            View cricketerView = layout_list_comp_reg.getChildAt(i);


            name_of_person_Array.add(input_edt_name_CRView.getText().toString());
            Log.e("CompRegActivity", "name of person==" + name_of_person_Array);

            email_Array.add(input_edt_emailCRView.getText().toString());
            Log.e("CompRegActivity", "email_==" + email_Array);

            mobile_Array.add(input_edt_mobile_noCRView.getText().toString());
            Log.e("CompRegActivity", "mobile no ==" + mobile_Array);

            f_h_name_Array.add(input_edt_f_h_nameCRView.getText().toString());
            Log.e("CompRegActivity", "middle name==" + f_h_name_Array);

            dob_Array.add(input_edt_dobCRView.getText().toString());
            Log.e("CompRegActivity", "dob==" + dob_Array);

            pan_no_Array.add(input_edt_pan_no_CRView.getText().toString());
            Log.e("CompRegActivity", "pan no.==" + pan_no_Array);

            adhar_no_Array.add(input_edt_adhar_no_CRView.getText().toString());
            Log.e("CompRegActivity", "adhar no==" + adhar_no_Array);

            address_Array.add(input_edt_addrCRView.getText().toString());
            Log.e("CompRegActivity", "address==" + address_Array);

            designation_Array.add(input_edt_designationCRView.getText().toString());
            Log.e("CompRegActivity", "din no==" + designation_Array);

            TextView txt_photoCRView = (TextView) cricketerView.findViewById(R.id.txt_photoCRView);
            photo_Array.add(txt_photoCRView.getText().toString());
            Log.e("CompRegActivity", "photo_Array==" + photo_Array);

            TextView txt_adhar_f_CRView = (TextView) cricketerView.findViewById(R.id.txt_adhar_f_CRView);
            adhar_front_Array.add(txt_adhar_f_CRView.getText().toString());
            Log.e("CompRegActivity", "adhar_front_Array==" + adhar_front_Array);

            TextView txt_adhar_b_CRView = (TextView) cricketerView.findViewById(R.id.txt_adhar_b_CRView);
            adhar_back_Array.add(txt_adhar_b_CRView.getText().toString());
            Log.e("CompRegActivity", "adhar_back_Array==" + adhar_back_Array);

            TextView txt_pan_CRView = (TextView) cricketerView.findViewById(R.id.txt_pan_CRView);
            pan_file_Array.add(txt_pan_CRView.getText().toString());
            Log.e("CompRegActivity", "pan_file_Array==" + pan_file_Array);

            TextView txt_bank_stmt_CRView = (TextView) cricketerView.findViewById(R.id.txt_bank_stmt_CRView);
            statement_Array.add(txt_bank_stmt_CRView.getText().toString());
            Log.e("CompRegActivity", "statement_Array==" + statement_Array);


        }

        name_of_person_Array.add(p_name);
        email_Array.add(email);
        mobile_Array.add(mobile);
        f_h_name_Array.add(f_h_name);
        dob_Array.add(dob);
        pan_no_Array.add(pan_no);
        adhar_no_Array.add(adhar_no);
        address_Array.add(buss_addr);
        designation_Array.add(din_no);
        photo_Array.add(photo);
        adhar_front_Array.add(adhar_ff);
        adhar_back_Array.add(adhar_bb);
        pan_file_Array.add(pan_photo);
        statement_Array.add(stamt_photo);

        String nature_of_proprty = nature_of_proprt_sp_pvt.getSelectedItem().toString();
        Log.e("CompRegActivity", "name of person==" + nature_of_proprty);


        if (firm_name.toString().isEmpty()) {
            input_edt_firmCR.setError("Firm Name is Empty");
            input_edt_firmCR.requestFocus();

        } else {

            if (state.toString().isEmpty()) {
                input_edit_stateCR.setError("State is Empty");
                input_edit_stateCR.requestFocus();

            } else {

                if (district.toString().isEmpty()) {
                    input_edt_districtCR.setError("District is Empty");
                    input_edt_districtCR.requestFocus();

                } else {


                    if (buss_addr.toString().isEmpty()) {
                        input_edt_buiseness_addCR.setError("Business Address is Empty");
                        input_edt_buiseness_addCR.requestFocus();

                    } else {

                        if (nature_of_proprt_sp_pvt.getSelectedItem().toString().equals("Nature Of Property")) {
                            Toast.makeText(this, "Please Select Nature of Property", Toast.LENGTH_SHORT).show();

                        } else {

                            if (p_name.toString().isEmpty()) {
                                input_edt_nameCR.setError("Person name is Empty");
                                input_edt_nameCR.requestFocus();

                            } else {

                                if (email.toString().isEmpty()) {
                                    input_edt_emailDCR.setError("Email is Empty");
                                    input_edt_emailDCR.requestFocus();

                                } else {

                                    if(!(email.matches(emailPattern) ))
                                    {
                                        Toast.makeText(CompanyRegistrationActivity.this, "Please Enter valid Email Address", Toast.LENGTH_SHORT).show();

                                    }else
                                    {
                                        if (mobile.toString().isEmpty()) {
                                            input_edt_mobile_noDCR.setError("Mobile is Empty");
                                            input_edt_mobile_noDCR.requestFocus();

                                        } else {

                                            if (mobile.length() == 0 || mobile.length() > 10 || mobile.length() < 10) {

                                                input_edt_mobile_noDCR.setError("Mobile is Empty");
                                                input_edt_mobile_noDCR.requestFocus();

                                            } else {

                                                if (f_h_name.toString().isEmpty()) {
                                                    input_edt_f_h_nameCR.setError("Father/Husband is Empty");
                                                    input_edt_f_h_nameCR.requestFocus();

                                                } else {

                                                    if (dob.toString().isEmpty()) {
                                                        input_edt_f_h_nameCR.setError("Father/Husband is Empty");
                                                        input_edt_f_h_nameCR.requestFocus();

                                                    } else {

                                                        int length = layout_list_comp_reg.getChildCount();
                                                        Log.e("CompnyRegistration", "length" + length);

                                                        if (txt_electricitybill_CR.getText().toString().equals("No file Choosen")) {
                                                            Toast.makeText(CompanyRegistrationActivity.this, "Please Select Eletricity bill", Toast.LENGTH_SHORT).show();
                                                        } else {


                                                            if(txt_photoCR.getText().toString().equals("No file Choosen"))
                                                            {
                                                                Toast.makeText(CompanyRegistrationActivity.this, "Please Select Photo", Toast.LENGTH_SHORT).show();

                                                            }else {
                                                                if (txt_adhar_f_CR.getText().toString().equals("No file Choosen")) {

                                                                    Toast.makeText(CompanyRegistrationActivity.this, "Please Select Aadhar Front  ", Toast.LENGTH_SHORT).show();

                                                                } else {

                                                                    if (txt_adhar_b_CR.getText().toString().equals("No file Choosen")) {

                                                                        Toast.makeText(CompanyRegistrationActivity.this, "Please Select Aadhar Back  ", Toast.LENGTH_SHORT).show();

                                                                    } else {
                                                                        if (txt_pan_CR.getText().toString().equals("No file Choosen")) {
                                                                            Toast.makeText(CompanyRegistrationActivity.this, "Please Select Pan", Toast.LENGTH_SHORT).show();

                                                                        } else {

                                                                            if (txt_bank_stmtDCR.getText().toString().equals("No file Choosen")) {
                                                                                Toast.makeText(CompanyRegistrationActivity.this, "Please Select Statement", Toast.LENGTH_SHORT).show();

                                                                            } else {
                                                                                if (checkIfValidateData()) {
                                                                                    submitToServer(state, nature_of_proprty, district, firm_name, buss_addr);
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
                                    }
                                }
                            }
                        }


                    }
                }

            }
        }

    }




    private boolean checkIfValidateData()
    {

        boolean result = true;

        for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++) {


            View inflate_view = layout_list_comp_reg.getChildAt(i);


            input_edt_name_CRView = inflate_view.findViewById(R.id.input_edt_name_CRView);
            input_edt_emailCRView = inflate_view.findViewById(R.id.input_edt_emailCRView);
            input_edt_mobile_noCRView = inflate_view.findViewById(R.id.input_edt_mobile_noCRView);
            input_edt_f_h_nameCRView = inflate_view.findViewById(R.id.input_edt_f_h_nameCRView);
            input_edt_dobCRView = inflate_view.findViewById(R.id.input_edt_dobCRView);
            input_edt_pan_no_CRView = inflate_view.findViewById(R.id.input_edt_pan_no_CRView);
            input_edt_adhar_no_CRView = inflate_view.findViewById(R.id.input_edt_adhar_no_CRView);
            input_edt_addrCRView = inflate_view.findViewById(R.id.input_edt_addrCRView);
            input_edt_designationCRView = inflate_view.findViewById(R.id.input_edt_designationCRView);

            TextView txt_photoCRView = (TextView) inflate_view.findViewById(R.id.txt_photoCRView);
            Log.e("CompRegActivity", "photo_Array==" + photo_Array);

            TextView txt_adhar_f_CRView = (TextView) inflate_view.findViewById(R.id.txt_adhar_f_CRView);
            Log.e("CompRegActivity", "adhar_front_Array==" + adhar_front_Array);

            TextView txt_adhar_b_CRView = (TextView) inflate_view.findViewById(R.id.txt_adhar_b_CRView);
            Log.e("CompRegActivity", "adhar_back_Array==" + adhar_back_Array);

            TextView txt_pan_CRView = (TextView) inflate_view.findViewById(R.id.txt_pan_CRView);
            Log.e("CompRegActivity", "pan_file_Array==" + pan_file_Array);

            TextView txt_bank_stmt_CRView = (TextView) inflate_view.findViewById(R.id.txt_bank_stmt_CRView);
            Log.e("CompRegActivity", "statement_Array==" + statement_Array);



            if (!(input_edt_name_CRView.getText().toString().equals(""))&&
                    !(input_edt_emailCRView.getText().toString().equals(""))&&
                    !(input_edt_mobile_noCRView.getText().toString().equals(""))&&
                    !(input_edt_f_h_nameCRView.getText().toString().equals(""))&&
                    !(input_edt_dobCRView.getText().toString().equals(""))&&
                    !(input_edt_pan_no_CRView.getText().toString().equals(""))&&
                    !(input_edt_adhar_no_CRView.getText().toString().equals(""))&&
                    !(input_edt_addrCRView.getText().toString().equals(""))&&
                    !(input_edt_designationCRView.getText().toString().equals("")))
            {
                name_of_person_Array.add(input_edt_name_CRView.getText().toString());
                name_of_person_Array.add(input_edt_emailCRView.getText().toString());
                name_of_person_Array.add(input_edt_mobile_noCRView.getText().toString());
                name_of_person_Array.add(input_edt_f_h_nameCRView.getText().toString());
                name_of_person_Array.add(input_edt_dobCRView.getText().toString());
                name_of_person_Array.add(input_edt_pan_no_CRView.getText().toString());
                name_of_person_Array.add(input_edt_adhar_no_CRView.getText().toString());
                name_of_person_Array.add(input_edt_addrCRView.getText().toString());
                name_of_person_Array.add(input_edt_designationCRView.getText().toString());



            } else {

                Toast.makeText(CompanyRegistrationActivity.this, "Fill All data", Toast.LENGTH_SHORT).show();
                result = false;
                break;
            }


            if(!(txt_photoCRView.getText().toString().equals("No file Choosen")) &&
                    !(txt_adhar_f_CRView.getText().toString().equals("No file Choosen")) &&
                    !(txt_adhar_b_CRView.getText().toString().equals("No file Choosen")) &&
                    ! (txt_pan_CRView.getText().toString().equals("No file Choosen"))&&
                    !(txt_bank_stmt_CRView.getText().toString().equals("No file Choosen"))
            )
            {

                photo_Array.add(txt_photoCRView.getText().toString());
                adhar_front_Array.add(txt_adhar_f_CRView.getText().toString());
                adhar_back_Array.add(txt_adhar_b_CRView.getText().toString());
                pan_file_Array.add(txt_pan_CRView.getText().toString());
                statement_Array.add(txt_bank_stmt_CRView.getText().toString());


            }else {

                Toast.makeText(CompanyRegistrationActivity.this, "Fill All data", Toast.LENGTH_SHORT).show();
                result = false;
                break;
            }

        }

        return   result;
    }

    void submitToServer(String state, String nature_of_proprty, String district, String firm_name, String buss_addr){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(COMP_REG_ROOT_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);


        MultipartBody.Part[] multipartTypedPhoto = new MultipartBody.Part[photo_Array.size()];

        for (int index = 0; index < photo_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + photo_Array.get(index));
            File file2 = new File(photo_Array.get(index));
            RequestBody surveyBodyPhoto = RequestBody.create(MediaType.parse("image/*"), file2);
//            RequestBody surveyBodyPhoto = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

            multipartTypedPhoto[index] = MultipartBody.Part.createFormData("userfile[]", file2.getPath(), surveyBodyPhoto);
        }


        MultipartBody.Part[] multipartTypedadhar_f = new MultipartBody.Part[adhar_front_Array.size()];

        for (int index = 0; index < adhar_front_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + adhar_front_Array.get(index));
            File file2 = new File(adhar_front_Array.get(index));
            RequestBody surveyBodyadhar_f = RequestBody.create(MediaType.parse("image/*"), file2);
//            RequestBody surveyBodyadhar_f = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

            multipartTypedadhar_f[index] = MultipartBody.Part.createFormData("adhar_front[]", file2.getPath(), surveyBodyadhar_f);
        }

        MultipartBody.Part[] multipartTypedadhar_b = new MultipartBody.Part[adhar_back_Array.size()];

        for (int index = 0; index < adhar_back_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + adhar_back_Array.get(index));
            File file2 = new File(adhar_back_Array.get(index));
            RequestBody surveyBodyadhar_b = RequestBody.create(MediaType.parse("image/*"), file2);
//            RequestBody surveyBodyadhar_b = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

            multipartTypedadhar_b[index] = MultipartBody.Part.createFormData("adhar_back[]", file2.getPath(), surveyBodyadhar_b);
        }

        MultipartBody.Part[] multipartTypedPan = new MultipartBody.Part[pan_file_Array.size()];

        for (int index = 0; index < pan_file_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + pan_file_Array.get(index));
            File file2 = new File(pan_file_Array.get(index));
            RequestBody surveyBodyPan = RequestBody.create(MediaType.parse("image/*"), file2);
//            RequestBody surveyBodyPan = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

            multipartTypedPan[index] = MultipartBody.Part.createFormData("pan_file[]", file2.getPath(), surveyBodyPan);
        }

        MultipartBody.Part[] multipartType_bank_stmt = new MultipartBody.Part[statement_Array.size()];

        for (int index = 0; index < statement_Array.size(); index++) {
            Log.d("Upload request --", "requestUploadSurvey: survey image " + index + "  " + statement_Array.get(index));
            File file2 = new File(statement_Array.get(index));
            RequestBody surveyBodyStmt = RequestBody.create(MediaType.parse("image/*"), file2);
//            RequestBody surveyBodyStmt = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

            multipartType_bank_stmt[index] = MultipartBody.Part.createFormData("statement[]", file2.getPath(), surveyBodyStmt);
        }

        if(txt_rent_agreement_CR.getText().toString().equals("No file Choosen"))
        {
            txt_rent_agreement_CR.setText("");
        }
        file_e = new File(txt_electricitybill_CR.getText().toString());
        file_rent = new File(txt_rent_agreement_CR.getText().toString());


        if(file_e == null){
            file_e = new File(file_e.getPath());
        }

        RequestBody reuestFile_e = RequestBody.create(MediaType.parse("multipart/form-data"), file_e.getPath());
        RequestBody reuestFile_rent = RequestBody.create(MediaType.parse("multipart/form-data"), file_rent.getPath());

        MultipartBody.Part requestauth_cert_file = null;
        MultipartBody.Part requestauth_moa_file = null;
        MultipartBody.Part requestauth_aoa_file = null;
        MultipartBody.Part requestauth_electricity_file = null;
        MultipartBody.Part requestauth_rent_agree_file = null;


        //  requestauth_cert_file = MultipartBody.Part.createFormData("coi", file_e.getPath(), reuestFile);
//      requestauth_moa_file = MultipartBody.Part.createFormData("auth_sign_file", "download.jpg", reuestFile);
//      requestauth_aoa_file = MultipartBody.Part.createFormData("auth_sign_file", "download.jpg", reuestFile);
        requestauth_electricity_file = MultipartBody.Part.createFormData("electricity_bill", file_e.getPath(), reuestFile_e);
        requestauth_rent_agree_file = MultipartBody.Part.createFormData("rent_agreement", file_rent.getPath(), reuestFile_rent);

//        String nature_of_proprty = nature_of_proprt_sp_pvt.getSelectedItem().toString();

        RequestBody RBcreated_by = RequestBody.create(MediaType.parse("text/plain"), member_id);

        RequestBody RBstate = RequestBody.create(MediaType.parse("text/plain"), state);
        RequestBody RBdistrict = RequestBody.create(MediaType.parse("text/plain"), district);
        RequestBody RBbusiness_adress = RequestBody.create(MediaType.parse("text/plain"), buss_addr);
        RequestBody RBnop = RequestBody.create(MediaType.parse("text/plain"), nature_of_proprty);
        RequestBody RBfirm_name = RequestBody.create(MediaType.parse("text/plain"), firm_name);

        Log.d("length of array", "--->" + "" + name_of_person_Array.size());


        Call<JsonObject> callUploadImg = retrofitAPI.postImageComReg(RBstate, RBdistrict, RBbusiness_adress,
                RBnop, RBfirm_name, RBcreated_by, multipartTypedPhoto, multipartTypedadhar_f, multipartTypedadhar_b,
                multipartTypedPan, multipartType_bank_stmt, requestauth_electricity_file, requestauth_rent_agree_file,
                name_of_person_Array, f_h_name_Array, dob_Array, email_Array, mobile_Array,
                pan_no_Array, adhar_no_Array, designation_Array, address_Array);

        callUploadImg.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // progressBar.dismiss();

                Log.d("fb_regist_response", "--->" + "" + response.body());
                Log.d("fb_regist_response1", "--->" + "" + response.body());

                JsonObject object = response.body();
                Log.d("fb_regist_response", object.toString());

                String status = String.valueOf(object.get("status"));
                Log.d("fb_regist_response", status);

                String message = String.valueOf(object.get("Message"));
                Log.d(" message ===", message);

                if (status.equals("true")) {
                    Toast.makeText(CompanyRegistrationActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(CompanyRegistrationActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("onFail_fb_regist_res", t.getMessage());
            }
        });


    }

    private void InflateAddNewFieldLayout() {


        final View inflate_view = getLayoutInflater().inflate(R.layout.add_new_comp_reg_fields_layout, null, false);


        input_edt_name_CRView = inflate_view.findViewById(R.id.input_edt_name_CRView);
        input_edt_emailCRView = inflate_view.findViewById(R.id.input_edt_emailCRView);
        input_edt_mobile_noCRView = inflate_view.findViewById(R.id.input_edt_mobile_noCRView);
        input_edt_f_h_nameCRView = inflate_view.findViewById(R.id.input_edt_f_h_nameCRView);
        input_edt_dobCRView = inflate_view.findViewById(R.id.input_edt_dobCRView);
        input_edt_pan_no_CRView = inflate_view.findViewById(R.id.input_edt_pan_no_CRView);
        input_edt_adhar_no_CRView = inflate_view.findViewById(R.id.input_edt_adhar_no_CRView);
        input_edt_addrCRView = inflate_view.findViewById(R.id.input_edt_addrCRView);
        input_edt_designationCRView = inflate_view.findViewById(R.id.input_edt_designationCRView);


        delete_buttonCRView = inflate_view.findViewById(R.id.delete_buttonCRView);
        delete_buttonCRView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeView(inflate_view);


            }
        });

        input_edt_dobCRView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                final int index = layout_list_comp_reg.indexOfChild(inflate_view);

                showAddFieldDateDialog(inflate_view);
            }
        });
        Button btn_bank_stmt_chose_CRView = inflate_view.findViewById(R.id.btn_bank_stmt_chose_CRView);
        Button btn_pan_g_chose_CRView = inflate_view.findViewById(R.id.btn_pan_g_chose_CRView);
        Button btn_adhar_b_g_chose_CRView = inflate_view.findViewById(R.id.btn_adhar_b_g_chose_CRView);
        Button btn_adhar_f_g_chose_CRView = inflate_view.findViewById(R.id.btn_adhar_f_g_chose_CRView);
        Button btn_photo_choseCRView = inflate_view.findViewById(R.id.btn_photo_choseCRView);


        btn_photo_choseCRView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexPhoto = layout_list_comp_reg.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("photo_index", String.valueOf(indexPhoto));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexPhoto);


                setPhotoImageOn(indexPhoto);

            }
        });


        btn_adhar_f_g_chose_CRView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexPhoto = layout_list_comp_reg.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("photo_index", String.valueOf(indexPhoto));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexPhoto);


                setAdharFrontImageOn(indexPhoto);

            }
        });


        btn_adhar_b_g_chose_CRView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexPhoto = layout_list_comp_reg.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("photo_index", String.valueOf(indexPhoto));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexPhoto);


                setAdharBackImageOn(indexPhoto);

            }
        });


        btn_pan_g_chose_CRView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexPhoto = layout_list_comp_reg.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("photo_index", String.valueOf(indexPhoto));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexPhoto);


                setPanImageOn(indexPhoto);

            }
        });


        btn_bank_stmt_chose_CRView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexPhoto = layout_list_comp_reg.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("photo_index", String.valueOf(indexPhoto));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexPhoto);


                setBankStmtImageOn(indexPhoto);

            }
        });

        layout_list_comp_reg.addView(inflate_view, layout_list_comp_reg.getChildCount() - 1);


    }

    private void setBankStmtImageOn(int indexPhoto) {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_BANK_STATEMT_CR_VIEW_RESULT);

    }

    private void setPanImageOn(int indexPhoto) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_PAN_CR_VIEW_RESULT);

    }

    private void setAdharBackImageOn(int indexPhoto) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_ADHAR_B_CR_VIEW_RESULT);

    }

    private void setAdharFrontImageOn(int indexPhoto) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_ADHAR_F_CR_VIEW_RESULT);

    }

    private void setPhotoImageOn(int indexPhoto) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_PHOTO_CR_VIEW_RESULT);

    }

    private void showAddFieldDateDialog(View inflate_view) {


        for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++) {
            // View cricketerView = layout_list_new_gst.getChildAt(index_value);

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(CompanyRegistrationActivity.this,

                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            EditText editText = (EditText) inflate_view.findViewById(R.id.input_edt_dobCRView);

                            editText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }


    }

    private void removeView(View inflate_view) {

        layout_list_comp_reg.removeView(inflate_view);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {


            if (requestCode == IMG_ELECTRICITY_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};
                File file = new File(URI.getPath());
                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_electricitybill_CR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_RENT_AGRREMENT_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_rent_agreement_CR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_USERFILE_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_photoCR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_ADHAR_F_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_adhar_f_CR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_ADHAR_B_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_adhar_b_CR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_PAN_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_pan_CR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_BANK_STATEMENT_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();


                txt_bank_stmtDCR.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_PHOTO_CR_VIEW_RESULT && resultCode == RESULT_OK
                    && null != data) {

                Log.e("TAG", "onActivityResult: " + data.getStringExtra("position"));
                Log.e("TAG", "onActivityResult: " + data.getExtras().getString("position"));

                Bundle extras = data.getExtras();
                String vbvbv = extras.getString("key");
                Log.e("GSTReturnActivity", "vbvbv ==" + vbvbv);

                String sessionId = data.getStringExtra("position");
                Log.e("GSTReturnActivity", "sessionId ==" + sessionId);
                Log.e("GSTReturnActivity", "sessionId ???" + data.getExtras());


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();



                for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++)
                {
                    String key_nn = sharedpreferences.getString("photo_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_comp_reg.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_photoCRView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }


            if (requestCode == IMG_ADHAR_F_CR_VIEW_RESULT && resultCode == RESULT_OK
                    && null != data) {

                Log.e("TAG", "onActivityResult: " + data.getStringExtra("position"));
                Log.e("TAG", "onActivityResult: " + data.getExtras().getString("position"));

                Bundle extras = data.getExtras();
                String vbvbv = extras.getString("key");
                Log.e("GSTReturnActivity", "vbvbv ==" + vbvbv);

                String sessionId = data.getStringExtra("position");
                Log.e("GSTReturnActivity", "sessionId ==" + sessionId);
                Log.e("GSTReturnActivity", "sessionId ???" + data.getExtras());


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();



                for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++)
                {
                    String key_nn = sharedpreferences.getString("photo_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_comp_reg.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_adhar_f_CRView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }



            if (requestCode == IMG_ADHAR_B_CR_VIEW_RESULT && resultCode == RESULT_OK
                    && null != data) {

                Log.e("TAG", "onActivityResult: " + data.getStringExtra("position"));
                Log.e("TAG", "onActivityResult: " + data.getExtras().getString("position"));

                Bundle extras = data.getExtras();
                String vbvbv = extras.getString("key");
                Log.e("GSTReturnActivity", "vbvbv ==" + vbvbv);

                String sessionId = data.getStringExtra("position");
                Log.e("GSTReturnActivity", "sessionId ==" + sessionId);
                Log.e("GSTReturnActivity", "sessionId ???" + data.getExtras());


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();



                for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++)
                {
                    String key_nn = sharedpreferences.getString("photo_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_comp_reg.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_adhar_b_CRView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }



            if (requestCode == IMG_PAN_CR_VIEW_RESULT && resultCode == RESULT_OK
                    && null != data) {

                Log.e("TAG", "onActivityResult: " + data.getStringExtra("position"));
                Log.e("TAG", "onActivityResult: " + data.getExtras().getString("position"));

                Bundle extras = data.getExtras();
                String vbvbv = extras.getString("key");
                Log.e("GSTReturnActivity", "vbvbv ==" + vbvbv);

                String sessionId = data.getStringExtra("position");
                Log.e("GSTReturnActivity", "sessionId ==" + sessionId);
                Log.e("GSTReturnActivity", "sessionId ???" + data.getExtras());


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();



                for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++)
                {
                    String key_nn = sharedpreferences.getString("photo_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_comp_reg.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_pan_CRView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }



            if (requestCode == IMG_BANK_STATEMT_CR_VIEW_RESULT && resultCode == RESULT_OK
                    && null != data) {

                Log.e("TAG", "onActivityResult: " + data.getStringExtra("position"));
                Log.e("TAG", "onActivityResult: " + data.getExtras().getString("position"));

                Bundle extras = data.getExtras();
                String vbvbv = extras.getString("key");
                Log.e("GSTReturnActivity", "vbvbv ==" + vbvbv);

                String sessionId = data.getStringExtra("position");
                Log.e("GSTReturnActivity", "sessionId ==" + sessionId);
                Log.e("GSTReturnActivity", "sessionId ???" + data.getExtras());


                Uri URI = data.getData();
                String[] FILE = {MediaStore.Images.Media.DATA};

                Log.e("KYCManagement", "URI ==" + URI);

                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);

                Log.e("KYCManagement", "ImageDecode ==" + ImageDecode);

                cursor.close();



                for (int i = 0; i < layout_list_comp_reg.getChildCount(); i++)
                {
                    String key_nn = sharedpreferences.getString("photo_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_comp_reg.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_bank_stmt_CRView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }
            }
        } catch (Exception e) {

        }
    }
}