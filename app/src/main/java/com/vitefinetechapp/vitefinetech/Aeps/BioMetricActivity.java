package com.vitefinetechapp.vitefinetech.Aeps;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Aeps2.BioMetricActivity2;
import com.vitefinetechapp.vitefinetech.Aeps2.SubmitAeps2Transaction;
import com.vitefinetechapp.vitefinetech.MantraSDk.AepsCaptureResponseModel;
import com.vitefinetechapp.vitefinetech.MantraSDk.CaptureResponse;
import com.vitefinetechapp.vitefinetech.MantraSDk.DeviceInfo;
import com.vitefinetechapp.vitefinetech.MantraSDk.FormXML;
import com.vitefinetechapp.vitefinetech.MantraSDk.RDServiceInfo;
import com.vitefinetechapp.vitefinetech.MantraSDk.SplitXML;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

public class BioMetricActivity extends AppCompatActivity {

    Spinner select_biometric_device;
    ArrayList<String> list;
    ImageButton backbutton;
    String adharcard_number, mobileno, amount, transaction_type, name, bank_code,access_token;

    public static final String CUSTOM_ACTION_INFO_FINGERPRINT = "in.gov.uidai.rdservice.fp.INFO";
    public static final String CUSTOM_ACTION_CAPTURE_FINGERPRINT = "in.gov.uidai.rdservice.fp.CAPTURE";
    public static final int INFO_REQUEST = 0;
    public static final int CAPTURE_REQUEST = 1;
    public static final String DeviceINFO_KEY = "DEVICE_INFO";
    public static final String RD_SERVICE_INFO = "RD_SERVICE_INFO";
    public static final String PID_DATA = "PID_DATA";
    public static final String PID_OPTIONS = "PID_OPTIONS";

    public static final boolean isPreProduction = true;

    String piddataxml;
    protected DeviceInfo deviceInfo = new DeviceInfo();
    protected RDServiceInfo rdServiceInfo = new RDServiceInfo();

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String latitude,longitude,_piddata;
    Button capturebtn;

