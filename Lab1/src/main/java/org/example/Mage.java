package org.example;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Mage implements Comparable<Mage> {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices = new TreeSet<>(Mage.alternativeComparator);


    public void addApprentice(Mage apprentice) {
        apprentices.add(apprentice);
    }

    public Set<Mage> getapprentices(){
        return apprentices;
    }
    Mage(String name,int level,double power){
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = apprentices;
    }

    @Override
    public String toString() {
        return this.name +" "+ this.level +" "+ this.power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, power);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mage mage = (Mage) obj;
        return level == mage.level && Double.compare(mage.power, power) == 0 && mage.name.equals(name);
    }

    @Override
    public int compareTo(Mage o) {
        if(this.name.compareTo(o.name) > 0) {
            return 1;
        }
        if (this.name.compareTo(o.name) == 0){
            if(this.level > o.level){
                return 1;
            }else if(this.level == o.level){
                return Double.compare(this.power, o.power);
            }
            return -1;
        }
        return -1;
    }

    public static Comparator<Mage> alternativeComparator = new Comparator<Mage>() {
        @Override
        public int compare(Mage o1, Mage o2) {
            if(o1.level > o2.level) {
                return 1;
            }
            if (o1.level == o2.level){
                if(o1.compareTo(o2) > 0){
                    return 1;
                }else if(o1.name.compareTo(o2.name) == 0){
                    return Double.compare(o1.power, o2.power);
                }
                return -1;
            }
            return -1;
        }
    };

    public void printApprentices() {
        printApprenticesRecursive(this, 0);
    }

    private void printApprenticesRecursive(Mage mage, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("-");
        }
        System.out.println(indent.toString() + mage);
        for (Mage apprentice : mage.apprentices) {
            printApprenticesRecursive(apprentice, depth + 1);
        }
    }
}
