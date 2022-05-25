package sg.edu.nus.iss.GarbageGoober.Models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String email;
    private String profilePicUrl;
    private String phoneNumber;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = true;
    }

    @OneToMany(mappedBy = "recycler")
    private Collection<RecyclingList> recyclingLists;

    @OneToMany(mappedBy = "collector")
    private Collection<RecyclingList> collectionLists;

    @OneToOne(mappedBy = "user")
    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<RecyclingList> getCollectionLists() {
        return collectionLists;
    }

    public void setCollectionLists(Collection<RecyclingList> collectionLists) {
        this.collectionLists = collectionLists;
    }

    @Override
    public String toString() {
        return "User [address=" + address + ", collectionLists=" + collectionLists + ", email=" + email + ", enabled="
                + enabled + ", password=" + password + ", phoneNumber=" + phoneNumber + ", profilePicUrl="
                + profilePicUrl + ", recyclingLists=" + recyclingLists + ", role=" + role + ", userId=" + userId
                + ", username=" + username + "]";
    }



    
    


    
}
