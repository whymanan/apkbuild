package com.vitefinetechapp.vitefinetech.GST_Registration;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class GSTRegistrationActivity extends AppCompatActivity implements View.OnClickListener {


    private static int IMG_CERTIFICATE_RESULT = 1;
    private static int IMG_MOA_RESULT = 2;
    private static int IMG_AOA_RESULT = 3;
    private static int IMG_ELECTRICITY_RESULT = 4;
    private static int IMG_RENT_AGRREMENT_RESULT = 5;
    private static int IMG_PHOTO_RESULT = 6;
    private static int IMG_ADHAR_F_RESULT = 7;
    private static int IMG_ADHAR_B_RESULT = 8;
    private static int IMG_PAN_RESULT = 9;
    private static int IMG_BANK_STATEMENT_RESULT = 10;
    private static int IMG_PHOTO_VIEW_RESULT = 11;
    private static int IMG_PAN_VIEW_RESULT = 12;
    private static int IMG_ADHAR_F_VIEW_RESULT = 13;
    private static int IMG_ADHAR_B_VIEW_RESULT = 14;
    private static int IMG_BANK_STATEMT_VIEW_RESULT = 15;
    private ProgressDialog progressBar;

    private int mYear;
    private int mMonth;
    private int mDay;

    private static final String ROOT_URL = "https://vitefintech.com/viteapi/legal_api/";

    ArrayList<String> list, n_of_propt_list;
    Spinner sp_sel_comp_type, nature_of_proprt_sp;
    RelativeLayout relative_direct_form;
    TextView txt_dirct_name, txt_certi, txt_moaa, txt_aoa_b_g, txt_electricitybill_g, txt_rent_agreement_g,
            txt_photoD, txt_adhar_f_gD, txt_adhar_b_gD, txt_pan_gD, txt_bank_stmtD;
    CardView card_view_photo, card_view_adhar_f, card_view_adhar_b, card_view_pan, card_view_bank_stmt, card_view_moa, card_view_aoa;
    Button add_new_field, login_bt, delete_button, btn_certi_chose1, btn_moa_chose, btn_aoa_chose, btn_electrcity_chose,
            btn_rent_agree_chose, btn_photo_choseD, btn_adhar_f_g_choseD, btn_adhar_b_g_choseD, btn_pan_g_choseD,
            btn_bank_stmt_choseD;

    LayoutInflater linf;
    LinearLayout layout_list_new_gst, linear____;

    RelativeLayout relative_main;
    LayoutInflater inflater;
    View inflate_view;
    int id;
    String member_id;
    View myView;
    ViewGroup vv;

    //EditText input_edt_emailD;
    TextInputEditText input_edt_name_a, input_edt_emailD_a, input_edt_mobile_noD_a, input_edt_f_h_nameD_a, input_edt_dobD_a, input_edt_pan_no_gD_a, input_edt_adhar_no_gD_a, input_edt_addrD_a, input_edt_din_noD_a,
            input_edt_name, input_edt_emailD, input_edt_mobile_noD, input_edt_f_h_nameD, input_edt_dobD, input_edt_pan_no_gD, input_edt_adhar_no_gD, input_edt_addrD, input_edt_din_noD;
    TextInputLayout input_layout_din_no, input_layout_din_no_add;
    TextInputEditText input_edt_firm, input_edt_natureof_business, input_edit_state, input_edt_district, input_edt_buiseness_add;


    ArrayList<GSTModel> gstList = new ArrayList<>();
    Intent intent;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    ArrayList<String> name_of_person_Array;
    ArrayList<String> email_Array;
    ArrayList<String> mobile_Array;
    ArrayList<String> f_h_name_Array;
    ArrayList<String> dob_Array;
    ArrayList<String> pan_no_Array;
    ArrayList<String> adhar_no_Array;
    ArrayList<String> address_Array;
    ArrayList<String> din_Array;

    ArrayList<String> photo_Array;
    ArrayList<String> adhar_front_Array;
    ArrayList<String> adhar_back_Array;
    ArrayList<String> pan_file_Array;
    ArrayList<String> statement_Array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstregistration);

        sp_sel_comp_type = findViewById(R.id.sel_comp_type_spinner);
        relative_direct_form = findViewById(R.id.relative_direct_form);
        txt_dirct_name = findViewById(R.id.txt_dirct_name);
        card_view_photo = findViewById(R.id.card_view_photo);
        card_view_adhar_f = findViewById(R.id.card_view_adhar_f);
        card_view_adhar_b = findViewById(R.id.card_view_adhar_b);
        card_view_pan = findViewById(R.id.card_view_pan);
        card_view_bank_stmt = findViewById(R.id.card_view_bank_stmt);
        add_new_field = findViewById(R.id.btn_add_field);
        login_bt = findViewById(R.id.login_bt);
        input_layout_din_no = findViewById(R.id.input_layout_din_no);
        linear____ = findViewById(R.id.linear____);
        relative_main = findViewById(R.id.relative_main);
        card_view_aoa = findViewById(R.id.card_view_aoa);
        card_view_moa = findViewById(R.id.card_view_moa);
        nature_of_proprt_sp = findViewById(R.id.nature_of_proprt_sp);
        input_edt_firm = findViewById(R.id.input_edt_firm);
        input_edt_natureof_business = findViewById(R.id.input_edt_natureof_business);
        input_edit_state = findViewById(R.id.input_edit_state);
        input_edt_district = findViewById(R.id.input_edt_district);
        input_edt_buiseness_add = findViewById(R.id.input_edt_buiseness_add);


        input_edt_name = findViewById(R.id.input_edt_name);
        input_edt_emailD = findViewById(R.id.input_edt_emailD);
        input_edt_mobile_noD = findViewById(R.id.input_edt_mobile_noD);
        input_edt_f_h_nameD = findViewById(R.id.input_edt_f_h_nameD);
        input_edt_dobD = findViewById(R.id.input_edt_dobD);
        input_edt_pan_no_gD = findViewById(R.id.input_edt_pan_no_gD);

        input_edt_adhar_no_gD = findViewById(R.id.input_edt_adhar_no_gD);
        input_edt_addrD = findViewById(R.id.input_edt_addrD);
        input_edt_din_noD = findViewById(R.id.input_edt_din_noD);

        layout_list_new_gst = findViewById(R.id.layout_list_new_gst);


        btn_certi_chose1 = findViewById(R.id.btn_certi_chose1);
        btn_moa_chose = findViewById(R.id.btn_moa_chose);
        btn_aoa_chose = findViewById(R.id.btn_aoa_chose);
        btn_electrcity_chose = findViewById(R.id.btn_electrcity_chose);
        btn_rent_agree_chose = findViewById(R.id.btn_rent_agree_chose);


        btn_photo_choseD = findViewById(R.id.btn_photo_choseD);
        btn_adhar_f_g_choseD = findViewById(R.id.btn_adhar_f_g_choseD);
        btn_adhar_b_g_choseD = findViewById(R.id.btn_adhar_b_g_choseD);
        btn_pan_g_choseD = findViewById(R.id.btn_pan_g_choseD);
        btn_bank_stmt_choseD = findViewById(R.id.btn_bank_stmt_choseD);

        txt_certi = findViewById(R.id.txt_certi);
        txt_moaa = findViewById(R.id.txt_moaa);
        txt_aoa_b_g = findViewById(R.id.txt_aoa_b_g);
        txt_electricitybill_g = findViewById(R.id.txt_electricitybill_g);
        txt_rent_agreement_g = findViewById(R.id.txt_rent_agreement_g);
        txt_photoD = findViewById(R.id.txt_photoD);
        txt_adhar_f_gD = findViewById(R.id.txt_adhar_f_gD);
        txt_adhar_b_gD = findViewById(R.id.txt_adhar_b_gD);
        txt_pan_gD = findViewById(R.id.txt_pan_gD);
        txt_bank_stmtD = findViewById(R.id.txt_bank_stmtD);


        // inflate_view = inflater.inflate(R.layout.add_new_fields_layout, null,false);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        member_id = sharedpreferences.getString("member_id", null);
        Log.e("KYCManagementActivity", "member_id" + member_id);

        inflater = GSTRegistrationActivity.this.getLayoutInflater();

        inflate_view = new View(GSTRegistrationActivity.this);


        id = GSTRegistrationActivity.this.getResources().getIdentifier("internetnotconnected", "layout", GSTRegistrationActivity.this.getPackageName());

        add_new_field.setOnClickListener(this);
        login_bt.setOnClickListener(this);
        btn_certi_chose1.setOnClickListener(this);
        btn_moa_chose.setOnClickListener(this);
        btn_aoa_chose.setOnClickListener(this);
        btn_electrcity_chose.setOnClickListener(this);
        btn_rent_agree_chose.setOnClickListener(this);
        btn_photo_choseD.setOnClickListener(this);
        btn_adhar_f_g_choseD.setOnClickListener(this);
        btn_adhar_b_g_choseD.setOnClickListener(this);
        btn_pan_g_choseD.setOnClickListener(this);
        btn_bank_stmt_choseD.setOnClickListener(this);
        input_edt_dobD.setOnClickListener(this);

        selectCompanyType();

        relative_direct_form.setVisibility(View.GONE);

        card_view_photo.setVisibility(View.GONE);
        card_view_adhar_f.setVisibility(View.GONE);
        card_view_adhar_b.setVisibility(View.GONE);
        card_view_pan.setVisibility(View.GONE);
        add_new_field.setVisibility(View.GONE);
        card_view_bank_stmt.setVisibility(View.GONE);
        inflate_view.setVisibility(View.GONE);
        linear____.setVisibility(View.GONE);
        card_view_moa.setVisibility(View.GONE);
        card_view_aoa.setVisibility(View.GONE);
        login_bt.setVisibility(View.GONE);

        myView = findViewById(R.id.relative_main);
        vv = (ViewGroup) myView.getParent();

    }

    private void selectNatureOfProperty() {
        n_of_propt_list = new ArrayList<>();

        n_of_propt_list.add(0, "Nature Of Property");
        n_of_propt_list.add(1, "Rented");
        n_of_propt_list.add(2, "Owned");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GSTRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, n_of_propt_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nature_of_proprt_sp.setAdapter(dataAdapter);
        nature_of_proprt_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String companytype = parent.getItemAtPosition(position).toString();
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

    private void selectCompanyType() {

        list = new ArrayList<>();

        list.add(0, "Select Company Types");
        list.add(1, "Private Limited");
        list.add(2, "Partnership");
        list.add(3, "Proprietorship");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GSTRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sel_comp_type.setAdapter(dataAdapter);
        sp_sel_comp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String companytype = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString().equals("Select Company Types")) {

                    relative_direct_form.setVisibility(View.GONE);
                    txt_dirct_name.setText("");
                    layout_list_new_gst.removeAllViews();

                    card_view_photo.setVisibility(View.GONE);
                    card_view_adhar_f.setVisibility(View.GONE);
                    card_view_adhar_b.setVisibility(View.GONE);
                    card_view_pan.setVisibility(View.GONE);
                    card_view_bank_stmt.setVisibility(View.GONE);
                    add_new_field.setVisibility(View.GONE);
                    login_bt.setVisibility(View.GONE);
                    input_layout_din_no.setVisibility(View.GONE);
                    linear____.setVisibility(View.GONE);


                } else if (parent.getItemAtPosition(position).toString().equals("Private Limited")) {
                    selectNatureOfProperty();

                    layout_list_new_gst.removeAllViews();

                    relative_direct_form.setVisibility(View.VISIBLE);
                    txt_dirct_name.setText("Enter Director details");
                    add_new_field.setVisibility(View.VISIBLE);
                    add_new_field.setText("Add Director");
                    inflate_view.setVisibility(View.GONE);
                    // linear____.removeView(inflate_view);

                    card_view_photo.setVisibility(View.VISIBLE);
                    card_view_adhar_f.setVisibility(View.VISIBLE);
                    card_view_adhar_b.setVisibility(View.VISIBLE);
                    card_view_bank_stmt.setVisibility(View.VISIBLE);
                    card_view_moa.setVisibility(View.GONE);
                    card_view_aoa.setVisibility(View.GONE);

                    card_view_pan.setVisibility(View.VISIBLE);
                    login_bt.setVisibility(View.VISIBLE);
                    input_layout_din_no.setVisibility(View.VISIBLE);
                    linear____.setVisibility(View.VISIBLE);


                } else if (parent.getItemAtPosition(position).toString().equals("Partnership")) {


                    layout_list_new_gst.removeAllViews();

                    selectNatureOfProperty();

                    relative_direct_form.setVisibility(View.VISIBLE);
//                    linear____.removeView(inflate_view);
                    input_layout_din_no.setVisibility(View.GONE);

                    txt_dirct_name.setText("Enter Partner details");
                    add_new_field.setVisibility(View.VISIBLE);
                    add_new_field.setText("Add Partner");
                    inflate_view.setVisibility(View.GONE);

                    card_view_photo.setVisibility(View.VISIBLE);
                    card_view_adhar_f.setVisibility(View.VISIBLE);
                    card_view_adhar_b.setVisibility(View.VISIBLE);
                    card_view_pan.setVisibility(View.VISIBLE);
                    card_view_bank_stmt.setVisibility(View.VISIBLE);
                    login_bt.setVisibility(View.VISIBLE);
                    linear____.setVisibility(View.VISIBLE);
                    card_view_moa.setVisibility(View.GONE);
                    card_view_aoa.setVisibility(View.GONE);


                } else if (parent.getItemAtPosition(position).toString().equals("Proprietorship")) {

                    selectNatureOfProperty();

                    layout_list_new_gst.removeAllViews();

                    relative_direct_form.setVisibility(View.VISIBLE);
                    txt_dirct_name.setText("Enter Proprietor details");
                    add_new_field.setVisibility(View.GONE);
                    add_new_field.setText("");
                    input_layout_din_no.setVisibility(View.GONE);


                    card_view_photo.setVisibility(View.VISIBLE);
                    card_view_adhar_f.setVisibility(View.VISIBLE);
                    card_view_adhar_b.setVisibility(View.VISIBLE);
                    card_view_pan.setVisibility(View.VISIBLE);
                    card_view_bank_stmt.setVisibility(View.VISIBLE);
                    linear____.setVisibility(View.VISIBLE);
                    card_view_moa.setVisibility(View.GONE);
                    card_view_aoa.setVisibility(View.GONE);
                    login_bt.setVisibility(View.VISIBLE);


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
            case R.id.btn_add_field:
                inflateLayout();
                break;

            case R.id.delete_button:
                relative_main.removeView((View) v.getParent());
                break;

            case R.id.login_bt:
                SumitFormAllData();
                break;

            case R.id.btn_certi_chose1:
                getCertiImage();

                break;

            case R.id.btn_moa_chose:
                getMoaImage();

                break;

            case R.id.btn_aoa_chose:
                getAoaImage();

                break;

            case R.id.btn_electrcity_chose:
                getElectricityImage();

                break;

            case R.id.btn_rent_agree_chose:
                getRentAgreemetImage();

                break;

            case R.id.btn_photo_choseD:
                getPhotoImage();

                break;

            case R.id.btn_adhar_f_g_choseD:
                getAdharFrontImage();

                break;

            case R.id.btn_adhar_b_g_choseD:
                getAdharBackImage();

                break;

            case R.id.btn_pan_g_choseD:
                getPanImage();

                break;

            case R.id.btn_bank_stmt_choseD:
                getBankStatementImage();

                break;
            case R.id.input_edt_dobD:
                showDateDialog();
                break;


        }
    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(GSTRegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        input_edt_dobD.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getMoaImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_MOA_RESULT);

    }

    private void getAoaImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_AOA_RESULT);

    }

    private void getElectricityImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_ELECTRICITY_RESULT);

    }

    private void getRentAgreemetImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_RENT_AGRREMENT_RESULT);

    }

    private void getPhotoImage() {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_PHOTO_RESULT);

    }

    private void getAdharFrontImage() {
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

    private void getCertiImage() {
        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_CERTIFICATE_RESULT);

    }

    private void SumitFormAllData() {

        name_of_person_Array = new ArrayList<>();
        email_Array = new ArrayList<>();
        mobile_Array = new ArrayList<>();
        f_h_name_Array = new ArrayList<>();
        dob_Array = new ArrayList<>();
        pan_no_Array = new ArrayList<>();
        adhar_no_Array = new ArrayList<>();
        address_Array = new ArrayList<>();
        din_Array = new ArrayList<>();

        photo_Array = new ArrayList<>();
        adhar_front_Array = new ArrayList<>();
        adhar_back_Array = new ArrayList<>();
        pan_file_Array = new ArrayList<>();
        statement_Array = new ArrayList<>();


        String firm_name = input_edt_firm.getText().toString();
        String nature_of_busi = input_edt_natureof_business.getText().toString();
        String state = input_edit_state.getText().toString();
        String district = input_edt_district.getText().toString();
        String bussi_addr = input_edt_buiseness_add.getText().toString();

        String name_of_person = input_edt_name.getText().toString();
        String email = input_edt_emailD.getText().toString();
        String mobile_no = input_edt_mobile_noD.getText().toString();
        String middle_name = input_edt_f_h_nameD.getText().toString();
        String dob = input_edt_dobD.getText().toString();
        String pan_no = input_edt_pan_no_gD.getText().toString();
        String adhar_no = input_edt_adhar_no_gD.getText().toString();
        String address = input_edt_addrD.getText().toString();
        String din_no = input_edt_din_noD.getText().toString();

        name_of_person_Array.add(name_of_person);
        Log.e("GSTRegistrationActivity", "name of person==" + name_of_person_Array);

        email_Array.add(email);
        Log.e("GSTRegistrationActivity", "email_==" + email_Array);

        mobile_Array.add(mobile_no);
        Log.e("GSTRegistrationActivity", "mobile no ==" + mobile_Array);

        f_h_name_Array.add(middle_name);
        Log.e("GSTRegistrationActivity", "middle name==" + f_h_name_Array);

        dob_Array.add(dob);
        Log.e("GSTRegistrationActivity", "dob==" + dob_Array);

        pan_no_Array.add(pan_no);
        Log.e("GSTRegistrationActivity", "pan no.==" + pan_no_Array);

        adhar_no_Array.add(adhar_no);
        Log.e("GSTRegistrationActivity", "adhar no==" + adhar_no_Array);

        address_Array.add(address);
        Log.e("GSTRegistrationActivity", "address==" + address_Array);

        din_Array.add(din_no);
        Log.e("GSTRegistrationActivity", "din no==" + din_Array);


        String photo = txt_photoD.getText().toString();
        String adhar_ff = txt_adhar_f_gD.getText().toString();
        String adhar_bb = txt_adhar_b_gD.getText().toString();
        String pan_photo = txt_pan_gD.getText().toString();
        String stamt_photo = txt_bank_stmtD.getText().toString();

        photo_Array.add(photo);
        adhar_front_Array.add(adhar_ff);
        adhar_back_Array.add(adhar_bb);
        pan_file_Array.add(pan_photo);
        statement_Array.add(stamt_photo);

        String nature_of_proprty = nature_of_proprt_sp.getSelectedItem().toString();


        for (int i = 0; i < layout_list_new_gst.getChildCount(); i++) {

            View cricketerView = layout_list_new_gst.getChildAt(i);


            name_of_person_Array.add(input_edt_name_a.getText().toString());
            Log.e("GSTRegistrationActivity", "name of person==" + name_of_person_Array);

            email_Array.add(input_edt_emailD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "email_==" + email_Array);

            mobile_Array.add(input_edt_mobile_noD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "mobile no ==" + mobile_Array);

            f_h_name_Array.add(input_edt_f_h_nameD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "middle name==" + f_h_name_Array);

            dob_Array.add(input_edt_dobD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "dob==" + dob_Array);

            pan_no_Array.add(input_edt_pan_no_gD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "pan no.==" + pan_no_Array);

            adhar_no_Array.add(input_edt_adhar_no_gD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "adhar no==" + adhar_no_Array);

            address_Array.add(input_edt_addrD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "address==" + address_Array);

            din_Array.add(input_edt_din_noD_a.getText().toString());
            Log.e("GSTRegistrationActivity", "din no==" + din_Array);

            TextView txt_photoDView = (TextView) cricketerView.findViewById(R.id.txt_photoDView);
            photo_Array.add(txt_photoDView.getText().toString());
            Log.e("GSTRegistrationActivity", "photo_Array==" + photo_Array);

            TextView txt_adhar_f_gD_aView = (TextView) cricketerView.findViewById(R.id.txt_adhar_f_gD_aView);
            adhar_front_Array.add(txt_adhar_f_gD_aView.getText().toString());
            Log.e("GSTRegistrationActivity", "adhar_front_Array==" + adhar_front_Array);

            TextView txt_adhar_b_gD_aView = (TextView) cricketerView.findViewById(R.id.txt_adhar_b_gD_aView);
            adhar_back_Array.add(txt_adhar_b_gD_aView.getText().toString());
            Log.e("GSTRegistrationActivity", "adhar_back_Array==" + adhar_back_Array);

            TextView txt_pan_gD_aView = (TextView) cricketerView.findViewById(R.id.txt_pan_gD_aView);
            pan_file_Array.add(txt_pan_gD_aView.getText().toString());
            Log.e("GSTRegistrationActivity", "pan_file_Array==" + pan_file_Array);

            TextView txt_bank_stmtD_aView = (TextView) cricketerView.findViewById(R.id.txt_bank_stmtD_aView);
            statement_Array.add(txt_bank_stmtD_aView.getText().toString());
            Log.e("GSTRegistrationActivity", "statement_Array==" + statement_Array);


        }

        name_of_person_Array.add(name_of_person);
        email_Array.add(email);
        mobile_Array.add(mobile_no);
        f_h_name_Array.add(middle_name);
        dob_Array.add(dob);
        pan_no_Array.add(pan_no);
        adhar_no_Array.add(adhar_no);
        address_Array.add(address);
        din_Array.add(din_no);
        photo_Array.add(photo);
        adhar_front_Array.add(adhar_ff);
        adhar_back_Array.add(adhar_bb);
        pan_file_Array.add(pan_photo);
        statement_Array.add(stamt_photo);


        if (firm_name.toString().isEmpty()) {
            input_edt_firm.setError("Firm Name is Empty");
            input_edt_firm.requestFocus();

        } else {

            if (nature_of_busi.toString().isEmpty()) {

                input_edt_natureof_business.setError("Nature of Bussiness is Empty");
                input_edt_natureof_business.requestFocus();

            } else {

                if (state.toString().isEmpty()) {
                    input_edit_state.setError("state is Empty");
                    input_edit_state.requestFocus();

                } else {

                    if (district.toString().isEmpty()) {
                        input_edt_district.setError("District is Empty");
                        input_edt_district.requestFocus();

                    } else {

                        if (bussi_addr.toString().isEmpty()) {

                            input_edt_buiseness_add.setError("Bussiness Address is Empty");
                            input_edt_buiseness_add.requestFocus();

                        } else {

                            if (name_of_person.toString().isEmpty()) {
                                input_edt_name.setError("Name of Person is Empty");
                                input_edt_name.requestFocus();

                            } else {

                                if (email.toString().isEmpty()) {
                                    input_edt_emailD.setError("Email is Empty");
                                    input_edt_emailD.requestFocus();

                                } else {

                                    if (mobile_no.toString().isEmpty()) {

                                        input_edt_mobile_noD.setError("Mobile No. is Empty");
                                        input_edt_mobile_noD.requestFocus();
                                    } else {

                                        if (mobile_no.length() == 0 || mobile_no.length() > 10 || mobile_no.length() < 10) {
                                            input_edt_mobile_noD.setError("Please Enter Valid Mobile No");
                                            input_edt_mobile_noD.requestFocus();
                                        } else {

                                            if (middle_name.toString().isEmpty()) {
                                                input_edt_f_h_nameD.setError("Middle name is Empty");
                                                input_edt_f_h_nameD.requestFocus();

                                            } else {

                                                if (dob.toString().isEmpty()) {

                                                    input_edt_dobD.setError("Date of Birth is Empty");
                                                    input_edt_dobD.requestFocus();
                                                    Toast.makeText(this, "Please Select your Date of Birth", Toast.LENGTH_SHORT).show();

                                                } else {

                                                    if (pan_no.toString().isEmpty()) {

                                                        input_edt_pan_no_gD.setError("Pan no. is Empty");
                                                        input_edt_pan_no_gD.requestFocus();

                                                    } else {
                                                        if (adhar_no.toString().isEmpty()) {
                                                            input_edt_adhar_no_gD.setError("Adhar No. is Empty");
                                                            input_edt_adhar_no_gD.requestFocus();

                                                        } else {

                                                            if (address.toString().isEmpty()) {
                                                                input_edt_addrD.setError("Bussiness Address is Empty");
                                                                input_edt_addrD.requestFocus();

                                                            } else {

                                                                if (nature_of_proprt_sp.getSelectedItem().toString().equals("Nature Of Property")) {
                                                                    Toast.makeText(this, "Please Select Nature Of Property", Toast.LENGTH_SHORT).show();
                                                                } else {

//                                                                    int length = layout_list_new_gst.getChildCount();
//                                                                    if (length == 0) {
//                                                                        serverSubmitInfo(state, nature_of_busi, nature_of_proprty, address, firm_name, district);
//                                                                    } else {


                                                                    if(checkIfValidateData())
                                                                    {
                                                                        serverSubmitInfo(state,nature_of_busi,nature_of_proprty,address,firm_name,district);
                                                                    }
                                                                }

                                                                //}


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

        for (int i = 0; i < layout_list_new_gst.getChildCount(); i++) {

            View cricketerView = layout_list_new_gst.getChildAt(i);

            input_edt_name_a = cricketerView.findViewById(R.id.input_edt_name_a);
            input_edt_emailD_a = cricketerView.findViewById(R.id.input_edt_emailD_a);
            input_edt_mobile_noD_a = cricketerView.findViewById(R.id.input_edt_mobile_noD_a);
            input_edt_f_h_nameD_a = cricketerView.findViewById(R.id.input_edt_f_h_nameD_a);
            input_edt_dobD_a = cricketerView.findViewById(R.id.input_edt_dobD_a);
            input_edt_pan_no_gD_a = cricketerView.findViewById(R.id.input_edt_pan_no_gD_a);
            input_edt_adhar_no_gD_a = cricketerView.findViewById(R.id.input_edt_adhar_no_gD_a);
            input_edt_addrD_a = cricketerView.findViewById(R.id.input_edt_addrD_a);
            input_edt_din_noD_a = cricketerView.findViewById(R.id.input_edt_din_noD_a);

            TextView txt_photoDView = (TextView) cricketerView.findViewById(R.id.txt_photoDView);

            TextView txt_adhar_f_gD_aView = (TextView) cricketerView.findViewById(R.id.txt_adhar_f_gD_aView);

            TextView txt_adhar_b_gD_aView = (TextView) cricketerView.findViewById(R.id.txt_adhar_b_gD_aView);

            TextView txt_pan_gD_aView = (TextView) cricketerView.findViewById(R.id.txt_pan_gD_aView);

            TextView txt_bank_stmtD_aView = (TextView) cricketerView.findViewById(R.id.txt_bank_stmtD_aView);


            if (!(input_edt_name_a.getText().toString().equals(""))&&
                    !(input_edt_emailD_a.getText().toString().equals(""))&&
                    !(input_edt_din_noD_a.getText().toString().equals(""))&&
                    !(input_edt_mobile_noD_a.getText().toString().equals(""))&&
                    !(input_edt_f_h_nameD_a.getText().toString().equals(""))&&
                    !(input_edt_dobD_a.getText().toString().equals(""))&&
                    !(input_edt_pan_no_gD_a.getText().toString().equals(""))&&
                    !(input_edt_adhar_no_gD_a.getText().toString().equals(""))&&
                    !(input_edt_addrD_a.getText().toString().equals("")))
            {
                name_of_person_Array.add(input_edt_name_a.getText().toString());
                email_Array.add(input_edt_emailD_a.getText().toString());
                din_Array.add(input_edt_din_noD_a.getText().toString());
                mobile_Array.add(input_edt_mobile_noD_a.getText().toString());
                name_of_person_Array.add(input_edt_f_h_nameD_a.getText().toString());
                f_h_name_Array.add(input_edt_dobD_a.getText().toString());
                pan_no_Array.add(input_edt_pan_no_gD_a.getText().toString());
                adhar_no_Array.add(input_edt_adhar_no_gD_a.getText().toString());
                address_Array.add(input_edt_addrD_a.getText().toString());
                photo_Array.add(txt_photoDView.getText().toString());
                adhar_front_Array.add(txt_adhar_f_gD_aView.getText().toString());
                adhar_back_Array.add(txt_adhar_b_gD_aView.getText().toString());
                pan_file_Array.add(txt_pan_gD_aView.getText().toString());
                statement_Array.add(txt_bank_stmtD_aView.getText().toString());



            } else {
                Toast.makeText(GSTRegistrationActivity.this, "Fill All data", Toast.LENGTH_SHORT).show();
                result = false;
                break;
            }



        }

      return   result;
    }


    void serverSubmitInfo(String state, String nature_of_busi, String nature_of_proprty, String address, String firm_name,String district)
    {
        Toast.makeText(GSTRegistrationActivity.this, "serverSubmitInfo", Toast.LENGTH_SHORT).show();
        progressBar = new ProgressDialog(GSTRegistrationActivity.this);
        progressBar.setCancelable(true);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);


        MultipartBody.Part[] multipartTypedPhoto = new MultipartBody.Part[photo_Array.size()];

        for (int index = 0; index < photo_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + photo_Array.get(index));
            File file2 = new File(photo_Array.get(index));
            //RequestBody surveyBodyPhoto = RequestBody.create(MediaType.parse("image/*"), file2);
            RequestBody surveyBodyPhoto = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

           // multipartTypedPhoto[index] = MultipartBody.Part.createFormData("userfile[]", file2.getPath(), surveyBodyPhoto);
            multipartTypedPhoto[index] = MultipartBody.Part.createFormData("userfile[]", "download.jpg", surveyBodyPhoto);

        }


        MultipartBody.Part[] multipartTypedadhar_f = new MultipartBody.Part[adhar_front_Array.size()];

        for (int index = 0; index < adhar_front_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + adhar_front_Array.get(index));
            File file2 = new File(adhar_front_Array.get(index));
//                                                                    RequestBody surveyBodyadhar_f = RequestBody.create(MediaType.parse("image/*"), file2);
            RequestBody surveyBodyadhar_f = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

            multipartTypedadhar_f[index] = MultipartBody.Part.createFormData("adhar_front[]", "download.jpg", surveyBodyadhar_f);

//            multipartTypedadhar_f[index] = MultipartBody.Part.createFormData("adhar_front[]", file2.getPath(), surveyBodyadhar_f);
        }

        MultipartBody.Part[] multipartTypedadhar_b = new MultipartBody.Part[adhar_back_Array.size()];

        for (int index = 0; index < adhar_back_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + adhar_back_Array.get(index));
            File file2 = new File(adhar_back_Array.get(index));
//                                                                    RequestBody surveyBodyadhar_b = RequestBody.create(MediaType.parse("image/*"), file2);
            RequestBody surveyBodyadhar_b = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

//            multipartTypedadhar_b[index] = MultipartBody.Part.createFormData("adhar_back[]", file2.getPath(), surveyBodyadhar_b);
            multipartTypedadhar_b[index] = MultipartBody.Part.createFormData("adhar_back[]", "download.jpg", surveyBodyadhar_b);

        }

        MultipartBody.Part[] multipartTypedPan = new MultipartBody.Part[pan_file_Array.size()];

        for (int index = 0; index < pan_file_Array.size(); index++) {
            Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + pan_file_Array.get(index));
            File file2 = new File(pan_file_Array.get(index));
//                                                                    RequestBody surveyBodyPan = RequestBody.create(MediaType.parse("image/*"), file2);
            RequestBody surveyBodyPan = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

//            multipartTypedPan[index] = MultipartBody.Part.createFormData("pan_file[]", file2.getPath(), surveyBodyPan);
            multipartTypedPan[index] = MultipartBody.Part.createFormData("pan_file[]", "download.jpg", surveyBodyPan);

        }

        MultipartBody.Part[] multipartType_bank_stmt = new MultipartBody.Part[statement_Array.size()];

        for (int index = 0; index < statement_Array.size(); index++) {
            Log.d("Upload request --", "requestUploadSurvey: survey image " + index + "  " + statement_Array.get(index));
            File file2 = new File(statement_Array.get(index));
//                                                                    RequestBody surveyBodyStmt = RequestBody.create(MediaType.parse("image/*"), file2);
            RequestBody surveyBodyStmt = RequestBody.create(MediaType.parse("image/*"), "download.jpg");

//            multipartType_bank_stmt[index] = MultipartBody.Part.createFormData("statement[]", file2.getPath(), surveyBodyStmt);

            multipartType_bank_stmt[index] = MultipartBody.Part.createFormData("statement[]", "download.jpg", surveyBodyStmt);

        }

        RequestBody reuestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "download.jpg");

        MultipartBody.Part requestauth_cert_file = null;
        MultipartBody.Part requestauth_moa_file = null;
        MultipartBody.Part requestauth_aoa_file = null;
        MultipartBody.Part requestauth_electricity_file = null;
        MultipartBody.Part requestauth_rent_agree_file = null;

        requestauth_cert_file = MultipartBody.Part.createFormData("coi", "download.jpg", reuestFile);
//                                                                requestauth_moa_file = MultipartBody.Part.createFormData("auth_sign_file", "download.jpg", reuestFile);
//                                                                requestauth_aoa_file = MultipartBody.Part.createFormData("auth_sign_file", "download.jpg", reuestFile);
        requestauth_electricity_file = MultipartBody.Part.createFormData("electricity_bill", "download.jpg", reuestFile);
        requestauth_rent_agree_file = MultipartBody.Part.createFormData("rent_agreement", "download.jpg", reuestFile);

        String com_type = sp_sel_comp_type.getSelectedItem().toString();
        nature_of_proprty = nature_of_proprt_sp.getSelectedItem().toString();

        RequestBody RBcreated_by = RequestBody.create(MediaType.parse("text/plain"), member_id);
        RequestBody RBcom_type = RequestBody.create(MediaType.parse("text/plain"), com_type);

        RequestBody RBstate = RequestBody.create(MediaType.parse("text/plain"), state);
        RequestBody RBdistrict = RequestBody.create(MediaType.parse("text/plain"), district);
        RequestBody RBbusiness_adress = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody RBnob = RequestBody.create(MediaType.parse("text/plain"), nature_of_busi);
        RequestBody RBnop = RequestBody.create(MediaType.parse("text/plain"), nature_of_proprty);
        RequestBody RBfirm_name = RequestBody.create(MediaType.parse("text/plain"), firm_name);

        Log.d("length of array", "--->" + "" + name_of_person_Array.size());


        Call<JsonObject> callUploadImg = retrofitAPI.postImageNewData(RBstate, RBdistrict, RBbusiness_adress, RBnob,
                RBnop, RBfirm_name, RBcom_type, RBcreated_by, multipartTypedPhoto, multipartTypedadhar_f, multipartTypedadhar_b,
                multipartTypedPan, multipartType_bank_stmt, requestauth_cert_file, requestauth_electricity_file, requestauth_rent_agree_file,
                name_of_person_Array, f_h_name_Array, dob_Array, email_Array, mobile_Array,
                pan_no_Array, adhar_no_Array, address_Array, din_Array);

        callUploadImg.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBar.dismiss();

                Log.d("fb_regist_response", "--->" + "" + response.body());
                Log.d("fb_regist_response1", "--->" + "" + response.body());

                JsonObject object = response.body();
                Log.d("fb_regist_response", object.toString());

                String status = String.valueOf(object.get("status"));
                Log.d("fb_regist_response", status);

                String message = String.valueOf(object.get("Message"));
                Log.d(" message ===", message);

                if(status.equals("true"))
                {
                    Toast.makeText(GSTRegistrationActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(GSTRegistrationActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("onFail_fb_regist_res", t.getMessage());
            }
        });


    }
    private void inflateLayout() {

        final View inflate_view = getLayoutInflater().inflate(R.layout.add_new_fields_layout, null, false);


        input_layout_din_no_add = inflate_view.findViewById(R.id.input_layout_din_no_add);

        input_edt_name_a = inflate_view.findViewById(R.id.input_edt_name_a);
        input_edt_emailD_a = inflate_view.findViewById(R.id.input_edt_emailD_a);
        input_edt_mobile_noD_a = inflate_view.findViewById(R.id.input_edt_mobile_noD_a);
        input_edt_f_h_nameD_a = inflate_view.findViewById(R.id.input_edt_f_h_nameD_a);
        input_edt_dobD_a = inflate_view.findViewById(R.id.input_edt_dobD_a);
        input_edt_pan_no_gD_a = inflate_view.findViewById(R.id.input_edt_pan_no_gD_a);
        input_edt_adhar_no_gD_a = inflate_view.findViewById(R.id.input_edt_adhar_no_gD_a);
        input_edt_addrD_a = inflate_view.findViewById(R.id.input_edt_addrD_a);
        input_edt_din_noD_a = inflate_view.findViewById(R.id.input_edt_din_noD_a);



        if(sp_sel_comp_type.getSelectedItem().toString().equals("Private Limited"))
        {
            input_layout_din_no_add.setVisibility(View.VISIBLE);

        }else if (sp_sel_comp_type.getSelectedItem().toString().equals("Partnership")) {
            input_layout_din_no_add.setVisibility(View.GONE);


        }
        else if (sp_sel_comp_type.getSelectedItem().toString().equals("Proprietorship")) {
            input_layout_din_no_add.setVisibility(View.GONE);

        }

        delete_button = inflate_view.findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeView(inflate_view);


            }
        });

        input_edt_dobD_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final   int index = layout_list_new_gst.indexOfChild(inflate_view);

                showAddFieldDateDialog(inflate_view);
            }
        });
       Button btn_bank_stmt_choseD_aView = inflate_view.findViewById(R.id.btn_bank_stmt_choseD_aView);
         Button btn_pan_g_choseD_aView = inflate_view.findViewById(R.id.btn_pan_g_choseD_aView);
       Button btn_adhar_b_g_choseD_aView = inflate_view.findViewById(R.id.btn_adhar_b_g_choseD_aView);
       Button btn_adhar_f_g_choseD_aView = inflate_view.findViewById(R.id.btn_adhar_f_g_choseD_aView);
      Button  btn_photo_choseDView = inflate_view.findViewById(R.id.btn_photo_choseDView);

        btn_photo_choseDView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexPhoto = layout_list_new_gst.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("photo_index", String.valueOf(indexPhoto));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexPhoto);


                setPhotoImageOn(indexPhoto);

            }
        });

        btn_adhar_f_g_choseD_aView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexValueTown = layout_list_new_gst.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("key_index", String.valueOf(indexValueTown));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexValueTown);


                setAdharFImageOn(indexValueTown);

            }
        });


        btn_adhar_b_g_choseD_aView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexValueTown = layout_list_new_gst.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("key_index", String.valueOf(indexValueTown));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexValueTown);


                setAdharBImageOn(indexValueTown);

            }
        });


        btn_pan_g_choseD_aView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexValueTown = layout_list_new_gst.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("key_index", String.valueOf(indexValueTown));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexValueTown);


                setPanImageOn(indexValueTown);

            }
        });


        btn_bank_stmt_choseD_aView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexValueTown = layout_list_new_gst.indexOfChild(inflate_view);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("key_index", String.valueOf(indexValueTown));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexValueTown);


                setBankStatemtImageOn(indexValueTown);

            }
        });



        layout_list_new_gst.addView(inflate_view, layout_list_new_gst.getChildCount() - 1);


    }

    private void showAddFieldDateDialog(View index_value) {



        for (int i = 0; i < layout_list_new_gst.getChildCount(); i++)
        {
           // View cricketerView = layout_list_new_gst.getChildAt(index_value);

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(GSTRegistrationActivity.this,

                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            EditText editText = (EditText) index_value.findViewById(R.id.input_edt_dobD_a);

                            editText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }



    }


    private void setAdharFImageOn(int indexValueTown) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_ADHAR_F_VIEW_RESULT);

    }

    private void setAdharBImageOn(int indexValueTown) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_ADHAR_B_VIEW_RESULT);

    }

    private void setPanImageOn(int indexValueTown) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_PAN_VIEW_RESULT);

    }

    private void setBankStatemtImageOn(int indexValueTown) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_BANK_STATEMT_VIEW_RESULT);

    }

    private void setPhotoImageOn(int indexValueTown) {


        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());

        startActivityForResult(intent, IMG_PHOTO_VIEW_RESULT);

    }


    private void removeView(View inflate_view) {
        layout_list_new_gst.removeView(inflate_view);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {


            if (requestCode == IMG_CERTIFICATE_RESULT && resultCode == RESULT_OK
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


                txt_certi.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            }


            if (requestCode == IMG_MOA_RESULT && resultCode == RESULT_OK
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


                txt_moaa.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));


            }

            if (requestCode == IMG_AOA_RESULT && resultCode == RESULT_OK
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


                txt_aoa_b_g.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));


            }

            if (requestCode == IMG_ELECTRICITY_RESULT && resultCode == RESULT_OK
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


                txt_electricitybill_g.setText(ImageDecode);
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


                txt_rent_agreement_g.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));


            }

            if (requestCode == IMG_PHOTO_RESULT && resultCode == RESULT_OK
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


                txt_photoD.setText(ImageDecode);
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


                txt_adhar_f_gD.setText(ImageDecode);
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


                txt_adhar_b_gD.setText(ImageDecode);
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


                txt_pan_gD.setText(ImageDecode);
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


                txt_bank_stmtD.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));


            }

            if (requestCode == IMG_PHOTO_VIEW_RESULT && resultCode == RESULT_OK
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



                for (int i = 0; i < layout_list_new_gst.getChildCount(); i++)
                {
                    String key_nn = sharedpreferences.getString("photo_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_new_gst.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_photoDView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }

            if (requestCode == IMG_BANK_STATEMT_VIEW_RESULT && resultCode == RESULT_OK
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



                for (int i = 0; i < layout_list_new_gst.getChildCount(); i++)
                {
                    String key_nn =         sharedpreferences.getString("key_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_new_gst.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_bank_stmtD_aView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }


            if (requestCode == IMG_ADHAR_F_VIEW_RESULT && resultCode == RESULT_OK
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



                for (int i = 0; i < layout_list_new_gst.getChildCount(); i++)
                {
                    String key_nn =         sharedpreferences.getString("key_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_new_gst.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_adhar_f_gD_aView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }


            if (requestCode == IMG_ADHAR_B_VIEW_RESULT && resultCode == RESULT_OK
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



                for (int i = 0; i < layout_list_new_gst.getChildCount(); i++)
                {
                    String key_nn =         sharedpreferences.getString("key_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_new_gst.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_adhar_b_gD_aView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }

            if (requestCode == IMG_PAN_VIEW_RESULT && resultCode == RESULT_OK
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



                for (int i = 0; i < layout_list_new_gst.getChildCount(); i++)
                {
                    String key_nn =         sharedpreferences.getString("key_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layout_list_new_gst.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_pan_gD_aView);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }


            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        }
}