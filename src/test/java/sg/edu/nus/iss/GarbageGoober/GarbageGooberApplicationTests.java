package sg.edu.nus.iss.GarbageGoober;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.UserRepository;

@SpringBootTest
class GarbageGooberApplicationTests {

	@Autowired
	UserRepository userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	void addUserShouldPass(){

		Optional<User> user1 = userRepo.findById(1L);

		if(user1.isEmpty()){
		User user = new User();
		user.setUsername("Alejandro");

		userRepo.save(user);
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + user1.get().getUsername());

		assertTrue(user1.get().getUsername().equals("Alejandro"));

	}

}
