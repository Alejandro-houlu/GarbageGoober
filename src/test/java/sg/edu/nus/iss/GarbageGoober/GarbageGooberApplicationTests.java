package sg.edu.nus.iss.GarbageGoober;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.DistanceMatrix;
import sg.edu.nus.iss.GarbageGoober.Models.RecyclingList;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.UserRepository;
import sg.edu.nus.iss.GarbageGoober.Services.LocationInterface;
import sg.edu.nus.iss.GarbageGoober.Services.RecycleInterface;
import sg.edu.nus.iss.GarbageGoober.Services.UserInterface;

@SpringBootTest
@AutoConfigureMockMvc
class GarbageGooberApplicationTests {

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserInterface userSvc;

	@Autowired
	RecycleInterface recycleSvc;

	@Autowired
	LocationInterface locationSvc;
	
	@Autowired
	private MockMvc mvc;



	@Test
	void contextLoads() {
	}

	@Test
	void addUserShouldPass(){

		Optional<User> user1 = userRepo.findById(1L);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user1.get().getUsername());

		assertTrue(user1.get().getUsername().equals("Alejandro"));

	}

	@Test
	void sameUserCreationNotAllowed(){
		User user = new User();
		user.setEmail("alejandrostraightedge@gmail.com");

		String s = userSvc.validateUserEmail(user);
		System.out.println(s);

		assertTrue(s.equals("Sorry, this user already exists"));
	}

	@Test
	void findAllListsByUserShouldPass(){

		Optional<User> user = userRepo.findById(1L);
		List<RecyclingList> rList = recycleSvc.findAllByRecycler(user.get());
		System.out.println(rList.size());

		assertTrue(rList.size()==6);
	}

	@Test
	void findByListIdShouldPass(){

		Optional<RecyclingList> rList = recycleSvc.findByListId(2L);

		assertFalse(rList.get() == null);
	}

	@Test
	void incomingReqShouldHaveRequestedStatus(){


		Optional<User> user = userRepo.findById(1L);
		List<RecyclingList> rList = recycleSvc.getIncomingReq(user.get());

		rList.stream().forEach(x->{
			System.out.println(x.getStatus());
			assertTrue(x.getStatus().toString().equals("REQUESTED"));
		});
	}

	@Test
	void allStatusShouldBeCorrect(){

		User user = userRepo.findById(1L).get();

		List<RecyclingList> list1 = recycleSvc.getDiscardList(user);
		List<RecyclingList> list2 = recycleSvc.getCollectionList(user);
		List<RecyclingList> list3 = recycleSvc.getDiscardHistory(user);
		List<RecyclingList> list4 = recycleSvc.getCollectionHistory(user);

		list1.stream().forEach(x->{
			assertTrue(x.getStatus().toString().equals("IN_PROGRESS"));
		});
		list2.stream().forEach(x->{
			assertTrue(x.getStatus().toString().equals("IN_PROGRESS"));
		});
		list3.stream().forEach(x->{
			assertTrue(x.getStatus().toString().equals("TAKEN"));
		});
		list4.stream().forEach(x->{
			assertTrue(x.getStatus().toString().equals("TAKEN"));
		});
	}

	@Test
	void addressShouldBeInvalid(){
		Optional<Address> opt = locationSvc.getAddress("Singapore", 000000);

		assertTrue(opt.isEmpty());
	}

	@Test
	void getMapUrl(){
		String s = locationSvc.getMapUrl();

		assertFalse(s.isEmpty());
	}

	@Test
	void retrieveDistanceMatrixShouldPass(){

		User user = userRepo.findById(1L).get();
		List<Address> addList =locationSvc.findClosestLists(user.getAddress(), 15);

		assertTrue(addList.size() > 1);

		List<DistanceMatrix> dmList = locationSvc.getDistanceMatrix(addList, user.getAddress());
		dmList.stream().forEach(System.out::println);

		assertTrue(dmList.size() > 1);

	}
	@Test
	void getAddByUserShouldPass(){

		User user = userRepo.findById(1L).get();

		Address add = locationSvc.findByUser(user);

		System.out.println(add.getPostalCode());
		assertTrue(add.getPostalCode() == 598742);
	}

	@Test
	void loginInShouldPass() throws Exception{

		this.mvc.perform(get("/home/login")).andDo(print()).andExpect(status().isOk());

	}

	@Test
	void viewSignUpForm() throws Exception{

		this.mvc.perform(get("/home/signUp")).andDo(print()).andExpect(status().isOk());
	}


	@Test
	void ajaxCallsShouldPass() throws Exception{
		this.mvc.perform(get("/request/findListById/2")).andExpect(status().isOk());
		this.mvc.perform(get("/transaction/findByListId/2")).andExpect(status().isOk());
		this.mvc.perform(get("/recycle/findByListId/2")).andExpect(status().isOk());
		this.mvc.perform(get("/collect/findListByUserId/1")).andExpect(status().isOk());

	}


}
