package sg.edu.nus.iss.GarbageGoober.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;

public interface RecycleInterface {

    public RecyclingList createListFromPayLoad(User user, MultiValueMap<String, String> payload);
    public Boolean saveList(RecyclingList rlist);
    public List<RecyclingList> findAllByRecycler(User user);
    public Optional<RecyclingList> findByListId(Long id);

}
