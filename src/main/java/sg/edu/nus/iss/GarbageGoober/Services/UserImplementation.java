package sg.edu.nus.iss.GarbageGoober.Services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.GarbageGoober.Models.Address;
import sg.edu.nus.iss.GarbageGoober.Models.User;
import sg.edu.nus.iss.GarbageGoober.Repositories.UserRepository;

@Service
public class UserImplementation implements UserInterface{

    @Autowired
    UserRepository userRepo;

    @Autowired
    AmazonS3 s3;

    @Override
    public String validateUserEmail(User user) {

        Optional<User> opt = userRepo.findByEmail(user.getEmail());
        if(!opt.isEmpty()){
            return "Sorry, this user already exists";
        }
        
        return "";
    }


    @Override
    public Boolean saveNewUser(User user) {

        String rawPassword = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        try {
        userRepo.save(user);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public String uploadImageTos3(User user, MultipartFile rawImage) {

        String objId = UUID.randomUUID().toString().substring(0,8);
        String bucketName = "alejandrobucket";
        Map<String, String> userCustomMetadata = new HashMap<>();
        userCustomMetadata.put("uploader",user.getUsername());
        userCustomMetadata.put("role", user.getRole().toString());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(rawImage.getContentType());
        metadata.setContentLength(rawImage.getSize());
        metadata.setUserMetadata(userCustomMetadata);


        try {
            PutObjectRequest putReq = new PutObjectRequest(bucketName,"%s/images/%s".formatted("GG_"+user.getUsername(), objId), 
            rawImage.getInputStream(), metadata);
            putReq.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = s3.getUrl(bucketName, "%s/images/%s".formatted("GG_"+user.getUsername(), objId)).toString();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + url);

        return url;

    }


    
}
