package com.vitefinetechapp.vitefinetech.DTH;

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

public class DTH_Adapter extends RecyclerView.Adapter<DTH_Adapter.MyHolder> {

    List<DTH_ModelClass> list = new ArrayList<>();

    public Context mcts;
    ItemClickListener2 itemClickListener2;


    public DTH_Adapter( Context mcts, List<DTH_ModelClass> list) {
        this.list = list;
        this.mcts = mcts;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_d_t_h__adapter,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        final DTH_ModelClass productList = list.get(position);

        holder.tv_operator.setText("Operator:"+" "+productList.getOperator());
        holder.tv_plan.setText("Plan"+" "+productList.getPlanName());
        holder.tv_discription.setText("Discription:"+" "+productList.getDescription());
        holder.tv_amount.setText("Amount:"+" "+productList.getAmount());
        holder.tv_validity.setText("Validity:"+" "+productList.getValidity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hhhh", String.valueOf(position));
                String amount =  holder.tv_amount.getText().toString().replace("Amount:","");
                itemClickListener2.OnItemClick(position,amount);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_operator,tv_plan,tv_discription,tv_amount,tv_validity;

        public MyHolder(View itemView) {
            super(itemView);

            tv_operator     = itemView.findViewById(R.id.Operation_textView_ID);
            tv_plan         = itemView.findViewById(R.id.planName__textView_ID);
            tv_discription  = itemView.findViewById(R.id.Disciption_textView_ID);
            tv_amount       = itemView.findViewById(R.id.Amount_textView_ID);
            tv_validity     = itemView.findViewById(R.id.validity_textView_ID);
        }
    }

    public void setOnItemClickListener2(ItemClickListener2 itemClickListener2){
        this.itemClickListener2 = itemClickListener2;
    }
}