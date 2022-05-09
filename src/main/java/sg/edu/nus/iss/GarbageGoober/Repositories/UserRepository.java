package sg.edu.nus.iss.GarbageGoober.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.GarbageGoober.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User getUsersByEmail(String email);
    
}
