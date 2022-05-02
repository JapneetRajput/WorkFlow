package com.example.workflow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Attendance extends AppCompatActivity {
    String loginTime;
    String logoutTime;
    TextView difference;
    int hrDiff,minDiff,secDiff;
//    FirebaseFirestore db;
//    String absentCount="0",presentCount="0",halfDayCount="0";
//    int AbsentCount=0,PresentCount=0,HalfDayCount=0;
//    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    String initialYear,finalYear,initialMonth,finalMonth,initialDay,finalDay,initialHour,finalHour,initialMinutes,finalMinutes,initialSeconds,finalSeconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().hide();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String officeSSID=wifiInfo.getSSID();
        String officeIP=""+Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        String employeeSSID = wifiInfo.getSSID();
        String employeeIP =""+Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        Button login = findViewById(R.id.login);
        Button logout = findViewById(R.id.logout);
        difference= findViewById(R.id.difference);
//        db=FirebaseFirestore.getInstance();
//        TextView check = (TextView) findViewById(R.id.textView3);
//        check.setText(officeSSID+" "+officeIP+" "+employeeSSID+" "+employeeIP);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (employeeSSID.equals(officeSSID) && employeeIP.equals(officeIP)){
//                    db.collection("Users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            if(documentSnapshot!=null) {
//                                absentCount = documentSnapshot.getString("absentCount");
//                                AbsentCount = Integer.parseInt(absentCount);
//                                presentCount = documentSnapshot.getString("presentCount");
//                                PresentCount = Integer.parseInt(presentCount);
//                                halfDayCount = documentSnapshot.getString("halfDayCount");
//                                HalfDayCount = Integer.parseInt(halfDayCount);
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Attendance.this, "Hi", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    loginTime= new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss").format(new Date());
                    TextView time = (TextView) findViewById(R.id.logintime);
//                    time.setText("Login Time: "+loginTime);
                    initialYear= loginTime.substring(0,4);
                    initialMonth= loginTime.substring(5,7);
                    initialDay=loginTime.substring(8,10);
                    initialHour=loginTime.substring(14,16);
                    initialMinutes=loginTime.substring(17,19);
                    initialSeconds=loginTime.substring(20,22);
                    time.setText("Login Time: "+initialYear+"."+initialMonth+"."+initialDay+" at "+initialHour+":"+initialMinutes+":"+initialSeconds);
                    logout.setVisibility(View.VISIBLE);
                    login.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(Attendance.this, "Please check your Network and try again... ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (employeeSSID.equals(officeSSID) && employeeIP.equals(officeIP)){
                    logoutTime= new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss").format(new Date());
//                    if(timedifference(loginTime,logoutTime)>12){
//                        Toast.makeText(Attendance.this, "As last login session lasted for more than 12 hours Attendance won't we logged.", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
                    TextView time = (TextView) findViewById(R.id.logouttime);
//                    time.setText("Logout Time: " + logoutTime);
                    finalYear= logoutTime.substring(0,4);
                    finalMonth= logoutTime.substring(5,7);
                    finalDay=logoutTime.substring(8,10);
                    finalHour=logoutTime.substring(14,16);
                    finalMinutes=logoutTime.substring(17,19);
                    finalSeconds=logoutTime.substring(20,22);
//                    int hrDiff = logoutTime.charAt(0)*10+logoutTime.charAt(1)-loginTime.charAt(0)*10-loginTime.charAt(1);
//                    int hrMin = logoutTime.charAt(3)*10+logoutTime.charAt(4)-loginTime.charAt(3)*10-loginTime.charAt(4);
//                    difference.setText(hrDiff+ " "+ hrMin);
                    time.setText("Login Time: "+finalYear+"."+finalMonth+"."+finalDay+" at "+finalHour+":"+finalMinutes+":"+finalSeconds);
                    logout.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                    hrDiff = -Integer.parseInt(initialHour) + Integer.parseInt(finalHour);
                    minDiff = -Integer.parseInt(initialMinutes) + Integer.parseInt(finalMinutes);
                    secDiff = -Integer.parseInt(initialSeconds) + Integer.parseInt(finalSeconds);
//                    }
                    if(initialYear.equals(finalYear) || initialMonth.equals(finalMonth) || initialDay.equals(finalDay)){
                        difference.setText(hrDiff+":"+minDiff+":"+secDiff);
                        if(hrDiff<4){
//                            AbsentCount++;
                            Toast.makeText(Attendance.this, "Attendance not marked!", Toast.LENGTH_SHORT).show();
                        }
                        else if(hrDiff>4&&hrDiff<8){
//                            HalfDayCount++;
                            Toast.makeText(Attendance.this, "Attendance partially marked!", Toast.LENGTH_SHORT).show();

                        }
                        else if(hrDiff>16){
//                            AbsentCount++;
                            Toast.makeText(Attendance.this, "Attendance not marked!", Toast.LENGTH_SHORT).show();
                        }
                        else{
//                            PresentCount++;
                            Toast.makeText(Attendance.this, "Attendance marked!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
//                        AbsentCount++;
                        Toast.makeText(Attendance.this, "Attendance not marked!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Attendance.this, "Please check your Network and try again. ", Toast.LENGTH_SHORT).show();
                }
//                Map<String, Object> attt = new HashMap<>();
//                absentCount=AbsentCount+"";
//                presentCount=PresentCount+"";
//                halfDayCount=HalfDayCount+"";
//                attt.put("absentCount",absentCount);
//                attt.put("presentCount",presentCount);
//                attt.put("halfDayCount",halfDayCount);
//                db.collection("Users").document(uid).set(attt).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(Attendance.this, "Updated successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(Attendance.this, Attendance.class));
//                            finish();
//                        } else {
//                            Toast.makeText(Attendance.this, "Update failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Attendance.this,HomeActivity.class));
        finish();
    }
}