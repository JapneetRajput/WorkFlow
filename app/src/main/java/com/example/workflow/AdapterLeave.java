package com.example.workflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterLeave extends RecyclerView.Adapter<AdapterLeave.MyViewHolder> {

    Context context;
    ArrayList<LeaveList> list;
    private static RecyclerViewClickListener listener;

    public AdapterLeave(Context context, ArrayList<LeaveList> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        AdapterLeave.listener =listener;
    }
    public AdapterLeave(Context context, ArrayList<LeaveList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.leave_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LeaveList leaveList = list.get(position);
        holder.type.setText(leaveList.getType());
        holder.email.setText(leaveList.getEmail());
        holder.to_date.setText(leaveList.getTo_date());
        holder.from_date.setText(leaveList.getFrom_date());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView email, type,to_date,from_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.leavetype);
            email=itemView.findViewById(R.id.Email);
            to_date=itemView.findViewById(R.id.toDate);
            from_date=itemView.findViewById(R.id.fromDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
