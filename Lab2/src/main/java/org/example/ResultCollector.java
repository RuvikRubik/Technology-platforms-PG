package org.example;

import java.util.ArrayList;
import java.util.List;
public class ResultCollector {
    public int zakonczono = 0;
    private final List<Wynik> results = new ArrayList<>();

    public synchronized void addResult(Wynik result) {
        results.add(result);
    }

    public synchronized List<Wynik> getResults() {
        return new ArrayList<>(results); // Zwracamy kopię listy wyników, aby uniknąć bezpośredniego dostępu do współdzielonej listy
    }
}