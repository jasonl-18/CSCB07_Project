package com.example.testdisplayevents;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUserEventActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    UserEventsAdapter userEventsAdapter;
    ArrayList<UserEvents> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_event);

        recyclerView = findViewById(R.id.recycler1);
        //NEED TO CREATE INTENT SO THAT I CAN GET THE USER WHICH IS LOGGED IN AND DO
        // USER + / + events
        databaseReference = FirebaseDatabase.getInstance().getReference("user1/events");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<UserEvents>();
        userEventsAdapter = new UserEventsAdapter(this, list);
        recyclerView.setAdapter(userEventsAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot shot : snapshot.getChildren()){
                    //String str = shot.getValue(String.class);
                    //Log.d(TAG, "value is: " + str);
                    UserEvents event = shot.getValue(UserEvents.class);
                    list.add(event);
                }

                userEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}