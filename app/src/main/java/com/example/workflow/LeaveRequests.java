package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LeaveRequests extends AppCompatActivity {

    String pos,department;
    RecyclerView recyclerView;
    AdapterLeave.RecyclerViewClickListener listener;
    AdapterLeave adapterLeave;
    ArrayList<LeaveList> list;
    FirebaseFirestore db;
    Integer count;
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
                    EventChangeListener();
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
        setOnClickListener();
//        Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
        adapterLeave = new AdapterLeave(this,list,listener);
        db=FirebaseFirestore.getInstance();
        recyclerView.setAdapter(adapterLeave);
    }
    private void setOnClickListener() {
        listener=new AdapterLeave.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position){
//                Dialog dialog = new Dialog(NoticeActivity.this);
//                dialog.setContentView(R.layout.notice_crud);
//                TextView heading = dialog.findViewById(R.id.headingCrud);
//                TextInputEditText noticeTitle=dialog.findViewById(R.id.noticeTitle);
//                TextInputEditText noticeDescription=dialog.findViewById(R.id.noticeDescription);
//                Button actionButton=dialog.findViewById(R.id.addNotice);
//                actionButton.setText("Update notice");
//                heading.setText(list.get(position).getTitle());
//
//                dialog.show();
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra("title",list.get(position).getTitle());
//                intent.putExtra("description",list.get(position).getDescription());
//                startActivity(intent);
////                layout.addView(v);
//                noticeCounT.child("Users").child(uid).child("starCount").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                            starCount = snapshot.getValue(Integer.class);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                starCount=1;

//                noticeCounT.child("noticeCount").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                            count = snapshot.getValue(Integer.class);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                String Position = (position+1) + "";
//                Toast.makeText(LeaveRequests.this, Position, Toast.LENGTH_SHORT).show();
                count = list.get(position).getCount();
                String Position = (count) + "";
                new AlertDialog.Builder(LeaveRequests.this)
                        .setMessage("Do you wish to approve this request?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("Leave").document(Position)
                                        .update("approved", 1);
                                Toast.makeText(getApplicationContext(), "Leave Approved!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LeaveRequests.this, LeaveRequests.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("Leave").document(Position)
                                        .update("approved", -1);
                                Toast.makeText(getApplicationContext(), "Leave Denied!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LeaveRequests.this, LeaveRequests.class));
                                finish();
                            }
                        })
                        .show();
            }
        };
    }
    private void EventChangeListener() {

        db.collection("Leave")
                .whereEqualTo("department",department)
                .whereEqualTo("approved",0)
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

//db.collection("users")
//        .document("frank")
//        .update({
//        "age": 13,
//        "favorites.color": "Red"
//        });