package com.vitefinetechapp.vitefinetech.DTH.newDTH;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vitefinetechapp.vitefinetech.Recharge.newRecharge.recharge2Fragment;
import com.vitefinetechapp.vitefinetech.Recharge.newRecharge.rechargeHistoryFragment;

public class DthFragmentAdapter extends FragmentStateAdapter {
    public DthFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new DthFragment();

        return new DthHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
