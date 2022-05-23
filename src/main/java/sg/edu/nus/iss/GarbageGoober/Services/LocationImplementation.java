package sg.edu.nus.iss.GarbageGoober.Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.DistanceMatrix;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.LocationRepository;

@Service
public class LocationImplementation implements LocationInterface{

    private String apiUrl = "https://maps.googleapis.com/maps/api"; 

    @Value("${OPEN_GOOGLE_MAP}")
    String apiKey;

    @Autowired
    LocationRepository locationRepo;

    @Override
    public Optional<Address> getAddress(String country, Integer postalCode) {

        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/geocode/json")
            .queryParam("address", postalCode)
            .queryParam("components", "country:" + country)
            .queryParam("key", apiKey)
            .toUriString();

            RequestEntity<Void> req = RequestEntity.get(url).build();
            RestTemplate rtemplate = new RestTemplate();
            ResponseEntity<String> resp = rtemplate.exchange(req, String.class);

            Optional<Address> opt = Address.createModel(resp.getBody());


        return opt;
    }

    @Override
    public Boolean saveNewAddress(Address address){

        try {
        locationRepo.save(address);
        } catch (Exception ex) {
            return false;
        }
        
        return true;
    }

    @Override
    public List<Address> findClosestLists(Address address, Integer distanceWithinUser) {
    
        List<Address> tempAddList = locationRepo.findListsClosest(address.getLat(), address.getLng(), distanceWithinUser);
        List<Address> resultList = new ArrayList<>();

        tempAddList.stream().filter(a->a.getUser().getUserId() != address.getUser().getUserId()).forEach(a->resultList.add(a));

        return resultList;
    }

    @Override
    public Address findByUser(User user) {
        
        return locationRepo.findByUser(user);
        
    }

    @Override
    public List<DistanceMatrix> getDistanceMatrix(List<Address> closestAddresses, Address userAddress) {
        
        List<DistanceMatrix> distanceMatrixList = new ArrayList<>();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>API KEY" + apiKey);

        for(Address a : closestAddresses){

        String url = UriComponentsBuilder.fromUriString(apiUrl)
            .path("/distancematrix/json")
            .queryParam("destinations","place_id:" + a.getPlaceId() )
            .queryParam("origins", "place_id:" + userAddress.getPlaceId())
            .queryParam("units", "metric")
            .queryParam("key", apiKey)
            .queryParam("mode", "driving")
            .toUriString();

            RequestEntity<Void> req = RequestEntity.get(url).build();
            RestTemplate rtemplate = new RestTemplate();
            ResponseEntity<String> resp = rtemplate.exchange(req, String.class);

            DistanceMatrix dm = DistanceMatrix.createModel(resp.getBody());
            distanceMatrixList.add(dm);

        }

        distanceMatrixList = distanceMatrixList.stream()
            .sorted((d1,d2)->d1.getDistance().compareTo(d2.getDistance())).collect(Collectors.toList());

        return distanceMatrixList;
    }

    @Override
    public JsonObject createJson(Address address) {

            JsonObject body = Json.createObjectBuilder()
            .add("lat", address.getLat())
            .add("lng", address.getLng())
            .build();

        String s = body.toString().replace("\"", "");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + s);


        return body;
    }

    @Override
    public String getMapUrl() {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
        .path("/js")
        .queryParam("key", apiKey)
        .queryParam("callback", "initMap")
        .queryParam("v", "weekly")
        .toUriString();
        return url;
    }
    
}
