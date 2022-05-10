package sg.edu.nus.iss.GarbageGoober.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.iss.GarbageGoober.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail (String email);
    public User getUserByEmail(String email);

    
}