    private ProgressDialog progressDialog;
    String deviceready, capture;
    String errcode,errinfo,icount,fcount,nmpoint,ftype,qSscore,pid,skey,ci,hmac,type,rdsId,rdsVer,dc,mi,mc,dpId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_metric2);

        select_biometric_device    =     (Spinner) findViewById(R.id.biometric_spinner);
        backbutton                 =     (ImageButton) findViewById(R.id.backbutton);
        capturebtn                 =     (Button) findViewById(R.id.capture);

        list = new ArrayList<String>();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        latitude          = sharedPreferences.getString("latitude", null);
        longitude         = sharedPreferences.getString("longitude", null);


        transaction_type    = getIntent().getStringExtra("transaction_type");
        adharcard_number    = getIntent().getStringExtra("adhar_no");
        mobileno            = getIntent().getStringExtra("mobile_no");
        amount              = getIntent().getStringExtra("amount");
        bank_code           = getIntent().getStringExtra("bank_code");
        access_token        = getIntent().getStringExtra("access_token");


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        capturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_biometric_device.getSelectedItem().toString().equals("Select Device")) {
                    Toast.makeText(BioMetricActivity.this, "Please select biometric device", Toast.LENGTH_SHORT).show();
                } else {
                    openFPSensor();
                    capture = "CAPTURE";
                }
            }
        });

        getbiometricdevice();
    }

    public void getbiometricdevice() {
        list.add(0, "Select Device");
        list.add(1, "Mantra MFS100");
        list.add(2, "Morpho");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BioMetricActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_biometric_device.setAdapter(dataAdapter);
        select_biometric_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleted_device = parent.getItemAtPosition(position).toString();
                if (seleted_device.equals("Mantra MFS100")) {
                    openFPSensor();
                    deviceready = "DEVICEREADY";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void openFPSensor() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(CUSTOM_ACTION_INFO_FINGERPRINT);
            this.startActivityForResult(sendIntent, 0);

        } catch (ActivityNotFoundException var4) {
            var4.printStackTrace();
            if (getContext() != null)
                Toast.makeText(BioMetricActivity.this, "Please install RD service", Toast.LENGTH_SHORT).show();
        }
    }

    private int getScannerType(String info) {
        List<String> sensorNameList =
                Arrays.asList(getApplication().getResources().getStringArray(R.array.fingerprint));
        int scannerType = -1;

        for (int i = 0; i < sensorNameList.size(); i++) {
            if (info.toUpperCase().contains(sensorNameList.get(i).toUpperCase())) {

                return i;
            }
        }
        Toast.makeText(getApplicationContext(), "getScannerType " + scannerType, Toast.LENGTH_SHORT).show();
        return scannerType;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String captureRequestXML;
        try {
            if (data != null) {
                if (requestCode == 0) {
                    String strDeviceInfo = data.getStringExtra(DeviceINFO_KEY);
                    String strRDServiceInfo = data.getStringExtra(RD_SERVICE_INFO);

                    Log.e("Shetty D Info", strRDServiceInfo);
                    if (strRDServiceInfo != null && !strRDServiceInfo.isEmpty()) {
                        if (strDeviceInfo == null || strDeviceInfo.equals("") || strDeviceInfo.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Device information not found", Toast.LENGTH_SHORT).show();
                        } else if (deviceready.equals("DEVICEREADY")) {
                            this.rdServiceInfo = (new
                                    SplitXML()).SplitRDServiceInfo(strRDServiceInfo);
                            if (this.rdServiceInfo != null) {
                                if (this.rdServiceInfo.status.equalsIgnoreCase("Ready")) {
                                    Toast.makeText(getApplicationContext(), "Device is ready", Toast.LENGTH_SHORT).show();

                                    if (capture.equals("CAPTURE")) {
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
                                                captureRequestXML = "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" wadh=\"\" posh=\"UNKNOWN\" env=\"P\" /><CustOpts><Param name=\"mantrakey\" value=\"\" /></CustOpts></PidOptions>";
//                                            Toast.makeText(BioMetricActivity2.this, captureRequestXML, Toast.LENGTH_SHORT).show();
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
                                                break;
                                            case 6:
                                                //device is Secugen biometric
                                                captureRequestXML = "<PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"0\" format=\"0\" timeout=\"10000\" pidVer=\"2.0\" posh=\"UNKNOWN\" env=\"" + enviroment + "\"/></PidOptions>";
                                                break;
                                            default:
                                                captureRequestXML = null;
                                                break;
                                        }

                                        Log.d(TAG, "onActivityResult: " + captureRequestXML);
                                        if (captureRequestXML != null && !captureRequestXML.isEmpty()) {
                                            Intent sendIntent = new Intent();
                                            sendIntent.setAction(CUSTOM_ACTION_CAPTURE_FINGERPRINT);
                                            sendIntent.putExtra(PID_OPTIONS, captureRequestXML);
                                            this.startActivityForResult(sendIntent, CAPTURE_REQUEST);
                                        }
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Device is not ready", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "RD information is null", Toast.LENGTH_SHORT).show();
                    }
                }else if (requestCode == 1) {
                    piddataxml = data.getStringExtra(PID_DATA);

                    InputStream is = new ByteArrayInputStream(piddataxml.getBytes());
                    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                    domFactory.setIgnoringComments(true);
                    DocumentBuilder builder = domFactory.newDocumentBuilder();
                    Document doc = builder.parse(is);
                    errcode = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("errCode").getTextContent();
                    errinfo = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("errInfo").getTextContent();
                    icount = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("iCount").getTextContent();
                    fcount = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("fCount").getTextContent();
                    ftype = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("fType").getTextContent();
                    nmpoint = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("nmPoints").getTextContent();
                    qSscore = doc.getElementsByTagName("Resp").item(0).getAttributes().getNamedItem("qScore").getTextContent();
                    pid = doc.getElementsByTagName("Data").item(0).getTextContent();
                    skey = doc.getElementsByTagName("Skey").item(0).getTextContent();
                    ci = doc.getElementsByTagName("Skey").item(0).getAttributes().getNamedItem("ci").getTextContent();
                    hmac = doc.getElementsByTagName("Hmac").item(0).getTextContent();
                    type = doc.getElementsByTagName("Data").item(0).getAttributes().getNamedItem("type").getTextContent();
                    dpId = doc.getElementsByTagName("DeviceInfo").item(0).getAttributes().getNamedItem("dpId").getTextContent();
                    rdsId = doc.getElementsByTagName("DeviceInfo").item(0).getAttributes().getNamedItem("rdsId").getTextContent();
                    rdsVer = doc.getElementsByTagName("DeviceInfo").item(0).getAttributes().getNamedItem("rdsVer").getTextContent();
                    dc = doc.getElementsByTagName("DeviceInfo").item(0).getAttributes().getNamedItem("dc").getTextContent();
                    mi = doc.getElementsByTagName("DeviceInfo").item(0).getAttributes().getNamedItem("mi").getTextContent();
                    mc = doc.getElementsByTagName("DeviceInfo").item(0).getAttributes().getNamedItem("mc").getTextContent();

                    if (!piddataxml.toString().equals("")) {
                        SplitXML splitXML = new SplitXML();
                        CaptureResponse response =
                                splitXML.SplitCaptureResponse(piddataxml);

                        Element elementsDeviceInfo = (Element) doc.getElementsByTagName("DeviceInfo").item(0);
                        Element elementsAdditionInfo = (Element) elementsDeviceInfo.getElementsByTagName("additional_info").item(0);
                        NodeList nList = elementsAdditionInfo.getElementsByTagName("Param");
                        CaptureResponse captureResponse = new CaptureResponse();

                        for (int temp = 0; temp < nList.getLength(); temp++) {
                            Node node = nList.item(temp);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                //Print each employee's detail
                                Element eElement = (Element) node;

                                Log.d("CaptureResponseModel", " name : " + eElement.getAttribute("name"));
                                Log.d("CaptureResponseModel: ", " value : " + eElement.getAttribute("value"));

                                captureResponse.additional_info = eElement.getAttribute("name");
                                captureResponse.additional_info2 = eElement.getAttribute("value");

                                if(eElement.getAttribute("name").equals("srno")){
                                    captureResponse.additional_info3 = eElement.getAttribute("value");
                                }
                                if(eElement.getAttribute("name").equals("sysid")){
                                    captureResponse.additional_info4 = eElement.getAttribute("value");
                                }

                            }
                        }

                        _piddata = ("<?xml version=\"1.0\"?>\n" +
                                "<PidData>\n"+
                                "<Resp" +" "+"errCode="+'"'+errcode+'"'+" "+"errInfo="+'"'+errinfo+'"'+" "+"fCount="+'"'+fcount+'"'+" "+"fType="+'"'+ftype+'"'+" "+"nmPoints="+'"'+nmpoint+'"'+" "+"qScore="+'"'+qSscore+'"'+"/>\n"+
                                "<DeviceInfo"+" "+"dpId="+'"'+dpId+'"'+" "+"rdsId="+'"'+rdsId+'"'+" "+"rdsVer="+'"'+rdsVer+'"'+" "+"mi="+'"'+mi+'"'+" "+"mc="+'"'+mc+'"'+" "+"dc="+'"'+dc+'"'+">\n"+
                                "<additional_info>\n" +
                                "<Param name="+'"'+"srno"+'"'+" "+"value="+'"'+captureResponse.additional_info3+'"'+"/>\n"+
                                "<Param name="+'"'+"sysid"+'"'+" "+"value="+'"'+captureResponse.additional_info4+'"'+"/>\n"+
                                "<Param name="+'"'+captureResponse.additional_info+'"'+" "+"value="+'"'+captureResponse.additional_info2+'"'+"/>\n"+
                                "</additional_info>\n" +
                                "</DeviceInfo>\n"+
                                "<Skey"+" "+"ci="+'"'+ci+'"'+">"+skey+"</Skey>\n"+
                                "<Hmac>" +hmac+"</Hmac>\n"+
                                "<Data type="+'"'+type+'"'+">"+pid+"</Data>\n"+
                                "</PidData>");

                        Log.d("sssssssssssssssssss",_piddata);


                        if(!response.toString().equals("")){
                            Intent i = new Intent(BioMetricActivity.this, SubmitAepsTransaction.class);
                            i.putExtra("transaction_type", transaction_type);
                            i.putExtra("adhar_no", adharcard_number);
                            i.putExtra("mobile_no", mobileno);
                            i.putExtra("amount", amount);
                            i.putExtra("bank_code", bank_code);
                            i.putExtra("amount", amount);
                            i.putExtra("access_token", access_token);
                            i.putExtra("latitude", latitude);
                            i.putExtra("longitude", longitude);
                            i.putExtra("data", response.toString()); //response.tostring
                            i.putExtra("pidData", _piddata);  //piddata

                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Device Capture Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return true;
    }
}