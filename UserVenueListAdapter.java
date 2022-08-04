package com.example.b07_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserVenueListAdapter extends RecyclerView.Adapter<UserVenueListAdapter.UVViewHolder> {
    Context context;
    ArrayList<venueModel> venueList = new ArrayList<>();
    String username;

    public UserVenueListAdapter(Context context, ArrayList<venueModel> venueList, String username) {
        this.context = context;
        this.venueList = venueList;
        this.username = username;
    }

    @NonNull
    @Override
    public UserVenueListAdapter.UVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout here
        // This is how we give look to our rows
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.venue_row_recycler_view, parent, false);
        return new UserVenueListAdapter.UVViewHolder(view, username, venueList);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVenueListAdapter.UVViewHolder holder, int position) {
        // Assign values to each row depending on the position
        holder.title.setText(venueList.get(position).getVenueName());
        holder.location.setText(venueList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        // Get total number of items
        return venueList.size();
    }

    public static class UVViewHolder extends RecyclerView.ViewHolder{
        // Sort of like the onCreate method
        // Assign data from row to certain variables
        TextView title;
        TextView location;
        Button schedule;
        Button join;

        public UVViewHolder(@NonNull View itemView, String username, ArrayList<venueModel> venueList) {
            super(itemView);
            title = itemView.findViewById(R.id.venueNodeTitleid);
            location = itemView.findViewById(R.id.venueNodeLocationid);
            schedule = itemView.findViewById(R.id.venueNodeScheduleButtonid);
            join = itemView.findViewById(R.id.venueNodeJoinButtonid);

            // Need to set on click listener here
            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), userScheduleActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("venueName", venueList.get(getAdapterPosition()).getVenueName());
                    view.getContext().startActivity(intent);
                }
            });

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), UserVenueSpecificEventListActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("venueName", venueList.get(getAdapterPosition()).getVenueName());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

