package sg.edu.nus.iss.GarbageGoober.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.GarbageGoober.Config.MyUserDetails;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.getUserByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User does not exist");
        }
        return new MyUserDetails(user);
    }
    
}
