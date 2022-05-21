package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;

@Controller
@RequestMapping("/recycle")
public class RecycleController {

    @Autowired
    RecycleInterface recycleSvc;

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("recycleHome.html");
        User user = userDetails.getUser();
        List<RecyclingList> list = recycleSvc.findAllByRecycler(user);

        //test
        list.stream().forEach(System.out::println);

        mav.addObject("user", user);
        mav.addObject("lists", list);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @GetMapping("/createList")
    public ModelAndView createList(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("recycleForm.html");
        User user = userDetails.getUser();
        mav.addObject("user", user);

        return mav;
    }

    @PostMapping("/saveList")
    public ModelAndView saveList(@AuthenticationPrincipal MyUserDetails userDetails, @RequestBody MultiValueMap<String, String> payload){
        ModelAndView mav = new ModelAndView("recycleHome.html");
        User user = userDetails.getUser();
        mav.addObject("user", user);

        //test
        //Set<String> keys = payload.keySet();
        //keys.stream().forEach(x->System.out.println(payload.get(x)));

        RecyclingList list = recycleSvc.createListFromPayLoad(user, payload);

        //test
        // System.out.println(list);
        // for(Item i : list.getItems()){
        //     System.out.println(i);
        // }

        //save to db
        if(!recycleSvc.saveList(list)){
            mav.setStatus(HttpStatus.BAD_REQUEST);
            throw new IllegalArgumentException("Save list failed");
        }
        List<RecyclingList> rlist = recycleSvc.findAllByRecycler(user);
        mav.addObject("lists",rlist);


        
        return mav;
    }

    @GetMapping("/findByListId/{id}")
    public ModelAndView findByListId(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long id){
    
        ModelAndView mav = new ModelAndView("/Fragments/listView.html");
        RecyclingList list = recycleSvc.findByListId(id).get();

        mav.addObject("list", list);

        return mav;


    }

    @GetMapping("/test/{id}")
    public ModelAndView test(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long id){
    
        //ModelAndView mav = new ModelAndView("/Fragments/listView.html");
        //RecyclingList list = recycleSvc.findByListId(id).get();

        ModelAndView mav = new ModelAndView("test.html");
        mav.addObject("id",id);

        return mav;


    }
    
}
