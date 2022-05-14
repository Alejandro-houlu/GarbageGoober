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
import javax.persistence.OneToOne;

@Entity
public class RecyclingList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private String remarks;
    private Date pickUpDate;
    private Time collectionTime;


    @ManyToOne
    private User recycler;

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

    @Override
    public String toString() {
        return "RecyclingList [address=" + address + ", collectionTime=" + collectionTime + ", items=" + items
                + ", listId=" + listId + ", pickUpDate=" + pickUpDate + ", recycler=" + recycler + ", remarks="
                + remarks + ", status=" + status + "]";
    }

    
    
}
