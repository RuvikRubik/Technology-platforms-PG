package org.example;

import java.util.ArrayList;
import java.util.List;

public class Wynik {
    public long liczba;
    public List<Long> results = new ArrayList<>();

    Wynik(long wynik,List<Long> result){
        this.liczba = wynik;
        this.results = result;
    }
}
