package sg.edu.nus.iss.GarbageGoober.Models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String address;
    private String email;
    private String profilePicUrl;
    private String phoneNumber;
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = true;
    }

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "recycler")
    private Collection<RecyclingList> recyclingLists;

    @Override
    public String toString() {
        return "User [address=" + address + ", email=" + email + ", password=" + password + ", phoneNumber="
                + phoneNumber + ", profilePicUrl=" + profilePicUrl + ", recyclingLists=" + recyclingLists + ", role="
                + role + ", userId=" + userId + ", username=" + username + "]";
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Collection<RecyclingList> getRecyclingLists() {
        return recyclingLists;
    }

    public void setRecyclingLists(Collection<RecyclingList> recyclingLists) {
        this.recyclingLists = recyclingLists;
    }

    
    


    
}
