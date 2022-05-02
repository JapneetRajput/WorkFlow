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

public class MyProjects extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterProjects.RecyclerViewClickListener listener;
    AdapterProjects adapterProjects;
    ArrayList<ProjectList> list;
    FirebaseFirestore db;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    ProgressDialog progressDialog;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationViewMyProjects);
        bottomNavigationView.setSelectedItemId(R.id.myProjects);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.allProjects:
                    startActivity(new Intent(MyProjects.this,ProjectActivity.class));
                    finish();
                    break;
                case R.id.myProjects:
                    startActivity(new Intent(MyProjects.this, MyProjects.class));
                    finish();
                    break;
                case R.id.starredProjects:
                    startActivity(new Intent(MyProjects.this,StarredProjects.class));
                    finish();
                    break;
            }
            return true;
        });

        // Recycler view
        recyclerView=findViewById(R.id.projectsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<ProjectList>();
        setOnClickListener();
        adapterProjects = new AdapterProjects(this,list,listener);
        db=FirebaseFirestore.getInstance();

        recyclerView.setAdapter(adapterProjects);

        EventChangeListener();
    }

    private void setOnClickListener() {
        listener=new AdapterProjects.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Dialog dialog = new Dialog(MyProjects.this);
                dialog.setContentView(R.layout.project_crud);
                TextView heading = dialog.findViewById(R.id.projectHeadingCrud);
                TextInputEditText ProjectTitle=dialog.findViewById(R.id.projectTitle);
                TextInputEditText ProjectDescription=dialog.findViewById(R.id.projectDescription);
                Button actionButton=dialog.findViewById(R.id.addProject);
                actionButton.setText("Update Project");
                heading.setText("Update Project");

                Integer count = list.get(position).getCount();
                String Position = (count) + "";
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("Projects").document(Position)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        Boolean isFavourite = documentSnapshot.getBoolean("isFavourite");
                                        String title = ProjectTitle.getText().toString();
                                        String description = ProjectDescription.getText().toString();
                                        String uidProject = documentSnapshot.getString("uid");
                                        String uidDepartment = documentSnapshot.getString("department");
                                        String uidFav = documentSnapshot.getString("uidFav");
//                                Integer count = documentSnapshot.get("count",Integer.class);

                                        Map<String, Object> Projects = new HashMap<>();
//                                        Projects.put("isFavourite", isFavourite);
                                        Projects.put("description", description);
                                        Projects.put("title", title);
                                        Projects.put("uid", uidProject);
                                        Projects.put("uidFav", uidFav);
                                        Projects.put("count", count);
                                        Projects.put("department", uidDepartment);

                                        if (uidProject.equals(uid)){
                                            db.collection("Projects").document(Position).update(Projects).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(MyProjects.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(MyProjects.this, ProjectActivity.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(MyProjects.this, "Update failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        else{
                                            Toast.makeText(MyProjects.this, "You can only edit Projects you created", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MyProjects.this, "Failed to fetch data" + e, Toast.LENGTH_SHORT).show();
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

        db.collection("Projects")
                .whereEqualTo("department","Full Stack Developer")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(MyProjects.this, "Snapshot error", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(MyProjects.this,HomeActivity.class));
        finish();
    }

}