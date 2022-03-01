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
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticeList noticeList = list.get(position);
        holder.title.setText(noticeList.getTitle());
        holder.description.setText(noticeList.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description, title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.NoticeTitle);
            description=itemView.findViewById(R.id.NoticeDesc);
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
