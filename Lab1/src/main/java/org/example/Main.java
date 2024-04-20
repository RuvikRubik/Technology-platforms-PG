package org.example;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Mage> magowie;

        switch (args[0]){
            case "sort":
                magowie = new TreeSet<>();
                break;
            case "altsort":
                magowie = new TreeSet<>(Mage.alternativeComparator);
                break;
            default:
                magowie = new HashSet<>();
                break;
        }
        Mage mage = new Mage("Mag10",10,7.0);
        Mage mage1 = new Mage("Mag15",16,15.3);
        Mage mage2 = new Mage("Mag0",29,17.0);
        Mage mage3 = new Mage("Mag50",30,10.7);
        Mage mage4 = new Mage("Mag106",13,6.0);
        Mage mage5 = new Mage("Mag30",6,15.0);
        Mage mage6 = new Mage("Mag108",8,50.0);
        Mage mage7 = new Mage("Mag",3,30.0);
        Mage mage8 = new Mage("Mag310",16,60.0);
        Mage mage9 = new Mage("Mag1",104,105.0);
        mage.addApprentice(mage1);
        mage.addApprentice(mage2);
        mage.addApprentice(mage3);
        mage.addApprentice(mage4);
        mage4.addApprentice(mage5);
        mage4.addApprentice(mage6);
        mage6.addApprentice(mage7);
        mage2.addApprentice(mage8);
        mage2.addApprentice(mage9);
        magowie.add(mage);
        //mage.printApprentices();
        for(Mage mag: magowie){
            mag.printApprentices();
        }
    }
}