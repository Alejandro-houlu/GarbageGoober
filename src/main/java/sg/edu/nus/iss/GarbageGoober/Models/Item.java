package sg.edu.nus.iss.GarbageGoober.Models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Enumerated(EnumType.STRING)
    private Materials material;

    private String remarks;
    private Float weight;

    @ManyToOne
    private RecyclingList list;


    @Override
    public String toString() {
        return "Item [itemId=" + itemId + ", list=" + list + ", material=" + material + ", remarks=" + remarks
                + ", weight=" + weight + "]";
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }


    public Materials getMaterial() {
        return material;
    }

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public RecyclingList getList() {
        return list;
    }

    public void setList(RecyclingList list) {
        this.list = list;
    }

    
    
}
