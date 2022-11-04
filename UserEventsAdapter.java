package com.example.testdisplayevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserEventsAdapter extends RecyclerView.Adapter<UserEventsAdapter.UserEventsViewholder>{

    Context context;
    ArrayList<UserEvents> list;

    public UserEventsAdapter(Context context, ArrayList<UserEvents> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserEventsAdapter.UserEventsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_events, parent, false);
        return new UserEventsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEventsAdapter.UserEventsViewholder holder, int position) {
        UserEvents user = list.get(position);
        holder.sport.setText(user.getSport());
        holder.players.setText(String.valueOf(user.getPlayers()));
        holder.start.setText(user.getStart());
        holder.end.setText(user.getEnd());
        holder.venue.setText(user.getVenue());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserEventsViewholder extends RecyclerView.ViewHolder {
        TextView sport, players, start, end, venue;
        public UserEventsViewholder(@NonNull View itemView)
        {
            super(itemView);

            sport = itemView.findViewById(R.id.sport);
            players = itemView.findViewById(R.id.players);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            venue = itemView.findViewById(R.id.venue);
        }
    }
}