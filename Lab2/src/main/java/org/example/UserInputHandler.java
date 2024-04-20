package org.example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInputHandler implements Runnable {
    int zakonczono = 0;
    private final TaskQueue taskQueue;
    private final ResultCollector resultCollector;
    private final List<Thread> threads;

    private int numThreads;
    public UserInputHandler(TaskQueue taskQueue, ResultCollector resultCollector,List<Thread> threads,int numThreads) {
        this.taskQueue = taskQueue;
        this.resultCollector = resultCollector;
        this.threads = threads;
        this.numThreads = numThreads;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Wprowadź nowe zadanie (lub wpisz 'exit' aby zakończyć):");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            try {
                long numberToCheck = Long.parseLong(input);
                taskQueue.addTask(new Task(numberToCheck));
                System.out.println("Dodano zadanie: " + numberToCheck);
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowy format liczby, spróbuj ponownie.");
            }
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }
        while( resultCollector.zakonczono != numThreads){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        List<Wynik> results = resultCollector.getResults();
        System.out.println("Wyniki obliczeń:");
        PrintWriter zapis;
        try {
            zapis = new PrintWriter("D:\\193113\\untitled\\src\\main\\java\\org\\example\\wynik.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Wynik result : results) {
            zapis.println(result.liczba+" "+result.results);
            System.out.println(result.liczba+" "+result.results);
        }
        zapis.close();
    }
}