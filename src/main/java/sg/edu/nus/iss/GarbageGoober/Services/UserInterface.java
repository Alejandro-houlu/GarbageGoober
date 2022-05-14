package sg.edu.nus.iss.GarbageGoober.Services;

import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.User;

public interface UserInterface {

    public String validateUserEmail(User user);
    public Boolean saveNewUser(User user);
    public String uploadImageTos3(User user, MultipartFile rawImage);
   
    
}
