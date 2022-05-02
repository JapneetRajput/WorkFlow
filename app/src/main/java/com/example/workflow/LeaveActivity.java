package com.example.workflow;
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.NullValue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LeaveActivity extends AppCompatActivity {

    EditText id,toDate,fromDate,leaveDescription,dep;
    Spinner leaveType;
    TextView tv;
    Button apply;
    FirebaseFirestore db;
    String pos,dept;
    DatabaseReference db1= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user;
    int c=0,d=0;
    Integer count;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        Bundle b=getIntent().getExtras();
        pos=b.getString("pos");
        dept=b.getString("dep");
        id=findViewById(R.id.id);
        apply=findViewById(R.id.apply);
        fromDate=findViewById(R.id.fromDate);
        toDate=findViewById(R.id.toDate);
        leaveType=findViewById(R.id.leavetype);
        leaveDescription=findViewById(R.id.leaveDescription);
        dep=findViewById(R.id.department);
        tv=findViewById(R.id.leave2);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String email= user.getEmail();
        id.setText(email);
        dep.setText(dept);
        db=FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

//        fromDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        Leave.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
//                        ,setListener,year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//
//            }
//        });
//
//        setListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                month = month+1;
//                String date = day+"/"+month+"/"+year;
//                fromDate.setText(date);
//;            }
//        };
        String[] positions=getResources().getStringArray(R.array.lType);
        ArrayAdapter array=new ArrayAdapter(this,R.layout.dropdown_items,positions){
            @Override
            public boolean isEnabled(int position) {
                return position!=0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view= super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveType.setAdapter(array);
        final String[] to_date = new String[1];
        final String[] from_date = new String[1];


        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        to_date[0] = day+"/"+month+"/"+year;
                        toDate.setText(to_date[0]);
                    }
                },year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        Calendar calendar1 = Calendar.getInstance();
        final int year1 = calendar1.get(Calendar.YEAR);
        final int month1 = calendar1.get(Calendar.MONTH);
        final int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(
                        LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        from_date[0] = day+"/"+month+"/"+year;
                        fromDate.setText(from_date[0]);
                    }
                },year1,month1,day1);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog1.show();
            }
        });
        db1= FirebaseDatabase.getInstance().getReference();
        db1.child("LeaveCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    count = snapshot.getValue(Integer.class);
//                            department=snapshot.child("Users").child(uid).child("department").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_type=leaveType.getSelectedItem().toString();
                String des = leaveDescription.getText().toString();


                Map<String, Object> leave = new HashMap<>();
                leave.put("email", email);
                leave.put("to_date", to_date[0]);
                leave.put("from_date", from_date[0]);
                leave.put("Type", l_type);
                leave.put("description", des);
                leave.put("department", dept);
                leave.put("approved",0);
                count++;
                leave.put("count",count);
                String Count = count.toString();
                Map<String, Object> LCount = new HashMap<>();
                LCount.put("LeaveCount", count);
                db1.updateChildren(LCount);

                db.collection("Leave").document(Count).set(leave).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Leave Application submitted.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LeaveActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LeaveActivity.this, "Application failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaveActivity.this,HomeActivity.class));
        finish();
    }
}