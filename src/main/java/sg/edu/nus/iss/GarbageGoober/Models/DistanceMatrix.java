package sg.edu.nus.iss.GarbageGoober.Models;

import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.springframework.data.geo.Distance;

public class DistanceMatrix {

    private String destination; 
    private String origin;
    private Float distance;
    private String duration;
    private List<RecyclingList> recyclingList;

    

    public List<RecyclingList> getRecyclingList() {
        return recyclingList;
    }
    public void setRecyclingList(List<RecyclingList> recyclingList) {
        this.recyclingList = recyclingList;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public Float getDistance() {
        return distance;
    }
    public void setDistance(Float distance) {
        this.distance = distance;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "DistanceMatrix [destination=" + destination + ", distance=" + distance + ", duration=" + duration
                + ", origin=" + origin + "]";
    }

    public static DistanceMatrix createModel(String resp){

        DistanceMatrix dm = new DistanceMatrix();
        DocumentContext jsonContext = JsonPath.parse(resp);

        List<String> destination = jsonContext.read("$.destination_addresses");
        List<String> origin = jsonContext.read("$.origin_addresses");
        List<String> distance = jsonContext.read("$.*.*.*.*.distance.text");
        List<String> duration = jsonContext.read("$.*.*.*.*.duration.text");

        dm.setDestination(destination.get(0));
        dm.setOrigin(origin.get(0));
        String s = distance.get(0);
        dm.setDistance(Float.valueOf(s.substring(0,s.length() - 2)));
        dm.setDuration(duration.get(0));


        return dm;
    }
    
    
    
}
