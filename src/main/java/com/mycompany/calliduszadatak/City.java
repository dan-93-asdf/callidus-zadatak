
package com.mycompany.calliduszadatak;

/**
 *
 * @author Danilo
 */
public class City {
    
    private String id;
    private String name;
    private String lat;
    private String lon;
    
    public City(String i, String n, String l1, String l2) {
        id = i;
        name = n;
        lat = l1;
        lon = l2;
    }
    
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLat() {
        return lat;
    }
    public String getLon() {
        return lon;
    }
    public void setId(String i) {
        id = i;
    }
    public void setName(String n) {
        name = n;
    }
    public void setLat(String l) {
        lat = l;
    }
    public void setLon(String l) {
        lon = l;
    }
        
        
    
}
