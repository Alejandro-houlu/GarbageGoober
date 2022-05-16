package sg.edu.nus.iss.GarbageGoober.Services;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Repositories.LocationRepository;

@Service
public class LocationImplementation implements LocationInterface{

    private String apiUrl = "https://maps.googleapis.com/maps/api";
    // @Value("$(OPEN_GOOGLE_MAP)")
    String apiKey = "AIzaSyAvWeaEpYdx0CRVg18W348eUT_ZTZl9z6w";

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
    
}
