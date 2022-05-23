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
    public void saveCollectionReq (User user, RecyclingList rList);
    public List<RecyclingList> getIncomingReq (User user);
    public List<RecyclingList> getOutgoingReq (User user);
    public void confirmRequest(RecyclingList rlist);
    public void rejectRequest(RecyclingList rlist);
    public List<RecyclingList> getDiscardList(User user);
    public List<RecyclingList> getCollectionList(User user);
    public void confirmTransaction(RecyclingList rlist);
    public void denyTransaction(RecyclingList rlist);
    public List<RecyclingList> getDiscardHistory(User user);
    public List<RecyclingList> getCollectionHistory(User user);

}
