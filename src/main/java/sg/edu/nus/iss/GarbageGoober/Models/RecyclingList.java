package sg.edu.nus.iss.GarbageGoober.Models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    private String remarks;
    private Date pickUpDate;
    private Time collectionTime;
    private Timestamp created;


    @ManyToOne
    private User recycler;

    @ManyToOne
    private User collector;

    @OneToMany(mappedBy = "list")
    private Collection<Item> items;

    @ManyToOne
    private Address address;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public User getCollector() {
        return collector;
    }

    public void setCollector(User collector) {
        this.collector = collector;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "RecyclingList [collectionTime=" + collectionTime + ", created=" + created + ", listId=" + listId
                + ", pickUpDate=" + pickUpDate + ", remarks=" + remarks + ", status=" + status + "]";
    }

    

    
    
}
