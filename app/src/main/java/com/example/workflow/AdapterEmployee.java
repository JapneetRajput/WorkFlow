package com.example.workflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterEmployee extends RecyclerView.Adapter<AdapterEmployee.MyViewHolder> {

    ArrayList<EmployeeListModel> mList;
    Context context;

    public AdapterEmployee(Context context , ArrayList<EmployeeListModel> mList){

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.employee_card , parent ,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EmployeeListModel model = mList.get(position);
        holder.name.setText("Name: "+ model.getFirstName()+ " " + model.getLastName());
        holder.position.setText("Position: "+model.getPosition());
        holder.department.setText("Department: "+model.getDepartment());
        holder.email.setText("Email: "+model.getEmail());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name , position,department, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.employeeName);
            department = itemView.findViewById(R.id.employeeDepartment);
            position = itemView.findViewById(R.id.employeePosition);
            email = itemView.findViewById(R.id.employeeEmail);
        }
    }
}