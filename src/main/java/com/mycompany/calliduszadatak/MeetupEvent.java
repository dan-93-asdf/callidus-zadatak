
package com.mycompany.calliduszadatak;

/**
 *
 * @author Danilo
 */
public class MeetupEvent {
    
    private String name;
    private String description;
    private String time;                
    private String venueName;
    private String venueAddress;    
    
    public MeetupEvent(String n, String d, String t, String vn, String va) {
        name = n;
        description = d;
        time = t;
        venueName = vn;
        venueAddress = va;
    }
    
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getTime() {
        return time;
    }
    public String getVenueName() {
        return venueName;
    }
    public String getVenueAddress() {
        return venueAddress;
    }
    
    public void setName(String n) {
        name = n;
    }
    public void setDescription(String d) {
        description = d;
    }
    public void setTime(String t) {
        time = t;
    }
    public void setVenueName(String vn) {
        venueName = vn;
    }
    public void setVenueAddress(String va) {
        venueAddress = va;
    }
    
}
