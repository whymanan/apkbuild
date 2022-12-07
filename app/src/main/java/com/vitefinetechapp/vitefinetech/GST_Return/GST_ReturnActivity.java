package com.vitefinetechapp.vitefinetech.GST_Return;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.vitefinetechapp.vitefinetech.GST_Registration.GSTModel;
import com.vitefinetechapp.vitefinetech.KYCDetails.ApiInterface;
import com.vitefinetechapp.vitefinetech.R;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GST_ReturnActivity extends AppCompatActivity implements View.OnClickListener{

    private static int IMG_ch_file_RESULT = 1;
    private static int IMG_fisrt_ch_file_RESULT = 2;

    ImageButton backbutton;
    ArrayList<String> list;
    Spinner sp_gst_retrn_type;
    RelativeLayout relative_gst_return_form;
    Button btn_add_sales_field, submitSales;
    LayoutInflater inflater;
    View inflate_view;
    LinearLayout linear_gst_ss;
    EditText txt_gst_retrn;
    ViewGroup vv;
    LinearLayout layoutList;
    ArrayList<GSTModel> cricketersList = new ArrayList<>();
    String water_oprator_string[];
    Intent intent;
    TextView txt_sales1;
    Button btn_pf_chose,btn_sales_chose;
    private static final String ROOT_URL = "https://vitefintech.com/viteapi/legal_api/";


    String member_id;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_s_t__return);

        backbutton = (ImageButton) findViewById(R.id.backbuttonG);
        sp_gst_retrn_type = findViewById(R.id.gst_return_type_sp);

        relative_gst_return_form = findViewById(R.id.relative_gst_return_form);
        btn_add_sales_field = findViewById(R.id.btn_add_sales_field);
        submitSales = findViewById(R.id.submitSales);
        linear_gst_ss = findViewById(R.id.linear_gst_ss);
        layoutList = findViewById(R.id.layout_list);
        btn_sales_chose = findViewById(R.id.btn_sales_chose);
        txt_sales1 = findViewById(R.id.txt_sales1);

        submitSales.setVisibility(View.GONE);
        btn_add_sales_field.setVisibility(View.GONE);
        relative_gst_return_form.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        member_id = sharedpreferences.getString("member_id",null);
        Log.e("KYCManagementActivity","member_id"+member_id);

        submitSales.setOnClickListener(this);
        btn_add_sales_field.setOnClickListener(this);
        btn_sales_chose.setOnClickListener(this);

        inflater = GST_ReturnActivity.this.getLayoutInflater();

        inflate_view = new View(GST_ReturnActivity.this);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GstReturnTypeSp();

    }

    private void GstReturnTypeSp() {

        list = new ArrayList<>();

        list.add(0, "Select GST Return Types");
        list.add(1, "Purchase Return");
        list.add(2, "Sales Return");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GST_ReturnActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gst_retrn_type.setAdapter(dataAdapter);

        sp_gst_retrn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String companytype = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString().equals("Select GST Return Types")) {


                    relative_gst_return_form.setVisibility(View.GONE);
                    submitSales.setVisibility(View.GONE);
                    btn_add_sales_field.setVisibility(View.GONE);


                } else if (parent.getItemAtPosition(position).toString().equals("Purchase Return")) {

                    relative_gst_return_form.setVisibility(View.VISIBLE);
                    submitSales.setVisibility(View.VISIBLE);
                    btn_add_sales_field.setVisibility(View.VISIBLE);


                } else if (parent.getItemAtPosition(position).toString().equals("Sales Return")) {

                    relative_gst_return_form.setVisibility(View.VISIBLE);
                    submitSales.setVisibility(View.VISIBLE);
                    btn_add_sales_field.setVisibility(View.VISIBLE);


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

            case R.id.btn_add_sales_field:
                addView();
                break;

            case R.id.submitSales:
                submit();
                break;
            case R.id.btn_sales_chose:
                getImage();
        }
    }

    private void getImage() {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(intent, IMG_fisrt_ch_file_RESULT);

    }

    private void submit() {

        try {
            String n[] = new String[layoutList.getChildCount()];

            ArrayList<String> nameArray = new ArrayList<>();

            for (int i = 0; i < layoutList.getChildCount(); i++) {

                View cricketerView = layoutList.getChildAt(i);

                TextView editTextName = (TextView) cricketerView.findViewById(R.id.txt_pf);


                GSTModel cricketer = new GSTModel();

                cricketer.setFirm_name(editTextName.getText().toString());
                nameArray.add(editTextName.getText().toString());

                String[] nnnn = {editTextName.getText().toString()};

                cricketersList.add(cricketer);
                Log.e("GSTRegistrationActivity", "cricketersList==" + nameArray);

                Log.e("GSTRegistrationActivity", "setFirm_name==" + editTextName.getText().toString());
                Log.e("GSTRegistrationActivity", "nnnn==" + nnnn);
                Log.e("GSTRegistrationActivity", "nnnn ??" + n[i]);

                Log.e("GSTRegistrationActivity", "nnnn ??//" + n);


            }

            String return_type = sp_gst_retrn_type.getSelectedItem().toString();
            nameArray.add(txt_sales1.getText().toString());


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface retrofitAPI = retrofit.create(ApiInterface.class);


            MultipartBody.Part[] multipartTypedOutput = new MultipartBody.Part[nameArray.size()];

            for (int index = 0; index < nameArray.size(); index++) {
                Log.d("Upload request", "requestUploadSurvey: survey image " + index + "  " + nameArray.get(index));
                File file2 = new File(nameArray.get(index));
                RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), file2);
                multipartTypedOutput[index] = MultipartBody.Part.createFormData("userfile[]", file2.getPath(), surveyBody);
            }

            RequestBody memberId1 = RequestBody.create(MediaType.parse("text/plain"), member_id);
            RequestBody actionType1 = RequestBody.create(MediaType.parse("text/plain"), return_type);

            retrofitAPI.postImage(memberId1, actionType1, multipartTypedOutput).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("fb_regist_response", "--->" + "" + response.body());

                    JsonObject object = response.body();
                    Log.d("fb_regist_response", object.toString());

                    String status = String.valueOf(object.get("status"));
                    Log.d("fb_regist_response", status);

                    String message = String.valueOf(object.get(" Message"));
                    Log.d(" message ===", message);

                    if(status.equals("true"))
                    {
                        Toast.makeText(GST_ReturnActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(GST_ReturnActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("onFail_fb_regist_res", t.getMessage());
                }
            });

        }catch (Exception e)
        {

        }
    }

    private void addView() {

        View cricketerView = getLayoutInflater().inflate(R.layout.add_purchase_return_layout, null, false);

        TextView editText = (TextView) cricketerView.findViewById(R.id.txt_pf);
        Button btn_pf_chose = cricketerView.findViewById(R.id.btn_pf_chose);
        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove);

        vv  = (ViewGroup) layoutList.getParent();//c is a layout in which position i want to add view
        Log.e("GSTRegistrationActivity", "posValue==" + cricketerView);

        int posValue = layoutList.indexOfChild(cricketerView);
        Log.e("GSTRegistrationActivity", "posValue==" + cricketerView);


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteView(cricketerView);

            }
        });

        btn_pf_chose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final   int indexValueTown = layoutList.indexOfChild(cricketerView);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.clear();
                editor.putString("key_index", String.valueOf(indexValueTown));
                editor.commit();

                Log.e("GSTRegistrationActivity", "indexValueTown***" + indexValueTown);


                setImageOn(indexValueTown);

            }
        });

