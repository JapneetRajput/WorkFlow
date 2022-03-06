package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
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

public class MyNotices extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterNotices.RecyclerViewClickListener listener;
    AdapterNotices adapterNotices;
    ArrayList<NoticeList> list;
    FirebaseFirestore db;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    ProgressDialog progressDialog;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notices);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationViewMyNotices);
        bottomNavigationView.setSelectedItemId(R.id.myNotices);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.allNotices:
                    startActivity(new Intent(MyNotices.this,NoticeActivity.class));
                    finish();
                    break;
                case R.id.myNotices:
                    startActivity(new Intent(MyNotices.this, MyNotices.class));
                    finish();
                    break;
                case R.id.starred:
                    startActivity(new Intent(MyNotices.this,Starred.class));
                    finish();
                    break;
            }

            return true;
        });

        // Recycler view
        recyclerView=findViewById(R.id.noticesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<NoticeList>();
        setOnClickListener();
        adapterNotices = new AdapterNotices(this,list,listener);
        db=FirebaseFirestore.getInstance();

        recyclerView.setAdapter(adapterNotices);

        EventChangeListener();
    }

    private void setOnClickListener() {
        listener=new AdapterNotices.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Dialog dialog = new Dialog(MyNotices.this);
                dialog.setContentView(R.layout.notice_crud);
                TextView heading = dialog.findViewById(R.id.headingCrud);
                TextInputEditText noticeTitle=dialog.findViewById(R.id.noticeTitle);
                TextInputEditText noticeDescription=dialog.findViewById(R.id.noticeDescription);
                Button actionButton=dialog.findViewById(R.id.addNotice);
                actionButton.setText("Update notice");
                heading.setText("Update Notice");

                Integer count = list.get(position).getCount();
                String Position = (count) + "";
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("Notices").document(Position)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        Boolean isFavourite = documentSnapshot.getBoolean("isFavourite");
                                        String title = noticeTitle.getText().toString();
                                        String description = noticeDescription.getText().toString();
                                        String uidNotice = documentSnapshot.getString("uid");
                                        String uidFav = documentSnapshot.getString("uidFav");
//                                Integer count = documentSnapshot.get("count",Integer.class);

                                        Map<String, Object> notices = new HashMap<>();
//                                        notices.put("isFavourite", isFavourite);
                                        notices.put("description", description);
                                        notices.put("title", title);
                                        notices.put("uid",uidNotice);
                                        notices.put("uidFav",uidFav);
                                        notices.put("count",count);

                                        db.collection("Notices").document(Position).set(notices).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MyNotices.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(MyNotices.this, NoticeActivity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(MyNotices.this, "Update failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MyNotices.this, "Failed to fetch data" + e, Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });

                dialog.show();
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra("title",list.get(position).getTitle());
//                intent.putExtra("description",list.get(position).getDescription());
//                startActivity(intent);
//                layout.addView(v);
            }
        };
    }

    private void EventChangeListener() {

        db.collection("Notices")
                .whereEqualTo("uid",uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(MyNotices.this, "Snapshot error", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            for(DocumentChange dc : value.getDocumentChanges()){

                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();

                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    list.add(dc.getDocument().toObject(NoticeList.class));
                                }
                                adapterNotices.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyNotices.this,HomeActivity.class));
        finish();
    }

}