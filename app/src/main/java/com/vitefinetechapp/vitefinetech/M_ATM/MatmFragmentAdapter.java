package com.vitefinetechapp.vitefinetech.M_ATM;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.BoardingAeps2Fragment;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.HistoryAeps2Fragment;
import com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New.TransactionAeps2Fragment;

public class MatmFragmentAdapter extends FragmentStateAdapter {


    public MatmFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new MatmBalanceFragment();
        else if(position==1)
            return new MatmWithdrawFragment();

        return new MatmHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
