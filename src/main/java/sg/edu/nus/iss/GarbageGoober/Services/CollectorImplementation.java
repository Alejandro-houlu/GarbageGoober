package sg.edu.nus.iss.GarbageGoober.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.RecyclingListRepository;
import sg.edu.nus.iss.GarbageGoober.Repositories.UserRepository;

@Service
public class CollectorImplementation implements CollectorInterface{

    @Autowired
    RecyclingListRepository recycleRepo;

    @Autowired
    UserRepository userRepo;

    @Override
    public List<RecyclingList> findListByUserId(Long userId) {

        User user = userRepo.findByUserId(userId);

        return recycleRepo.findAllByRecycler(user);
    }
    
}
