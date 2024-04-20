package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany(mappedBy = "tower", cascade = CascadeType.REMOVE)
    private List<Mage> mages;

    public Tower() {
    }

    public Tower(String name, int height) {
        this.name = name;
        this.height = height;
        this.mages = new ArrayList<>();;
    }

    /*@PreRemove
    private void setnull(){
        for(Mage m : mages){
            m.setTower(null);
        }
    }
    */

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }
}

