package com.example.workflow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class LeaveActivity extends AppCompatActivity {

    EditText id,toDate,fromDate,leaveType,leaveDescription;
    TextView tv;
    Button apply;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        id=findViewById(R.id.id);
        apply=findViewById(R.id.apply);
        fromDate=findViewById(R.id.fromDate);
        toDate=findViewById(R.id.toDate);
        leaveType=findViewById(R.id.leaveType);
        leaveDescription=findViewById(R.id.leaveDescription);
        tv=findViewById(R.id.leave2);
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

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        toDate.setText(date);
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
                        String date = day+"/"+month+"/"+year;
                        fromDate.setText(date);
                    }
                },year1,month1,day1);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog1.show();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDate.setText("");
                fromDate.setText("");
                id.setText("");
                leaveDescription.setText("");
                leaveType.setText("");
                Toast.makeText(getApplicationContext(), "Leave Application submitted.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaveActivity.this,HomeActivity.class));
        finish();
    }
}