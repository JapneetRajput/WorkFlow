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
        holder.type.setText(LeaveList.getType());
        holder.description.setText(LeaveList.getDescription());
        holder.department.setText(LeaveList.getDepartment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description, type,department;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.leavetype);
            description=itemView.findViewById(R.id.Email);
            department=itemView.findViewById(R.id.NoticeDepartment);
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
