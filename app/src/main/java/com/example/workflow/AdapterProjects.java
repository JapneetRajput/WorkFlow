package com.example.workflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProjects extends RecyclerView.Adapter<AdapterProjects.MyViewHolder> {

    Context context;
    ArrayList<ProjectList> list;
    private static RecyclerViewClickListener listener;

    public AdapterProjects(Context context, ArrayList<ProjectList> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        AdapterProjects.listener =listener;
    }
    public AdapterProjects(Context context, ArrayList<ProjectList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notices_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProjectList ProjectList = list.get(position);
        holder.title.setText(ProjectList.getTitle());
        holder.description.setText(ProjectList.getDescription());
        holder.department.setText(ProjectList.getDepartment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description, title,department;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.NoticeTitle);
            description=itemView.findViewById(R.id.NoticeDesc);
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
