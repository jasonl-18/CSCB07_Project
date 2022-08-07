package com.example.myloginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceAdmin;


    EditText EmailLogin;
    EditText PasswordLogin;

    Boolean UserExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button makeaccount = findViewById(R.id.makeaccount);
        Button login = findViewById(R.id.login);

        EmailLogin = findViewById(R.id.emaillogin);
        PasswordLogin = findViewById(R.id.passwordlogin);

        makeaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity2();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailString = EmailLogin.getText().toString();
                String PasswordString = PasswordLogin.getText().toString();

                checkuser(EmailString,PasswordString);
            }
        });
    }
    public void openMainActivity2(){
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

    public void openMainActivity3(String UserName){
        Intent intent = new Intent(this,MainActivity3.class);
        intent.putExtra("UserName",UserName);
        startActivity(intent);
    }

    public void checkuser(String EmailString,String PasswordString){
        UserExist = true;
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("userTest").child(String.valueOf(EmailString.hashCode()));
        databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference().child("adminTest").child(String.valueOf(EmailString.hashCode()));

        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    databaseReferenceAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) {
                                Toast.makeText(getApplicationContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
                            } else {
                                if(!PasswordString.equals(snapshot.child("Password").getValue().toString())){
                                    Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                                    openMainActivity3(snapshot.child("Name").getValue().toString());
                                    return;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    if(!PasswordString.equals(snapshot.child("Password").getValue().toString())){
                        Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                        openMainActivity3(snapshot.child("Name").getValue().toString());
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read data", Toast.LENGTH_SHORT).show();

            }
        });

    }

}