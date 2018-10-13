
package com.mycompany.calliduszadatak;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Danilo
 */
public class DataGetter {
    
    String country = "rs";       
    String key = "7645412b2c6444b7574d2f1d5a2d79";
    
    //metoda konstruise API poziv za sve gradove u datoj drzavi
    public ArrayList<City> getCities() throws Exception{      
        
        String path_code = "/2/cities";
        
        URI request = new URIBuilder().setScheme("http").setHost("api.meetup.com").setPath(path_code)
                    .setParameter("country", country)
                    .setParameter("key", key)
                    .build();
        
        HttpGet get = new HttpGet(request);
        
        String result;
        
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity());        
        
        response.close();            
                
        return DecodeJSONCities(result);    
    }  
    
    //metoda konstruise API poziv za sve dogadjaje u datom gradu (Meetup API identifikuje grad geografskom sirinom i duzinom)
    public ArrayList<MeetupEvent> getEvents(City c) throws Exception{  
               
	String path_code = "/find/upcoming_events";	
        
        URI request = new URIBuilder().setScheme("http").setHost("api.meetup.com").setPath(path_code)
                    .setParameter("lat", c.getLat())
                    .setParameter("lon", c.getLon())
                    .setParameter("radius", "10")               //Radius od 10 milja - proizvoljno odabran
                    .setParameter("order", "time")
                    .setParameter("key", key)
                    .build();
        
        HttpGet get = new HttpGet(request);
        
        String result;
        
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity());        
        
        response.close();            
        
        return DecodeJSONEvents(result);    
    }   
    
    //pomocne metode razlazu JSON response u upotrebljivu listu objekata
    public ArrayList<City> DecodeJSONCities(String cities) {
        
        JSONParser parser = new JSONParser();
        
        ArrayList<City> rezultat = new ArrayList<>();
        
        try {            
            JSONObject obj = (JSONObject) parser.parse(cities);            
            JSONArray gradovi = (JSONArray) obj.get("results");   
                        
            Iterator i = gradovi.iterator(); 
            int j = 0;
            while(i.hasNext()){                             //prolazimo kroz sve objekte - rezultate
		JSONObject grad = (JSONObject) i.next();
                j++;
                                             
                String id = String.valueOf(j);              //za svaki kontruisemo novi City objekat...   
                String name = grad.get("city").toString();
                String lat = grad.get("lat").toString();
                String lon = grad.get("lon").toString();          
                
                City c = new City(id,name,lat,lon);
                
                rezultat.add(c);                            //i dodajemo ga u listu - rezultat
            }                       
        } catch (Exception ex) {
            Logger.getLogger(DataGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return rezultat;
    }
    
    public ArrayList<MeetupEvent> DecodeJSONEvents(String events) {
        
        JSONParser parser = new JSONParser();
        
        ArrayList<MeetupEvent> rezultat = new ArrayList<>();
        
        try {            
            JSONObject obj = (JSONObject) parser.parse(events);            
            JSONArray dogadjaji = (JSONArray) obj.get("events");    
                        
            Iterator i = dogadjaji.iterator(); 
            while(i.hasNext()){                                     //slicno, prolazimo kroz JSON zapis svih dogadjaja
		JSONObject dogadjaj = (JSONObject) i.next();
                                
                JSONObject venue = (JSONObject) dogadjaj.get("venue");
                
                String name = dogadjaj.get("name").toString();
                
                String description = "";
                if(dogadjaj.get("description")!=null) description = dogadjaj.get("description").toString();
                String time = dogadjaj.get("local_date").toString() + " " + dogadjaj.get("local_time").toString();  
                
                String venueName = "Mesto nepoznato";               //Za polja koja mozda ne postoje definisemo default vrednosti
                String venueAddress = "";                
                if (venue!=null) {
                    venueName = venue.get("name").toString();
                    venueAddress = venue.get("address_1").toString();       
                }
                                
                MeetupEvent me = new MeetupEvent(name, description, time, venueName, venueAddress);
                
                rezultat.add(me);                                   //...i konstruisemo listu MeetupEvent objekata                
            }                       
        } catch (Exception ex) {
            Logger.getLogger(DataGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return rezultat;
    }
    
    
}
