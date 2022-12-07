package com.vitefinetechapp.vitefinetech.Recharge.newRecharge;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.BoardingAeps2Fragment;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.HistoryAeps2Fragment;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.TransactionAeps2Fragment;

public class rechargeFragmentAdapter extends FragmentStateAdapter {

    public rechargeFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new recharge2Fragment();

        return new rechargeHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
