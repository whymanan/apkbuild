package com.vitefinetechapp.vitefinetech.Aeps;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.vitefinetechapp.vitefinetech.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ScanActivity extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;

    String resultdata;
    String attrName;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    public static TextView uid,name;
    public static String uissss,nameee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scannerView     =    findViewById(R.id.scanner_view);
        uid             =    findViewById(R.id.uid);
        name            =    findViewById(R.id.name);

        codeScanner = new CodeScanner(this,scannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resultdata = result.getText();
                        Log.d("resultdata",result.toString());

                        String str = result.toString();

                        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                        documentBuilderFactory.setValidating(false);
                        DocumentBuilder documentBuilder = null;
                        try {
                            documentBuilder = documentBuilderFactory.newDocumentBuilder();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        }
                        try {
                            Document doc = documentBuilder.parse(new ByteArrayInputStream(str.getBytes("utf-8")));
                            NodeList nodeList = doc.getElementsByTagName("*");

                            int num = nodeList.getLength();

                            for (int i = 0; i < num; i++) {
                                Element node = (Element) nodeList.item(i);

                                System.out.println("List attributes for node: " + node.getNodeName());

                                // get a map containing the attributes of this node
                                NamedNodeMap attributes = node.getAttributes();

                                // get the number of nodes in this map
                                int numAttrs = attributes.getLength();

                                for (int j = 0; j < numAttrs; j++) {
                                    Attr attr = (Attr) attributes.item(j);

                                    attrName = attr.getNodeName();
                                    String attrValue = attr.getNodeValue();

                                    // Do your stuff here
                                    System.out.println("Found attribute: " + attrName + " with value: " + attrValue);

                                    if(attrName.equals("u")){
                                        uid.setText(attrValue);
                                        Log.d("uid",uid.getText().toString());
                                    }else if(attrName.equals("uid")){
                                        uid.setText(attrValue);
                                    }

                                    if(attrName.equals("n")){
                                        name.setText(attrValue);
                                    }else if(attrName.equals("name")){
                                        name.setText(attrValue);
                                    }

                                }

                                if(!attrName.equals("")){
                                    uissss = uid.getText().toString();
                                    nameee = name.getText().toString();

                                    Intent intent = new Intent();
                                    intent.putExtra("editvalue", uissss.trim().toString());
                                    intent.putExtra("editvalue2", nameee.trim().toString());
                                    setResult(RESULT_OK,intent);
                                    finish();

                                    onBackPressed();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });


        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }


    private void requestForCamera(){
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(ScanActivity.this,"Camera permission is Required",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }


}