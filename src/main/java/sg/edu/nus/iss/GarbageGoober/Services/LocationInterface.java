package sg.edu.nus.iss.GarbageGoober.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.DistanceMatrix;
import sg.edu.nus.iss.GarbageGoober.Models.User;

public interface LocationInterface {

    public Optional<Address>getAddress(String country, Integer postalCode);
    public Boolean saveNewAddress(Address address);
    public Address findByUser(User user);
    public List<Address>findClosestLists(Address address, Integer distance);
    public List<DistanceMatrix> getDistanceMatrix(List<Address> closestAddresses, Address userAddress);
    public JsonObject createJson(Address address);
    public String getMapUrl(); 
    
}
