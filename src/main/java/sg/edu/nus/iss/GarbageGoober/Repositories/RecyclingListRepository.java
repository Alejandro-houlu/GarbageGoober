package sg.edu.nus.iss.GarbageGoober.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;

@Repository
public interface RecyclingListRepository extends JpaRepository<RecyclingList, Long> {

    public List<RecyclingList> findAllByRecycler(User user);
    
}
