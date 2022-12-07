package com.vitefinetechapp.vitefinetech.M_ATM;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.paysprint.microatmlib.activities.HostActivity;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.Aeps2FragmentAdapter;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.MainAeps2Activity;
import com.vitefinetechapp.vitefinetech.DashboardActivity;
import com.vitefinetechapp.vitefinetech.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class M_Atm1 extends AppCompatActivity {


    int _resultCode=999;
    int REQUEST_ENABLE_BT=5;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ImageButton backBtn;
    private String[] titles = new String[]{"Balance", "Withdraw"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m__atm1);
        isStoragePermissionGranted();
        isBlueToothEnables();


        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);
        backBtn=findViewById(R.id.backbutton);
        MatmFragmentAdapter adapter = new MatmFragmentAdapter(this);

        tabLayout.addTab(tabLayout.newTab().setText("Balance"));
        tabLayout.addTab(tabLayout.newTab().setText("Withdraw"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   viewPager2.setCurrentItem(tab.getPosition());
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {

                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {

                                               }
                                           }
        );

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(M_Atm1.this, DashboardActivity.class);
                startActivity(i);
            }
        });


    }

    private void isBlueToothEnables() {
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (!bluetooth.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    public void isStoragePermissionGranted(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                    Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
            ) {
                int PERMISSION_REQUEST_CODE = 200;
                String[] per ={Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET};
                ActivityCompat.requestPermissions(this,per, PERMISSION_REQUEST_CODE);
            }
        }
    }


}
