package com.vitefinetechapp.vitefinetech.Recharge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.vitefinetechapp.vitefinetech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopUPActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TopUpAdapter adapter;

    List<Recharge> list = new ArrayList<>();
    ProgressBar progressBar;
    String oprator, circle, type, url;
    ImageButton backbutton;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;


    private ProgressDialog progressDialog;
    TextView tv;

    String response;
    JSONObject obj = null;
    TabLayout tabLayout;
    ViewPager2 pager2;
    RechargeFragmentAdapter FragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_u_p);


        backbutton = (ImageButton) findViewById(R.id.backbutton);
        response = getIntent().getStringExtra("response");
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);


        try {
            obj = new JSONObject(response);
//            Log.d("response-TopUp", obj.getJSONObject("info").names().get(0).toString());

            FragmentManager fm = getSupportFragmentManager();
            FragmentAdapter = new RechargeFragmentAdapter(fm, getLifecycle(),response);
            pager2.setAdapter(FragmentAdapter);

//        tabLayout.addTab(tabLayout.newTab().setText("First"));
//        tabLayout.addTab(tabLayout.newTab().setText("Second"));
//        tabLayout.addTab(tabLayout.newTab().setText("Third"));
            for (int i = 0; i < obj.getJSONObject("info").length(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(obj.getJSONObject("info").names().get(i).toString()));
            }


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    pager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.selectTab(tabLayout.getTabAt(position));
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


        /**recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

         progressDialog = new ProgressDialog(TopUPActivity.this);
         progressDialog.setMessage("loading...");
         progressDialog.setCancelable(false);

         progressDialog.show();**/

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        /**oprator = getIntent().getStringExtra("oprator");
         circle = getIntent().getStringExtra("circle");
         type = getIntent().getStringExtra("type");

         Log.d("oprator", oprator);
         Log.d("circle", circle);
         Log.d("type", type);


         url = "https://vitefintech.com/viteapi/recharge/mobile_plan?circle=" + circle + "&operator=" + oprator + "&type=" + type;

         Log.d("str", url);

         retrivedata();**/
    }

    public void retrivedata() {
        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", response.toString());
                try {
                    progressDialog.dismiss();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);

                        String amount = data.getString("amount");
                        String detail = data.getString("detail");
                        String Validity = data.getString("validity");
                        String talktime = data.getString("talktime");


                        Recharge recharge = new Recharge();

                        recharge.setTv_amount(amount);
                        recharge.setTv_details(detail);
                        recharge.setTv_validity(Validity);
                        recharge.setTv_talktym(talktime);

//                        list.add(recharge);
//                        adapter = new TopUpAdapter(TopUPActivity.this, list);
//                        recyclerView.setAdapter(adapter);
//
//                        adapter.setOnItemClickListener(new ItemClickListener() {
//
//                            @Override
//                            public void OnItemClick(Integer position, String amount) {
//                                Log.d("amt", amount);
//                                Log.d("posi", position.toString());
//
//                                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedpreferences.edit();
//                                editor.putString("amount", amount);
//                                editor.apply();
//
//                                onBackPressed();
//                            }
//                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(TopUPActivity.this, "No offer details available for this category", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error3:" + error.toString());
                Toast.makeText(TopUPActivity.this, "No offer details available for this category", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest2);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

}