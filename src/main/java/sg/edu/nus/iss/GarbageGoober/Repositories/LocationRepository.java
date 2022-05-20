package sg.edu.nus.iss.GarbageGoober.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.User;

@Repository
public interface LocationRepository extends JpaRepository<Address,Long> {

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:lat)) * cos(radians(a.lat)) *" +
        " cos(radians(a.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(a.lat))))";

    @Query("SELECT a FROM Address a WHERE " + HAVERSINE_FORMULA + " < :distance ORDER BY " + HAVERSINE_FORMULA + "DESC")
    List<Address> findListsClosest(@Param("lat")Float lat, @Param("lng")Float lng, @Param("distance") double distanceWithinUser);

    public Address findByUser(User user);    
    
}
