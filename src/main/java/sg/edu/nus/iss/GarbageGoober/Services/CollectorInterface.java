package sg.edu.nus.iss.GarbageGoober.Services;

import java.util.List;

import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;

public interface CollectorInterface {

    List<RecyclingList> findListByUserId(Long userId);
    
}
