package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {
    Button userPage;
    Button adminPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        userPage = findViewById(R.id.user_register_button);
        adminPage = findViewById(R.id.admin_login_button);

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserLogin();
            }
        });

        adminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminLogin();
            }
        });

    }

    public void openUserLogin(){
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
    public void openAdminLogin(){
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
    }
}