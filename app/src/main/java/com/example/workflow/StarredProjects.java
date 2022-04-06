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
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class StarredProjects extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterProjects.RecyclerViewClickListener listener;
    AdapterProjects adapterProjects;
    ArrayList<ProjectList> list;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    ProgressDialog progressDialog;
    DatabaseReference projectCounT= FirebaseDatabase.getInstance().getReference();
    Integer count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_projects);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationViewStarredProjects);
        bottomNavigationView.setSelectedItemId(R.id.starredProjects);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.allProjects:
                    startActivity(new Intent(StarredProjects.this,ProjectActivity.class));
                    finish();
                    break;
                case R.id.myProjects:
                    startActivity(new Intent(StarredProjects.this, MyProjects.class));
                    finish();
                    break;
                case R.id.starredProjects:
                    startActivity(new Intent(StarredProjects.this,StarredProjects.class));
                    finish();
                    break;
            }

            return true;
        });

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        // Recycler view
        recyclerView=findViewById(R.id.starredProjectsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<ProjectList>();
        setOnClickListener();
        adapterProjects = new AdapterProjects(this,list,listener);
        db=FirebaseFirestore.getInstance();
        recyclerView.setAdapter(adapterProjects);

        EventChangeListener();

//        projectCounT.child("projectCount").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    count = snapshot.getValue(Integer.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }


    private void setOnClickListener() {
        listener=new AdapterProjects.RecyclerViewClickListener() {
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

                Integer count = list.get(position).getCount();
                String Position = (count) + "";
                db.collection("Projects").document(Position)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            Boolean isFavourite = documentSnapshot.getBoolean("isFavourite");
                                String title = documentSnapshot.getString("title");
                                String desc = documentSnapshot.getString("description");
                                String department = documentSnapshot.getString("department");
                                String uidNotice = documentSnapshot.getString("uid");
                                String uidFav = documentSnapshot.getString("uidFav");
//                                            Toast.makeText(StarredProjects.this, "Favourite: "+isFavourite, Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(StarredProjects.this)
                                        .setMessage("Do you want to remove this from favourites?")
                                        .setCancelable(true)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Map<String, Object> notices = new HashMap<>();
//                                          notices.put("isFavourite", false);
                                                notices.put("description", desc);
                                                notices.put("uidFav",uidNotice+false);
                                                notices.put("title", title);
                                                notices.put("uid",uidNotice);
                                                notices.put("count",count);
                                                notices.put("department",department);
//                                        position++;
//                                        String counT = count.toString();
//
//                                            Map<String, Object> noticeCount = new HashMap<>();
//                                            noticeCount.put("starCount", starCount);
//                                            noticeCounT.child("Users").child(uid).updateChildren(noticeCount);

                                                db.collection("Projects").document(Position).set(notices).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(StarredProjects.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(StarredProjects.this, StarredProjects.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(StarredProjects.this, "Update failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StarredProjects.this, "Failed to fetch data" + e, Toast.LENGTH_SHORT).show();
                            }
                        });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
        };
    }

    private void EventChangeListener() {

        db.collection("Projects")
            .whereEqualTo("uidFav",uid+true)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null){
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(StarredProjects.this, "Snapshot error", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                list.add(dc.getDocument().toObject(ProjectList.class));
                            }
                            adapterProjects.notifyDataSetChanged();
                        }
                    }
                }
            });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(StarredProjects.this,HomeActivity.class));
        finish();
    }
}