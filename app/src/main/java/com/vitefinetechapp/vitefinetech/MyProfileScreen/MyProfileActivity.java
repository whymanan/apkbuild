package com.vitefinetechapp.vitefinetech.MyProfileScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.vitefinetechapp.vitefinetech.R;

public class MyProfileActivity extends AppCompatActivity {
    ImageButton backbutton;
    ImageView selectimage,img_userImage,edit;

    private static final int GALLERY_INTENT = 2;
    public static final int READ_EXTERNAL_STORAGE = 0;
    public static final int RequestPermissionCode = 1;

    EditText e_name,e_mobnumber,e_mail,e_bankname,e_branch,e_accountno,e_ifsccode;
    TextView t_name,t_mobnumber,t_mail,t_bankname,t_branch,t_accountno,t_ifsccode;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        backbutton       =      (ImageButton) findViewById(R.id.backbutton);
        selectimage      =      (ImageView) findViewById(R.id.selectimage);
        img_userImage    =      (ImageView) findViewById(R.id.img_userImage);
        edit             =      (ImageView) findViewById(R.id.edit);
        save             =      (Button) findViewById(R.id.save);

        e_name            =      (EditText) findViewById(R.id.name_edit);
        e_mobnumber       =      (EditText) findViewById(R.id.mobno_edit);
        e_mail            =      (EditText) findViewById(R.id.email_edit);

        t_name            =      (TextView) findViewById(R.id.name_txt);
        t_mobnumber       =      (TextView) findViewById(R.id.mobile_txt);
        t_mail            =      (TextView) findViewById(R.id.email_txt);



       save.setVisibility(View.GONE);

       edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_name.setVisibility(View.VISIBLE);
                e_mobnumber.setVisibility(View.VISIBLE);
                e_mail.setVisibility(View.VISIBLE);

                save.setVisibility(View.VISIBLE);

                t_name.setVisibility(View.GONE);
                t_mobnumber.setVisibility(View.GONE);
                t_mail.setVisibility(View.GONE);

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
                builder.setTitle("Select From");
                builder.setCancelable(true);
                builder.setPositiveButton("Gallery", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //                        Check for Runtime Permission
                        if (ContextCompat.checkSelfPermission(MyProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MyProfileActivity.this, "Call for Permission", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                            }
                        } else {
                            callgalary();
                        }
                    }
                });
                builder
                        .setNegativeButton(
                                "Camera",
                                new DialogInterface
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if (ContextCompat.checkSelfPermission(MyProfileActivity.this, Manifest.permission.CAMERA)
                                                != PackageManager.PERMISSION_GRANTED) {
                                            Toast.makeText(MyProfileActivity.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_SHORT).show();
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
                                            }
                                        } else {
                                            callCamera();

                                        }
                                    }
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    private void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callgalary();
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            final Uri mImageUri = data.getData();
            img_userImage.setImageURI(mImageUri);
        }
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_userImage.setImageBitmap(photo);

        }
    }
}