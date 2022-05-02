package com.example.workflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNotices extends RecyclerView.Adapter<AdapterNotices.MyViewHolder> {

    Context context;
    ArrayList<NoticeList> list;
    private static RecyclerViewClickListener listener;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }

    public AdapterNotices(Context context, ArrayList<NoticeList> list, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        AdapterNotices.listener =listener;
    }
    public AdapterNotices(Context context, ArrayList<NoticeList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notices_card,parent,false);
        return new MyViewHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticeList noticeList = list.get(position);
        holder.title.setText(noticeList.getTitle());
        holder.description.setText(noticeList.getDescription());
        holder.department.setText(noticeList.getDepartment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description, title,department;
        public MyViewHolder(@NonNull View itemView,onItemClickListener listener) {
            super(itemView);
            title=itemView.findViewById(R.id.NoticeTitle);
            description=itemView.findViewById(R.id.NoticeDesc);
            department=itemView.findViewById(R.id.NoticeDepartment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
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
