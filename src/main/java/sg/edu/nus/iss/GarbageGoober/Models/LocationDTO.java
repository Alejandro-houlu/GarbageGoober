package sg.edu.nus.iss.GarbageGoober.Models;

public class LocationDTO {

    private Float lat;
    private Float lng;
    public Float getLat() {
        return lat;
    }
    public void setLat(Float lat) {
        this.lat = lat;
    }
    public Float getLng() {
        return lng;
    }
    public void setLng(Float lng) {
        this.lng = lng;
    }
    public LocationDTO(Float lat, Float lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public LocationDTO() {
    }
    
    
}
