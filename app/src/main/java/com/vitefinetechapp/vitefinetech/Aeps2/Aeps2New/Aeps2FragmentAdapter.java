package com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Aeps2FragmentAdapter extends FragmentStateAdapter {

String from="";
    public Aeps2FragmentAdapter(@NonNull FragmentActivity fragmentActivity,String from) {
        super(fragmentActivity);
        this.from=from;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(from.equals("aadhar")){
            if(position==0)
                return new TransactionAeps2Fragment();

            return new HistoryAeps2Fragment();
        }
        else{
            if(position==0)
                return new TransactionAeps2Fragment();
            else if(position==1)
                return new HistoryAeps2Fragment();

            return new BoardingAeps2Fragment();
        }

    }

    @Override
    public int getItemCount() {
        if(from.equals("aadhar")){
            return 2;
        }
        return 3;
    }
}
