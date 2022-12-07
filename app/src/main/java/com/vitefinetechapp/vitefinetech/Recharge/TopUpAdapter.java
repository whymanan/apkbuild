package com.vitefinetechapp.vitefinetech.Recharge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;
import java.util.List;

public class TopUpAdapter extends RecyclerView.Adapter<TopUpAdapter.MyHolder> {
    List<Recharge> list;

    public Context mcts;
    ItemClickListener itemClickListener;
    String amount;
    static private RecyclerViewClickListener listener;


    public TopUpAdapter( Context mcts, List<Recharge> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.mcts = mcts;
        this.listener=listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_top_up_adapter,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder,int position) {

        Recharge productList = list.get(position);
        Log.d("onBind-topUp: ","onBind-topUp: ");

        holder.tv_talktym.setText("Talktime:"+" "+productList.getTv_talktym());
        holder.tv_details.setText(productList.getTv_details());
        holder.tv_amount.setText("Amount:"+" "+productList.getTv_amount());
        holder.tv_validity.setText("Validity:"+" "+productList.getTv_validity());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("hhhh", String.valueOf(position));
//                amount =  holder.tv_amount.getText().toString().replace("Amount:","");
//                itemClickListener.OnItemClick(position,amount);
//            }
//        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_talktym,tv_details,tv_amount,tv_validity;

        public MyHolder(View itemView) {
            super(itemView);

            tv_talktym = itemView.findViewById(R.id.talktime);
            tv_validity = itemView.findViewById(R.id.validity);
            tv_details = itemView.findViewById(R.id.detils);
            tv_amount = itemView.findViewById(R.id.amount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }

//    public void setOnItemClickListener(ItemClickListener itemClickListener){
//        this.itemClickListener = itemClickListener;
//    }


    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }

}