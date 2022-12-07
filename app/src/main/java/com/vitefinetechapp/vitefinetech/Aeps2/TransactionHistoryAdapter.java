package com.vitefinetechapp.vitefinetech.Aeps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.Viewholder> {

    Context context;
    ArrayList<History> historyArrayList;

    // Constructor
    public TransactionHistoryAdapter(Context context, ArrayList<History> historyArrayList) {
        this.context = context;
        this.historyArrayList = historyArrayList;
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_transaction_history_adapter, parent, false);
        return new TransactionHistoryAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryAdapter.Viewholder holder, int position) {

        History model = historyArrayList.get(position);

        holder.date.setText(model.getDate());
        holder.txnstatus.setText(model.getTrastatus());
        holder.amount.setText( model.getAmount());
        holder.aepstype.setText(model.getAepstype());
    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView date, txnstatus, amount, aepstype;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            aepstype = itemView.findViewById(R.id.aepstype);
            amount = itemView.findViewById(R.id.txt_amount);
            txnstatus = itemView.findViewById(R.id.tra_status);
            date = itemView.findViewById(R.id.date_time);
        }
    }
}