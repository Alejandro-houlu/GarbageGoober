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


    
}
