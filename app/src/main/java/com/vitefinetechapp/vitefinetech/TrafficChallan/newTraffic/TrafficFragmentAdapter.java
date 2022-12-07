package com.vitefinetechapp.vitefinetech.TrafficChallan.newTraffic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vitefinetechapp.vitefinetech.DTH.newDTH.DthFragment;
import com.vitefinetechapp.vitefinetech.DTH.newDTH.DthHistoryFragment;

public class TrafficFragmentAdapter extends FragmentStateAdapter {
    public TrafficFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new TrafficFragment();

        return new TrafficHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
