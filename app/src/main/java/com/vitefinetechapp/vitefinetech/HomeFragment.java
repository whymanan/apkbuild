package com.vitefinetechapp.vitefinetech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vitefinetechapp.vitefinetech.Aeps.BankActivity;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.MainAeps2Activity;
import com.vitefinetechapp.vitefinetech.Aeps2.SelectBankActivity2;
import com.vitefinetechapp.vitefinetech.Aeps2.TransactionActivity;
import com.vitefinetechapp.vitefinetech.Aeps3.SelectBankActivity3;
import com.vitefinetechapp.vitefinetech.DATA_Card.DataCardRechargeActivity;
import com.vitefinetechapp.vitefinetech.DMT.DMTScreenActivity;
import com.vitefinetechapp.vitefinetech.DMT.DMTScreenActivity2;
import com.vitefinetechapp.vitefinetech.DTH.DthRechargActivity;
import com.vitefinetechapp.vitefinetech.DTH.newDTH.DthNewActivity;
import com.vitefinetechapp.vitefinetech.EMI.EmiActivity;
import com.vitefinetechapp.vitefinetech.EMI.newEmi.EmiNewActivity;
import com.vitefinetechapp.vitefinetech.Electricity.ElecticityActivity;
import com.vitefinetechapp.vitefinetech.Electricity.newElectricity.ElectricNewActivity;
import com.vitefinetechapp.vitefinetech.FASTag.FastTagActivity;
import com.vitefinetechapp.vitefinetech.GAS.GasBillPayActivity;
import com.vitefinetechapp.vitefinetech.GAS.newGas.GasNewActivity;
import com.vitefinetechapp.vitefinetech.GST_Registration.CompanyRegistrationActivity;
import com.vitefinetechapp.vitefinetech.GST_Registration.GSTRegistrationActivity;
import com.vitefinetechapp.vitefinetech.GST_Return.GST_ReturnActivity;
import com.vitefinetechapp.vitefinetech.Insurance.InsuranceActivity;
import com.vitefinetechapp.vitefinetech.Insurance.newInsurance.InsuranceNewActivity;
import com.vitefinetechapp.vitefinetech.LandLine.LandlineBillActivity;
import com.vitefinetechapp.vitefinetech.LandLine.newLandline.LandLineNewActivity;
import com.vitefinetechapp.vitefinetech.M_ATM.M_Atm1;
import com.vitefinetechapp.vitefinetech.Municipality.MunicipalityBillPayActivity;
import com.vitefinetechapp.vitefinetech.Municipality.newMunicipality.MunicipalityNewActivity;
import com.vitefinetechapp.vitefinetech.Payout.PayOutActivity;
import com.vitefinetechapp.vitefinetech.Recharge.RechargeActivity;
import com.vitefinetechapp.vitefinetech.Recharge.newRecharge.RechargeActivity2;
import com.vitefinetechapp.vitefinetech.TrafficChallan.TrafficActivity;
import com.vitefinetechapp.vitefinetech.TrafficChallan.newTraffic.TrafficNewActivity;
import com.vitefinetechapp.vitefinetech.Water.WaterActivity;
import com.vitefinetechapp.vitefinetech.Coupon.CouponPurchaseActivity;
import com.vitefinetechapp.vitefinetech.Pan.PanActivity;
import com.vitefinetechapp.vitefinetech.Water.newWater.WaterNewActivity;
import com.vitefinetechapp.vitefinetech.wallet.RequestBalanceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private DashboardActivity appCompatActivity;
    ImageButton imageButtonaeps, imageButtonaeps2, imageButtonaeps3, imageButtondmt, imageButtonDTH, imageButtonElectri,
            imageButtonGAS, imageButtonLandline, imageButtonrecharge, imageButtonTraffic, imageButtonMunicipal,
            imageButtonWater, imageButtonInsurance, imageButtonFastTag, imageButtonM_ATM, imageButtonGSTRegistration,
            imageButtonGSTReturn, imageButtonPrivateLmt, imageBu, imageTally, imageBtnEmi,imageButtonAadharpay;
    Button payout;
    TextView textView4, textView2;
    String memberid,user_id;
    String bal;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedPreferences;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
