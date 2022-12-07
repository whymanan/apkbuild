package com.vitefinetechapp.vitefinetech.LandLine.newLandline;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vitefinetechapp.vitefinetech.DTH.newDTH.DthFragment;
import com.vitefinetechapp.vitefinetech.DTH.newDTH.DthHistoryFragment;

public class LandlineFragmentAdapter extends FragmentStateAdapter {
    public LandlineFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new LandlineFragment();

        return new LandlineHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
