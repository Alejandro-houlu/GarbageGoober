package sg.edu.nus.iss.GarbageGoober.Services;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.GarbageGoober.Models.Item;
import sg.edu.nus.iss.GarbageGoober.Models.Materials;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.Status;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.ItemRepository;
import sg.edu.nus.iss.GarbageGoober.Repositories.RecyclingListRepository;

@Service
public class RecycleImplementation implements RecycleInterface{

    @Autowired
    RecyclingListRepository recycleRepo;

    @Autowired
    ItemRepository itemRepo;

    @Override
    public RecyclingList createListFromPayLoad(User user, MultiValueMap<String, String> payload) {
        
    RecyclingList rlist = new RecyclingList();
    List<Item> items = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Long now = System.currentTimeMillis();
    Timestamp timestamp = new Timestamp(now);

    //Set list
    rlist.setPickUpDate(Date.valueOf(payload.getFirst("date")));
    rlist.setCollectionTime(Time.valueOf((payload.getFirst("time"))+":00"));
    rlist.setRemarks(payload.getFirst("remarks"));
    rlist.setAddress(user.getAddress());
    rlist.setCreated(Timestamp.valueOf(sdf.format(timestamp)));
    rlist.setStatus(Status.AVAILABLE);
    rlist.setRecycler(user);

    //Handle items
    Integer noOfItems = (payload.size() - 3) / 3;
    System.out.println(">>>>>>>>> No of Items: " + noOfItems);

    for(int i = 0; i < noOfItems; i++){
        Item item = new Item();
        item.setList(rlist);
        item.setMaterial(Materials.valueOf(payload.getFirst("material[" + i + "]")));
        item.setWeight(Float.valueOf(payload.getFirst("weight[" + i + "]")));
        item.setRemarks(payload.getFirst("description[" + i + "]"));
        items.add(item);
    }
    rlist.setItems(items);

        return rlist;
    }

    @Override
    @Transactional
    public Boolean saveList(RecyclingList rlist) {

        try {
            recycleRepo.save(rlist);
            rlist.getItems().stream().forEach(x->itemRepo.save(x));     
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public List<RecyclingList> findAllByRecycler(User user) {
        
        return recycleRepo.findAllByRecycler(user);
        
    }

    @Override
    public Optional<RecyclingList> findByListId(Long id) {
        
        return recycleRepo.findById(id);
        
        
    }

    @Override
    @Transactional
    public void saveCollectionReq(User user, RecyclingList rList) {

        rList.setStatus(Status.REQUESTED);
        rList.setCollector(user);

        try {
            recycleRepo.save(rList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
       
        
    }

    @Override
    public List<RecyclingList> getIncomingReq(User user) {

        List<RecyclingList> resultList = new ArrayList<>();

        List<RecyclingList> tempList = recycleRepo.findAllByRecycler(user);

        tempList.stream().filter(x-> x.getStatus() == Status.REQUESTED).forEach(x->resultList.add(x));


        return resultList;
    }

    @Override
    public List<RecyclingList> getOutgoingReq(User user) {

        List<RecyclingList> resultList = new ArrayList<>();

        List<RecyclingList> tempList = recycleRepo.findAllByCollector(user);

        tempList.stream().filter(x->x.getStatus() == Status.REQUESTED).forEach(x->resultList.add(x));

        return resultList;
    }

    @Override
    public void confirmRequest(RecyclingList rlist) {
       
        rlist.setStatus(Status.IN_PROGRESS);
        recycleRepo.save(rlist);
        
    }

    @Override
    public void rejectRequest(RecyclingList rlist) {

        rlist.setStatus(Status.AVAILABLE);
        rlist.setCollector(null);
        recycleRepo.save(rlist);
        
    }

    @Override
    public List<RecyclingList> getDiscardList(User user) {
        
        return recycleRepo.findAllByRecyclerAndStatus(user, Status.IN_PROGRESS);
    }

    @Override
    public List<RecyclingList> getCollectionList(User user) {
        
        return recycleRepo.findAllByCollectorAndStatus(user, Status.IN_PROGRESS);
    }

    @Override
    public void confirmTransaction(RecyclingList rlist) {
       
        rlist.setStatus(Status.TAKEN);
        recycleRepo.save(rlist);
    }

    @Override
    public void denyTransaction(RecyclingList rlist) {
        
        rlist.setStatus(Status.AVAILABLE);
        rlist.setCollector(null);
        recycleRepo.save(rlist);
        
    }

    @Override
    public List<RecyclingList> getDiscardHistory(User user) {
        

        return recycleRepo.findAllByRecyclerAndStatus(user, Status.TAKEN);
    }

    @Override
    public List<RecyclingList> getCollectionHistory(User user) {

        return recycleRepo.findAllByCollectorAndStatus(user, Status.TAKEN);
    }


    
}
