package sg.edu.nus.iss.GarbageGoober.Controllers;

import java.util.List;

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

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Services.CollectorInterface;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RecycleInterface recycleSvc;

    @Autowired
    CollectorInterface collectSvc;


    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal MyUserDetails userDetails){
        ModelAndView mav = new ModelAndView("requestHome.html");
        User user = userDetails.getUser();
        mav.addObject("user", user);

        List<RecyclingList> incomingReqList = recycleSvc.getIncomingReq(user);
        List<RecyclingList> outgoingReqList = recycleSvc.getOutgoingReq(user);

        mav.addObject("incomingReqList", incomingReqList);
        mav.addObject("outgoingReqList", outgoingReqList);



        return mav;
    }

    @GetMapping("/findListById/{listId}")
    public ModelAndView findListByUserId(@AuthenticationPrincipal MyUserDetails userDetails, @PathVariable Long listId){
        ModelAndView mav = new ModelAndView("requestView.html");

        RecyclingList list = recycleSvc.findByListId(listId).get();

        mav.addObject("list", list);
        mav.setStatus(HttpStatus.OK);

        return mav;
    }

    @PostMapping("/confirmRequest")
    public ModelAndView saveRequest(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam Long listId){
        ModelAndView mav = new ModelAndView();
        User user = userDetails.getUser();
        RecyclingList rList = recycleSvc.findByListId(listId).get();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "THIS IS FROM CONFIRM REQUEST" + listId);

        recycleSvc.confirmRequest(rList);

        mav.setStatus(HttpStatus.OK);

        return null;
    }
    @PostMapping("/rejectRequest")
    public ModelAndView rejectRequest(@AuthenticationPrincipal MyUserDetails userDetails, @RequestParam Long listId){
        ModelAndView mav = new ModelAndView();
        User user = userDetails.getUser();
        RecyclingList rList = recycleSvc.findByListId(listId).get();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "THIS IS FROM REJECT REQUEST" + listId);

        recycleSvc.rejectRequest(rList);

        mav.setStatus(HttpStatus.OK);

        return null;
    }
    
}
