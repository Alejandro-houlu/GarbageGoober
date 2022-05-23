package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/findByListId/{id}")
    public ModelAndView findByListId(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long id){
   
        ModelAndView mav = new ModelAndView("transactionView.html");
        RecyclingList list = recycleSvc.findByListId(id).get();

        mav.addObject("list", list);

        return mav;


    }

    @PostMapping("/confirmTransaction")
    public ModelAndView confirmTransaction(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam Long listId){

        RecyclingList rList = recycleSvc.findByListId(listId).get();
        recycleSvc.confirmTransaction(rList);

        return null;
    }

    @PostMapping("/denyTransaction")
    public ModelAndView denyTransaction(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam Long listId){

        RecyclingList rList = recycleSvc.findByListId(listId).get();
        recycleSvc.denyTransaction(rList);

        return null;
    }

    @GetMapping("/discard/history")
    public ModelAndView viewDiscardHistory(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("history.html");
        User user =  userDetails.getUser();

        List<RecyclingList> lists = recycleSvc.getDiscardHistory(user);
        mav.addObject("lists", lists);
        mav.addObject("title", "Recycling History");
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/collection/history")
    public ModelAndView viewCollectionHistory(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("history.html");
        User user =  userDetails.getUser();

        List<RecyclingList> lists = recycleSvc.getCollectionHistory(user);
        mav.addObject("lists", lists);
        mav.addObject("title", "Colletion History");
        mav.addObject("user", user);

        return mav;
    }

    
    
}
