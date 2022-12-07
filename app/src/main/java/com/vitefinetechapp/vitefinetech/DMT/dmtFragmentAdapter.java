package com.vitefinetechapp.vitefinetech.DMT;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vitefinetechapp.vitefinetech.Recharge.newRecharge.recharge2Fragment;
import com.vitefinetechapp.vitefinetech.Recharge.newRecharge.rechargeHistoryFragment;

public class dmtFragmentAdapter extends FragmentStateAdapter {

    public dmtFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new dmtMainFragment();

        return new dmtHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
