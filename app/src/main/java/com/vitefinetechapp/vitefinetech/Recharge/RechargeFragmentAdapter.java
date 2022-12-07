package com.vitefinetechapp.vitefinetech.Recharge;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class RechargeFragmentAdapter extends FragmentStateAdapter {

    String response;
    JSONObject obj = null;
    int size=0;

    public RechargeFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,String response) {
        super(fragmentManager, lifecycle);
        this.response=response;

        try {
            obj = new JSONObject(response);
            size=obj.getJSONObject("info").length();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        try {
            return new RechargePlanFragment(obj.getJSONObject("info").getString(obj.getJSONObject("info").names().get(position).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {

        return size;
    }
}