ImageButton btn_add_balance;
    public HomeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appCompatActivity = (DashboardActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageButtonaeps = (ImageButton) view.findViewById(R.id.imageButtonAeps);
        imageButtonaeps2 = (ImageButton) view.findViewById(R.id.imageButtonaeps2);
        imageButtonaeps3 = (ImageButton) view.findViewById(R.id.imageButtonAeps3);
        imageButtondmt = (ImageButton) view.findViewById(R.id.imageButtonDMT);
        imageButtonrecharge = (ImageButton) view.findViewById(R.id.imageButtonRecharge);
        imageButtonDTH = (ImageButton) view.findViewById(R.id.imageButtonDTH);
        imageButtonElectri = (ImageButton) view.findViewById(R.id.imageButtonElectricity);
        imageButtonGAS = (ImageButton) view.findViewById(R.id.imageButtonGas);
        imageButtonLandline = (ImageButton) view.findViewById(R.id.imageButtonLandLine);
//        imageButtondatacard       = (ImageButton)view.findViewById(R.id.imageButtonDAtaCard);
        imageButtonWater = (ImageButton) view.findViewById(R.id.imageButtonWater);
        imageButtonInsurance = (ImageButton) view.findViewById(R.id.imageButtonInsurance);
        imageButtonFastTag = (ImageButton) view.findViewById(R.id.imageButtonFastTag);
        imageButtonM_ATM = (ImageButton) view.findViewById(R.id.imageButtonMicroAtm);
        imageButtonGSTRegistration = (ImageButton) view.findViewById(R.id.imageButtonGstRegistration);
        imageButtonGSTReturn = (ImageButton) view.findViewById(R.id.imageButtonGSTReturn);
        imageButtonPrivateLmt = (ImageButton) view.findViewById(R.id.imageButtonPrivateLmt);
        imageBu = view.findViewById(R.id.imageBu);
        imageTally = view.findViewById(R.id.imageTally);
        payout = (Button) view.findViewById(R.id.payout);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        imageBtnEmi = view.findViewById(R.id.imageButtonEmi);
        imageButtonTraffic = view.findViewById(R.id.imageButtontraffic);
        imageButtonMunicipal=view.findViewById(R.id.imageButtonmunicipal);
        imageButtonAadharpay=view.findViewById(R.id.imageButtonAadharpay);
        btn_add_balance=view.findViewById(R.id.btn_add_balance);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        memberid = sharedPreferences.getString("member_id", null);
        user_id=sharedPreferences.getString("user_id", null);
        textView4.setText(memberid);
        Log.d("homefragment_shared", sharedPreferences.getAll() + "");

        setBalance();


        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        BannerAdapter viewPagerAdapter = new BannerAdapter(getActivity());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

//        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for (int i = 0; i < dotscount; i++) {
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.nonactive_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        btn_add_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), RequestBalanceActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra("from","aadhar");
                startActivity(intent);
            }
        });

        payout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_payout = new Intent(getActivity().getApplicationContext(), PayOutActivity.class);
                intent_payout.putExtra("avail_bal",bal);
                startActivity(intent_payout);
            }
        });

        imageButtonAadharpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainAeps2Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("from","aadhar");
                startActivity(intent);
            }
        });


        imageButtonMunicipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), MunicipalityNewActivity.class);
                startActivity(i);
            }
        });

        imageButtonTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), TrafficNewActivity.class);
                startActivity(i);
            }
        });


        imageButtonaeps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), BankActivity.class);
                startActivity(i);
            }
        });

        imageButtonaeps2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainAeps2Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("from","main");
                startActivity(intent);
            }
        });

        imageButtonaeps3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SelectBankActivity3.class);
                startActivity(intent);
            }
        });


        imageButtondmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), DMTScreenActivity2.class);
                startActivity(i);
            }
        });

        imageButtonrecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity().getApplicationContext(), RechargeActivity.class);
                Intent i = new Intent(getActivity().getApplicationContext(), RechargeActivity2.class);
                startActivity(i);
            }
        });

        imageButtonDTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), DthNewActivity.class);
                startActivity(i);
            }
        });

        imageButtonElectri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ElectricNewActivity.class);
                startActivity(i);
            }
        });

        imageButtonGAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), GasNewActivity.class);
                startActivity(i);
            }
        });

        imageButtonLandline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), LandLineNewActivity.class);
                startActivity(i);
            }
        });
//        imageButtondatacard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity().getApplicationContext(), DataCardRechargeActivity.class);
//                startActivity(i);
//            }
//        });

        imageButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), WaterNewActivity.class);
                startActivity(i);
            }
        });

        imageButtonInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), InsuranceNewActivity.class);
                startActivity(i);
            }
        });

        imageButtonFastTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), FastTagActivity.class);
                startActivity(i);
            }
        });

        imageButtonM_ATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), M_Atm1.class);
                startActivity(i);
            }
        });

        imageButtonGSTRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), GSTRegistrationActivity.class);
                startActivity(i);
            }
        });

        imageButtonGSTReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), GST_ReturnActivity.class);
                startActivity(i);
            }
        });

        imageButtonPrivateLmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), CompanyRegistrationActivity.class);
                startActivity(i);

            }
        });

        imageBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity().getApplicationContext(), CouponPurchaseActivity.class);
                startActivity(i);

            }
        });

        imageTally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity().getApplicationContext(), PanActivity.class);
                startActivity(i);

            }
        });

        imageBtnEmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), EmiNewActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void setBalance() {
        String url = "https://nextmoney.co.in/nextmoney/Wallet/wallet_balance";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("api_key","NMA001");
            Log.d("setBalanceJson", "Json:" + jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject,
                response -> {
                    Log.d("setBalanceres", "res:" + response);
//                    Log.d("setBalance", response);
//                    JSONObject obj = null;
                    try {
                        Boolean st=response.getBoolean("status");
                        if(st==true) {
                            JSONArray data = response.getJSONArray("data");
                            JSONObject obj = data.getJSONObject(0);
                            textView2.setText(obj.getInt("balance")+"");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("setBalanceerror", "error:" + error.getMessage());
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(3);
                        }
                        else if (viewPager.getCurrentItem() == 2) {
                            viewPager.setCurrentItem(0);
                        }
//                        else if (viewPager.getCurrentItem() == 3) {
//                            viewPager.setCurrentItem(0);
//                        }
                    }
                });
            } catch (Exception e) {

            }


        }
    }
}