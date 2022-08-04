package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserVenueListActivity extends AppCompatActivity {
    // Declare layout variables
    RecyclerView recyclerView;

    // Declare shared preference
    SharedPreferences sp;

    // Declare variable to save intent/preferences
    // ArrayList<venue> venues = new ArrayList<venue>();
    ArrayList<venueModel> venueModels = new ArrayList<>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_venue_list);

        // Initialize shared preference
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if(getIntent().hasExtra("username")){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.commit();
        }

        // Retrieve data from shared preference into appropriate variables
        username = sp.getString("username", null);
        recyclerView = findViewById(R.id.user_venue_list_rv_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        UserVenueListAdapter adapter = new UserVenueListAdapter(this,  venueModels, username);
        recyclerView.setAdapter(adapter);

        // Get all venues from the database
        DataBaseClass checker = new DataBaseClass("Venues");
        checker.dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    String name = snap.child("venueName").getValue().toString();
                    String loc = snap.child("location").getValue().toString();
                    venueModels.add(new venueModel(name, loc));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void openVenueSpecificEventListActivity(String username, venue venueInstance){
        Intent intent = new Intent(this, UserVenueSpecificEventListActivity.class);

//        eventModel eventModels[] = venueInstance.toArray();
//
//        for (int i = 0; i < eventModels.length; i++){
//            intent.putExtra("eventTitle" + String.valueOf(i), eventModels[i].name);
//            intent.putExtra("venueName" + String.valueOf(i), eventModels[i].venue);
//            intent.putExtra("eventDate" + String.valueOf(i), eventModels[i].date);
//            intent.putExtra("spaceAvailability" + String.valueOf(i), eventModels[i].spaceAvailable);
//            intent.putExtra("noParticipants" + String.valueOf(i), eventModels[i].noParticipants);
//            intent.putExtra("eventTime" + String.valueOf(i), eventModels[i].time);
//        }

        intent.putExtra("username", username);
        intent.putExtra("venueName", venueInstance.getVenueName());
//        intent.putExtra("no_events", eventModels.length);
        startActivity(intent);
    }
}