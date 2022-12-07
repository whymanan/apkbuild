package com.vitefinetechapp.vitefinetech.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitefinetechapp.vitefinetech.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Viewholder>  {

    private Context context;
    private ArrayList<HistoryModel> historyModelArrayList;

    // Constructor
    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList) {
        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card_view, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        HistoryModel model = historyModelArrayList.get(position);
        holder.txt_course_name.setText(model.getCourse_name());
        holder.txt_person_name.setText("" + model.getPerson_name());
        holder.txt_amount.setText( "Rs."+ model.getAmount());

        holder.history_image.setImageResource(model.getHistory_image());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return historyModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView history_image;
        private TextView txt_person_name, txt_course_name,txt_date,txt_days_ago,txt_transaction_type,txt_amount;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txt_person_name = itemView.findViewById(R.id.txt_cousre_name);
            txt_course_name = itemView.findViewById(R.id.txt_user_name);
            history_image = itemView.findViewById(R.id.img_user);
            txt_amount = itemView.findViewById(R.id.txt_amount);
        }
    }
}
