package sg.edu.nus.iss.GarbageGoober.Services;

import java.sql.SQLException;
import java.util.Optional;

import sg.edu.nus.iss.GarbageGoober.Models.Address;

public interface LocationInterface {

    public Optional<Address>getAddress(String country, Integer postalCode);
    public Boolean saveNewAddress(Address address);
    
}
