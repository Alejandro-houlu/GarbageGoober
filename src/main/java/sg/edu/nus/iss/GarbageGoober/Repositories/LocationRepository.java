package sg.edu.nus.iss.GarbageGoober.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.GarbageGoober.Models.Address;

public interface LocationRepository extends JpaRepository<Address,Long> {
    
}