//        imageClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeView(cricketerView);
//            }
//        });

        layoutList.addView(cricketerView);
        int position = layoutList.indexOfChild(cricketerView);
        layoutList.setTag(position);



    }

    private void deleteView(View cricketerView) {

        layoutList.removeView(cricketerView);

    }

    private void setImageOn(Integer index) {

        intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // intent.putExtra("view",cricketerView.getId());
        //        intent.putExtras("name", cricketerView.toString());
        Bundle bundleOptions = new Bundle();
        bundleOptions.putString("key", index.toString());
        Log.e("GSTRegistrationActivity", "key ???" + index);

        intent.putExtras(bundleOptions);

        startActivityForResult(intent, IMG_ch_file_RESULT,bundleOptions);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        try {



            if (requestCode == IMG_fisrt_ch_file_RESULT && resultCode == RESULT_OK
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


                txt_sales1.setText(ImageDecode);
                // img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));



            }
            if (requestCode == IMG_ch_file_RESULT && resultCode == RESULT_OK
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



                for (int i = 0; i < layoutList.getChildCount(); i++)
                {
                    String key_nn =         sharedpreferences.getString("key_index",null);

                    Log.e("KYCManagement", "key_nn  ==" + key_nn);

                    View cricketerView = layoutList.getChildAt(Integer.parseInt(key_nn));

                    int indexAndId = cricketerView.getId();

                    TextView editText = (TextView) cricketerView.findViewById(R.id.txt_pf);
                    // ((ViewGroup)editText.getParent()).removeView(editText);

                    editText.setText(ImageDecode);

                    Log.e("KYCManagement", "ImageDecode for ==" + ImageDecode);

                    //  img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }
}