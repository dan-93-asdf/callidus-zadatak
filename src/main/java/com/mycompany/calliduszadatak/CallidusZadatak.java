


package com.mycompany.calliduszadatak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Danilo
 */
public class CallidusZadatak {
    
    static DataGetter dataGetter = new DataGetter();
    
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    static ArrayList<City> alc;
    static ArrayList<MeetupEvent> alm;
    
    //metoda cityView predstavlja prvi "Screen" - listu gradova koji se mogu odabrati
    public static void cityView() throws IOException {
    
        System.out.println("GRADOVI: ");
        
        alc = new ArrayList<>();
        
        try {
            alc = dataGetter.getCities();
        } catch (Exception ex) {
            Logger.getLogger(CallidusZadatak.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (City alc1 : alc) {            
            System.out.println(alc1.getId() + " " +  alc1.getName());            
        }        
        System.out.println("");
        System.out.println("Unesite redni broj grada ili quit");
        
        String n = reader.readLine();
        
        if("quit".equals(n)) System.exit(0);
        
        eventView(n);               
    }
    
    //metoda eventView predstavlja drugi "screen" - podatke o dogadjajima u izabranom gradu
    public static void eventView(String n) throws IOException {
        
        alm = new ArrayList<>();
        
        try {
            for (City alc1 : alc) {            
                if (alc1.getId().equals(n)) {
                        alm = dataGetter.getEvents(alc1);   
                        System.out.println("GRAD: " + alc1.getName());     
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CallidusZadatak.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        for (MeetupEvent alm1 : alm) {
            System.out.println("");
            System.out.println("NAZIV: " + alm1.getName());
            System.out.println("OPIS: " + alm1.getDescription());
            System.out.println("VREME: " + alm1.getTime());
            System.out.println("MESTO: " + alm1.getVenueName());
            System.out.println("ADRESA: " + alm1.getVenueAddress());
        }
        System.out.println("");
        System.out.println("Pritisnite Enter za povratak");
        
        String m = reader.readLine();        
        if("quit".equals(m)) System.exit(0);

        cityView();    
    }
        
    public static void main(String[] args) {                
        
        try {
            cityView();
        } catch (IOException ex) {
            Logger.getLogger(CallidusZadatak.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
