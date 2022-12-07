package com.vitefinetechapp.vitefinetech.Aeps2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitefinetechapp.vitefinetech.History.HistoryAdapter;
import com.vitefinetechapp.vitefinetech.History.HistoryModel;
import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class TransactionReceiptAdapter extends RecyclerView.Adapter<TransactionReceiptAdapter.Viewholder>  {

    Context context;
    ArrayList<Ministatement> ministatementArrayList;

    // Constructor
    public TransactionReceiptAdapter(Context context, ArrayList<Ministatement> ministatementArrayList) {
        this.context = context;
        this.ministatementArrayList = ministatementArrayList;
    }

    @NonNull
    @Override
    public TransactionReceiptAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_receipt_adapter, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionReceiptAdapter.Viewholder holder, int position) {

        Ministatement model = ministatementArrayList.get(position);
        holder.date.setText(model.getDate());
        holder.txntype.setText(model.getTxntype());
        holder.amount.setText( model.getAmount());
        holder.narriation.setText(model.getNarriation());
    }

    @Override
    public int getItemCount() {
        return ministatementArrayList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView date, txntype, amount,narriation;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            txntype = itemView.findViewById(R.id.tra_type);
            amount = itemView.findViewById(R.id.txt_amount);
            narriation = itemView.findViewById(R.id.narration);
        }
    }
}