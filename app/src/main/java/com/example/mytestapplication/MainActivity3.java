package com.example.mytestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {
    TextView VenueText;
    TextView EventDate;
    TextView Stattime;
    TextView Endtime;
    TextView Sports;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("venues");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        VenueText = findViewById(R.id.VenueText);
        EventDate = findViewById(R.id.DateText);
        Stattime = findViewById(R.id.StimeText);
        Endtime = findViewById(R.id.EtimeText);
        Sports = findViewById(R.id.SportsText);

        Intent intent = getIntent();

        VenueText.setText(intent.getStringExtra("Venue"));
        EventDate.setText(intent.getStringExtra("Date"));
        Stattime.setText(intent.getStringExtra("Start Time"));
        Endtime.setText(intent.getStringExtra("End Time"));
        Sports.setText(intent.getStringExtra("Sports"));

        Button BackButton = findViewById(R.id.back);
        Button ConfirmButton = findViewById(R.id.confirm);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Location = VenueText.getText().toString();
                String Date = EventDate.getText().toString();
                String Start_Time = Stattime.getText().toString();
                String End_Time = Endtime.getText().toString();
                String Sports_Selected = Sports.getText().toString();

                HashMap<String,String> Venues = new HashMap<>();

                Venues.put("Location",Location);
                Venues.put("Date",Date);
                Venues.put("Start Time",Start_Time);
                Venues.put("End Time",End_Time);
                Venues.put("Sports",Sports_Selected);

                root.push().setValue(Venues);
            }
        });

    }
}