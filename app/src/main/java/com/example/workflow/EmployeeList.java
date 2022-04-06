package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeList extends AppCompatActivity {

    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Users");
    private AdapterEmployee adapter;
    private ArrayList<EmployeeListModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        getSupportActionBar().hide();

        RecyclerView recyclerView = findViewById(R.id.EmployeeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AdapterEmployee(this ,list );

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    EmployeeListModel model = dataSnapshot.getValue(EmployeeListModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(EmployeeList.this,HomeActivity.class));
        finish();
    }
}