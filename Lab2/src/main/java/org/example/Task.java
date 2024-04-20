package org.example;

public class Task {
    private final long numberToCheck;

    public Task(long numberToCheck) {
        this.numberToCheck = numberToCheck;
    }

    public long getNumberToCheck() {
        return numberToCheck;
    }
}