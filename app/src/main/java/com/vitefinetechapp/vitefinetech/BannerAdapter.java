package com.vitefinetechapp.vitefinetech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BannerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
//    private Integer [] images = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};
//private Integer [] images = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner4};
ArrayList<String> images;
    public BannerAdapter(Context context) {
        this.context = context;
        images=new ArrayList<>();
        getImagesUrl();

    }

    private void getImagesUrl() {
        //        Log.d("banner_t", "here2");
        String url="https://nextmoney.co.in/nextmoney/Rechargebillpayment/slider";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                response -> {
                    try {
                        JSONObject obj=new JSONObject(response);
                        Boolean st=obj.getBoolean("status");
                        if(st){
//                            String[] msg=(String)obj.getJSONArray("msg");
                            JSONArray msg= obj.getJSONArray("msg");

//                            Toast.makeText(context,"len"+msg.length(), Toast.LENGTH_SHORT).show();
                            for(int i=0;i<msg.length();i++){
                                images.add(msg.get(i)+"");
                                notifyDataSetChanged();
//                                Log.d("banner_t_msgurl", images.get(i));
                            }
                            Log.d("banner_t_msgurl", images.size()+"");
                        }else{
                            Log.d("banner_t_error", obj.getString("msg"));
//                            Toast.makeText(context, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("banner_t_error", "error:"+e.getMessage());
//                        Toast.makeText(context, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.d("banner_t_tryerror","error:"+ error.getMessage());
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String SHARED_PREFS = "shared_prefs";
                SharedPreferences sharedPreferences;
                sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                String memberid = sharedPreferences.getString("member_id", null);

                params.put("member_Id", memberid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_banner_adapter, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setImageResource(images[position]);
        Glide.with(context).load(images.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        imageView.setImageResource(R.drawable.banner1);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == 0){
                    Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
                } else if(position == 1){
                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
