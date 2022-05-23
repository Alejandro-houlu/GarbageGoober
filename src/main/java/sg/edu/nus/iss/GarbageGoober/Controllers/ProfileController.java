package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.Item;
import sg.edu.nus.iss.GarbageGoober.Models.Levels;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    RecycleInterface recycleSvc;

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("dashboard.html");
        User user = userDetails.getUser();
        List<RecyclingList> lists = recycleSvc.findAllByRecycler(user);
        lists = lists.stream().limit(5).collect(Collectors.toList());
        
        mav.addObject("user", user);
        mav.addObject("lists", lists);

        //Dashboard items

        List<RecyclingList> discardList = recycleSvc.getDiscardHistory(user);
        List<RecyclingList> collectionList = recycleSvc.getCollectionHistory(user);

        Integer recycledItems = 0;
        Integer collectedItems = 0;
        Integer transactions = discardList.size() + collectionList.size();
        Float progress = 0f;
        String levelName = "";

        for(RecyclingList r : discardList){
            recycledItems += r.getItems().size();
            for(Item i : r.getItems()){
                progress += i.getWeight();
            }
        }

        for(RecyclingList r : collectionList){
            collectedItems += r.getItems().size();
            for(Item i : r.getItems()){
                progress += i.getWeight();
            }
        }

        if(progress < 100){
            levelName = Levels.NORMIE.getLevelNames();
        }
        else if(progress >= 100 && progress < 200){
            levelName = Levels.INTEMEDIATE.getLevelNames();
        }
        else if(progress >= 200 && progress < 300){
            levelName = Levels.CRAFTSMAN.getLevelNames();
        }




        progress = progress % 100;





        mav.addObject("recycledItems", recycledItems);
        mav.addObject("collectedItems", collectedItems);
        mav.addObject("transactions", transactions);
        mav.addObject("progress", progress);
        mav.addObject("levelName", levelName);

        

        return mav;
    }
    
}
