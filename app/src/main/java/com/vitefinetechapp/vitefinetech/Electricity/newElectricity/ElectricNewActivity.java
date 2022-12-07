package com.vitefinetechapp.vitefinetech.Electricity.newElectricity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.vitefinetechapp.vitefinetech.DTH.newDTH.DthFragmentAdapter;
import com.vitefinetechapp.vitefinetech.R;

public class ElectricNewActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ImageButton backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_new);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);
        backbutton = findViewById(R.id.backbutton);
        ElectricityFragmentAdapter adapter = new ElectricityFragmentAdapter(this);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("Electricity bill"));
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
    }
}