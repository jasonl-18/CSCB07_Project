package com.example.b07_project;

import java.util.ArrayList;
import java.util.List;

public class venueModel {

    String venueName;
    String location;

    public venueModel(String name, String loc){
        venueName = name;
        location = loc;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getLocation() {
        return location;
    }
}
