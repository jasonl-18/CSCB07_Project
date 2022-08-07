package com.example.myloginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference rootuser = db.getReference().child("userTest");
    private DatabaseReference rootadmin = db.getReference().child("adminTest");
    DatabaseReference databaseReference;
    Boolean Confirm;
    ArrayList<String> Emails = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText UserName = findViewById(R.id.Username);
        EditText Email = findViewById(R.id.email);
        EditText Password = findViewById(R.id.password);
        EditText ConfirmPassword = findViewById(R.id.confirmpassword);

        Button user = findViewById(R.id.user);
        Button admin = findViewById(R.id.admin);

        email_adder();

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserNameString = UserName.getText().toString();
                String EmailString = Email.getText().toString();
                String PasswordString = Password.getText().toString();
                String PasswordConfirmString = ConfirmPassword.getText().toString();

                Confirm = Emails.contains(EmailString);

                HashMap<String,String> users = new HashMap<>();

                users.put("Name",UserNameString);
                users.put("Email",EmailString);
                users.put("Password",PasswordString);

                if(Confirm)
                    Toast.makeText(MainActivity2.this, "Account Already Exist", Toast.LENGTH_LONG).show();
                else if(validate(UserNameString,EmailString,PasswordString,PasswordConfirmString)) {
                    rootuser.child(String.valueOf(EmailString.hashCode())).setValue(users);

                    Toast.makeText(MainActivity2.this, "User Account Registered", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserNameString = UserName.getText().toString();
                String EmailString = Email.getText().toString();
                String PasswordString = Password.getText().toString();
                String PasswordConfirmString = ConfirmPassword.getText().toString();

                Confirm = Emails.contains(EmailString);

                HashMap<String,String> admins = new HashMap<>();

                admins.put("Name",UserNameString);
                admins.put("Email",EmailString);
                admins.put("Password",PasswordString);

                if(Confirm)
                    Toast.makeText(MainActivity2.this, "Account Already Exist", Toast.LENGTH_LONG).show();
                else if(validate(UserNameString,EmailString,PasswordString,PasswordConfirmString)) {
                    rootadmin.child(String.valueOf(EmailString.hashCode())).setValue(admins);
                    Toast.makeText(MainActivity2.this, "Admin Account Registered", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
    public boolean validate(String UserNameString,String EmailString,String PasswordString,String PasswordConfirmString) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher matcher = pattern.matcher(EmailString);
        if (UserNameString.equals("") || EmailString.equals("") || PasswordString.equals("") || PasswordConfirmString.equals("")){
            Toast.makeText(MainActivity2.this,"Fill In All Of The Boxes",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(matcher.matches())){
            Toast.makeText(MainActivity2.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!PasswordString.equals(PasswordConfirmString)){
            Toast.makeText(MainActivity2.this,"Password Not Matching",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void email_adder(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("adminTest");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Emails.add(data.child("Email").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("userTest");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Emails.add(data.child("Email").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();

            }
        });
    }
}