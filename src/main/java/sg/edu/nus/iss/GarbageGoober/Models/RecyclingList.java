package sg.edu.nus.iss.GarbageGoober.Models;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class RecyclingList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private String remarks;
    private Date pickUpDate;
    private Time collectionTime;
    private Float latitude;
    private Float longtitude;

    @ManyToOne
    private User recycler;

    @OneToMany(mappedBy = "list")
    private Collection<Item> items;

    @Override
    public String toString() {
        return "RecyclingList [address=" + address + ", collectionTime=" + collectionTime + ", items=" + items
                + ", latitude=" + latitude + ", listId=" + listId + ", longtitude=" + longtitude + ", pickUpDate="
                + pickUpDate + ", recycler=" + recycler + ", remarks=" + remarks + ", status=" + status + "]";
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Time getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Time collectionTime) {
        this.collectionTime = collectionTime;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }

    public User getRecycler() {
        return recycler;
    }

    public void setRecycler(User recycler) {
        this.recycler = recycler;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    
    
}
