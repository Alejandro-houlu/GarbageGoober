package sg.edu.nus.iss.GarbageGoober.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;

@Repository
public interface RecyclingListRepository extends JpaRepository<RecyclingList, Long> {

    public List<RecyclingList> findAllByRecycler(User user);

    public List<RecyclingList> findAllByCollector(User user);

    public List<RecyclingList> findAllByRecyclerAndStatus(User user, Enum status);

    public List<RecyclingList> findAllByCollectorAndStatus(User user, Enum status);


    
}
