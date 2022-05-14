package sg.edu.nus.iss.GarbageGoober.Models;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String placeId;
    private Integer postalCode;
    private Float lat;
    private Float lng;

    public Long getAddressId() {
        return addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    public Integer getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getPlaceId() {
        return placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Collection<RecyclingList> getRecyclingList() {
        return recyclingList;
    }
    public void setRecyclingList(Collection<RecyclingList> recyclingList) {
        this.recyclingList = recyclingList;
    }



    @OneToOne
    private User user;

    @OneToMany(mappedBy="address")
    private Collection<RecyclingList> recyclingList;

    @Override
    public String toString() {
        return "Address [addressId=" + addressId + ", lat=" + lat + ", lng=" + lng + ", placeId=" + placeId
                + ", postalCode=" + postalCode + ", recyclingList=" + recyclingList + ", user=" + user + "]";
    }
    public static Optional<Address> createModel(String resp){

        Address address = new Address();
        DocumentContext jsonContext = JsonPath.parse(resp);


        try {
        List<Boolean> check = jsonContext.read("$.results.*.partial_match");
        if(check.get(0)){return Optional.empty();}
        } catch (Exception ex) {}

        List<String> placeId = jsonContext.read("$.*.*.place_id");
        List<Number> lat = jsonContext.read("$.*.*.*.*.lat");
        List<Number> lng = jsonContext.read("$.*.*.*.*.lng");

        address.setPlaceId(placeId.get(0));
        address.setLat(lat.get(0).floatValue());
        address.setLng(lng.get(0).floatValue());

        return Optional.of(address);

    }






    
    
}
