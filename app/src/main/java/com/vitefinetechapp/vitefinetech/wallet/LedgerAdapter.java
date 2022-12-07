package com.vitefinetechapp.vitefinetech.wallet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class LedgerAdapter extends RecyclerView.Adapter<LedgerAdapter.Viewholder> {

    private Context context;
    private ArrayList<LedgerModel> ledgerModelArrayList;

    // Constructor
    public LedgerAdapter(Context context, ArrayList<LedgerModel> ledgerModelArrayList) {
        this.context = context;
        this.ledgerModelArrayList = ledgerModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ledger_card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
       // String model = ledgerModelArrayList.get(position);

            holder.ref.setText("" + ledgerModelArrayList.get(position).getLedger_ref());
            holder.amt.setText("" + ledgerModelArrayList.get(position).getLedger_amt());
            holder.mode.setText(" " + ledgerModelArrayList.get(position).getLedger_mode());
            holder.stockTy.setText("" + ledgerModelArrayList.get(position).getLedger_stock_type());
//            holder.bank.setText("" + ledgerModelArrayList.get(position).getLedger_bank());
            holder.com.setText("" + ledgerModelArrayList.get(position).getLedger_commision());
            holder.nar.setText(" " + ledgerModelArrayList.get(position).getLedger_narration());
//            holder.transId.setText("" + ledgerModelArrayList.get(position).getLedger_wallet_transaction_id());
//            holder.walletId.setText("" + ledgerModelArrayList.get(position).getLedger_wallet_id());
//            holder.memberTo.setText("" + ledgerModelArrayList.get(position).getLedger_member_to());
//            holder.memberFrom.setText("" + ledgerModelArrayList.get(position).getLedger_member_from());
//            holder.serId.setText("" + ledgerModelArrayList.get(position).getLedger_service_id());
            holder.surCh.setText("" + ledgerModelArrayList.get(position).getLedger_surcharge());
            holder.bal.setText("" + ledgerModelArrayList.get(position).getLedger_balance());
            holder.clBal.setText("" + ledgerModelArrayList.get(position).getLedger_closebalance());
            holder.type.setText("" + ledgerModelArrayList.get(position).getLedger_type());
            holder.trType.setText("" + ledgerModelArrayList.get(position).getLedger_trans_type());
            holder.upd.setText("" + ledgerModelArrayList.get(position).getLedger_updated());
            holder.date.setText("" + ledgerModelArrayList.get(position).getLedger_date());
            holder.sts.setText("" + ledgerModelArrayList.get(position).getLedger_status());

//        if(ct==0){
//
//        }

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return ledgerModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        final private LinearLayout ledger_card_view;
//        final private TextView transId,walletId,memberTo,memberFrom,serId,surCh,ref,mode,bank,stockTy,bal,clBal,com,type,trType,upd,date,nar,sts,amt;
final private TextView surCh,ref,mode,stockTy,bal,clBal,com,type,trType,upd,date,nar,sts,amt;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            ledger_card_view = itemView.findViewById(R.id.ledger_card_view);
//            transId= itemView.findViewById(R.id.transId);
//            walletId= itemView.findViewById(R.id.walletId);
//            memberTo= itemView.findViewById(R.id.memberTo);
//            memberFrom= itemView.findViewById(R.id.memberFrom);
//            serId= itemView.findViewById(R.id.serId);
            surCh= itemView.findViewById(R.id.surCh);
            ref= itemView.findViewById(R.id.ref);
            mode= itemView.findViewById(R.id.mode);
//            bank= itemView.findViewById(R.id.bank);
            stockTy= itemView.findViewById(R.id.stockTy);
            bal= itemView.findViewById(R.id.bal);
            clBal= itemView.findViewById(R.id.clBal);
            com= itemView.findViewById(R.id.com);
            type= itemView.findViewById(R.id.type);
            trType= itemView.findViewById(R.id.trType);
            upd= itemView.findViewById(R.id.upd);
            date= itemView.findViewById(R.id.date);
            nar= itemView.findViewById(R.id.nar);
            sts= itemView.findViewById(R.id.sts);
            amt=itemView.findViewById(R.id.amt);
        }
    }
}
