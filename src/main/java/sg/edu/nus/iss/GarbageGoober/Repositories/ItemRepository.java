package sg.edu.nus.iss.GarbageGoober.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.GarbageGoober.Models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
