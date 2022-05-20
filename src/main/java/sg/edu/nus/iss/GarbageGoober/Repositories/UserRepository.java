package sg.edu.nus.iss.GarbageGoober.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.GarbageGoober.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail (String email);
    public User getUserByEmail(String email);
    public User findByUserId(Long userId);

    
}
