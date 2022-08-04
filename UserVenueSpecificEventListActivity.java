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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserVenueSpecificEventListActivity extends AppCompatActivity {
    SharedPreferences sp;
    String username;
    String venueName;

    ArrayList<eventModel> list;

    boolean added;

    RecyclerView recyclerView;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_venue_specific_event_list);

        // Initialize shared preference
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        // Retrieve data from intent into shared preference
        if (getIntent().hasExtra("username")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", getIntent().getStringExtra("username"));
            editor.putString("venueName", getIntent().getStringExtra("venueName"));
            editor.commit();
        }

        // Retrieve data from shared preference into appropriate variables
        username = sp.getString("username", null);
        venueName = sp.getString("venueName", null);

        Log.d("CREATION", username);
        Log.d("CREATION", venueName);


        list = new ArrayList<eventModel>();
        recyclerView = findViewById(R.id.user_venue_specific_event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        VenueSpecificEventAdapter adapter = new VenueSpecificEventAdapter(this, list, username);
        recyclerView.setAdapter(adapter);

        dbref = FirebaseDatabase.getInstance().getReference().child("Venues");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String n = data.child("venueName").getValue().toString();
                    if (n.compareTo(venueName) == 0) {
                        HashMap<String, HashMap<String, String>> temp = (HashMap<String, HashMap<String, String>>) data.child("venueEvents").getValue();
                        for (HashMap<String, String> itr : temp.values()) {
                            list.add(new eventModel(itr.get("name"), itr.get("date"), itr.get("venue"),
                                    String.valueOf(itr.get("noParticipants")), itr.get("time")));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        int count = getIntent().getIntExtra("no_events", 0);
//
//        username = getIntent().getStringExtra("username");
//
//        // Get Event details pertaining to the current venue
//        String [] eventTitle = new String[count];
//        String [] venueName = new String[count];
//        String [] eventDate = new String[count];
//        boolean [] spaceAvailability = new boolean[count];
//        int [] noParticipants = new int[count];
//        String [] eventTime = new String[count];
//
//        for(int i = 0; i < count; i++){
//            eventTitle[i] = getIntent().getStringExtra("eventTitle" + i);
//            venueName[i] = getIntent().getStringExtra("venueName" + i);
//            eventDate[i] = getIntent().getStringExtra("eventDate" + i);
//            spaceAvailability[i] = getIntent().getBooleanExtra("spaceAvailability" + i, true);
//            noParticipants[i] = getIntent().getIntExtra("noParticipants" + i, 0);
//            eventTime[i] = getIntent().getStringExtra("eventTime" + i);
//        }
//
//        listView = findViewById(R.id.user_venue_event_rv_id);
//        MyAdapter adapter = new MyAdapter(username, this, eventTitle, venueName, eventDate,
//                spaceAvailability, eventTime, noParticipants);
//        listView.setAdapter(adapter);
    }
}

//    class MyAdapter extends ArrayAdapter<String> {
//        Context context;
//        String [] rEventTitle; String [] rVenueName; String [] rEventDate;
//        boolean [] rSpaceAvailability; int [] rNoParticipants; String [] rEventTime;
//        String username;
//        String key;
//        MyAdapter(String name, Context c, String[] title, String[] venue, String[] date,
//                  boolean[] space, String[] time, int[] count){
//            super(c, R.layout.event_node, R.id.eventTitleid, title);
//            this.rEventTitle = title;
//            this.rVenueName = venue;
//            this.rEventDate = date;
//            this.rSpaceAvailability = space;
//            this.rEventTime = time;
//            this.rNoParticipants = count;
//            this.username = name;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
//            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService
//                    (Context.LAYOUT_INFLATER_SERVICE);
//            View eventNode = layoutInflater.inflate(R.layout.join_event_node, parent, false);
//            TextView title = eventNode.findViewById(R.id.joinEventTitleid);
//            TextView venue = eventNode.findViewById(R.id.joinEventVenueid);
//            TextView date = eventNode.findViewById(R.id.joinEventDateid);
//            TextView time = eventNode.findViewById(R.id.joinEventTimeid);
//            TextView space = eventNode.findViewById(R.id.joinEventSpaceid);
//            TextView count = eventNode.findViewById(R.id.joinEventCountid);
//            Button join = eventNode.findViewById(R.id.joinEventButtonid);
//
//            // Need to set resources on views
//            title.setText(rEventTitle[position]);
//            venue.setText(rVenueName[position]);
//            date.setText("Date: " + rEventDate[position]);
//            time.setText("Time: " + rEventTime[position]);
//
//
//            if(rSpaceAvailability[position] == true){
//                space.setText("Space Available");
//            }
//            else{
//                space.setText("Space Available");
//            }
//            count.setText("Participants: " + rNoParticipants[position]);
//
//            join.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    eventModel temp = new eventModel(rEventTitle[position], rEventDate[position], rVenueName[position],
//                            String.valueOf(10), rEventTime[position]);
//                    dbref = FirebaseDatabase.getInstance().getReference().child("user");
//                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            // Got the user node
//                            for (DataSnapshot snap : snapshot.getChildren()){
//                                String n = snap.child("username").getValue().toString();
//                                if (n.compareTo(username) == 0){
//                                    key = snap.getKey();
//                                }
//                            }
//
//
//                            // Check if the event is already in the user node
//                            // To do this, iterate through the user's events
//                            DataBaseClass checker = new DataBaseClass("user/" + key + "/userEventsJoined");
//                            checker.dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    added = false;
//                                    for (DataSnapshot snap : snapshot.getChildren()){
//                                        String name = snap.child("name").getValue().toString();
//                                        String date = snap.child("date").getValue().toString();
//                                        String venue = snap.child("venue").getValue().toString();
//                                        String time = snap.child("time").getValue().toString();
//
//                                        eventModel m = new eventModel(name, date, venue, String.valueOf(10), time);
//                                        if (m.equals(temp) == true){
//                                            added = true;
//                                            break;
//                                        }
//                                    }
//
//                                    // Now add the event
//                                    if (added == false) {
//                                        DataBaseClass dat = new DataBaseClass("user/" + key + "/userEventsJoined");
//                                        dat.add(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(getApplicationContext(), "Event joined successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                    else{
//                                        Toast.makeText(getApplicationContext(), "Event already joined", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {}
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {}
//                    });
//                }
//            });
//            return eventNode;
//        }
//    }
//
//    public void openUserVenueListActivity(String name) {
//        Intent intent = new Intent(this, UserVenueListActivity.class);
//        intent.putExtra("username", name);
//        startActivity(intent);
//    }
//}