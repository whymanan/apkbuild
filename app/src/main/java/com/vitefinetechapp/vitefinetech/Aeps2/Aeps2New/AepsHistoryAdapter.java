package com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class AepsHistoryAdapter extends RecyclerView.Adapter<AepsHistoryAdapter.MyViewHolder> {

    private ArrayList<AepsHistory> historyList;

    public AepsHistoryAdapter(ArrayList<AepsHistory> historyList) {
        this.historyList = historyList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView ref,tType,status,mob,amt,bCode,bIfsc,created;
        private Button btn_pdf;
        private LinearLayout ll_bCode,ll_bIfsc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_bIfsc=itemView.findViewById(R.id.ll_bIfsc);
            ll_bCode=itemView.findViewById(R.id.ll_bCode);
            ref=itemView.findViewById(R.id.rrn_no);
            tType=itemView.findViewById(R.id.txn_type);
            status=itemView.findViewById(R.id.status);
            mob=itemView.findViewById(R.id.trMob);
            amt=itemView.findViewById(R.id.trAmount);
            bCode=itemView.findViewById(R.id.trBankCode);
            bIfsc=itemView.findViewById(R.id.trBankIfsc);
            created=itemView.findViewById(R.id.created);
//            btn_pdf=itemView.findViewById(R.id.btn_pdf);
        }
    }

    @NonNull
    @Override
    public AepsHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.aeps_history_list,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AepsHistoryAdapter.MyViewHolder holder, int position) {

        if(historyList.get(position).gettType().equals("recharge")){
            holder.ll_bIfsc.setVisibility(View.GONE);
            holder.ll_bCode.setVisibility(View.GONE);
        }
        holder.ref.setText(historyList.get(position).getRefNo());
        holder.tType.setText(historyList.get(position).gettType());
        holder.status.setText(historyList.get(position).getStatus());
        holder.mob.setText(historyList.get(position).getMobile());
        holder.amt.setText(historyList.get(position).getAmount());
        holder.bCode.setText(historyList.get(position).getbCode());
        holder.bIfsc.setText(historyList.get(position).getbIfsc());
        holder.created.setText(historyList.get(position).getCreated());

//        holder.btn_pdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pageHeight = 1120;
//                int pagewidth = 792;
//                Bitmap bmp, scaledbmp;
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
