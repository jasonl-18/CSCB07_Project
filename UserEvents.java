package com.example.testdisplayevents;

public class UserEvents {
    // Variable to store data corresponding
    // to firstname keyword in database
    String sport;

    // Variable to store data corresponding
    // to lastname keyword in database
    int players;

    // Variable to store data corresponding
    // to age keyword in database
    String start;

    String end;

    String venue;

    // Mandatory empty constructor
    // for use of FirebaseUI
    public UserEvents() {}

    // Getter and setter method
    public String getSport()
    {
        return sport;
    }
    public void setSport(String Sport)
    {
        this.sport = Sport;
    }
    public int getPlayers()
    {
        return players;
    }
    public void setPlayers(int players)
    {
        this.players = players;
    }
    public String getStart()
    {
        return start;
    }
    public void setStart(String Start)
    {
        this.start = Start;
    }
    public String getEnd()
    {
        return end;
    }
    public void setEnd(String End)
    {
        this.end = End;
    }
    public String getVenue()
    {
        return venue;
    }
    public void setVenue(String Venue)
    {
        this.venue = Venue;
    }
}

