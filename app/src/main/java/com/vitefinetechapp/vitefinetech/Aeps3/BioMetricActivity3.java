package com.vitefinetechapp.vitefinetech.Aeps3;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vitefinetechapp.vitefinetech.MantraSDk.AepsCaptureResponseModel;
import com.vitefinetechapp.vitefinetech.MantraSDk.CaptureResponse;
import com.vitefinetechapp.vitefinetech.MantraSDk.DeviceInfo;
import com.vitefinetechapp.vitefinetech.MantraSDk.FormXML;
import com.vitefinetechapp.vitefinetech.MantraSDk.RDServiceInfo;
import com.vitefinetechapp.vitefinetech.MantraSDk.SplitXML;
import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class BioMetricActivity3 extends AppCompatActivity {

    Spinner select_biometric_device;
    ArrayList<String> list;
    ImageButton backbutton;
    String adharcard_number;

    public static final String CUSTOM_ACTION_INFO_FINGERPRINT =
            "in.gov.uidai.rdservice.fp.INFO";
    public static final String CUSTOM_ACTION_CAPTURE_FINGERPRINT =
            "in.gov.uidai.rdservice.fp.CAPTURE";
    public static final int INFO_REQUEST = 0;
    public static final int CAPTURE_REQUEST = 1;
    public static final String DeviceINFO_KEY = "DEVICE_INFO";
    public static final String RD_SERVICE_INFO = "RD_SERVICE_INFO";
    public static final String PID_DATA = "PID_DATA";
    public static final String PID_OPTIONS = "PID_OPTIONS";

    public static final boolean isPreProduction = true;

    String strDeviceInfo;

    protected DeviceInfo deviceInfo = new DeviceInfo();
    protected RDServiceInfo rdServiceInfo = new RDServiceInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_metric3);

        select_biometric_device    =     (Spinner)findViewById(R.id.biometric_spinner);
        backbutton                 =     (ImageButton) findViewById(R.id.backbutton);

        list = new ArrayList<String>();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adharcard_number = getIntent().getStringExtra("adharcard_number");

        getbiometricdevice();
    }

    public void getbiometricdevice() {
        list.add(0, "Select Device");
        list.add(1, "Mantra MFS100");
        list.add(2, "Morpho");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BioMetricActivity3.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_biometric_device.setAdapter(dataAdapter);
        select_biometric_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleted_device = parent.getItemAtPosition(position).toString();
                openFPSensor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void openFPSensor() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(this.CUSTOM_ACTION_INFO_FINGERPRINT);
            this.startActivityForResult(sendIntent, INFO_REQUEST);
        } catch (ActivityNotFoundException var4) {
            var4.printStackTrace();
            if (getContext() != null)
                Toast.makeText(BioMetricActivity3.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private int getScannerType(String info) {
        List<String> sensorNameList =
                Arrays.asList( getApplication().getResources().getStringArray(R.array.fingerprint));
        int scannerType = -1;
        for (int i = 0; i < sensorNameList.size(); i++) {
            if (info.toUpperCase().contains(sensorNameList.get(i).toUpperCase())) {
                return i;
            }
        }
//        showToast("getScannerType " + scannerType);
        Toast.makeText(getApplicationContext(),"getScannerType " + scannerType,Toast.LENGTH_SHORT).show();
        return scannerType;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String piddataxml;
        String text;
        String captureRequestXML;
        String s;
        try {
            if (data != null) {
                if (requestCode == 0) {
                    piddataxml = data.getStringExtra("DNC");
                    text = data.getStringExtra("DNR");
                    String strDeviceInfo = data.getStringExtra(DeviceINFO_KEY);
                    String strRDServiceInfo = data.getStringExtra(this.RD_SERVICE_INFO);
                    // String appName = data.getComponent().flattenToShortString();
                    // Log.e("Shetty App Name", appName);
                    Log.e("Shetty D Info", strDeviceInfo);
                    if (piddataxml != null) {
                        Toast.makeText(getApplicationContext(),"Device is not registered",Toast.LENGTH_SHORT).show();
                    } else if (text != null) {
                        Toast.makeText(getApplicationContext(),"Device is not registered",Toast.LENGTH_SHORT).show();
                    } else if (strRDServiceInfo != null && !strRDServiceInfo.isEmpty()) {this.rdServiceInfo = (new
                            SplitXML()).SplitRDServiceInfo(strRDServiceInfo);
                        if (this.rdServiceInfo != null) {
                            if (this.rdServiceInfo.status.equalsIgnoreCase("Ready")) {
                                if (!strDeviceInfo.toString().equals("")) {
                                    this.deviceInfo = (new
                                            SplitXML()).SplitDeviceInfo(strDeviceInfo);
                                    String enviroment = "P";
                                    if (isPreProduction) {
                                        enviroment = "PP";
                                    }
                                    switch (getScannerType(strDeviceInfo)) {
                                        case 0:
                                            //device is next biometric
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver =\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" /><Demo></Demo><CustOpts></CustOpts></PidOptions>";
                                            break;
                                        case 1:
                                            //device is Precision biometric
                                            captureRequestXML = "<PidOptions ver=\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\" wadh=\"\" posh=\"UNKNOWN\" /><Demo></Demo><CustOpts><Param name=\"\" value=\"\" /></CustOpts></PidOptions>";
                                            break;
                                        case 2:
                                            //device is Tatvik biometric
                                            captureRequestXML = "<PidOptions ver=\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\" wadh=\"\" posh=\"UNKNOWN\" /><Demo></Demo><CustOpts><Param name=\"\" value=\"\" /></CustOpts></PidOptions>";
                                            break;
                                        case 3:
                                            //device is Mantra biometric
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver =\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>";
                                            break;
                                        case 4:
                                            //device is Morpho biometric
                                            FormXML.posh = "UNKNOWN";
                                            captureRequestXML = "<PidOptions ver=\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"2000\" wadh=\"\" posh=\"UNKNOWN\" /><Demo></Demo><CustOpts><Param name=\"\" value=\"\" /></CustOpts></PidOptions>";
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone= \"yes\"?><PidOptions ver =\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>";
                                            break;
                                        case 5:
                                            //device is Startek biometric
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions><Opts fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"20000\" otp=\"\" env=\"" + enviroment + "\" wadh=\"\" posh=\"UNKNOWN\"/><Demo/><CustOpts><Param name=\"\" value=\"\"/></CustOpts></PidOptions>";
                                            break;case 6:
                                            //device is Secugen biometric
                                            captureRequestXML = "<PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"0\" format=\"0\" timeout=\"10000\" pidVer=\"2.0\" posh=\"UNKNOWN\" env=\"" + enviroment + "\"/></PidOptions>";
                                            break;
                                        default:
                                            captureRequestXML = null;
                                            break;
                                    }
                                    Log.d(TAG, "onActivityResult: " + captureRequestXML);
                                    if (captureRequestXML != null &&
                                            !captureRequestXML.isEmpty()) {
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(this.CUSTOM_ACTION_CAPTURE_FINGERPRINT);
                                        sendIntent.putExtra(this.PID_OPTIONS,
                                                captureRequestXML);
                                        this.startActivityForResult(sendIntent,
                                                this.CAPTURE_REQUEST);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(),"Device information not found",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"Device is not ready",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"RD information is null",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String rdsId;
                    String rdsVer;
                    String dc;
                    String mi;
                    String mc;
                    String dpId;
                    if (requestCode == 1){
                        piddataxml = data.getStringExtra(this.PID_DATA);
                        if (!piddataxml.toString().equals("")) {

                            SplitXML splitXML = new SplitXML();
                            CaptureResponse response =
                                    splitXML.SplitCaptureResponse(piddataxml);
                            AepsCaptureResponseModel aepsCaptureResponseModel =
                                    splitXML.SplitAepsCaptureResponseModel(piddataxml);

                            Log.d(TAG, "onActivityResult: " +
                                    aepsCaptureResponseModel.getJSON(adharcard_number));

                            if (aepsCaptureResponseModel.getJSON(adharcard_number) != null) {
                                if
                                (aepsCaptureResponseModel.getJSON(adharcard_number.toString()).getString("errCode").equalsIgnoreCase("0") ||
                                        aepsCaptureResponseModel.getJSON(adharcard_number.toString()).getString("errCode").equalsIgnoreCase("00")) {
                                    Log.d(TAG, "onActivityResult: send");

                                    //TODO : You can code your logic here.
                                    //TODO : You will get fingerprint data as output in piddataxml.

                                } else {
                                    Log.d(TAG, "onActivityResult: not send");
                                    //                                    showDialog("Device Capture Failed",
//                                            aepsCaptureResponseModel.getJSON(adharcard_number.toString()).
//                                                    getString("errInfo"), true);
                                    Toast.makeText(getApplicationContext(),"Device Capture Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"captured_xml_null",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    public boolean isValidString(strDeviceInfo) {
//
//        strDeviceInfo = "1";
//        return false;
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}