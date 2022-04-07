package com.example.workflow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class LeaveRequests extends AppCompatActivity {
    String pos,dep;
    RecyclerView recyclerView;
    AdapterProjects.RecyclerViewClickListener listener;
    AdapterProjects adapterProjects;
    ArrayList<ProjectList> list;
    FirebaseFirestore db;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    ProgressDialog progressDialog;
    DatabaseReference noticeCounT= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requests);
        getSupportActionBar().hide();
        Bundle b=getIntent().getExtras();
        pos=b.getString("pos");
        dep=b.getString("dep");
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        // Recycler view
        recyclerView=findViewById(R.id.projectsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<ProjectList>();
//        Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
        adapterProjects = new AdapterProjects(this,list,listener);
        db=FirebaseFirestore.getInstance();
        recyclerView.setAdapter(adapterProjects);


        EventChangeListener();

    }
    private void EventChangeListener() {

        db.collection("Leave")
                .orderBy("count")
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
                                    list.add(dc.getDocument().toObject(ProjectList.class));
                                }
                                adapterProjects.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}