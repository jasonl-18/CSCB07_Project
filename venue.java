package com.example.b07_project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class venue {
    public String getVenueName() {
        return venueName;
    }

    public String getLocation() {
        return location;
    }

    public String venueName;
    public String location;

    List<eventModel> eventModels;

    public venue(String name, String loc){
        venueName = name;
        location = loc;
        eventModels = new ArrayList<eventModel>();
    }

    public void add_event(eventModel e){
        eventModels.add(e);
    }

    public void delete_event(eventModel e) {
       if (eventModels.contains(e)){
           eventModels.remove(e);
        }
    }

    public eventModel[] getAvailableEvents(){
        int noAvailable = 0;

        for (eventModel itr : eventModels){
            if (itr.spaceAvailable == true){
                noAvailable++;
            }
        }

        eventModel[] temp = new eventModel[noAvailable];
        int counter = 0;

        for (eventModel itr: eventModels){
            if (itr.spaceAvailable == true){
                temp[counter] = itr;
                counter++;
            }
        }
        return temp;
    }

    public eventModel[] toArray(){
        eventModel[] temp = new eventModel[eventModels.size()];
        int counter = 0;
        for (eventModel itr : eventModels){
            temp[counter] = itr;
            counter++;
        }
        return temp;
    }
}
