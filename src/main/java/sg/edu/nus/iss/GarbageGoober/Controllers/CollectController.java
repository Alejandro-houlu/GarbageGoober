package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.DistanceMatrix;
import sg.edu.nus.iss.GarbageGoober.Models.LocationDTO;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.CollectorInterface;
import sg.edu.nus.iss.GarbageGoober.Services.LocationInterface;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;

@Controller
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    RecycleInterface recycleSvc;

    @Autowired
    LocationInterface locationSvc;

    @Autowired
    CollectorInterface collectSvc;



    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("collectHome.html");
        User user = userDetails.getUser();
        List<RecyclingList> list = recycleSvc.findAllByRecycler(user);
        Address address = locationSvc.findByUser(user);
        Integer distance = 11;
        Set<Long>check = new HashSet<>();

        List<Address> closestAddresses = locationSvc.findClosestLists(address, distance);

        Collection<RecyclingList> closestLists = new ArrayList<>();
        closestAddresses.stream().forEach(a-> closestLists.addAll(a.getRecyclingList()));

        List<Address> newClosestAddList = new ArrayList<>();
        closestLists.stream().filter(a->check.add(a.getAddress().getAddressId())).forEach(a -> newClosestAddList.add(a.getAddress()));

        //test
        newClosestAddList.stream().forEach(System.out::println);

        LocationDTO userLocation = new LocationDTO(address.getLat(),address.getLng());
        List<LocationDTO> locations = new ArrayList<>();
        newClosestAddList.stream().forEach(a -> {
            LocationDTO l = new LocationDTO(a.getLat(), a.getLng());
            locations.add(l);
        });



        List<DistanceMatrix> dmList = locationSvc.getDistanceMatrix(newClosestAddList, address);

        //test
        dmList.stream().forEach(System.out::println);

        for(DistanceMatrix d : dmList){
            List<RecyclingList> rlist = new ArrayList<>();
            String s = d.getDestination(); 
            Number pc = Integer.valueOf(s.replaceAll("\\D+",""));
            for(Address a : newClosestAddList){
                if(pc.equals(a.getPostalCode())){
                    rlist.addAll(a.getRecyclingList());
                }
            }
            d.setRecyclingList(rlist);
        }
        //test
        for(DistanceMatrix d : dmList){
            d.getRecyclingList().stream().forEach(System.out::println);
        }

        mav.addObject("user", user);
        mav.addObject("lists", list);
        mav.addObject("dmLists", dmList);
        mav.addObject("userLocation", userLocation);
        mav.addObject("locations", locations);
        mav.setStatus(HttpStatus.OK);



        return mav;
    }

    @GetMapping("/findListByUserId/{userId}")
    public ModelAndView findListByUserId(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long userId){
        ModelAndView mav = new ModelAndView("collectorView.html");

        List<RecyclingList> rlists = collectSvc.findListByUserId(userId);

        rlists.stream().forEach(l -> {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FROM REQUEST CONTROLLER" + l );
        });

        mav.addObject("rlists", rlists);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @PostMapping("/saveRequest")
    public ModelAndView saveRequest(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam Long listId){
        ModelAndView mav = new ModelAndView();
        User user = userDetails.getUser();
        RecyclingList rList = recycleSvc.findByListId(listId).get();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "THIS IS FROM SAVE REQUEST" + listId);

        recycleSvc.saveCollectionReq(user, rList);

        mav.setStatus(HttpStatus.OK);

        return null;
    }


    
}
