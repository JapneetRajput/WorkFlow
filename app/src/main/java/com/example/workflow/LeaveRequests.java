package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class LeaveRequests extends AppCompatActivity {
    String pos,department;
    RecyclerView recyclerView;
    AdapterLeave.RecyclerViewClickListener listener;
    AdapterLeave adapterLeave;
    ArrayList<LeaveList> list;
    FirebaseFirestore db;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    ProgressDialog progressDialog;
    DatabaseReference noticeCounT= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests);
//        DatabaseReference dbRoot= FirebaseDatabase.getInstance().getReference();
//        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        getSupportActionBar().hide();
        noticeCounT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
//                    count = snapshot.child("count").getValue(Integer.class);
                    pos=snapshot.child("Users").child(uid).child("position").getValue(String.class);
                    department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
//                    department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        // Recycler view
        recyclerView=findViewById(R.id.recyclerViewLeaves);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<LeaveList>();
//        Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
        adapterLeave = new AdapterLeave(this,list,listener);
        db=FirebaseFirestore.getInstance();
        recyclerView.setAdapter(adapterLeave);

        EventChangeListener();

    }
    private void EventChangeListener() {

        db.collection("Leave")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(LeaveRequests.this, "Snapshot error", Toast.LENGTH_SHORT).show();
                        } else {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();

                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    list.add(dc.getDocument().toObject(LeaveList.class));
                                }
                                adapterLeave.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}