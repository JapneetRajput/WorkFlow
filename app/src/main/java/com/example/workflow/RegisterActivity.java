package com.example.workflow;

import static android.content.ContentValues.TAG;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.textfield.TextInputEditText;
        import com.google.android.material.textfield.TextInputLayout;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
import com.scwang.wave.MultiWaveHeader;

import java.util.zip.Inflater;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText emailET,firstNameET,lastNameET, passwordET1, passwordET2,departmentET;
    Spinner positionET;
    Button registerBTN;

    DatabaseReference usersReference;

    FirebaseUser user;
    private FirebaseAuth auth;

    Window window;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        MultiWaveHeader waveFooter;
        waveFooter = findViewById(R.id.wave_footer);

        waveFooter.setVelocity(1);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setStartColor(Color.parseColor("#E0F1F8"));
        waveFooter.setCloseColor(Color.parseColor("#A569BD"));

        emailET= findViewById(R.id.email);
        firstNameET=findViewById(R.id.firstName);
        lastNameET=findViewById(R.id.lastName);
        departmentET=findViewById(R.id.department);
        passwordET1=findViewById(R.id.password1);
        passwordET2=findViewById(R.id.password2);
        registerBTN=findViewById(R.id.register);
        positionET=findViewById(R.id.position);
        Log.i(TAG, "onCreate: 2");

        String[] positions=getResources().getStringArray(R.array.levels);
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
        positionET.setAdapter(array);

        auth = FirebaseAuth.getInstance();

        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = emailET.getText().toString();
                String txt_lastName = lastNameET.getText().toString();
                String txt_firstName = firstNameET.getText().toString();
                String txt_department = departmentET.getText().toString();
                String txt_password1 = passwordET1.getText().toString();
                String txt_password2 = passwordET2.getText().toString();
                String pos=positionET.getSelectedItem().toString();
                if(txt_lastName.isEmpty()||txt_department.isEmpty()||txt_firstName.isEmpty()||txt_email.isEmpty() || txt_password1.isEmpty() || txt_password2.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password1.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (txt_password1.equals(txt_password2)) {
                        registerUser(txt_firstName, txt_lastName,txt_department,txt_email,txt_password1,pos);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registerUser(String FirstName, String LastName,String Department,String Email, String password, String position) {
        auth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    createDatabaseValues(FirstName,LastName,Department,Email,position);
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createDatabaseValues(String FirstName, String LastName, String Department, String Email,String Position) {
        user = auth.getCurrentUser();
        dataExtract userr = new dataExtract(FirstName,LastName,Department,Email,Position);
        usersReference.child(user.getUid()).setValue(userr);
    }

}