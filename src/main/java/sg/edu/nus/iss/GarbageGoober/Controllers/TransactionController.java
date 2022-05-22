package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    RecycleInterface recycleSvc;

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("transactionHome.html");
        User user = userDetails.getUser();
        mav.addObject("user", user);

        List<RecyclingList> discardList = recycleSvc.getDiscardList(user);
        List<RecyclingList> collectionList = recycleSvc.getCollectionList(user);

        //test
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> DISCARD LIST");
        discardList.stream().forEach(System.out::println);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> COLLECTION LIST");
        collectionList.stream().forEach(System.out::println);

        mav.addObject("discardList", discardList);
        mav.addObject("collectionList", collectionList);


        return mav;
    }
    
}
