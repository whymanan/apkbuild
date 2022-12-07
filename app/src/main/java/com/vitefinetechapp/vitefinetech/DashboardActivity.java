package com.vitefinetechapp.vitefinetech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vitefinetechapp.vitefinetech.utils.LogOutTimerUtil;

import java.util.HashMap;
import java.util.Timer;

import static android.content.ContentValues.TAG;

public class DashboardActivity extends AppCompatActivity implements LogOutTimerUtil.LogOutListener{
    BottomNavigationView bottomNavigationView;
    private Timer timer;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;
    String e, p;
    private Handler handler;
    private Runnable r;


    SharedPreferences sharedpreferences;
    public static final String KEY_USERNAMES="member_id";
    public static final String KEY_PASSWORD="password";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btm_view);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        e = sharedPreferences.getString(KEY_USERNAMES, null);

        sharedpreferences =   getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new com.vitefinetechapp.vitefinetech.HomeFragment()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.menuProfile:
                        selectedFragment = new com.vitefinetechapp.vitefinetech.ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        return true;
                        case R.id.menuWallet:
                            selectedFragment = new WalletFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            return true;
                            case R.id.menuHome:
                                selectedFragment = new com.vitefinetechapp.vitefinetech.HomeFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                                return true;
                                case R.id.menuHistory:
                                    selectedFragment = new com.vitefinetechapp.vitefinetech.HistoryFragment();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                                    return true;
                }
                return false;
            }
        }
        );
    }

 @Override
    protected void onStart() {
        super.onStart();
        LogOutTimerUtil.startLogoutTimer(this, this);
        Log.e(TAG, "OnStart () &&& Starting timer");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        LogOutTimerUtil.startLogoutTimer(this, this);
        Log.e(TAG, "User interacting with screen");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "onResume()");
    }
//
//    /**
//     * Performing idle time logout
//     */
    @Override
    public void doLogout() {
        // write your stuff here
        Log.e(TAG, "dologout");
        Intent i = new Intent(com.vitefinetechapp.vitefinetech.DashboardActivity.this, MainActivity.class);

        sharedpreferences.edit().remove(KEY_USERNAMES).apply();
        sharedpreferences.edit().remove(KEY_PASSWORD).apply();
        sharedpreferences.edit().remove("tokenname").apply();

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }



}






